package cn.edu.hit.maptypro.dao;

import cn.edu.hit.maptypro.entity.domain.Journey;
import cn.edu.hit.maptypro.entity.domain.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaypointDAO extends JpaRepository<Waypoint, Integer> {
    List<Waypoint> findAllByJourney(Journey journey);
}