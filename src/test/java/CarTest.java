import org.alextheracer1.CarLib;
import org.junit.jupiter.api.Test;

public class CarTest {


  @Test
  public void testCar() throws InterruptedException {
    CarLib car = new CarLib(19, 18, 26, 24, 5, 6);


    car.releaseArduino();
    Thread.sleep(2000);
    car.blockArduino();

    car.steerLeft(2000);
    car.driveForward(2000);

    car.shutdown();

  }

}
