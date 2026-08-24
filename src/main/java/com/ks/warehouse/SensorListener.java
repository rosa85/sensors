package com.ks.warehouse;

import com.ks.model.Measurement;
import reactor.core.publisher.Flux;

public interface SensorListener {

    Flux<Measurement> listen();
}
