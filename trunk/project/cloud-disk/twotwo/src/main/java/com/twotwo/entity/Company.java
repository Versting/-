package com.twotwo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("公司")
public class Company {

    /**
     * 公司ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("公司ID")
    private Integer id;

    /**
     * 公司名称
     */
    @ApiModelProperty("公司名称")
    private String name;

}