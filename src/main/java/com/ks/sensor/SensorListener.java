package com.ks.sensor;

import com.ks.model.Measurement;
import reactor.core.publisher.Flux;

public interface SensorListener {

    Flux<Measurement> listen();
}
