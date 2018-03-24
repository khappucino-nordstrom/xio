package com.xjeffrose.xio.tracing;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XioTracingTest extends Assert {

  private XioTracing subject;
  private Config validParametersConfig;
  private Config invalidMissingParametersConfig;

  @Before
  public void setUp() {
    validParametersConfig = ConfigFactory.load().getConfig("xio.validZipkinParameters");
    invalidMissingParametersConfig = ConfigFactory.load().getConfig("xio.invalidMissingZipkinParameters");
  }

  @Test
  public void testValidParametersConfig() {
    subject = new XioTracing(validParametersConfig);
    assertTrue(subject.newClientHandler(true) != null);
    assertTrue(subject.newClientHandler(false) != null);
    assertTrue(subject.newServerHandler(true) != null);
    assertTrue(subject.newServerHandler(false) != null);
  }

  @Test
  public void testInvalidMissingParametersConfig() {
    subject = new XioTracing(validParametersConfig);
    assertTrue(subject.newClientHandler(true) == null);
    assertTrue(subject.newClientHandler(false) == null);
    assertTrue(subject.newServerHandler(true) == null);
    assertTrue(subject.newServerHandler(false) == null);
  }
}
