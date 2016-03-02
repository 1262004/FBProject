/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.fresher.za.service;

import com.vng.fresher.za.model.User;
import java.util.List;

/**
 *
 * @author quanvd
 */
public interface UserService {
    
    public User register(String username, String password);
    public User login(String username, String password);
    public User getUser(int userId);
    public User getUser(String username);
    public List<User> getUserList();
    public User changePassword(User user, String newPassword);
    public boolean changeRole(String username, String newRole);
    public boolean changeStatus(String username, String newStatus);
    public boolean deleleUser(int id);

    public List<User> getReporter(int appId);
}
