package com.xspace.java11.feature;

/**
 * JEP330: 启动单文件源代码程序
 *
 * <p>增强Java启动程序以运行作为单个Java源代码文件提供的程序
 *
 * <p>例如：jdk11之前都需要通过javac命名编译，再通过java命名执行字节文件
 *
 * <p>jdk11则可以直接使用java命令执行源代码文件
 *
 * @author xue.zeng
 * @date 2019-06-16 17:29
 */
public class SingleFileExample {
  public static void main(String[] args) {
    System.out.println("Hello Jdk11.");
  }
}
