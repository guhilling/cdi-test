import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import de.hilling.junit.cdi.cucumber.CucumberCdi;

@RunWith(CucumberCdi.class)
@CucumberOptions(features = {"src/test/resources/de/hilling/junit/cdi/cucumber"})
public class TestRunner {
}
