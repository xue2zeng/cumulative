package com.xspace.training.shiro.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Role implements Serializable {

  /**
   * 标识ID
   */
  private Long id;
  /**
   * 角色标识 程序中判断使用,如"admin"
   */
  @NonNull
  private String role;
  /**
   * 角色描述,UI界面显示使用
   */
  @NonNull
  private String description;
  /**
   * 是否可用,其中角色标识符用于在程序中进行隐式角色判断的，描述用于以后再前台界面显示的、是 否可用表示角色当前是否激活
   */
  @NonNull
  private Boolean available;
}
