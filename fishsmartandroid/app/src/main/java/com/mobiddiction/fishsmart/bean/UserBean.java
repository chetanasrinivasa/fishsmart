package com.mobiddiction.fishsmart.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class UserBean implements Serializable {
    public int id;
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    public String mobile;
    public ArrayList<CatchLogImageBean> image = new ArrayList();
}