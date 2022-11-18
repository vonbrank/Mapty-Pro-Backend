package cn.edu.hit.maptypro.service;

import cn.edu.hit.maptypro.dao.UserDAO;
import cn.edu.hit.maptypro.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user = getByName(username);
        return null != user;
    }

    public User getByName(String username) {
        return userDAO.findByUsername(username);
    }

    public User getByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User getByName(String username, String password) {
        return userDAO.getByUsernameAndPassword(username, password);
    }

    public User getByEmail(String email, String password) {
        return userDAO.getByEmailAndPassword(email, password);
    }

    public void add(User user) {
        userDAO.saveAndFlush(user);
    }
}
