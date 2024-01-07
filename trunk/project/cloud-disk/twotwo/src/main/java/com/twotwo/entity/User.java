package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bigsnake.annotation.Constraint;
import com.bigsnake.annotation.ExtendQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("用户")
public class User {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("用户ID")
    private Integer id;

    /**
     * 电子邮件
     */
    @ApiModelProperty("电子邮件")
    private String email;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 角色
     */
    @ApiModelProperty("角色 1-员工 2-部门主管 3-ceo")
    private Integer role;

    @ApiModelProperty("部门ID")
    @Constraint(referenceClass = Department.class,referenceField = "")
    private Integer departmentId;

    @Constraint(referenceClass = Company.class, referenceField = "")
    @ApiModelProperty("公司ID")
    private Integer companyId;

    /**
     * 状态
     */
    @ApiModelProperty("状态 0-审核中 1-正常")
    private Integer status;


    /*@TableField(exist = false)
    @ApiModelProperty("部门名称")
    @ExtendQuery(referenceClass = Department.class, targetField = "name")
    private String departmentName;

    @TableField(exist = false)
    @ApiModelProperty("公司名称")
    @ExtendQuery(referenceClass = Company.class, targetField = "name")
    private String companyName;*/
}
