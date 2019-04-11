package com.xspace.training.shiro.service;

import com.xspace.training.shiro.entity.Role;

public interface RoleService {

  /**
   * 创建角色
   * @param role
   * @return
   */
  Role createRole(Role role);

  /**
   * 删除角色
   * @param roleId
   */
  void deleteRole(Long roleId);

  /**
   * 添加角色-权限之间关系
   * @param roleId
   * @param permissionIds
   */
  void correlationPermissions(Long roleId, Long... permissionIds);

  /**
   * 移除角色-权限之间关系
   * @param roleId
   * @param permissionIds
   */
  void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
