package com.xspace.training.shiro.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Permission implements Serializable {

  /**
   * 标识ID
   */
  private Long id;
  /**
   * 权限标识 程序中判断使用,如"user:create"
   */
  @NonNull
  private String permission;
  /**
   * 权限描述,UI界面显示使用
   */
  @NonNull
  private String description;
  /**
   * 是否可用,如果不可用将不会添加给用户
   */
  @NonNull
  private Boolean available;
}
