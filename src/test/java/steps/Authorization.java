package steps;

import com.codeborne.selenide.Condition;
import config.UserConfig;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AuthPage;

public class Authorization {

    AuthPage authPage = new AuthPage();

    @When("Authorization page loaded")
    public void authorizationPageLoaded() {
        authPage.login.waitUntil(Condition.visible, 10000);
        authPage.password.should(Condition.visible);
    }

    @Then("Enter login")
    public void enterLogin() {
        authPage.inputLogin(UserConfig.USER_LOGIN);
    }

    @Then("Enter password")
    public void enterPassword() {
        authPage.inputPassword(UserConfig.USER_PASSWORD);
    }

    @Then("Click {string} input")
    public void clickInput(String arg0) {
        authPage.clickSignButton("Sign in");
    }


}
