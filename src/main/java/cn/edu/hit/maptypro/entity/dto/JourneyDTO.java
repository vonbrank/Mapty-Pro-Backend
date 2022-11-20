package cn.edu.hit.maptypro.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class JourneyDTO {
    String title;

    String description;

    int userId;

    List<WaypointDTO> waypoints;
}
