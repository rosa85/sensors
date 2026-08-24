package com.ks.publisher;

import com.ks.model.Measurement;
import reactor.core.publisher.Flux;

public interface MeasurementPublisher {

    void publish(Measurement measurement);

    Flux<Measurement> stream();
}
