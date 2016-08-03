package be.solid.paperboy;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"pretty", "html:build/reports/paperboy.html"},
        glue = {"be.solid.paperboy"}
)
public class RunCucumberTests {
}


