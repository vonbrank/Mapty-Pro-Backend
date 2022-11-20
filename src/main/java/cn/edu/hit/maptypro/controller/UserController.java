package cn.edu.hit.maptypro.controller;

import cn.edu.hit.maptypro.entity.domain.User;
import cn.edu.hit.maptypro.response.Response;
import cn.edu.hit.maptypro.response.ResponseCode;
import cn.edu.hit.maptypro.response.ResponseFactory;
import cn.edu.hit.maptypro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Response login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        if (username != null) username = HtmlUtils.htmlEscape(username);
        String email = requestUser.getEmail();
        if (email != null) email = HtmlUtils.htmlEscape(email);

        User user = null;
        User userByUsername = null;
        User userByEmail = null;
        if (username != null && !username.isEmpty())
            userByUsername = userService.getByName(username, requestUser.getPassword());
        if (email != null && !email.isEmpty()) userByEmail = userService.getByEmail(email, requestUser.getPassword());
        user = userByUsername;
        if (user == null) user = userByEmail;

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
        String username = requestUser.getUsername();
        if (username != null) username = HtmlUtils.htmlEscape(username);
        String email = requestUser.getEmail();
        if (email != null) email = HtmlUtils.htmlEscape(email);

        User user = null;
        User userByUsername = null;
        User userByEmail = null;
        if (username != null && !username.isEmpty()) userByUsername = userService.getByName(username);
        if (email != null && !email.isEmpty()) userByEmail = userService.getByEmail(email);
        if (userByUsername != null || userByEmail != null) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "User has existed.", null);
        }

        String password = requestUser.getPassword();
        if (username != null && email != null && password != null) {
            User newUser = new User(username, email, password);
            userService.add(newUser);
            return ResponseFactory.buildResult(ResponseCode.SUCCESS, "Register successfully.", newUser);
        } else {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "Input required.", null);
        }
    }
}
