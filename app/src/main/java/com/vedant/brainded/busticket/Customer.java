package com.vedant.brainded.busticket;

/**
 * Created by Dell on 6/23/2017.
 */

public class Customer {
    String name;
    String phone;
    String email;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Customer(){

    }

    public Customer(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }


}
