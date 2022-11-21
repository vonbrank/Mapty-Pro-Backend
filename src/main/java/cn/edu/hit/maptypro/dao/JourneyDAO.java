package cn.edu.hit.maptypro.dao;

import cn.edu.hit.maptypro.entity.domain.Journey;
import cn.edu.hit.maptypro.entity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JourneyDAO extends JpaRepository<Journey, Integer> {
    List<Journey> findAllByUser(User user);

    List<Journey> findAllByUserAndTitle(User user, String title);
}
