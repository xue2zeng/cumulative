package com.xspace.java11.feature;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * JEP309: 动态类文件常量 - CONSTANT_Dynamic 动态语言 API 测试
 *
 * @author xue.zeng
 * @date 2019-06-16 12:22
 */
public class DynamicExample {
  public static void main(String[] args) throws Throwable {
    MethodHandles.Lookup lookup = MethodHandles.lookup();
    MethodHandle methodHandle =
        lookup.findStatic(DynamicExample.class, "test", MethodType.methodType(void.class));
    methodHandle.invokeExact();
  }

  private static void test() {
    System.out.println("hello java.");
  }
}
