package com.xspace.training.shiro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class AuthenticatorTest {
  private void login(String configFile) {
    // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

    // 2、得到SecurityManager实例 并绑定给SecurityUtils
    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);

    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("zeng", "123##");

    subject.login(token);

  }

  @Test
  public void testAllSuccessfulStrategyWithSuccess() {
    login("classpath:shiro-authenticator-all-success.ini");
    Subject subject = SecurityUtils.getSubject();

    // 得到一个身份集合，其包含了Realm验证成功的身份信息
    PrincipalCollection principalCollection = subject.getPrincipals();
    assertEquals(2, principalCollection.asList().size());
  }

  @Test
  public void testAllSuccessfulStrategyWithFail() {
    assertThrows(
        UnknownAccountException.class,
        () -> {
          login("classpath:shiro-authenticator-all-fail.ini");
        });
  }

  @Test
  public void testAtLeastOneSuccessfulStrategyWithSuccess() {
    login("classpath:shiro-authenticator-atLeastOne-success.ini");
    Subject subject = SecurityUtils.getSubject();

    // 得到一个身份集合，其包含了Realm验证成功的身份信息
    PrincipalCollection principalCollection = subject.getPrincipals();
    assertEquals(2, principalCollection.asList().size());
  }

  @Test
  public void testFirstOneSuccessfulStrategyWithSuccess() {
    login("classpath:shiro-authenticator-first-success.ini");
    Subject subject = SecurityUtils.getSubject();

    // 得到一个身份集合，其包含了第一个Realm验证成功的身份信息
    PrincipalCollection principalCollection = subject.getPrincipals();
    assertEquals(1, principalCollection.asList().size());
  }

  @Test
  public void testAtLeastTwoStrategyWithSuccess() {
    login("classpath:shiro-authenticator-atLeastTwo-success.ini");
    Subject subject = SecurityUtils.getSubject();

    // 得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
    PrincipalCollection principalCollection = subject.getPrincipals();
    assertEquals(1, principalCollection.asList().size());
  }

  @Test
  public void testOnlyOneStrategyWithSuccess() {
    login("classpath:shiro-authenticator-onlyone-success.ini");
    Subject subject = SecurityUtils.getSubject();

    // 得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
    PrincipalCollection principalCollection = subject.getPrincipals();
    assertEquals(1, principalCollection.asList().size());
  }

  @AfterEach
  public void tearDown() {
    ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
  }
}
