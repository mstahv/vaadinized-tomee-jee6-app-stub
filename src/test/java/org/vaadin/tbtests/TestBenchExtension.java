package org.vaadin.tbtests;

import com.vaadin.testbench.TestBench;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.drone.spi.DroneInstanceEnhancer;
import org.jboss.arquillian.drone.spi.InstanceOrCallableInstance;
import org.openqa.selenium.WebDriver;

/**
 *
 * TODO split this into library or to TestBench
 */
public class TestBenchExtension implements LoadableExtension {

    @Override
    public void register(ExtensionBuilder eb) {
        eb.service(DroneInstanceEnhancer.class, TestBenchEnhancer.class);
    }

    static public class TestBenchEnhancer implements
            DroneInstanceEnhancer<WebDriver> {

        @Override
        public int getPrecedence() {
            return 0;
        }

        @Override
        public boolean canEnhance(InstanceOrCallableInstance instance,
                Class<?> droneType, Class<? extends Annotation> qualifier) {
            return WebDriver.class.isAssignableFrom(droneType);
        }

        @Override
        public WebDriver enhance(WebDriver instance,
                Class<? extends Annotation> qualifier) {
            System.out.println("Using TestBench enhanced the WebDriver");
            instance.manage().timeouts().setScriptTimeout(2, TimeUnit.SECONDS);
            return TestBench.createDriver(instance);
        }

        @Override
        public WebDriver deenhance(WebDriver enhancedInstance,
                Class<? extends Annotation> qualifier) {
            return enhancedInstance;
        }
    }

}
