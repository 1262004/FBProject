/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.service.ipml;

import com.vng.fresher.za.dao.UserDao;
import com.vng.fresher.za.dao.impl.jdbc.UserJdbcDao;
import com.vng.fresher.za.model.User;
import com.vng.fresher.za.service.UserService;
import com.vng.fresher.za.util.Helper;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author baonh2
 */
public class UserServiceImpl implements UserService {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_BANNED = "banned";

    private static final UserDao userDao = new UserJdbcDao();

    @Override
    public User login(String username, String password) {

        User user = userDao.selectByUsername(username);
        if (user != null) {
            String passHash = password;
            String roleHash = Helper.encrypt(user.getUsername() + ROLE_ADMIN + user.getRsalt());

            if (passHash.equals(user.getPassword())) {
                if (user.getRole().equals(roleHash)) {
                    user.setRole(ROLE_ADMIN);
                } else {
                    user.setRole(ROLE_USER);
                }
                return user;
            }
        }
        return null;
    }

    @Override
    public User register(String username, String password) {

        User user = userDao.selectByUsername(username);
        if (user == null) {
            if (Helper.validateUsername(username) && Helper.validatePassword(password)) {
                String pSalt = Helper.createSalt();
                String pHash = Helper.encrypt(username + password + pSalt);
                String rSalt = Helper.createSalt();
                String rHash = Helper.encrypt(username + ROLE_USER + rSalt);
                Timestamp registeredAt = new Timestamp(System.currentTimeMillis());

                user = new User(0, username, pHash, pSalt, rHash, rSalt, STATUS_ACTIVE, registeredAt);
                user = userDao.insert(user);
                if (user != null) {
                    user.setPassword(password);
                    user.setRole(ROLE_USER);
                }
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUser(int userId) {
        User user = userDao.selectById(userId);
        String rHash = Helper.encrypt(user.getUsername() + ROLE_ADMIN + user.getRsalt());
        if (rHash.equals(user.getRole())) {
            user.setRole(ROLE_ADMIN);
        } else {
            user.setRole(ROLE_USER);
        }
        return user;
    }
    
    @Override
    public User getUser(String username) {
        User user = userDao.selectByUsername(username);
        String rHash = Helper.encrypt(user.getUsername() + ROLE_ADMIN + user.getRsalt());
        if (rHash.equals(user.getRole())) {
            user.setRole(ROLE_ADMIN);
        } else {
            user.setRole(ROLE_USER);
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> users = userDao.selectAll();
        for (User user : users) {
            String rHash = Helper.encrypt(user.getUsername() + ROLE_ADMIN + user.getRsalt());
            if (rHash.equals(user.getRole())) {
                user.setRole(ROLE_ADMIN);
            } else {
                user.setRole(ROLE_USER);
            }
        }
        return users;
    }

    @Override
    public User changePassword(User user, String newPassword) {
        if (Helper.validatePassword(newPassword)) {
            String newSalt = Helper.createSalt();
            String newHash = Helper.encrypt(user.getUsername() + newPassword + newSalt);
            boolean success = userDao.updatePassword(user.getUsername(), newHash, newSalt);
            if (success) {
                user.setPassword(newPassword);
                user.setPsalt(newSalt);
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean changeRole(String username, String role) {

        if (role.equals(ROLE_ADMIN) || role.equals(ROLE_USER)) {
            String newSalt = Helper.createSalt();
            String newHash = Helper.encrypt(username + role + newSalt);
            boolean result = userDao.updateRole(username, newHash, newSalt);
            return result;
        }
        return false;
    }

    @Override
    public boolean changeStatus(String username, String status) {
        if (status.equals(STATUS_ACTIVE) || status.equals(STATUS_BANNED)) {
            boolean result = userDao.updateStatus(username, status);
            return result;
        }
        return false;
    }

    @Override
    public boolean deleleUser(int id) {
        boolean result = userDao.deleteById(id);
        return result;
    }

    @Override
    public List<User> getReporter(int appId) {
        return userDao.selectByAppId(appId);
    }
}
