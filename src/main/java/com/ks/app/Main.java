package com.ks.app;

import com.ks.alarm.AlarmNotifier;
import com.ks.alarm.LoggingAlarmNotifier;
import com.ks.model.SensorType;
import com.ks.monitoring.CentralMonitoringService;
import com.ks.publisher.InMemoryMeasurementPublisher;
import com.ks.publisher.MeasurementPublisher;
import com.ks.sensor.DefaultMeasurementParser;
import com.ks.warehouse.WarehouseService;
import com.ks.simulator.SensorSimulator;
import com.ks.sensor.SensorListener;
import com.ks.sensor.UdpSensorListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    public static void main(String[] args) throws IOException {

        SensorSimulator.simulate();

        MeasurementPublisher publisher = new InMemoryMeasurementPublisher(new ArrayBlockingQueue<>(10000));

        SensorListener temperatureListener = new UdpSensorListener(3344, SensorType.TEMPERATURE, new DefaultMeasurementParser());
        SensorListener humidityListener = new UdpSensorListener(3355, SensorType.HUMIDITY, new DefaultMeasurementParser());

        WarehouseService warehouseService = new WarehouseService(List.of(temperatureListener, humidityListener), publisher);
        warehouseService.start();

        AlarmNotifier alarmNotifier = new LoggingAlarmNotifier();

        CentralMonitoringService centralService = new CentralMonitoringService(35,50, publisher, alarmNotifier);
        centralService.start();
    }
}
