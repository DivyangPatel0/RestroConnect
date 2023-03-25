package com.Divyang.restroconnect;


public class UserHelperClass {

    String name, username, email, phoneNo, passcode;

    public UserHelperClass(){

    }

    public UserHelperClass(String name,String username,String email,String phoneNo,String passcode){
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.passcode = passcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
