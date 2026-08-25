package monitoring;

import com.ks.alarm.AlarmNotifier;
import com.ks.model.Measurement;
import com.ks.model.SensorType;
import com.ks.monitoring.CentralMonitoringService;
import com.ks.publisher.MeasurementPublisher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;



public class CentralMonitoringServiceTest {

    @Test
    void test() {
        MeasurementPublisher publisher = Mockito.mock(MeasurementPublisher.class);
        AlarmNotifier alarmNotifier = Mockito.mock(AlarmNotifier.class);
        CentralMonitoringService centralMonitoringService = new CentralMonitoringService(35,50, publisher, alarmNotifier);
        Mockito.when(publisher.stream()).thenReturn(Flux.just(new Measurement("t1", SensorType.TEMPERATURE, 50)));

        centralMonitoringService.start();
        Mockito.verify(alarmNotifier).notify(SensorType.TEMPERATURE, 35, 50);

    }
}
