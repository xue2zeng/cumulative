package com.xspace.training.shiro.realm;

import com.xspace.training.shiro.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm3 implements Realm {

  /**
   * realm name 为 “c”
   * @return
   */
  @Override
  public String getName() {
    return "c";
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof UsernamePasswordToken;
  }

  @Override
  public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    User user = new User("zeng", "123##");
    return new SimpleAuthenticationInfo(
        user,
        "123",
        getName()
    );
  }
}
