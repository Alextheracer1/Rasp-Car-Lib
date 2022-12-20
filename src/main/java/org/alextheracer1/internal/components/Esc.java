package org.alextheracer1.internal.components;

import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
import org.alextheracer1.internal.utils.ConfigUtils;

public class Esc {

  private final Pwm escPwm;

  private final int forwardMax = 95;
  private final int backwardMax = 50;
  private final int middle = 66;


  public Esc(Context pi4j, int address){
    this.escPwm = pi4j.create(ConfigUtils.buildPwmConfig(pi4j, "Esc", address, PwmType.HARDWARE, "pigpio-pwm"));
  }

  // f = forward, b = backwards
  public void controlEsc(char direction, int time) throws InterruptedException {
    if (direction == 'f') {
      escPwm.on(10, forwardMax);
      Thread.sleep(time);
      escPwm.on(10, middle);
    } else if (direction == 'b') {
      escPwm.on(10, backwardMax);
      Thread.sleep(time);
      escPwm.on(10, middle);
    }
  }

  public void enableEsc() {
    escPwm.on(10, middle);
  }

  public void deactivateEsc() {
    escPwm.off();
  }

  public void stop() throws InterruptedException {
    escPwm.on(10, middle);
    Thread.sleep(2000);
    escPwm.off();
  }

}
