package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.twotwo.ListTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 文件分享
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("文件分享")
public class FileSharing {

    /**
     * 分享ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("分享ID")
    private Integer id;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("用户ID")
    private Integer userId;

    /**
     * 文件id集合
     */
    @ApiModelProperty("文件id集合")
    @TableField(typeHandler = ListTypeHandler.class)
    private List<String> fileIdList;

    /**
     * 目录id集合
     */
    @ApiModelProperty("目录id集合")
    @TableField(typeHandler = ListTypeHandler.class)
    private List<String> directoryIdList;

    /**
     * 分享时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("分享时间")
    private Date createTime;

}
