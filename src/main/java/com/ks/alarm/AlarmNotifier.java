package com.ks.alarm;

import com.ks.model.SensorType;

public interface AlarmNotifier {

    void notify(SensorType type, int threshold, int value);
}
