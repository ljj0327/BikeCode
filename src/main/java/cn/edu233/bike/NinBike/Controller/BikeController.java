package cn.edu233.bike.NinBike.Controller;

import cn.edu233.bike.NinBike.pojo.Bike;
import cn.edu233.bike.NinBike.service.BikeAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 标记这个类是一个用于介绍请求和相应用户的一个控住器
 * 加上@Controller注解后，Spring容器就对它实例化
 */
@Controller
public class BikeController {
    //到spring容器中查找BikeService 类型的实例，然后注入到BikeController实例中
    @Autowired
    private BikeAService bikeService;
    @RequestMapping("/bike/add")
    @ResponseBody
    public String add( Bike bike){
       // System.out.println(bike);
        //调用service层将数据存储到mongodb中
        bikeService.save(bike);
        return "success" ;
    }
    @RequestMapping("/bike/findNear")
    @ResponseBody
    public  List<GeoResult<Bike>>  findNear(double longitude, double latitude){
        List<GeoResult<Bike>> bikes = bikeService.findNear(longitude,latitude);
        return  bikes;
    }
}
