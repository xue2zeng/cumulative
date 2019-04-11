package com.xspace.training.shiro.service.impl;

import com.xspace.training.shiro.dao.PermissionDao;
import com.xspace.training.shiro.entity.Permission;
import com.xspace.training.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

  @Autowired
  PermissionDao permissionDao;

  @Override
  public Permission createPermission(Permission permission) {
    return permissionDao.createPermission(permission);
  }

  @Override
  public void deletePermission(Long permissionId) {
    permissionDao.deletePermission(permissionId);
  }
}
