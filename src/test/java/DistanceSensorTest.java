import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

public class DistanceSensorTest {

  private static final int PIN_TRIGGER = 17;
  private static final int PIN_ECHO = 27;

  @Test
  public void testDistance() throws InterruptedException {

    var pi4j =  Pi4J.newAutoContext();




    var config = DigitalOutput.newConfigBuilder(pi4j)
        .address(PIN_TRIGGER)
        .initial(DigitalState.LOW)
        .shutdown(DigitalState.LOW)
        .provider("pigpio-digital-output")
        .build();

    var output = pi4j.create(config);

    var config2 = DigitalInput.newConfigBuilder(pi4j)
        .address(PIN_ECHO)
        .provider("pigpio-digital-input")
        .build();

    var input = pi4j.create(config2);


    AtomicLong unixTimeStart = new AtomicLong(-1);
    AtomicLong unixTimeEnd = new AtomicLong(-1);

    input.addListener(e -> {
      if (e.state() == DigitalState.HIGH) {
        unixTimeStart.set(System.currentTimeMillis());
      } else if (e.state() == DigitalState.LOW){
        if (unixTimeStart.get() != -1) {
          unixTimeEnd.set(System.currentTimeMillis());
        }
      }
    });


    output.state(DigitalState.HIGH);
    Thread.sleep(0, 1);
    output.state(DigitalState.LOW);






    while (unixTimeStart.get() == -1 || unixTimeEnd.get() == -1) {
      Thread.sleep(500);
    }

    System.out.println(input.state());

    float timeElapsed = unixTimeEnd.get() - unixTimeStart.get();
    System.out.println("TimeElapsed: " + timeElapsed);

    float distance = (timeElapsed * 34.3f) / 2f;

    System.out.println("Distance: " + distance);

    pi4j.shutdown();
  }

}
