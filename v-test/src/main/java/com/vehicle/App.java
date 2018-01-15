package com.vehicle;

import com.vehicle.service.vehicle.client.api.VehicleService;
import com.vehicle.service.vehicle.client.model.Vehicle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring.xml");// 此文件

        VehicleService vehicleBaseService = (VehicleService) context
                .getBean("vehicleBaseService");
        try {
            for (int i = 0; i < 10; i++) {
                Vehicle v = vehicleBaseService
                        .getVehicleByVin("MMV2ARB25G0010730");
                System.out.println(v.getLince());
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
