import static java.lang.Thread.sleep;

import com.pi4j.Pi4J;
import com.pi4j.extension.Plugin;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.util.Console;
import java.util.ServiceLoader;
import org.alextheracer1.internal.utils.ConfigUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ServoTest {

  private static Pwm pwm;

  private static final int PIN_SERVO = 18;

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

    pwm = pi4j.create(ConfigUtils.buildPwmConfig(pi4j, "Servo", PIN_SERVO, PwmType.HARDWARE, "pigpio-pwm"));

    var plugins = ServiceLoader.load(Plugin.class);


    pwm.on(10, 66);

    for (int i = 68; i < 95; i++) {
      pwm.on(10, i);
      System.out.println(i);
      sleep(200);
    }

    for (int i = 68; i > 50; i--) {
      pwm.on(10, i);
      System.out.println(i);
      sleep(200);
    }


    pwm.on(10, 66);
    sleep(2000);
    pwm.off();



    // Shutdown Pi4J
    pi4j.shutdown();
  }
}
