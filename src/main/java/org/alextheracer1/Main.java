package org.alextheracer1;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  MinimalExample.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static java.lang.Thread.sleep;
import static org.alextheracer1.internal.components.Servo.buildPwmConfig;

import com.pi4j.Pi4J;
import com.pi4j.extension.Plugin;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.util.Console;
import java.util.ServiceLoader;

/**
 * <p>This example fully describes the base usage of Pi4J by providing extensive comments in each step.</p>
 *
 * @author Frank Delporte (<a href="https://www.webtechie.be">https://www.webtechie.be</a>)
 * @version $Id: $Id
 */
public class Main {

    private static Pwm pwm;

    private static final int PIN_SERVO = 19;


    /**
     * This application blinks a led and counts the number the button is pressed. The blink speed increases with each
     * button press, and after 5 presses the application finishes.
     *
     * @param args an array of {@link String} objects.
     * @throws Exception if any.
     */
    public static void main(String[] args) throws Exception {
        // Create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // Print program title/header
        console.title("<-- The Pi4J Project -->", "Minimal Example project");

        // ************************************************************
        //
        // WELCOME TO Pi4J:
        //
        // Here we will use this getting started example to
        // demonstrate the basic fundamentals of the Pi4J library.
        //
        // This example is to introduce you to the boilerplate
        // logic and concepts required for all applications using
        // the Pi4J library.  This example will do use some basic I/O.
        // Check the pi4j-examples project to learn about all the I/O
        // functions of Pi4J.
        //
        // ************************************************************

        // ------------------------------------------------------------
        // Initialize the Pi4J Runtime Context
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // The 'Pi4J' static class includes a few helper context
        // creators for the most common use cases.  The 'newAutoContext()'
        // method will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'
        var pi4j = Pi4J.newAutoContext();

        // ------------------------------------------------------------
        // Output Pi4J Context information
        // ------------------------------------------------------------
        // The created Pi4J Context initializes platforms, providers
        // and the I/O registry. To help you to better understand this
        // approach, we print out the info of these. This can be removed
        // from your own application.
        // OPTIONAL


        // Here we will create I/O interfaces for a (GPIO) digital output
        // and input pin. We define the 'provider' to use PiGpio to control
        // the GPIO.

        /*
        var buttonConfig = DigitalOutput.newConfigBuilder(pi4j)
                .id("led")
                .name("LED Flasher")
                .address(PIN_SERVO)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .provider("pigpio-digital-output");
        var servo = pi4j.create(buttonConfig);

        */

        pwm = pi4j.create(buildPwmConfig(pi4j, PIN_SERVO));

        var plugins = ServiceLoader.load(Plugin.class);


        int g = 0;

        while (g < 20) {
            for (int i = 43; i <= 142; i++) {
                pwm.on(10, i);
                sleep(5);
                System.out.println(i);
            }
            g++;

            pwm.off();
            pwm.on(10, 43);
            sleep(500);
        }

        pwm.off();

            int random = (int) ((Math.random() * (143 - 43)) + 43);
            pwm.on(10, random);



        // ------------------------------------------------------------
        // Terminate the Pi4J library
        // ------------------------------------------------------------
        // We we are all done and want to exit our application, we must
        // call the 'shutdown()' function on the Pi4J static helper class.
        // This will ensure that all I/O instances are properly shutdown,
        // released by the the system and shutdown in the appropriate
        // manner. Terminate will also ensure that any background
        // threads/processes are cleanly shutdown and any used memory
        // is returned to the system.

        // Shutdown Pi4J
        pi4j.shutdown();
    }
}