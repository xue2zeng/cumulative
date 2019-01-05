package com.xspace.training.shiro.hash.realm;

import org.apache.shiro.realm.jdbc.JdbcRealm;

public class CustomizeJdbcRealm extends JdbcRealm {
  public void setSaltStyle(String saltStyle) {
    super.setSaltStyle(SaltStyle.valueOf(saltStyle));
  }
}
