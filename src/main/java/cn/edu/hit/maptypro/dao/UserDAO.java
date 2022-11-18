package cn.edu.hit.maptypro.dao;

import cn.edu.hit.maptypro.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserDAO extends JpaRepository<User,Integer> {
        User findByUsername(String username);

        User findByEmail(String email);

        User getByUsernameAndPassword(String username,String password);

        User getByEmailAndPassword(String email, String password);
}
