package cn.edu.hit.maptypro.service;

import cn.edu.hit.maptypro.dao.JourneyDAO;
import cn.edu.hit.maptypro.entity.domain.Journey;
import cn.edu.hit.maptypro.entity.dto.journey.JourneyDTO;
import cn.edu.hit.maptypro.entity.vo.JourneyVO;
import cn.edu.hit.maptypro.entity.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return getJourneyVOByJourneyList(journeys);
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

    public List<JourneyVO> getAllJourneyWithWaypointByUserBySeed(int seed, int count) {
//        System.out.printf("seed = %d, count = %d%n", seed, count);
        List<Journey> allJourneys = journeyDAO.findAll();
        Set<Integer> selectedJourneyID = new HashSet<>();

        int bound = allJourneys.size();

        if (count >= bound) {
            return getJourneyVOByJourneyList(allJourneys);
        }

        Random random = new Random(seed);
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(bound);
            while (selectedJourneyID.contains(index)) {
                index = random.nextInt(bound);
            }
            selectedJourneyID.add(index);
        }

        List<Journey> selectedJourneys = new ArrayList<>();

        selectedJourneyID.forEach((index) -> selectedJourneys.add(allJourneys.get(index)));

        return getJourneyVOByJourneyList(selectedJourneys);
    }

    public List<JourneyVO> getJourneyVOByJourneyList(List<Journey> journeyList) {
        if (journeyList == null) return new ArrayList<>();
        List<JourneyVO> journeyVOs = new ArrayList<>();
        journeyList.stream().map((Journey journey) -> {
            JourneyVO journeyVO = new JourneyVO();
            journeyVO.setId(journey.getId());
            journeyVO.setTitle(journey.getTitle());
            journeyVO.setDescription(journey.getDescription());
            journeyVO.setWaypoints(waypointService.getAllWaypointsByJourney(journey));
            return journeyVO;
        }).forEach(journeyVOs::add);
        return journeyVOs;
    }
}


