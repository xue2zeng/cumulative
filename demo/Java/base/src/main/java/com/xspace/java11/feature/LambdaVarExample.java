package com.xspace.java11.feature;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * JEP323: Lambda参数的本地变量语法
 *
 * <p>允许var在声明隐式类型的lambda表达式的形式参数时使用
 *
 * @author xue.zeng
 * @date 2019-06-16 16:47
 */
@Slf4j
public class LambdaVarExample {

  private static void lambdaInJava11() {
    List<Integer> nums = Arrays.asList(3, 9, 2, 5, 1);
    nums.sort(
        (Integer s1, Integer s2) -> {
          if (Objects.equals(s1, s2)) {
            return 0;
          } else {
            return s1 > s2 ? 1 : -1;
          }
        });
    log.info("jdk 8,9,10 sort: {}", nums.toString());

    nums.sort(
        (@NonNull var s1, @NonNull var s2) -> {
          if (Objects.equals(s1, s2)) {
            return 0;
          } else {
            return s1 > s2 ? 1 : -1;
          }
        });
    log.info("jdk 11 sort: {}", nums.toString());
  }

  public static void main(String[] args) {
    lambdaInJava11();
  }
}
