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

        publisher.stream().subscribe( m -> {

            switch (m.type()){
                case HUMIDITY -> checkThreshold(m.type(), humidityThreshold,  m.value());
                case TEMPERATURE -> checkThreshold(m.type(), temperatureThreshold,  m.value());
            }

        });
    }

    private void checkThreshold(SensorType type, int threshold, int value) {
        if (value > threshold) {
            logger.info(String.format("!!!! threshold %s for %s has been crossed by value %s !!!!!", threshold, type, value));
        }
    }
}
