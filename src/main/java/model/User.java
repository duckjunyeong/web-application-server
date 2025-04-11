package model;

import util.HttpRequestHandler;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;
    private Map<String, String> map;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        setMap();
    }

    private void setMap() {
        map = new HashMap<>();
        map.put("userId", getUserId());
        map.put("name", getName());
        map.put("email", getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getMap(){
        return map;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
