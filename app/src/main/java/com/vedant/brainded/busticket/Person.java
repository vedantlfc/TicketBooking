package com.vedant.brainded.busticket;

/**
 * Created by Dell on 6/16/2017.
 */

public class Person {

    String name;
    String phone;
    String email;

    public Person(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
