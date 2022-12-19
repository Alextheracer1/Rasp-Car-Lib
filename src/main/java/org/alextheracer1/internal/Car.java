package org.alextheracer1.internal;

import java.util.UUID;
import org.alextheracer1.internal.components.Arduino;
import org.alextheracer1.internal.components.DistanceSensor;
import org.alextheracer1.internal.components.Esc;
import org.alextheracer1.internal.components.Servo;

public class Car {

  private final UUID uuid;
  private final Servo steeringServo;
  private final Esc electronicSpeedController;
  private final DistanceSensor distanceSensor;
  private final Arduino arduino;

  public Car(Servo steeringServo, Esc electronicSpeedController, DistanceSensor distanceSensor, Arduino arduino) {
    this.uuid = UUID.randomUUID();
    this.steeringServo = steeringServo;
    this.electronicSpeedController = electronicSpeedController;
    this.distanceSensor = distanceSensor;
    this.arduino = arduino;
  }

  public UUID getUuid() {
    return uuid;
  }

  public Servo getSteeringServo() {
    return steeringServo;
  }

  public Esc getElectronicSpeedController() {
    return electronicSpeedController;
  }

  public DistanceSensor getDistanceSensor() {
    return distanceSensor;
  }

  public Arduino getArduino() {
    return arduino;
  }
}
