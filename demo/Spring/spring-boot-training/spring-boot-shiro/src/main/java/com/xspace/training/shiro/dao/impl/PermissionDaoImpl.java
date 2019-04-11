package com.xspace.training.shiro.dao.impl;

import com.xspace.training.shiro.dao.PermissionDao;
import com.xspace.training.shiro.entity.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionDaoImpl implements PermissionDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public Permission createPermission(Permission permission) {
    final String sql = "insert into sys_permissions(permission, description, available) values(?,?,?)";

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement psst = connection.prepareStatement(sql,  new String[] { "id" });
        psst.setString(1, permission.getPermission());
        psst.setString(2, permission.getDescription());
        psst.setBoolean(3, permission.getAvailable());
        return psst;
      }
    }, keyHolder);
    permission.setId(keyHolder.getKey().longValue());

    return permission;
  }

  @Override
  public void deletePermission(Long permissionId) {
    //首先把与permission关联的相关表的数据删掉
    String sql = "delete from sys_roles_permissions where permission_id=?";
    jdbcTemplate.update(sql, permissionId);

    sql = "delete from sys_permissions where id=?";
    jdbcTemplate.update(sql, permissionId);
  }
}
