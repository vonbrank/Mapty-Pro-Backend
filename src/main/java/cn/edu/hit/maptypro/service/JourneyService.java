package cn.edu.hit.maptypro.service;

import cn.edu.hit.maptypro.dao.JourneyDAO;
import cn.edu.hit.maptypro.entity.domain.Journey;
import cn.edu.hit.maptypro.entity.dto.journey.JourneyDTO;
import cn.edu.hit.maptypro.entity.vo.JourneyVO;
import cn.edu.hit.maptypro.entity.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JourneyService {
    @Autowired
    JourneyDAO journeyDAO;

    @Autowired
    WaypointService waypointService;

    public List<Journey> getAllJourneyByUser(User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return journeyDAO.findAllByUser(user);
    }

    public List<JourneyVO> getAllJourneyWithWaypointByUser(User user) {
        List<Journey> journeys = getAllJourneyByUser(user);
        List<JourneyVO> journeyVOs = new ArrayList<>();
        journeys.stream().map((Journey journey) -> {
            JourneyVO journeyVO = new JourneyVO();
            journeyVO.setId(journey.getId());
            journeyVO.setTitle(journey.getTitle());
            journeyVO.setDescription(journey.getDescription());
            journeyVO.setWaypoints(waypointService.getAllWaypointsByJourney(journey));
            return journeyVO;
        }).forEach(journeyVOs::add);
        return journeyVOs;
    }

    public Journey add(Journey journey) {
        return journeyDAO.saveAndFlush(journey);
    }

    public boolean checkUserJourneyExist(User user, JourneyDTO journey) {
        return !journeyDAO.findAllByUserAndTitle(user, journey.getTitle()).isEmpty();
    }

    public boolean checkNewJourneyValidation(JourneyDTO journeyDTO) {
        if (journeyDTO == null) return false;
        return (journeyDTO.getTitle() != null && !journeyDTO.getTitle().isEmpty()) &&
                journeyDTO.getDescription() != null &&
                (journeyDTO.getWaypoints() != null && journeyDTO.getWaypoints().size() >= 2);
    }
}


