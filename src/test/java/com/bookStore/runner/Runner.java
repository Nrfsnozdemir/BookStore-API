package com.bookStore.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com/bookStore/step_definitions",
        dryRun = true,
        tags = ""
)
public class Runner {
}
