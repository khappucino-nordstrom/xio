package com.xjeffrose.xio.tracing;

import static io.netty.handler.codec.http.HttpMethod.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.*;

import brave.Span;
import brave.Tracing;
import brave.context.slf4j.MDCCurrentTraceContext;
import brave.http.HttpTracing;
import brave.propagation.TraceContext;
import brave.sampler.Sampler;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.xjeffrose.xio.SSL.TlsConfig;
import com.xjeffrose.xio.bootstrap.ChannelConfiguration;
import com.xjeffrose.xio.client.ClientConfig;
import com.xjeffrose.xio.fixtures.JulBridge;
import com.xjeffrose.xio.fixtures.OkHttpUnsafe;
import com.xjeffrose.xio.http.Client;
import com.xjeffrose.xio.http.ClientState;
import com.xjeffrose.xio.http.DefaultFullRequest;
import com.xjeffrose.xio.http.DefaultStreamingRequest;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.concurrent.Future;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.logging.*;
import lombok.NonNull;
import lombok.val;
import okhttp3.Protocol;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import zipkin.reporter.Reporter;

// TODO(CK): These brave integration tests are flaky and stall out sometimes
// Turn them back on when they are fixed
public class HttpClientTracingHandlerIntegrationTest { // extends ITHttpClient<XioClient> {
  private class FakeTracer extends XioTracing {
    public FakeTracer(Config config) {
      super(config);
    }

    @Override
    Tracing buildTracing(
      @NonNull String name, @NonNull String zipkinUrl, float samplingRate) {
      if (zipkinUrl.isEmpty() || !(samplingRate > 0f)) {
        return null;
      }
      return Tracing.newBuilder()
        .localServiceName(name)
        .currentTraceContext(MDCCurrentTraceContext.create())
        .reporter(new Reporter<zipkin.Span>() {
          @Override
          public void report(zipkin.Span span) {
            System.out.println("");
          }
        })
        .sampler(Sampler.create(samplingRate))
        .build();
    }
  }

  @BeforeClass
  public static void setupJul() {
    JulBridge.initialize();
  }

  @Rule
  public TestWatcher testWatcher =
    new TestWatcher() {
      @Override
      protected void starting(final Description description) {
        String methodName = description.getMethodName();
        String className = description.getClassName();
        className = className.substring(className.lastIndexOf('.') + 1);
        // System.out.println("Starting JUnit-test: " + className + " " + methodName);
      }
    };

  static Logger disableJavaLogging() {
    Logger logger = Logger.getLogger("okhttp3.mockwebserver.MockWebServer");
    logger.setLevel(Level.WARNING);
    return logger;
  }

  Logger hush = disableJavaLogging();
  CompletableFuture<HttpResponse> local = new CompletableFuture<HttpResponse>();

  public class ApplicationHandler extends SimpleChannelInboundHandler<HttpObject> {

    HttpResponse response;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject object) throws Exception {
      if (object instanceof HttpResponse) {
        response = (HttpResponse) object;
        // System.out.println("Response: " + response);
      }
      if (object instanceof LastHttpContent) {
        local.complete(response);
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      // System.out.println("exceptionCaught: " + cause);
      local.completeExceptionally(cause);
    }
  }

  EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

  private Config config() {
    // TODO(CK): this creates global state across tests we should do something smarter
    System.setProperty("xio.baseClient.remotePort", Integer.toString(server.getPort()));
    System.setProperty("xio.proxyRouteTemplate.proxyPath", "/");
    ConfigFactory.invalidateCaches();
    Config root = ConfigFactory.load();
    return root.getConfig("xio.edgeProxyApplication");
  }

  Client newClient(int port, XioTracing tracing) {
    val channelConfig = ChannelConfiguration.clientConfig(1, "worker");
    val clientConfig = new ClientConfig(ConfigFactory.load().getConfig("xio.baseClient"));
    val clientState = new ClientState(channelConfig, clientConfig);

    val client = new Client(clientState, () -> new ApplicationHandler(), tracing);

    return client;
  }

  MockWebServer server;

  MockResponse buildResponse() {
    return new MockResponse().setBody("hello, world").setSocketPolicy(SocketPolicy.KEEP_OPEN);
  }

