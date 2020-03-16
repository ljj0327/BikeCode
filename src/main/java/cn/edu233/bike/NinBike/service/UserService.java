package cn.edu233.bike.NinBike.service;

import cn.edu233.bike.NinBike.pojo.User;

public interface UserService {
    boolean sendMsg(String countryCode, String phoneNum);

    boolean verify(String phoneNum, String verifyCode);

    void register(User user);

    void update(User user);
}
