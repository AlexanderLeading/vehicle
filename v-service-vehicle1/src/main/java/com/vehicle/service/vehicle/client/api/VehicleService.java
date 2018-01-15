package com.vehicle.service.vehicle.client.api;

import com.vehicle.service.vehicle.client.model.Vehicle;

/**
 * Created by Shinelon on 2018/1/14.
 */
public interface VehicleService {
    public Vehicle getVehicleByVin(String vin) throws Exception;
}
