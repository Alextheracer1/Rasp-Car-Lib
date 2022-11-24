import static java.lang.Thread.sleep;
import static org.alextheracer1.internal.components.Servo.buildPwmConfig;

import com.pi4j.Pi4J;
import com.pi4j.extension.Plugin;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.util.Console;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ServoTest {

  private static Pwm pwm;

  private static final int PIN_SERVO = 19;

@Test
public void test(){
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

  while (g < 20) {
    for (int i = 43; i <= 142; i++) {
      pwm.on(10, i);
      sleep(5);
      System.out.println(i);
    }
    g++;

    pwm.off();
    pwm.on(10, 43);
    sleep(500);
  }

  pwm.off();

  int random = (int) ((Math.random() * (143 - 43)) + 43);
  pwm.on(10, random);

  // Shutdown Pi4J
  pi4j.shutdown();
}
}
