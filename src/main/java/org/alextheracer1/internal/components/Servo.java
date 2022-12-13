package org.alextheracer1.internal.components;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;



public class Servo {
  /**
   * Default PWM frequency of the servo, based on values for SG92R
   */
  protected final static int DEFAULT_FREQUENCY = 50;

  /**
   * Default minimum angle of the servo motor, based on values for SG92R
   */
  protected final static float DEFAULT_MIN_ANGLE = -90;
  /**
   * Default maximum angle of the servo motor, based on values for SG92R
   */
  protected static final float DEFAULT_MAX_ANGLE = 90;

  /**
   * Default minimum PWM duty cycle to put the PWM into the minimum angle position
   */
  protected final static float DEFAULT_MIN_DUTY_CYCLE = 2;
  /**
   * Maximum PWM duty cycle to put the PWM into the maximum angle position
   */
  protected final static float DEFAULT_MAX_DUTY_CYCLE = 12;


  /**
   * Minimum angle of the servo motor used for this instance, should match previously tested real world values
   */
 // private final float minAngle;
  /**
   * Maximum angle of the servo motor used for this instance, should match previously tested real world values
   */
 // private final float maxAngle;
  /**
   * Minimum duty cycle of the servo motor for this instance, should match previously tested real world values
   */
  // private final float minDutyCycle;
  /**
   * Maximum duty cycle of the servo motor for this instance, should match previously tested real world values
   */
  // private final float maxDutyCycle;

  /**
   * Minimum value for user-defined range, defaults to 0
   */
  private float minRange = 0;
  /**
   * Maximum value for user-defined range, defaults to 1
   */
  private float maxRange = 1;




  private final static int inputPin = 0;
  private final Pwm pwm;


  public Servo(Context pi4j, int address) {
      this.pwm = pi4j.create(buildPwmConfig(pi4j, address));
    }

    /**
     * > Build a PWM configuration object for the given address
     *
     * @param pi4j The context of the Pi4J library.
     * @param address The GPIO pin number to use for the servo.
     * @return A {@link PwmConfig} object.
     */
    public static PwmConfig buildPwmConfig(Context pi4j, int address) {
      return Pwm.newConfigBuilder(pi4j)
          .id("BCM" + address)
          .name("ServoOutput")
          .address(address)
          .pwmType(PwmType.HARDWARE)
          .provider("pigpio-pwm")
          .initial(0)
          .shutdown(0)
          .build();
    }
  }

