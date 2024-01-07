package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 日志
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("日志")
public class Log {

    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("日志ID")
    private Integer id;

    /**
     * 异常url
     */
    @ApiModelProperty("异常url")
    private String name;

    /**
     * 请求方法
     */
    @ApiModelProperty("请求方法")
    private String method;

    /**
     * 异常信息
     */
    @ApiModelProperty("异常信息")
    private String stack;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createTime;

}
