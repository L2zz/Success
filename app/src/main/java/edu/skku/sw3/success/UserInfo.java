package edu.skku.sw3.success;

public class UserInfo {

    public String userEmail;
    public String password;

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserInfo(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

}

