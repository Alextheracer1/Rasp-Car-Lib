package org.alextheracer1.internal.utils;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmType;

public class ConfigUtils {
  private ConfigUtils() {}

  public static DigitalInputConfig buildInputConfig(Context pi4j, String name, int address, String provider) {
    return DigitalInput.newConfigBuilder(pi4j)
        .id("BCM" + address)
        .name(name)
        .address(address)
        .provider(provider)
        .build();
  }

  public static DigitalOutputConfig buildOutputConfig(Context pi4j, String name, int address, String provider) {
    return DigitalOutput.newConfigBuilder(pi4j)
        .id("BCM" + address)
        .name(name)
        .address(address)
        .initial(DigitalState.LOW)
        .shutdown(DigitalState.LOW)
        .provider(provider)
        .build();
  }

  public static PwmConfig buildPwmConfig(Context pi4j, String name, int address, PwmType pwmType, String provider) {
    return Pwm.newConfigBuilder(pi4j)
        .id("BCM" + address)
        .name(name)
        .address(address)
        .pwmType(pwmType)
        .provider(provider)
        .initial(0)
        .shutdown(0)
        .build();
  }

}
