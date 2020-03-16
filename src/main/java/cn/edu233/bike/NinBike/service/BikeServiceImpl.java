package cn.edu233.bike.NinBike.service;

import cn.edu233.bike.NinBike.pojo.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeAService{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void save(Bike bike) {
        //调用具体的业务
       // mongoTemplate.insert(bike,"bikes");
          mongoTemplate.insert(bike);//在bike的类中添加了注解，注解里保存映射关系
    }
/**
 * 根据当前的经纬度，查找附近的单车
 */
    @Override
    public List<GeoResult<Bike>> findNear(double longitude, double latitude) {
        NearQuery nearQuery = NearQuery.near(longitude,latitude);
        //查找的范围和距离单位
        nearQuery.maxDistance(0.2, Metrics.KILOMETERS);

      GeoResults<Bike>  goResults = mongoTemplate.geoNear(nearQuery.query(new Query(Criteria.where("status").is(0)).limit(20)),Bike.class);
            return  goResults.getContent();
    }
}
