package com.ks.alarm;

import com.ks.model.SensorType;

import java.util.logging.Logger;

public class LoggingAlarmNotifier implements AlarmNotifier {
    private static final Logger logger = Logger.getLogger("CentralMonitoringService");

    @Override
    public void notify(SensorType type, int threshold, int value) {
        logger.warning(String.format("ALARM!! threshold exceeded: value=%d, threshold=%d, type=%s", value, threshold, type));
    }
}
