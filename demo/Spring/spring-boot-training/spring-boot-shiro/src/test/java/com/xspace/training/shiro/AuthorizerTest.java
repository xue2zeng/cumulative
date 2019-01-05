package com.xspace.training.shiro;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AuthorizerTest extends BaseTest {
  @Test
  public void testIsPermitted() {
    login("classpath:shiro-authorizer.ini", "zeng", "123##");
    // 判断拥有权限：user:create
    assertTrue(subject().isPermitted("user1:update"));
    assertTrue(subject().isPermitted("user2:update"));
    //通过二进制位的方式表示权限
    assertTrue(subject().isPermitted("+user1+2"));//新增权限
    assertTrue(subject().isPermitted("+user1+8"));//查看权限
    assertTrue(subject().isPermitted("+user2+10"));//新增及查看

    assertFalse(subject().isPermitted("+user1+4"));//没有删除权限

    assertTrue(subject().isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
  }

  @Test
  public void testIsPermitted2() {
    login("classpath:shiro-jdbc-authorizer.ini", "zeng", "123##");
    //判断拥有权限：user:create
    assertTrue(subject().isPermitted("user1:update"));
    assertTrue(subject().isPermitted("user2:update"));
    //通过二进制位的方式表示权限
    assertTrue(subject().isPermitted("+user1+2"));//新增权限
    assertTrue(subject().isPermitted("+user1+8"));//查看权限
    assertTrue(subject().isPermitted("+user2+10"));//新增及查看

    assertFalse(subject().isPermitted("+user1+4"));//没有删除权限

    assertTrue(subject().isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
  }
}
