package cn.edu.hit.maptypro.service;

import cn.edu.hit.maptypro.dao.WaypointDAO;
import cn.edu.hit.maptypro.entity.domain.Journey;
import cn.edu.hit.maptypro.entity.domain.Waypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class WaypointService {
    @Autowired
    WaypointDAO waypointDAO;

    public List<Waypoint> getAllWaypointsByJourney(Journey journey) {
        if (journey == null) {
            return new ArrayList<>();
        }
        return waypointDAO.findAllByJourney(journey);
    }
}