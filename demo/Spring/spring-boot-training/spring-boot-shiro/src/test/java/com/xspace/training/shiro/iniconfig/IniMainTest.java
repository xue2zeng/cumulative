package com.xspace.training.shiro.iniconfig;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.jupiter.api.Test;

public class IniMainTest {
  @Test
  public void test() {

    Factory<SecurityManager> factory =
        new IniSecurityManagerFactory("classpath:shiro-config-main.ini");

    SecurityManager securityManager = factory.getInstance();

    //将SecurityManager设置到SecurityUtils 方便全局使用
    SecurityUtils.setSecurityManager(securityManager);

    Subject subject = SecurityUtils.getSubject();

    UsernamePasswordToken token = new UsernamePasswordToken("zeng", "123##");
    subject.login(token);

    assertTrue(subject.isAuthenticated());

  }
}
