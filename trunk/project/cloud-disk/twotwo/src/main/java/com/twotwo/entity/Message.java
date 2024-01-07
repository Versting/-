package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息通知
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("消息通知")
public class Message {

    /**
     * 消息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("消息ID")
    private Integer id;

    /**
     * 发送部门ID
     */
    @ApiModelProperty("发送部门ID")
    private Integer targetDepartmentId;

    /**
     * 发送用户ID
     */
    @ApiModelProperty("发送用户ID")
    private Integer targetUserId;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("用户ID")
    private Integer userId;

    /**
     * 消息文本
     */
    @ApiModelProperty("消息文本")
    private String text;

    /**
     * 是否已读
     */
    @ApiModelProperty("是否已读")
    private Integer isRead;

}
