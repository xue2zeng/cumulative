package com.xspace.training.shiro.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class RolePermssion implements Serializable {

  /**
   * 角色标识ID
   */
  private Long roleId;
  /**
   * 权限标识ID
   */
  private Long permissionId;
}
