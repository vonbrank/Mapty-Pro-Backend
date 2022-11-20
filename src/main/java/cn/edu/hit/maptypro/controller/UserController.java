package cn.edu.hit.maptypro.controller;

import cn.edu.hit.maptypro.entity.domain.User;
import cn.edu.hit.maptypro.response.Response;
import cn.edu.hit.maptypro.response.ResponseCode;
import cn.edu.hit.maptypro.response.ResponseFactory;
import cn.edu.hit.maptypro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
