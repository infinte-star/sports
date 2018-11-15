package edu.bjtu.example.sportsdashboard;

import edu.bjtu.example.sportsdashboard.User;

public interface UserService {
    //查验登录
    public String checkLogin(User user);
    //注册用户
    public String register(User user);
}
