package com.ks.monitoring;

import com.ks.model.SensorType;
import com.ks.publisher.MeasurementPublisher;

import java.util.logging.Logger;

public class CentralMonitoringService {

    private final Integer temperatureThreshold;
    private final Integer humidityThreshold;
    private final MeasurementPublisher publisher;
    private static final Logger logger = Logger.getLogger("CentralMonitoringService");

    public CentralMonitoringService(Integer temperatureThreshold, Integer humidityThreshold, MeasurementPublisher publisher) {
        this.temperatureThreshold = temperatureThreshold;
        this.humidityThreshold = humidityThreshold;
        this.publisher = publisher;
    }

    public void start() {

        publisher.stream().subscribe( measurement  -> {

            switch (measurement.type()){
                case HUMIDITY -> checkThreshold(measurement.type(), humidityThreshold,  measurement.value());
                case TEMPERATURE -> checkThreshold(measurement.type(), temperatureThreshold,  measurement.value());
            }

        });
    }

    private void checkThreshold(SensorType type, int threshold, int value) {
        if (value > threshold) {
            logger.warning(String.format("ALARM!! threshold exceeded: value=%d, threshold=%d, type=%s", value, threshold, type));
        }
    }
}
