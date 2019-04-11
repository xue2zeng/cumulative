package com.xspace.training.shiro.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User implements Serializable {

  /**
   * 用户标识ID
   */
  private Long id;
  /**
   * 用户名
   */
  @NonNull
  private String username;
  /**
   * 密码
   */
  @NonNull
  private String password;
  /**
   * 密码盐
   */
  private String salt;

  /**
   * 是否锁定，是否锁定用于封禁用户使用，其实最好使用Enum 字段存储，可以实现更复杂的用户状态 实现
   */
  private Boolean locked = Boolean.FALSE;

  /**
   * 用户名+加盐
   * @return
   */
  public String getCredentialsSalt() {
    return username + salt;
  }
}
