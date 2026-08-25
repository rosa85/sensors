package publisher;

import com.ks.model.Measurement;
import com.ks.publisher.InMemoryMeasurementPublisher;
import com.ks.publisher.MeasurementPublisher;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import java.util.concurrent.ArrayBlockingQueue;
import static com.ks.model.SensorType.TEMPERATURE;

public class InMemoryMeasurementPublisherTest {


    @Test
    void shouldEmitIncomingEvent() {
       MeasurementPublisher publisher = new InMemoryMeasurementPublisher(new ArrayBlockingQueue<>(1));
       StepVerifier.create(publisher.stream().take(1))
               .then(() -> publisher.publish(new Measurement("t1", TEMPERATURE, 25)))
               .expectNextMatches(m -> m.sensorId().equals("t1") && m.value() == 25)
               .verifyComplete();
    }
}
