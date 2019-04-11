package com.xspace.training.shiro.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserRole implements Serializable {

  /**
   * 用户标识ID
   */
  private Long userId;
  /**
   * 角色标识ID
   */
  private Long roleId;
}
