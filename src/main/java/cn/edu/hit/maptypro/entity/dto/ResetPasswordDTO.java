package cn.edu.hit.maptypro.entity.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

    String oldPassword;

    String newPassword;
}
