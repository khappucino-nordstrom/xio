package com.xjeffrose.xio.http;

import com.google.common.collect.ImmutableMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.xjeffrose.xio.application.ApplicationConfig;
import com.xjeffrose.xio.application.ApplicationState;
import com.xjeffrose.xio.bootstrap.ClientChannelConfiguration;
import com.xjeffrose.xio.bootstrap.XioServerBootstrap;
import com.xjeffrose.xio.bootstrap.ChannelConfiguration;
import com.xjeffrose.xio.client.ClientConfig;
import com.xjeffrose.xio.core.SocketAddressHelper;
import com.xjeffrose.xio.pipeline.SmartHttpPipeline;
import com.xjeffrose.xio.server.XioServer;
import com.xjeffrose.xio.server.XioServerConfig;
import com.xjeffrose.xio.server.XioServerState;
import com.xjeffrose.xio.tracing.HttpClientTracingHandler;
import com.xjeffrose.xio.tracing.HttpClientTracingState;
import com.xjeffrose.xio.tracing.XioTracing;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;

import java.util.function.Supplier;

import static com.sun.javaws.JnlpxArgs.verify;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.when;

public class ClientTest extends Assert {

  private EmbeddedChannel channel;
  private Client subject;

  boolean handlerWasAdded = true;

  @Mock private ChannelHandler channelHandler;
  @Mock private XioTracing tracing;
  @Mock private HttpClientTracingHandler tracingHandler;

  ChannelHandler appHandler = new ChannelHandler() {
    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
    }
  };


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    channel = new EmbeddedChannel();
  }

  @Test
  public void testDisabledTracing() {
    val channelConfig = ChannelConfiguration.clientConfig(1, "worker");
    val clientConfig = new ClientConfig(ConfigFactory.load().getConfig("xio.invalidMissingZipkinParameters"));
    val clientState = new ClientState(channelConfig, clientConfig);
    handlerWasAdded = false;
    subject = new Client(clientState, () -> {return appHandler;}, null);
    Request request =
      DefaultFullRequest.builder()
        .body(Unpooled.EMPTY_BUFFER)
        .headers(new DefaultHeaders())
        .method(GET)
        .path("/")
        .build();
    val writeFuture = subject.write(request);
    writeFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
      @Override
      public void operationComplete(Future<? super Void> future) throws Exception {
        assertFalse(handlerWasAdded);
      }
    });
  }

  @Test
  public void testEnabledTracing() {

    val channelConfig = ChannelConfiguration.clientConfig(1, "worker");
    val clientConfig = new ClientConfig(ConfigFactory.load().getConfig("xio.validZipkinParameters"));
    val clientState = new ClientState(channelConfig, clientConfig);
    /*HttpClientTracingHandler traceHandler = new HttpClientTracingHandler(clientState) {
      public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        handlerWasAdded = true;
      }
    };*/
    when(tracing.newClientHandler(clientConfig.getTls().isUseSsl())).thenReturn(tracingHandler);

    handlerWasAdded = false;
    subject = new Client(clientState, () -> {return appHandler;}, tracing);
    Request request =
      DefaultFullRequest.builder()
        .body(Unpooled.EMPTY_BUFFER)
        .headers(new DefaultHeaders())
        .method(GET)
        .path("/")
        .build();
    val writeFuture = subject.write(request);
    writeFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
      @Override
      public void operationComplete(Future<? super Void> future) throws Exception {
        ArgumentCaptor<ChannelHandlerContext> captor = ArgumentCaptor.forClass(ChannelHandlerContext.class);
        verify(tracingHandler.handlerAdded(captor.capture()));
      }
    });
  }
}
