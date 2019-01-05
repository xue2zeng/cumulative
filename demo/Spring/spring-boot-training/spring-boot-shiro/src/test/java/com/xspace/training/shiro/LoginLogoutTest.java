package com.xspace.training.shiro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class LoginLogoutTest {
  @Test
  public void testIniShiro() {
    // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
    SecurityManager securityManager = new DefaultSecurityManager(iniRealm);

    // 2、得到SecurityManager实例 并绑定给SecurityUtils
    SecurityUtils.setSecurityManager(securityManager);

    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("xue", "123456");

    try {
      // 4、登录，即身份验证
      subject.login(token);
    } catch (AuthenticationException e) {
      // 5、身份验证失败
    }

    assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

    // 6、退出
    subject.logout();
  }

  @Test
  public void testCustomizeRealm() {
    // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");

    // 2、得到SecurityManager实例 并绑定给SecurityUtils
    SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);

    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("zeng", "123##");

    try {
      // 4、登录，即身份验证
      subject.login(token);
    } catch (AuthenticationException e) {
      // 5、身份验证失败
      e.printStackTrace();
    }

    assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

    // 6、退出
    subject.logout();
  }

  @Test
  public void testCustomMultiRealm() {
    // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    Factory<org.apache.shiro.mgt.SecurityManager> factory =
        new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");

    // 2、得到SecurityManager实例 并绑定给SecurityUtils
    SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);

    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("zeng", "123##");

    try {
      // 4、登录，即身份验证
      subject.login(token);
    } catch (AuthenticationException e) {
      // 5、身份验证失败
      e.printStackTrace();
    }

    assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

    // 6、退出
    subject.logout();
  }

  @Test
  public void testJDBCRealm() {
    // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    Factory<org.apache.shiro.mgt.SecurityManager> factory =
        new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");

    // 2、得到SecurityManager实例 并绑定给SecurityUtils
    SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);

    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("zeng", "123##");

    try {
      // 4、登录，即身份验证
      subject.login(token);
    } catch (AuthenticationException e) {
      // 5、身份验证失败
      e.printStackTrace();
    }

    assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录

    // 6、退出
    subject.logout();
  }

  @AfterEach
  public void tearDown() throws Exception {
    ThreadContext.unbindSubject(); // 退出时请解除绑定Subject到线程 否则对下次测试造成影响
  }
}
