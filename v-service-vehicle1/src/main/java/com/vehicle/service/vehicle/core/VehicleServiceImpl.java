package com.vehicle.service.vehicle.core;

import com.vehicle.service.vehicle.client.api.VehicleService;
import com.vehicle.service.vehicle.client.model.Vehicle;

/**
 * Created by Shinelon on 2018/1/14.
 */
public class VehicleServiceImpl implements VehicleService {
    public Vehicle getVehicleByVin(String vin) throws Exception {
        //
        System.out.println("调用vehicle1方法!");
        Vehicle v = new Vehicle();
        v.setLince("京A888888");
        return v;
    }
}
