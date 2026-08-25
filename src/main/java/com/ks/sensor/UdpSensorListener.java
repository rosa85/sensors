package com.ks.sensor;

import com.ks.model.Measurement;
import com.ks.model.SensorType;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

public class UdpSensorListener implements SensorListener {

    private final int port;
    private final SensorType type;
    private final MeasurementParser parser;

    private static final Logger logger = Logger.getLogger("UdpSensorListener");

    public UdpSensorListener(int port, SensorType type, MeasurementParser parser) {
        this.port = port;
        this.type = type;
        this.parser = parser;
    }


    @Override
    public Flux<Measurement> listen() {

       return  Flux.<Measurement>create(emitter -> {
                    DatagramSocket socket;
                    try {
                        socket = new DatagramSocket(port);
                        socket.setSoTimeout(1000);
                    } catch (IOException e) {
                        emitter.error(e);
                        return;
                    }

                    emitter.onDispose(socket::close);

                    byte[] buffer = new byte[512];

                    while (!emitter.isCancelled()) {
                        try {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                            socket.receive(packet);

                            String raw = new String(packet.getData(), 0, packet.getLength());
                            logger.info(String.format("Received data %s", raw));

                            Measurement measurement = parser.parse(raw, type);
                            emitter.next(measurement);

                        } catch (SocketTimeoutException ignored) {

                        } catch (IOException e) {
                            if (!emitter.isCancelled()) {
                                emitter.error(e);
                            }
                            return;
                        }
                    }

                    emitter.complete();
                })
                .subscribeOn(Schedulers.boundedElastic());
    }


}
