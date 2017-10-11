package com.xjeffrose.xio.pipeline;

import com.xjeffrose.xio.SSL.XioSecurityHandlerImpl;
import com.xjeffrose.xio.server.XioServerConfig;
import com.xjeffrose.xio.server.XioServerState;
import com.xjeffrose.xio.handler.GentleSslHandler;
import com.xjeffrose.xio.handler.HttpsUpgradeHandler;
import com.xjeffrose.xio.http.CodecPlaceholderHandler;
import com.xjeffrose.xio.http.HttpNegotiationHandler;
import com.xjeffrose.xio.application.ApplicationState;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * SmartHttpPipeline does http well.
 * If configured with a certificate pair (which it should be by default), it will:
 *   - accept incoming TLS and negotiate the application protocol
 *     - supporting http/2 requests if the client can
 *     - supporting http/1 requests as a fallback
 *   - try to upgrade plain text requests with http response code 426
 *   - http/1.1 over cleartext if configured to do so (not the default)
 *
 */
@Slf4j
public class SmartHttpPipeline extends XioServerPipeline {

  private final XioPipelineFragment fragment;

  public SmartHttpPipeline() {
    fragment = null;
  }

  public SmartHttpPipeline(XioPipelineFragment fragment) {
    this.fragment = fragment;
  }

  public SmartHttpPipeline(XioChannelHandlerFactory factory) {
    this.fragment = new XioSimplePipelineFragment(factory);
  }

  public String applicationProtocol() {
    return "ssl-http/1.1";
  }

  public ChannelHandler getCodecNegotiationHandler(XioServerConfig config) {
    if (config.getTls().isUseSsl()) {
      return new HttpNegotiationHandler();
    } else {
      return null;
    }
  }

  public ChannelHandler getCodecHandler(XioServerConfig config) {
    if (config.getTls().isUseSsl()) {
      return CodecPlaceholderHandler.INSTANCE;
    } else {
      return new HttpServerCodec();
    }
  }

  public ChannelHandler getEncryptionHandler(XioServerConfig config, XioServerState state) {
    if (config.getTls().isUseSsl()) {
      return new GentleSslHandler(state.getSslContext(), new HttpsUpgradeHandler());
    } else {
      return null;
    }
  }

  public void buildHandlers(ApplicationState appState, XioServerConfig config, XioServerState state, ChannelPipeline pipeline) {
    super.buildHandlers(appState, config, state, pipeline);
    if (fragment != null) {
      fragment.buildHandlers(appState, config, state, pipeline);
    }
  }
}
