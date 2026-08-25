package com.ks.monitoring;

import com.ks.alarm.AlarmNotifier;
import com.ks.model.SensorType;
import com.ks.publisher.MeasurementPublisher;

public class CentralMonitoringService {

    private final Integer temperatureThreshold;
    private final Integer humidityThreshold;
    private final MeasurementPublisher publisher;
    private final AlarmNotifier alarmNotifier;


    public CentralMonitoringService(Integer temperatureThreshold, Integer humidityThreshold, MeasurementPublisher publisher, AlarmNotifier alarmNotifier) {
        this.temperatureThreshold = temperatureThreshold;
        this.humidityThreshold = humidityThreshold;
        this.publisher = publisher;
        this.alarmNotifier = alarmNotifier;
    }

    public void start() {

        publisher.stream().subscribe( measurement  -> {
            SensorType type = measurement.type();
            int value = measurement.value();
            switch (type) {
                case HUMIDITY -> checkThreshold(type, humidityThreshold, value);
                case TEMPERATURE -> checkThreshold(type, temperatureThreshold, value);
            }

        });
    }

    private void checkThreshold(SensorType type, int threshold, int value) {
        if (value > threshold) {
            alarmNotifier.notify(type, threshold, value);
        }
    }
}
