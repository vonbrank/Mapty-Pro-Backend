package cn.edu.hit.maptypro.controller;

import cn.edu.hit.maptypro.entity.vo.JourneyVO;
import cn.edu.hit.maptypro.entity.domain.User;
import cn.edu.hit.maptypro.response.Response;
import cn.edu.hit.maptypro.response.ResponseCode;
import cn.edu.hit.maptypro.response.ResponseFactory;
import cn.edu.hit.maptypro.service.JourneyService;
import cn.edu.hit.maptypro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class JourneyController {
    @Autowired
    JourneyService journeyService;

    @Autowired
    UserService userService;


    @GetMapping(value = "api/journey/getByUser")
    @ResponseBody
    public Response getJourneyByUser(@RequestHeader Map<String, String> headers) {
        String username = headers.get("username");
        System.out.println("test");

        if (username == null) {
            return ResponseFactory.buildResult(ResponseCode.SUCCESS, "OK", null);
        }

        User user = userService.getByName(username);
        List<JourneyVO> journeyWithWaypoints = journeyService.getAllJourneyWithWaypointByUser(user);
        System.out.println(journeyWithWaypoints);


        return ResponseFactory.buildResult(ResponseCode.SUCCESS, "OK", journeyWithWaypoints);
    }
}
