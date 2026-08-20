package utils;

import com.thoughtworks.gauge.AfterScenario;

public class Hooks {

    @AfterScenario
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}