package com.vehicle.service.vehicle1.core;

import com.vehicle.service.vehicle1.client.api.VehicleService;
import com.vehicle.service.vehicle1.client.model.Vehicle;

/**
 * Created by Shinelon on 2018/1/14.
 */
public class VehicleServiceImpl implements VehicleService {
    public Vehicle getVehicleByVin(String vin) throws Exception {
        //
        Vehicle v = new Vehicle();
        v.setLince("京A888888");
        return v;
    }
}
