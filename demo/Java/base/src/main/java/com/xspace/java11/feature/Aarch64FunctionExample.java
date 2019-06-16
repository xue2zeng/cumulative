package com.xspace.java11.feature;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * JEP315: 改进 Aarch64 函数
 *
 * @author xue.zeng
 * @date 2019-06-16 12:44
 */
@Slf4j
public class Aarch64FunctionExample {

  public static void main(String[] args) {
    long startTime = System.nanoTime();
    for (int i = 0; i < 100000000; i++) {
      Math.sin(i);
      Math.cos(i);
      Math.log(i);
    }
    long endTime = System.nanoTime();
    log.info("consuming time: {} ms", TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
  }
}
