package cn.edu233.bike.NinBike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//是SpringBoot的入口程序，在Spring启动时，会进行扫描，扫描带有特殊注解的类
@SpringBootApplication
public class NinBikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NinBikeApplication.class, args);
	}

}
