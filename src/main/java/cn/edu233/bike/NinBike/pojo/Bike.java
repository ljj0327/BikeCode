package cn.edu233.bike.NinBike.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.Indexed;
/*
* bike这个类跟mongodb中的bikes collection关联
* */
@Document(collection = "bikes")
public class Bike {
    //主键（唯一、建立索引），id对应的是mongodb中的_id
    @Id
    private  String id;

    //表示经纬度的数组[经度，纬度]
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private  double[]  location;
    //建立索引
    @Indexed
    private  long bikeNo;
    private  int  status;

    public String getId() {
        return id;
    }


    public int getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public long getBikeNo() {
        return bikeNo;
    }

    public void setBikeNo(long bikeNo) {
        this.bikeNo = bikeNo;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }
}
