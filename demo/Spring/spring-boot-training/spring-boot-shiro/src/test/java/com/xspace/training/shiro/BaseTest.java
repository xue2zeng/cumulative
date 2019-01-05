package com.xspace.training.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseTest {
  @AfterEach
  public void tearDown() throws Exception {
    ThreadContext.unbindSubject(); // 退出时请解除绑定Subject到线程 否则对下次测试造成影响
  }

  protected void login(String configFile, String username, String password) {
    // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

    // 2、得到SecurityManager实例 并绑定给SecurityUtils
    SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);

    // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);

    subject.login(token);
  }

  public Subject subject() {
    return SecurityUtils.getSubject();
  }
}
