package org.alextheracer1.internal;

import java.util.UUID;
import org.alextheracer1.internal.components.Esc;
import org.alextheracer1.internal.components.Servo;

public class Car {

  private UUID uuid;
  private Servo steeringServo;
  private Esc electronicSpeedController;

  public Car(UUID id, Servo steeringServo, Esc electronicSpeedController) {
    this.uuid = id;
    this.steeringServo = steeringServo;
    this.electronicSpeedController = electronicSpeedController;
  }


}
