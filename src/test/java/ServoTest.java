import static java.lang.Thread.sleep;
import static org.alextheracer1.internal.components.Servo.buildPwmConfig;

import com.pi4j.Pi4J;
import com.pi4j.extension.Plugin;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.util.Console;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ServoTest {

  private static Pwm pwm;
  private static DigitalInput digitalInput;

  // BCM Values
  private static final int PIN_SERVO = 19;

  @Test
  public void test() {
    Assertions.assertTrue(true);
  }

  @Test
  public void testServo() throws InterruptedException {
    final var console = new Console();

    // Print program title/header
    console.title("<-- The Pi4J Project -->", "Minimal Example project");

    var pi4j = Pi4J.newAutoContext();

    pwm = pi4j.create(buildPwmConfig(pi4j, PIN_SERVO));

    var plugins = ServiceLoader.load(Plugin.class);

    int g = 0;

    while (g < 3) {
      for (int i = 43; i <= 142; i++) {
        pwm.on(10, i);
        sleep(100);
        System.out.println(i);
      }
      g++;

      pwm.off();
      pwm.on(10, 43);
      sleep(500);
    }

    pwm.off();

    // Shutdown Pi4J
    pi4j.shutdown();
  }
}
