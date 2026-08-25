package com.ks.simulator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Random;

public class SensorSimulator {

    public static void simulate() {

        Thread thread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                boolean b = random.nextBoolean();
                int i =  random.nextInt(70);

                if(b) {
                    send(String.format("sensor_id=h1; value=%s", i), 3355);
                } else {
                    send(String.format("sensor_id=t1; value=%s", i), 3344);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        thread.start();
    }
    public static void send(String message, int port) {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            channel.send(buffer, new InetSocketAddress("localhost", port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
