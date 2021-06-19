package steps;

import io.cucumber.java.Before;

import static com.codeborne.selenide.Selenide.open;

public class Hooks {

    @Before
    public void openUrl() {
        open("http://test-app.d6.dev.devcaz.com/admin/login");
    }
}
