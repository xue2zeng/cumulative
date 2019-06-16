package com.xspace.java11.feature;

import lombok.extern.slf4j.Slf4j;

/**
 * 新增加的 String API
 *
 * @author xue.zeng
 * @date 2019-06-16 18:11
 */
@Slf4j
public class StringExample {
  public static void main(String[] args) {
    String example = "\u3000Hello, JDK11.\u3000";
    String empty = "\u3000";
    log.info("原始字符串：{}", example);
    log.info("<trim -> 删除字符串的头尾空白符>: {}", example.trim());
    log.info("<strip -> 删除字符串的头尾空白符>: {}", example.strip());
    log.info("<strip -> 删除字符串的头部空白符>: {}", example.stripLeading());
    log.info("<strip -> 删除字符串的头部空白符>: {}", example.stripTrailing());

    log.info("<isBlank -> 是否为空或仅包含空格>: {}", empty.isBlank());
    log.info("<isEmpty -> 是否为空或仅包含空格>: {}", empty.isEmpty());

    String lines = "hello\nworld\njdk11";
    lines.lines().forEach(System.out::println);

    log.info("*".repeat(50));
  }
}
