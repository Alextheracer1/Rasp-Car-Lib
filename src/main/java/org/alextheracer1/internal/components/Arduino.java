package org.alextheracer1.internal.components;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import org.alextheracer1.internal.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arduino {

  private Context pi4j;

  private final DigitalOutput output;
  private final DigitalInput input;

  private static final Logger log = LoggerFactory.getLogger(Arduino.class);
  // Pin used for telling the Arduino to pass the receiver input to the Servo and ESC
  private final int PIN_BLOCK;

  private final int PIN_RECEIVE;

  private boolean isBlocked = false;



  public Arduino(Context pi4j, int PIN_BLOCK, int PIN_RECEIVE) {
    this.pi4j = pi4j;
    this.PIN_BLOCK = PIN_BLOCK;
    this.PIN_RECEIVE = PIN_RECEIVE;
    output = pi4j.create(ConfigUtils.buildOutputConfig(pi4j, "BlockArduinoOutput", PIN_BLOCK, "pigpio-digital-output"));
    input = pi4j.create(ConfigUtils.buildInputConfig(pi4j, "ReceiveArduinoInput", PIN_RECEIVE, "pigpio-digital-input"));
  }

  private void batteryLevelCritical() {
    input.addListener( event -> {
      if (event.state() == DigitalState.HIGH) {
        // TODO: Add Code to block User from entering inputs
        // TODO: Add Code to inform the user that the battery level is critical
      }
    });
  }

  public void setControlOutput(boolean enabled){
    if (enabled){
      output.state(DigitalState.HIGH);
      isBlocked = true;
      log.info("Output enabled");
    } else {
      output.state(DigitalState.LOW);
      isBlocked = false;
      log.info("Output disabled");
    }
  }

  public boolean isBlocked(){
    return isBlocked;
  }

}
