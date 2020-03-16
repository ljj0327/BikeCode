package cn.edu233.bike.NinBike.Controller;

import cn.edu233.bike.NinBike.pojo.User;
import cn.edu233.bike.NinBike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/user/genCode")
    @ResponseBody
    public boolean genVerfyCond(String countryCode,String phoneNum){
        boolean flag = userService.sendMsg(countryCode,phoneNum);
            return  flag;
    }

    @RequestMapping("/user/verify")
    @ResponseBody
    public boolean verify(String phoneNum,String verifyCode){
        return userService.verify(phoneNum,verifyCode);
    }
    @RequestMapping("/user/register")
    @ResponseBody
    public boolean reg(User user){
        boolean flag = true;
        //调用service将用户的数据保存起来
        try {
            userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            //将错误信息引入到log里面
            flag = false;
        }
        return  flag;
    }
    @RequestMapping("/user/deposit")
    @ResponseBody
    public boolean deposit(User user){
        boolean flag = true;
        try {
            userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
    @RequestMapping("/user/identify")
   @ResponseBody
    public boolean identify(User user){
        boolean flag = true;
        try {
            userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return  flag;

    }
}
