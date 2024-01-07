package com.twotwo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterRequest {

    @ApiModelProperty("账号")
    private String email;

    @ApiModelProperty("密码")
    private String password;

/*    @ApiModelProperty("公司ID")
    private Integer companyId;

    @ApiModelProperty("部门ID")
    private Integer departmentId;
*/
    @ApiModelProperty("验证码")
    private String code;

}