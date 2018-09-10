package com.xue.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

/**
 * Created by xue.zeng on 8/6/2017.
 */
public class CommandHelloWorld extends HystrixCommand<String> {

  private final String name;

  public CommandHelloWorld(String name) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup1"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("ExampleGroup2")));
    this.name = name;
  }

  @Override
  protected String run() {
    // a real example would do work like a network call here
    return "Hello " + name + "!";
  }
}