  @Before
  public void setUp() throws Exception {
    TlsConfig tlsConfig =
      TlsConfig.fromConfig("xio.h2BackendServer.settings.tls", ConfigFactory.load());
    server = OkHttpUnsafe.getSslMockWebServer(tlsConfig);
    server.setProtocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1));
    server.enqueue(buildResponse());
    server.start();
  }

  @After
  public void tearDown() throws Exception {
    server.close();
  }

  @Test
  public void doStuff() throws Exception {
    val client = newClient(server.getPort(), new FakeTracer(config()));

    val request = DefaultFullRequest.builder()
        .method(GET)
        .body(new EmptyByteBuf(ByteBufAllocator.DEFAULT))
        .path("/v1/authinit")
        //.host("http://127.0.0.1:8080")
        .host("127.0.0.1" + ":" + server.getPort())
        .build();

    val future = client.write(request);
    future.awaitUninterruptibly();
//    RecordedRequest servedRequest = server.takeRequest();
 //   assertEquals("/moocow", servedRequest.getRequestUrl().encodedPath());
  }

  /*
    protected XioClient newClient(int port) {
      // System.out.println("newClient port: " + port);
      HttpTracing httpTracing = null; // TODO(CK): remove this when the tests are fixed
      state = new HttpClientTracingState(httpTracing, false);

      return new XioClientBootstrap(ClientConfig.fromConfig("xio.h1TestClient"), eventLoopGroup)
        .address(new InetSocketAddress("127.0.0.1", port))
        .ssl(false)
        .proto(Protocol.HTTP)
        .handler(new ApplicationHandler())
        .tracingHandler(() -> new HttpClientTracingHandler(state))
        .usePool(false)
        .build();
    }

    protected void closeClient(XioClient client) throws IOException {
      // System.out.println("closeClient client: " + client);
      client.close();
    }

    private XioRequest<HttpRequest> buildRequest(XioClient client, HttpRequest payload) {
      if (!payload.headers().contains(HttpHeaderNames.HOST)) {
        SocketAddress address = client.getBootstrap().config().remoteAddress();
        if (address instanceof InetSocketAddress) {
          InetSocketAddress socketAddress = (InetSocketAddress) address;
          String value = socketAddress.getHostString() + ":" + socketAddress.getPort();
          payload.headers().set(HttpHeaderNames.HOST, value);
        }
      }
      TraceContext parent = state.getTracing().currentTraceContext().get();
      XioRequest<HttpRequest> request = new XioRequest<>(payload, parent);
      return request;
    }

    protected void get(XioClient client, String pathIncludingQuery) throws Exception {
      // System.out.println("get client: " + client + " path: " + pathIncludingQuery);
      HttpRequest payload = new DefaultFullHttpRequest(HTTP_1_1, GET, pathIncludingQuery);
      XioRequest<HttpRequest> request = buildRequest(client, payload);
      Future<Void> future = client.write(request);
      future.awaitUninterruptibly();
      try {
        local.get();
      } catch (Exception e) {
        // System.out.println("caught e: " + e);
      }
    }

    protected void post(XioClient client, String pathIncludingQuery, String body) throws Exception {
      // System.out.println("post client: " + client + " path: " + pathIncludingQuery + " body: " +
      // body);
      ByteBuf content = Unpooled.copiedBuffer(body, UTF_8);
      DefaultFullHttpRequest payload =
        new DefaultFullHttpRequest(HTTP_1_1, POST, pathIncludingQuery, content);
      payload.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
      payload.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
      XioRequest<HttpRequest> request = buildRequest(client, payload);
      client.write(request).sync();
      local.get();
    }

    protected void getAsync(XioClient client, String pathIncludingQuery) throws Exception {
      // System.out.println("getAsync client: " + client + " path: " + pathIncludingQuery);
      HttpRequest payload = new DefaultFullHttpRequest(HTTP_1_1, GET, pathIncludingQuery);
      XioRequest<HttpRequest> request = buildRequest(client, payload);
      client.write(request);
    }
  */
  // @Override
  /*
  @Test(expected = ComparisonFailure.class)
  public void redirect() throws Exception {
    throw new AssumptionViolatedException("client does not support redirect");
  }

  // @Override
  @Test(expected = ComparisonFailure.class)
  public void addsStatusCodeWhenNotOk() throws Exception {
    throw new AssumptionViolatedException("test is flaky");
  }

  // @Override
  @Test(expected = ComparisonFailure.class)
  public void httpPathTagExcludesQueryParams() throws Exception {
    throw new AssumptionViolatedException("test is flaky");
  }
 */
}

