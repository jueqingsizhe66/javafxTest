/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.data.Entity2;

import java.io.Serializable;

/**
 *
 * @author apple
 */
public class User implements Serializable
{
    private int uid;
    private String uname;
    private String upassword;

    public User() {
    }

    public User(String uname, String upassword) {
        this.uname = uname;
        this.upassword = upassword;
    }
    public User(int uid, String uname, String upassword) {
        this.uid =uid;
        this.uname = uname;
        this.upassword = upassword;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upassword='" + upassword + '\'' +
                '}';
    }
}
