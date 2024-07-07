import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/features"
        , glue = "glue.stepDefs"
        , dryRun = true
        , monochrome = true
        , plugin = {"pretty", "html:target/Destination"}
)
public class TestRunner {
}