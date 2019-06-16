package com.xspace.webflux;

import com.google.common.collect.Lists;
import com.xspace.webflux.domain.CartItem;
import com.xspace.webflux.domain.Product;
import com.xspace.webflux.domain.Student;
import com.xspace.webflux.lambda.MyAtomicInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class lambdaTest extends BaseTest {
  @Test
  public void testFunctional() {
    List<CartItem> cartItems = Lists.newArrayList();
    cartItems.add(new CartItem(new Product("饼干", 8.5), 4));
    cartItems.add(new CartItem(new Product("干果", 39.9), 3));
    cartItems.add(new CartItem(new Product("长粒香米", 39), 2));
    cartItems.add(new CartItem(new Product("玉米油", 42.9), 1));
    cartItems.add(new CartItem(new Product("饼干", 8.5), 1));
    cartItems.add(new CartItem(new Product("牛肉干", 59), 2));
    cartItems.add(new CartItem(new Product("剃须刀", 259), 1));
    cartItems.add(new CartItem(new Product("干果", 39.9), -1));
    cartItems.add(new CartItem(new Product("牛肉干", 59), -1));

    double sum = 0;
    for (CartItem item : cartItems) {
      double result = item.getProduct().getPrice() * item.getQuantity();
      result = (result > 199) ? (result - 40) : result;
      sum += result;
    }

    log.info("订单金额：{}", sum);
  }

  @Test
  public void testStream() {
    // 直接使用Stream的of方法构造数据流
    Stream<CartItem> cartItemStream =
        Stream.of(
            new CartItem(new Product("饼干", 8.5), 4),
            new CartItem(new Product("干果", 39.9), 3),
            new CartItem(new Product("长粒香米", 39), 2),
            new CartItem(new Product("玉米油", 42.9), 1),
            new CartItem(new Product("饼干", 8.5), 1),
            new CartItem(new Product("牛肉干", 59), 2),
            new CartItem(new Product("剃须刀", 259), 1),
            new CartItem(new Product("干果", 39.9), -1),
            new CartItem(new Product("牛肉干", 59), -1));
    double sum =
        cartItemStream
            // 分别计算商品金额
            .mapToDouble(value -> value.getProduct().getPrice() * value.getQuantity())
            // 计算满减后的商品金额
            .map(operand -> (operand > 199) ? (operand - 40) : operand)
            // 金额累加
            .sum();
    sum = (sum > 500) ? sum : (sum + 50);
    log.info("订单金额：{}", sum);
  }

  /**
   * 1. 响应式之道 - 2 响应式流 （附录A）
   *
   * <p>通过扩展 AtomicInteger 实现了自定义的 AtomicInteger， 可以记录“Compare and Set”的失败次数。
   */
  @Test
  public void testCustomizeAtomic() throws InterruptedException {
    final MyAtomicInteger myAtomicInteger = new MyAtomicInteger();
    Thread[] incs = new Thread[10];
    Thread[] decs = new Thread[10];
    for (int i = 0; i < incs.length; i++) {
      incs[i] =
          new Thread(
              () -> {
                for (int j = 0; j < 10000; j++) {
                  myAtomicInteger.inc();
                }
              });
      incs[i].start();
      decs[i] =
          new Thread(
              () -> {
                for (int j = 0; j < 10000; j++) {
                  myAtomicInteger.dec();
                }
              });
      decs[i].start();
    }

    for (int i = 0; i < 10; i++) {
      incs[i].join();
      decs[i].join();
    }

    log.info("{} with {} failed tries.", myAtomicInteger.get(), myAtomicInteger.getFailureCount());
  }

  @Test
  public void testCompare() {
    List<Student> students = new ArrayList<>();
    students.add(new Student(10001, "张三", 1.73, 88));
    students.add(new Student(10002, "李四", 1.71, 96));
    students.add(new Student(10003, "王五", 1.85, 88));

    // 1. 通过实现类定义
    class StudentIdComparator<S extends Student> implements Comparator<S> {
      @Override
      public int compare(S s1, S s2) {
        return Integer.compare(s1.getId(), s2.getId());
      }
    }

    students.sort(new StudentIdComparator<>());
    log.info("Students customize sort : {}", students);

    // 2. 通过匿名内部类定义
    students.sort(
        new Comparator<Student>() {
          @Override
          public int compare(Student o1, Student o2) {
            return Double.compare(o1.getHeight(), o2.getHeight());
          }
        });
    log.info("Students inner class sort : {}", students);

    // 3. 通过lambda定义
    students.sort((Student o1, Student o2) -> Double.compare(o1.getHeight(), o2.getHeight()));
    log.info("Students lambda sort 1 : {}", students);

    // 3.1 简化版lambda
    //        students.sort(Comparator.comparingDouble(s -> s.getScore()));
    students.sort(Comparator.comparingDouble(Student::getScore));
    log.info("Students lambda sort 2 : {}", students);
  }
}
