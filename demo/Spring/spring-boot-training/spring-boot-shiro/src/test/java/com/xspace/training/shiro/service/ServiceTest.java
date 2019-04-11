package com.xspace.training.shiro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xspace.training.shiro.entity.Permission;
import com.xspace.training.shiro.entity.Role;
import com.xspace.training.shiro.entity.User;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServiceTest {

  @Autowired
  JdbcTemplate jdbcTemplate;
  @Autowired
  PermissionService permissionService;
  @Autowired
  RoleService roleService;
  @Autowired
  UserService userService;

  protected String password = "123##";

  protected Permission p1;
  protected Permission p2;
  protected Permission p3;
  protected Role r1;
  protected Role r2;
  protected User u1;
  protected User u2;
  protected User u3;
  protected User u4;


  @BeforeEach
  public void setUp() {
    jdbcTemplate.update("delete from sys_users");
    jdbcTemplate.update("delete from sys_roles");
    jdbcTemplate.update("delete from sys_permissions");
    jdbcTemplate.update("delete from sys_users_roles");
    jdbcTemplate.update("delete from sys_roles_permissions");


    //1、新增权限
    p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
    p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
    p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
    permissionService.createPermission(p1);
    permissionService.createPermission(p2);
    permissionService.createPermission(p3);
    //2、新增角色
    r1 = new Role("admin", "管理员", Boolean.TRUE);
    r2 = new Role("user", "用户管理员", Boolean.TRUE);
    roleService.createRole(r1);
    roleService.createRole(r2);
    //3、关联角色-权限
    roleService.correlationPermissions(r1.getId(), p1.getId());
    roleService.correlationPermissions(r1.getId(), p2.getId());
    roleService.correlationPermissions(r1.getId(), p3.getId());

    roleService.correlationPermissions(r2.getId(), p1.getId());
    roleService.correlationPermissions(r2.getId(), p2.getId());

    //4、新增用户
    u1 = new User("zeng", password);
    u2 = new User("li", password);
    u3 = new User("wu", password);
    u4 = new User("wang", password);
    u4.setLocked(Boolean.TRUE);
    userService.createUser(u1);
    userService.createUser(u2);
    userService.createUser(u3);
    userService.createUser(u4);
    //5、关联用户-角色
    userService.correlationRoles(u1.getId(), r1.getId());
  }

  @AfterEach
  public void tearDown() throws Exception {
    ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
  }

  protected void login(String configFile, String username, String password) {
    //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
    Factory<SecurityManager> factory =
        new IniSecurityManagerFactory(configFile);

    //2、得到SecurityManager实例 并绑定给SecurityUtils
    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);

    //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);

    subject.login(token);
  }

  public Subject subject() {
    return SecurityUtils.getSubject();
  }

  @Test
  public void testUserRolePermissionRelation() {

    //zeng
    Set<String> roles = userService.findRoles(u1.getUsername());
    assertEquals(1, roles.size());
    assertTrue(roles.contains(r1.getRole()));

    Set<String> permissions = userService.findPermissions(u1.getUsername());
    assertEquals(3, permissions.size());
    assertTrue(permissions.contains(p3.getPermission()));

    //li
    roles = userService.findRoles(u2.getUsername());
    assertEquals(0, roles.size());
    permissions = userService.findPermissions(u2.getUsername());
    assertEquals(0, permissions.size());


    //解除 admin-menu:update关联
    roleService.uncorrelationPermissions(r1.getId(), p3.getId());
    permissions = userService.findPermissions(u1.getUsername());
    assertEquals(2, permissions.size());
    assertFalse(permissions.contains(p3.getPermission()));


    //删除一个permission
    permissionService.deletePermission(p2.getId());
    permissions = userService.findPermissions(u1.getUsername());
    assertEquals(1, permissions.size());

    //解除 zhang-admin关联
    userService.uncorrelationRoles(u1.getId(), r1.getId());
    roles = userService.findRoles(u1.getUsername());
    assertEquals(0, roles.size());

  }

}
