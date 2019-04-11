package com.xspace.training.shiro.service.impl;

import com.xspace.training.shiro.dao.UserDao;
import com.xspace.training.shiro.entity.User;
import com.xspace.training.shiro.service.UserService;
import com.xspace.training.shiro.util.PasswordHelper;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

  @Autowired
  UserDao userDao;

  @Override
  public User createUser(User user) {
    PasswordHelper.encryptPassword(user);
    return userDao.createUser(user);
  }

  @Override
  public void changePassword(Long userId, String newPassword) {
    User user =userDao.findOne(userId);
    user.setPassword(newPassword);
    PasswordHelper.encryptPassword(user);
    userDao.updateUser(user);
  }

  @Override
  public void correlationRoles(Long userId, Long... roleIds) {
    userDao.correlationRoles(userId, roleIds);
  }

  @Override
  public void uncorrelationRoles(Long userId, Long... roleIds) {
    userDao.uncorrelationRoles(userId, roleIds);
  }

  @Override
  public User findByUsername(String username) {
    return userDao.findByUsername(username);
  }

  @Override
  public Set<String> findRoles(String username) {
    return userDao.findRoles(username);
  }

  @Override
  public Set<String> findPermissions(String username) {
    return userDao.findPermissions(username);
  }
}
