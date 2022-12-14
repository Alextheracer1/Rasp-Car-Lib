package org.alextheracer1;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import org.alextheracer1.internal.Car;
import org.alextheracer1.internal.components.Arduino;
import org.alextheracer1.internal.components.DistanceSensor;
import org.alextheracer1.internal.components.Esc;
import org.alextheracer1.internal.components.Servo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarLib {

  private static final Logger log = LoggerFactory.getLogger(CarLib.class);
  private final Context pi4j = Pi4J.newAutoContext();
  private final Car car;

  public CarLib(int ServoPin, int EscPin, int ArduinoBlockPin, int ArduinoReceivePin, int TriggerPin, int EchoPin) {

    Arduino arduino = new Arduino(pi4j, ArduinoBlockPin, ArduinoReceivePin);
    DistanceSensor distanceSensor = new DistanceSensor(pi4j, TriggerPin, EchoPin);
    Esc esc = new Esc(pi4j, EscPin);
    Servo servo = new Servo(pi4j, ServoPin);

    car = new Car(servo, esc, distanceSensor, arduino);

    initializeCar();

  }

  private void initializeCar () {
    car.getSteeringServo().enableServo();
    log.info("Servo has been initialized");
    car.getElectronicSpeedController().enableEsc();
    log.info("Esc has been initialized");

    deactivateArduino();
    log.info("Arduino disabled");
  }

  public double getDistance() {
    try {
      return car.getDistanceSensor().getDistance();
    } catch (InterruptedException e) {
      log.error("Error receiving distance from DistanceSenor", e);
    }
    return 0;
  }

  public void steerLeft (int time) {
    if (!car.getArduino().isEnabled()) {
      try {
        car.getSteeringServo().steerServo('l', time);
        log.info("Steering Servo to left for " + time/1000 + " seconds");
      } catch (InterruptedException e) {
        log.error("Error trying to steer left", e);
      }
    } else {
      log.warn("The Arduino is not disabled, cannot continue steering right");
    }
  }

  public void steerRight (int time) {
    if (!car.getArduino().isEnabled()) {
      try {
        car.getSteeringServo().steerServo('r', time);
        log.info("Steering Servo to right for " + time/1000 + " seconds");
      } catch (InterruptedException e) {
        log.error("Error trying to steer right", e);
      }
    } else {
      log.warn("The Arduino is not disabled, cannot continue steering right");
    }
  }

  public void driveForward (int time) {
    if (!car.getArduino().isEnabled()) {
      try {
        car.getElectronicSpeedController().controlEsc('f', time);
        log.info("Driving forwards for " + time/1000 + " seconds");
      } catch (InterruptedException e) {
        log.error("Error trying to drive forward", e);
      }
    } else {
      log.warn("The Arduino is not disabled, cannot continue driving forward");
    }
  }

  public void driveBackward (int time) {
    if (!car.getArduino().isEnabled()) {
      try {
        car.getElectronicSpeedController().controlEsc('b', time);
        log.info("Driving backwards for " + time/1000 + " seconds");
      } catch (InterruptedException e) {
        log.error("Error trying to drive backwards", e);
      }
    } else {
      log.warn("The Arduino is not disabled, cannot continue driving backwards");
    }
  }

  public void deactivateArduino () {
    log.info("deactivateArduino method called");

    car.getArduino().setControlOutput(false);
    log.info("Blocked Arduino");
    car.getElectronicSpeedController().enableEsc();
    log.info("Enabled ESC");
    car.getSteeringServo().enableServo();
    log.info("Enabled Servo");
  }

  public void activateArduino () {
    log.info("activateArduino method called");

    car.getArduino().setControlOutput(true);
    log.info("Released Arduino");
    car.getSteeringServo().deactivateServo();
    log.info("Deactivated Servo");
    car.getElectronicSpeedController().deactivateEsc();
    log.info("Deactivated ESC");

  }

  public void stopCar () {
    log.info("stopCar method called");

    try {
      if (car.getArduino().isEnabled()){
        deactivateArduino();
        car.getElectronicSpeedController().stop();
        activateArduino();
      } else {
        car.getElectronicSpeedController().stop();
      }

    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
