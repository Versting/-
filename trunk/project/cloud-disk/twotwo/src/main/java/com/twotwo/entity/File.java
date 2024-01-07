package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 网盘文件
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("网盘文件")
public class File {

    /**
     * 文件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("文件ID")
    private Integer id;

    /**
     * 上传用户ID
     */
    @TableField(fill = FieldFill.DEFAULT)
    @ApiModelProperty("上传用户ID")
    private Integer userId;

    /**
     * 目录ID
     */
    @ApiModelProperty("目录ID")
    @NotNull
    private Integer directoryId;

    /**
     * 文件名
     */
    @NotBlank
    @ApiModelProperty("文件名")
    private String name;

    /**
     * 文件url
     */
    @ApiModelProperty("文件url")
    private String url;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Long size;

    /**
     * 是否逻辑删除
     */
    @ApiModelProperty("是否逻辑删除")
    private Integer isDeleted;

    /**
     * 上传时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("上传时间")
    private Date createTime;

}
