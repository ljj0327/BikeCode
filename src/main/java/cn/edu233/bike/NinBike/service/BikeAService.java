package cn.edu233.bike.NinBike.service;

import cn.edu233.bike.NinBike.pojo.Bike;
import org.springframework.data.geo.GeoResult;

import java.util.List;

public interface BikeAService {
    void save(Bike bike);

     List<GeoResult<Bike>> findNear(double longitude, double latitude);
}
