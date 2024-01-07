package com.twotwo.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPasswordAgain;
    private String newPassword;
    private String id;
}