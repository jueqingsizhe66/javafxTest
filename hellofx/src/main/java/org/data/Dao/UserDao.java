/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.data.Dao;

import org.data.Entity.User;

import java.util.List;


/**
 *
 * @author apple
 */
public interface UserDao 
{
    public boolean addUser(User user);
    public boolean update(User user);
    public User getUserbyID(int id);
    public List<User> getallUser();
    public boolean certifyUser(String uname, String upassword);
            
}
