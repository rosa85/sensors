package com.ks.sensor;

import com.ks.model.Measurement;
import com.ks.model.SensorType;

public class DefaultMeasurementParser implements MeasurementParser {

    @Override
    public Measurement parse(String rawMessage, SensorType type) {
        String[] parts = rawMessage.split(";");
        String sensorId = parts[0].split("=")[1].trim();
        int value = Integer.parseInt(parts[1].split("=")[1].trim());
        return new Measurement(sensorId, type, value);
    }
}
