package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.xpath;

public class AuthPage {
    public SelenideElement login = $("[id='UserLogin_username']");
    public SelenideElement password = $("[id='UserLogin_password']");
    public SelenideElement signIn = $("[type='submit']");
    private String sign = "//input[@value='%s']";

    public void inputLogin(String user) { this.login.val(user); }

    public void inputPassword(String password) {
        this.password.val(password);
    }

    public void clickSignButton(String text) {
        $(xpath(String.format(sign, text))).click();
    }
}
