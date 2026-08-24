package com.ks.app;

import com.ks.model.SensorType;
import com.ks.monitoring.CentralMonitoringService;
import com.ks.publisher.InMemoryMeasurementPublisher;
import com.ks.publisher.MeasurementPublisher;
import com.ks.sensor.DefaultMeasurementParser;
import com.ks.sensor.WarehouseService;
import com.ks.simulator.SensorSimulator;
import com.ks.warehouse.SensorListener;
import com.ks.warehouse.UdpSensorListener;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    public static void main(String[] args) {

        System.out.println("Press any key to close");

        SensorSimulator.simulate();

        MeasurementPublisher publisher = new InMemoryMeasurementPublisher(new ArrayBlockingQueue<>(10000));

        SensorListener temperatureListener = new UdpSensorListener(3344, SensorType.TEMPERATURE, new DefaultMeasurementParser());
        SensorListener humidityListener = new UdpSensorListener(3355, SensorType.HUMIDITY, new DefaultMeasurementParser());

        WarehouseService warehouseService = new WarehouseService(List.of(temperatureListener, humidityListener), publisher);
        warehouseService.start();

        CentralMonitoringService centralService = new CentralMonitoringService(35,50, publisher);
        centralService.start();

    }
}
