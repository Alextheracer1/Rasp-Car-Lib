package org.alextheracer1.internal.components;

import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
import org.alextheracer1.internal.utils.ConfigUtils;


public class Servo {

  private final Pwm pwm;

  private final int maxLeft = 53;

  private final int maxRight = 90;

  private final int middle = 66;


    public Servo(Context pi4j, int address) {
      this.pwm = pi4j.create(ConfigUtils.buildPwmConfig(pi4j, "Servo", address, PwmType.HARDWARE, "pigpio-pwm"));
    }

    public void steerServo (char direction, int time) throws InterruptedException {

      if (direction == 'l') {
        pwm.on(10, maxLeft);
        Thread.sleep(time);
        pwm.on(10, middle);
      } else if (direction == 'r') {
        pwm.on(10, maxRight);
        Thread.sleep(time);
        pwm.on(10, middle);
      }
    }

    public void enableServo() {
      pwm.on(10, middle);
    }

    public void deactivateServo() {
      pwm.off();
    }
  }

