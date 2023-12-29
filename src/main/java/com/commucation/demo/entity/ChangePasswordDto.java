package com.commucation.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @ApiModelProperty(value = "电话号码")
    private String identityNo;

    @ApiModelProperty(value = "密码")
    private String beforePassword;

    @ApiModelProperty(value = "密码")
    private String newPassword;
}