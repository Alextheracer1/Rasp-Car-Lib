package org.alextheracer1.internal.components;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import org.alextheracer1.internal.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arduino {

  // Declare member variables for Input and Output
  private final DigitalOutput output;
  private final DigitalInput input;

  // Initialize Logger
  private static final Logger log = LoggerFactory.getLogger(Arduino.class);

  private boolean isBlocked = false;



  public Arduino(Context pi4j, int PIN_BLOCK, int PIN_RECEIVE) {
    // Pin used for telling the Arduino to pass the receiver input to the Servo and ESC
    output = pi4j.create(ConfigUtils.buildOutputConfig(pi4j, "BlockArduinoOutput", PIN_BLOCK, "pigpio-digital-output"));
    // TODO: Input to Raspberry for battery level
    input = pi4j.create(ConfigUtils.buildInputConfig(pi4j, "ReceiveArduinoInput", PIN_RECEIVE, "pigpio-digital-input"));
  }


  // TODO: Meth
  private void batteryLevelCritical() {
    /* input.addListener( event -> {
      if (event.state() == DigitalState.HIGH) {
        // TODO: Add Code to block User from entering inputs
        // TODO: Add Code to inform the user that the battery level is critical
      }
    });

     */
  }

  public void setControlOutput(boolean enabled){
    if (enabled){
      output.setState(true);
      isBlocked = true;
      log.info("Arduino is being blocked");
    } else {
      output.setState(false);
      isBlocked = false;
      log.info("Arduino is not being blocked");
    }
  }

  public boolean isBlocked(){
    return isBlocked;
  }


}
