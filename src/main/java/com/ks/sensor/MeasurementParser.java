package com.ks.sensor;

import com.ks.model.Measurement;
import com.ks.model.SensorType;

public interface MeasurementParser {

    Measurement parse(String rawMessage, SensorType type);
}
