package com.xspace.training.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {
  @Override
  public String getName() {
    return "myrealm1";
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof UsernamePasswordToken; //仅支持UsernamePasswordToken类型的Token
  }

  @Override
  public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String username = (String)token.getPrincipal();  //得到用户名
    String password = new String((char[])token.getCredentials()); //得到密码
    if(!"zeng".equals(username)) {
      throw new UnknownAccountException(); //如果用户名错误
    }
    if(!"123##".equals(password)) {
      throw new IncorrectCredentialsException(); //如果密码错误
    }
    //如果身份认证验证成功，返回一个AuthenticationInfo实现；
    return new SimpleAuthenticationInfo(username, password, getName());
  }
}
