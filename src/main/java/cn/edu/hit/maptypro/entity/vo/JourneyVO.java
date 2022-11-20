package cn.edu.hit.maptypro.entity.vo;

import cn.edu.hit.maptypro.entity.domain.User;
import cn.edu.hit.maptypro.entity.domain.Waypoint;
import lombok.Data;

import java.util.List;

@Data
public class JourneyVO {
    int id;

    private String title;

    private String description;

    private User user;

    private List<Waypoint> waypoints;
}
