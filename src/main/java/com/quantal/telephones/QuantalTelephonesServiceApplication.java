package com.quantal.telephones;

import de.invesdwin.instrument.DynamicInstrumentationLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.quantal", scanBasePackageClasses = {com.quantal.telephones.config.shared.SharedConfig.class})
public class QuantalTelephonesServiceApplication {

	static {
		//Starts the aspectj weaver so that we can weave the compile time aspects
		DynamicInstrumentationLoader.waitForInitialized(); //dynamically attach java agent to jvm if not already present
		DynamicInstrumentationLoader.initLoadTimeWeavingContext(); //weave all classes before they are loaded as beans
	}

	public static void main(String[] args) {
		SpringApplication.run(QuantalTelephonesServiceApplication.class, args);
	}
}
