package com.mobiddiction.fishsmart;

import java.util.List;

public class RegistrationModel {
    /**
     * {
     "firstName" : "Great",
     "lastName" : "User",
     "password" : "Mobi12@3",
     "matchingPassword": "Mobi12@3",
     "email": "info@info.com",
     "mobile": "0412345678"
     }
     */

    public String firstName;
    public String lastName;
    public String password;
    public String matchingPassword;
    public String email;
    public List<Integer> imageId;

    public RegistrationModel(String firstName, String lastName, String password,String matchingPassword,String email,List<Integer> imageId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
        this.imageId = imageId;
    }
}
