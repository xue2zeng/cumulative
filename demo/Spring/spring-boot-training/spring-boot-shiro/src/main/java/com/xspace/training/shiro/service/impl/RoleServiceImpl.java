package com.xspace.training.shiro.service.impl;

import com.xspace.training.shiro.dao.RoleDao;
import com.xspace.training.shiro.entity.Role;
import com.xspace.training.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

  @Autowired
  RoleDao roleDao;

  @Override
  public Role createRole(Role role) {
    return roleDao.createRole(role);
  }

  @Override
  public void deleteRole(Long roleId) {
    roleDao.deleteRole(roleId);
  }

  @Override
  public void correlationPermissions(Long roleId, Long... permissionIds) {
    roleDao.correlationPermissions(roleId, permissionIds);
  }

  @Override
  public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
    roleDao.uncorrelationPermissions(roleId, permissionIds);
  }
}
