package cn.edu233.bike.NinBike.service;

import cn.edu233.bike.NinBike.pojo.User;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceimpl implements  UserService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean sendMsg(String countryCode, String phoneNum) {
        boolean flag = true;
        //调用腾讯云的短信API
        //短信模板ID
        int templateId = 546374;
        //短信签名
        String smsSign = "程序猿757公众号";
        int appid = Integer.parseInt(stringRedisTemplate.opsForValue().get("appid"));
        String appkey = stringRedisTemplate.opsForValue().get("appkey");
       //随机生成一个6位数验证码
        Random random = new Random();
        String code ="";
        for (int i = 0;i<6;i++){
            code+=random.nextInt(10);
        }
        SmsSingleSenderResult result = null;
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
             result = ssender.send(0, countryCode, phoneNum, "【程序猿757公众号】您的注册验证码：" + code + "，如非本人操作，请忽略本短信！", "", "");
            // result = ssender.sendWithParam(countryCode,phoneNum,templateId,code,smsSign,"","");
            //将发送的手机号作为key，验证码为value保存到redis里面
            stringRedisTemplate.opsForValue().set(phoneNum,code,120,TimeUnit.SECONDS);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean verify(String phoneNum, String verifyCode) {
        boolean flag = false;
        //调用RedisTempiate ，根据手机号的key查找对应的验证码
        //Redis中保存的真是验证码
        String code = stringRedisTemplate.opsForValue().get(phoneNum);
        //将用户传入的验证码跟实际的验证码进行对比
        if(code !=null && code.equals(verifyCode)){
            flag = true;
        }
        return flag;
    }

    @Override
    public void register(User user) {
        //调用mongodb的dao 将用户数据储存
        mongoTemplate.insert(user);
    }

    @Override
    public void update(User user) {
        //insert 如果_id对应数据不存在那么插入 存在就更新
        //根据用户的手机号，对应数据进行更新，但是手机号不是主键_id
        //mongoTemplate.insert(user);
        Update update = new Update();
        if(user.getDeposit()!=null) {
            update.set("deposit", user.getDeposit());
        }
        if(user.getStatus() !=null){
            update.set("status",user.getStatus());
        }
        if(user.getName()!=null){
            update.set("Name",user.getName());
        }
        if(user.getIdNum()!=null){
            update.set("IdNum",user.getIdNum());
        }
        mongoTemplate.updateFirst(new Query(Criteria.where("phoneNum").is(user.getPhoneNum())),update,User.class);

    }
}
