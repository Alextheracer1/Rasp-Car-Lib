package org.alextheracer1.internal.components;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import java.util.concurrent.atomic.AtomicLong;

public class DistanceSensor {

  private final int PIN_TRIGGER;
  private final int PIN_ECHO;

  public DistanceSensor(int PIN_TRIGGER, int PIN_ECHO) {
    this.PIN_TRIGGER = PIN_TRIGGER;
    this.PIN_ECHO = PIN_ECHO;
  }

  public int getPIN_TRIGGER() {
    return PIN_TRIGGER;
  }

  public int getPIN_ECHO() {
    return PIN_ECHO;
  }

  private DigitalInputConfig buildInputConfig(Context pi4j, int address) {
    return DigitalInput.newConfigBuilder(pi4j)
        .id("BCM" + address)
        .name("DistanceSensorInput")
        .address(address)
        .provider("pigpio-digital-input")
        .build();
  }

  private DigitalOutputConfig buildOutputConfig(Context pi4j, int address) {
    return DigitalOutput.newConfigBuilder(pi4j)
        .id("BCM" + address)
        .name("DistanceSensorOutput")
        .address(address)
        .initial(DigitalState.LOW)
        .shutdown(DigitalState.LOW)
        .provider("pigpio-digital-output")
        .build();
  }


  public float getDistance() throws InterruptedException {

    var pi4j =  Pi4J.newAutoContext();

    var output = pi4j.create(buildOutputConfig(pi4j, PIN_TRIGGER));

    var input = pi4j.create(buildInputConfig(pi4j, PIN_ECHO));

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

    return distance;

  }


}
