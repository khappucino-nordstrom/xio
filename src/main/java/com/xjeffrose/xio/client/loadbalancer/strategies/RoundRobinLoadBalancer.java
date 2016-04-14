package com.xjeffrose.xio.client.loadbalancer.strategies;

import com.google.common.collect.ImmutableList;
import com.xjeffrose.xio.client.loadbalancer.Node;
import com.xjeffrose.xio.client.loadbalancer.Strategy;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer implements Strategy {

  private final AtomicInteger last = new AtomicInteger();

  @Override
  public boolean okToPick(Node node) {
    return true;
  }

  @Override
  public Node getNextNode(ImmutableList<Node> pool) {
    if (pool.isEmpty()) {
      return null;
    }

    int idx = last.getAndIncrement();
    if (idx == pool.size()) {
      last.set(1);
      idx = 0;
    }
    return pool.get(idx);
  }

}
