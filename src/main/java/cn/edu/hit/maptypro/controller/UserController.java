package cn.edu.hit.maptypro.controller;

import cn.edu.hit.maptypro.entity.domain.User;
import cn.edu.hit.maptypro.entity.dto.ResetPasswordDTO;
import cn.edu.hit.maptypro.entity.dto.UpdateUserProfileDTO;
import cn.edu.hit.maptypro.response.Response;
import cn.edu.hit.maptypro.response.ResponseCode;
import cn.edu.hit.maptypro.response.ResponseFactory;
import cn.edu.hit.maptypro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Response login(@RequestBody User requestUser) {

        requestUser = userService.userHtmlEscape(requestUser);

        if (!userService.checkLoginUserValidation(requestUser)) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "Input required.", null);
        }

        User user = userService.getByNameAndEmail(requestUser);

        if (user == null) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "User does not exist.", null);
        } else {
            return ResponseFactory.buildResult(ResponseCode.SUCCESS, "Login successfully.", user);
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/register")
    @ResponseBody
    public Response register(@RequestBody User requestUser) {

        User user = userService.userHtmlEscape(requestUser);

        if (!userService.checkRegisterUserValidation(user)) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "Input required.", null);
        }

        if (userService.checkUserRepetition(user)) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "User has existed.", null);
        }

        User newUser = new User(user.getUsername(), user.getEmail(), user.getPassword());
        userService.add(newUser);
        return ResponseFactory.buildResult(ResponseCode.SUCCESS, "Register successfully.", newUser);

    }

    @CrossOrigin
    @PostMapping(value = "api/auth/updateUserProfile")
    @ResponseBody
    public Response updateUserProfile(@RequestHeader Map<String, String> headers, @RequestBody UpdateUserProfileDTO updateUserProfileDTO) {

        // TODO:
        //  update user profile feature

        return ResponseFactory.buildResult(ResponseCode.INTERNAL_SERVER_ERROR, "Not Implemented.", null);

    }

    @CrossOrigin
    @PostMapping(value = "api/auth/resetPassword")
    @ResponseBody
    public Response resetPassword(@RequestHeader Map<String, String> headers, @RequestBody ResetPasswordDTO resetPasswordDTO) {

        String username = headers.get("username");
        String password = headers.get("password");
        User user = userService.getByName(username, password);

        if (user == null) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "Authorization failed.", null);
        }

        if (!user.getPassword().equals(resetPasswordDTO.getOldPassword())) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "Old password does not equal to the current one.", null);
        }

        if (!(resetPasswordDTO.getNewPassword() != null && !resetPasswordDTO.getNewPassword().isEmpty())) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "New password required.", null);
        }

        user.setPassword(resetPasswordDTO.getNewPassword());
        userService.add(user);

        return ResponseFactory.buildResult(ResponseCode.SUCCESS, "Reset password successfully.", user);

    }


}
