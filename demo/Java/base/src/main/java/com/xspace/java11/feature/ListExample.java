package com.xspace.java11.feature;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 新增加的 List API
 *
 * @author xue.zeng
 * @date 2019-06-16 18:24
 */
@Slf4j
public class ListExample {
  public static void main(String[] args) {
    List<String> list = List.of("java8", "java9", "java11", "java12");
    log.info(list.toString());

    // 旧的方法：传入String[]
    String[] oldArry = list.toArray(new String[0]);

    // 新的方法
    String[] newArry = list.toArray(String[]::new);
  }
}
