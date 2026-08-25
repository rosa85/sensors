package com.ks.warehouse;

import com.ks.model.Measurement;
import com.ks.publisher.MeasurementPublisher;
import com.ks.sensor.SensorListener;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

public class WarehouseService {

    private final List<SensorListener> listeners;
    private final MeasurementPublisher publisher;

    public WarehouseService(List<SensorListener> listeners, MeasurementPublisher publisher){
        this.listeners = listeners;
        this.publisher = publisher;

    }
   public Disposable start() {
        List<Flux<Measurement>> emitters = listeners.stream().map(SensorListener::listen).collect(Collectors.toList());
        return Flux.merge(emitters).subscribe(publisher::publish);
    };
}
