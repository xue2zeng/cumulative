package com.xspace.java11.feature;

import java.lang.reflect.Field;

/**
 * JEP181：基于子类嵌套的访问控制 - Nest
 *
 * @author xue.zeng
 * @date 2019-06-16 12:22
 */
public class NestAccessExample {
  public static class A {
    void test() throws Exception {
      B b = new B();
      b.x = 1;

      Field field = B.class.getDeclaredField("x");
      field.setInt(b, 2);
    }
  }

  public static class B {
    private int x = 0;
  }

  public static void main(String[] args) throws Exception {
    new A().test();
  }
}
