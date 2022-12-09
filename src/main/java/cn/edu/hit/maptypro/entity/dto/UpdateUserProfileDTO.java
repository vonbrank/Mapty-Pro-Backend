package cn.edu.hit.maptypro.entity.dto;

import lombok.Data;

@Data
public class UpdateUserProfileDTO {

    String newUsername;

    String newEmail;

    public static boolean checkValidation(UpdateUserProfileDTO userProfileDTO) {
        return (userProfileDTO.newUsername != null && !userProfileDTO.newUsername.isEmpty()) &&
                (userProfileDTO.newEmail != null && !userProfileDTO.newEmail.isEmpty());
    }
}