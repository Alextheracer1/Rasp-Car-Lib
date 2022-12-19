package org.alextheracer1;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import org.alextheracer1.internal.Car;
import org.alextheracer1.internal.components.Arduino;
import org.alextheracer1.internal.components.DistanceSensor;
import org.alextheracer1.internal.components.Esc;
import org.alextheracer1.internal.components.Servo;

public class CarLib {

  private final Context pi4j = Pi4J.newAutoContext();
  private Car car;

  public void setupCar(int ServoPin, int EscPin, int ArduinoBlockPin, int ArduinoReceivePin, int TriggerPin, int EchoPin) {

    Arduino arduino = new Arduino(pi4j, ArduinoBlockPin, ArduinoReceivePin);
    DistanceSensor distanceSensor = new DistanceSensor(pi4j, TriggerPin, EchoPin);
    Esc esc = new Esc(pi4j, EscPin);
    Servo servo = new Servo(pi4j, ServoPin);


    car = new Car(servo, esc, distanceSensor, arduino);

  }

  public float getDistance() throws InterruptedException {
    return car.getDistanceSensor().getDistance();
  }

  public void steerLeft (int time) {
    try {
      car.getSteeringServo().steerServo('l', time);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void steerRight (int time) {
    try {
      car.getSteeringServo().steerServo('r', time);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void driveForward (int time) {
    try {
      car.getElectronicSpeedController().controlEsc('f', time);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void driveBackward (int time) {
    try {
      car.getElectronicSpeedController().controlEsc('b', time);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void blockArduino () {
    car.getArduino().setControlOutput(true);
  }

  public void releaseArduino () {
    car.getArduino().setControlOutput(false);
  }

  public void stopCar () {
    try {
      blockArduino();
      car.getElectronicSpeedController().stop();
      releaseArduino();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
