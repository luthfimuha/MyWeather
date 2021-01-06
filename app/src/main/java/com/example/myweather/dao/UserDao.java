package com.example.myweather.dao;

import com.example.myweather.entity.User;

import java.util.List;

public interface UserDao {

    public void addUser(User user);
    public boolean checkUser(String username, String password);
    public boolean validateUsername(String username);
    public void saveCity(String username, String city);
    public Boolean checkSavedCity(String username, String city);
    public void removeCity(String username, String city);
    public List<String> getCitylist(String username);

}
