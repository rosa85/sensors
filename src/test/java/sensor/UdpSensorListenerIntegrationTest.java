package sensor;


import com.ks.model.Measurement;
import com.ks.sensor.DefaultMeasurementParser;
import com.ks.sensor.UdpSensorListener;
import com.ks.simulator.SensorSimulator;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static com.ks.model.SensorType.TEMPERATURE;

public class UdpSensorListenerIntegrationTest {

    @Test
    void shouldParseIncomingUdpMeasurement() {
        UdpSensorListener listener = new UdpSensorListener(3344, TEMPERATURE, new DefaultMeasurementParser());

        Flux<Measurement> flux = listener.listen();
        StepVerifier.create(flux.take(1))
                .thenAwait(Duration.ofMillis(100))
                .then(() -> SensorSimulator.send("sensor_id=t1; value=30",  3344))
                .expectNextMatches(m -> m.sensorId().equals("t1") && m.value() == 30.0)
                .verifyComplete();


    }
}
