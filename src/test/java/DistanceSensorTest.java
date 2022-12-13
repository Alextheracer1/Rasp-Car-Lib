import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import java.util.concurrent.atomic.AtomicLong;
import org.alextheracer1.internal.components.DistanceSensor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DistanceSensorTest {

  private static final int PIN_TRIGGER = 17;
  private static final int PIN_ECHO = 27;

  @Test
  public void testDistance() throws InterruptedException {

    var pi4j =  Pi4J.newAutoContext();

    DistanceSensor distanceSensor = new DistanceSensor(PIN_TRIGGER, PIN_ECHO);

    Assertions.assertTrue(distanceSensor.getDistance() >= 0);

  }

}
