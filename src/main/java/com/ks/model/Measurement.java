package com.ks.model;

public record Measurement(String sensorId, SensorType type, Integer value) {
}
