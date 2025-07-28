import io.cucumber.core.cli.Main;

public class SimpleRunner {
    public static void main(String[] args) {
        String[] cucumberArgs = new String[]{
            "--glue", "stepDefinition",
            "--plugin", "pretty",
            "--plugin", "html:target/cucumber-reports.html",
            "--plugin", "json:target/cucumber-reports/cucumber.json",
            "src/main/resources/Features"
        };
        
        Main.main(cucumberArgs);
    }
}
