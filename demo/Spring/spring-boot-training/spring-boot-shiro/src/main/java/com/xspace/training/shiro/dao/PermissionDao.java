package com.xspace.training.shiro.dao;

import com.xspace.training.shiro.entity.Permission;

public interface PermissionDao {

  /**
   * 创建权限
   * @param permission
   * @return
   */
  Permission createPermission(Permission permission);

  /**
   * 删除权限
   * @param permissionId
   */
  void deletePermission(Long permissionId);

}
