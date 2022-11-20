package cn.edu.hit.maptypro.service;

import cn.edu.hit.maptypro.dao.UserDAO;
import cn.edu.hit.maptypro.entity.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    public User getByNameAndEmail(User user) {
        user = userHtmlEscape(user);

        User userByUsername = null;
        User userByEmail = null;
        if (user.getUsername() != null && !user.getUsername().isEmpty())
            userByUsername = getByName(user.getUsername(), user.getPassword());
        if (user.getEmail() != null && !user.getEmail().isEmpty())
            userByEmail = getByEmail(user.getEmail(), user.getPassword());

        if (userByUsername != null) return userByUsername;
        return userByEmail;
    }

    public boolean checkUserRepetition(User user) {
        user = userHtmlEscape(user);

        User userByUsername = null;
        User userByEmail = null;
        if (user.getUsername() != null && !user.getUsername().isEmpty()) userByUsername = getByName(user.getUsername());
        if (user.getEmail() != null && !user.getEmail().isEmpty()) userByEmail = getByEmail(user.getEmail());

        return userByUsername != null || userByEmail != null;
    }

    public User userHtmlEscape(User user) {
        if (user == null) return null;
        String username = user.getUsername();
        if (username == null) username = "";
        else username = HtmlUtils.htmlEscape(username);
        String email = user.getEmail();
        if (email == null) email = "";
        else email = HtmlUtils.htmlEscape(email);

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(user.getPassword());

        return newUser;
    }

    public boolean checkRegisterUserValidation(User user) {
        if (user == null) return false;
        return user.getUsername() != null && !user.getUsername().isEmpty() &&
                user.getPassword() != null && !user.getPassword().isEmpty();
    }

    public boolean checkLoginUserValidation(User user) {
        if (user == null) return false;
        return ((user.getUsername() != null && !user.getUsername().isEmpty()) ||
                (user.getEmail() != null && !user.getEmail().isEmpty())) &&
                (user.getPassword() != null && !user.getPassword().isEmpty());
    }

    public void add(User user) {
        userDAO.saveAndFlush(user);
    }
}
