package com.ks.publisher;

import com.ks.model.Measurement;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

public class InMemoryMeasurementPublisher implements MeasurementPublisher {

    private final BlockingQueue<Measurement> topic;

    public InMemoryMeasurementPublisher(BlockingQueue<Measurement> topic) {
        this.topic = topic;
    }

    @Override
    public void publish(Measurement measurement) {
        topic.add(measurement);
    }

    @Override
    public Flux<Measurement> stream() {
        return Flux.<Measurement>create(emitter -> {

            Measurement m = null;

            while (!emitter.isCancelled()) {

                try {
                    m = topic.take();
                } catch (InterruptedException e) {
                    emitter.error(e);
                }
                emitter.next(m);

            }

            emitter.complete();

        }).subscribeOn(Schedulers.boundedElastic());
    }
}
