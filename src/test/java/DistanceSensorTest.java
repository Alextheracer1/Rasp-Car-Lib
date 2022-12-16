import org.alextheracer1.internal.components.DistanceSensor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class DistanceSensorTest {

  private static final int PIN_TRIGGER = 17;
  private static final int PIN_ECHO = 27;

  private static DistanceSensor distanceSensor;

  @BeforeAll
  public static void setup(){
    distanceSensor = new DistanceSensor(PIN_TRIGGER, PIN_ECHO);
  }

  @Test
  public void testDistance() throws InterruptedException {

    Assertions.assertTrue(distanceSensor.getDistance() >= 0);

  }

}
