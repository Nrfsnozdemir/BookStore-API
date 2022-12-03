package com.bookStore.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                //"pretty",
                "json:target/cucumber-report/cucumber.json",
                "html:target/cucumber-report.html"
        },
        features = "src/test/resources/features",
        glue = "com/bookStore/step_definitions",
        dryRun = false,
        tags = " "
)
public class Runner {
}
