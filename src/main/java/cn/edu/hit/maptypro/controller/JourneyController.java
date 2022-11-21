package cn.edu.hit.maptypro.controller;

import cn.edu.hit.maptypro.entity.domain.Journey;
import cn.edu.hit.maptypro.entity.domain.Waypoint;
import cn.edu.hit.maptypro.entity.dto.JourneyDTO;
import cn.edu.hit.maptypro.entity.vo.JourneyVO;
import cn.edu.hit.maptypro.entity.domain.User;
import cn.edu.hit.maptypro.response.Response;
import cn.edu.hit.maptypro.response.ResponseCode;
import cn.edu.hit.maptypro.response.ResponseFactory;
import cn.edu.hit.maptypro.service.JourneyService;
import cn.edu.hit.maptypro.service.UserService;
import cn.edu.hit.maptypro.service.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class JourneyController {

    @Autowired
    UserService userService;
    @Autowired
    JourneyService journeyService;

    @Autowired
    WaypointService waypointService;

    @GetMapping(value = "api/journey/getByUser")
    @ResponseBody
    public Response getJourneyByUser(@RequestHeader Map<String, String> headers) {
        String username = headers.get("username");
        User user = userService.getByName(username);

        if (user == null) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "Authorization failed.", null);
        }

        List<JourneyVO> journeyWithWaypoints = journeyService.getAllJourneyWithWaypointByUser(user);

        return ResponseFactory.buildResult(ResponseCode.SUCCESS, "OK", journeyWithWaypoints);
    }

    @PostMapping(value = "api/journey/create")
    @ResponseBody
    public Response createUserJourney(@RequestHeader Map<String, String> headers, @RequestBody JourneyDTO journeyDTO) {
        String username = headers.get("username");
        User user = userService.getByName(username);

        if (user == null) {
            return ResponseFactory.buildResult(ResponseCode.FAIL, "Authorization failed.", null);
        }

        Journey journeyToAdd = new Journey();

        journeyToAdd.setTitle(journeyDTO.getTitle());
        journeyToAdd.setDescription(journeyDTO.getDescription());
        journeyToAdd.setUser(user);

        Journey journey = journeyService.add(journeyToAdd);

        List<Waypoint> waypointsToAdd = new ArrayList<>();

        journeyDTO.getWaypoints().stream().map((waypointDTO) -> {
            Waypoint waypointToAdd = new Waypoint();
            waypointToAdd.setLabel(waypointDTO.getLabel());
            waypointToAdd.setTime(waypointDTO.getTime());
            waypointToAdd.setCoordinate(waypointDTO.getCoordinate());
            waypointToAdd.setJourney(journey);
            return waypointToAdd;
        }).forEach(waypointsToAdd::add);

        waypointService.addWaypoints(waypointsToAdd);

        return ResponseFactory.buildResult(ResponseCode.SUCCESS, "Create journey successfully.", null);
    }
}
