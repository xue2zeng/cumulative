package com.xspace.java12.feature;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * JMH（Java Microbenchmark Harness） 使用方法
 *
 * @author xue.zeng
 * @date 2019-06-16 18:38
 */
@Fork(2)
@Threads(8)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 5)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkExample {

  @Benchmark
  public void testStringAdd() {
    String a = "";
    for (int i = 0; i < 10; i++) {
      a += i;
    }
  }

  @Benchmark
  public void testStringBuilderAdd() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      builder.append(i);
    }
  }

  public static void main(String[] args) throws RunnerException {
    Options options =
        new OptionsBuilder()
            .include(BenchmarkExample.class.getSimpleName())
            // .output("./benckmark.log")
            .build();
    new Runner(options).run();
  }
}
