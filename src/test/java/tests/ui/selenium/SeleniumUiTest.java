package tests.ui.selenium;

import config.UserConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import steps.Hooks;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

//@CucumberOptions(plugin = "pretty", features = "src/test/resources/features")
public class SeleniumUiTest {

    @Test
    public void uiTest() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "\\dev\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.get(UserConfig.URL);

        WebElement login = driver.findElement(cssSelector("[id='UserLogin_username']"));
        WebElement password = driver.findElement(cssSelector("[id='UserLogin_password']"));
        WebElement signIn = driver.findElement(cssSelector("[type='submit']"));
        login.sendKeys(UserConfig.USER_LOGIN);
        password.sendKeys(UserConfig.USER_PASSWORD);
        signIn.click();

        WebElement userContextLogin = driver.findElement(cssSelector(".nav-profile span"));
        WebElement menuButton = driver.findElement(cssSelector(".fa-bars"));
        assertThat(userContextLogin.getText())
                .isEqualTo(UserConfig.USER_LOGIN)
                .as("Проверка, что мы авторизовались под правильным пользователем");

        menuButton.click();
        List<WebElement> menuItems = driver.findElements(xpath("//ul[@id='nav']//a[@data-target]/span"));
        assertThat(menuItems.size())
                .isEqualTo(14)
                .as("Проверяем, что загрузились все 14 элементов панели");

        WebElement users = driver.findElement(cssSelector("[data-target='#s-menu-users']"));
        users.click();
        driver.findElement(xpath("//a[@href='/user/player/admin']")).click();
        WebElement playersTable = driver.findElement(cssSelector("[id='payment-system-transaction-grid']"));
        Assertions.assertTrue(playersTable.isDisplayed());
        WebElement displayedAmountTip = driver.findElement(cssSelector(".summary"));
        assertThat(displayedAmountTip.getText()).startsWith("Displaying ").endsWith(" results.");

        WebElement externalIdColumn = driver
                .findElement(xpath("//a[@href='/user/player/admin?PlayerSearch_sort=unique_alias']"));
        externalIdColumn.click();
        Thread.sleep(2000);
        List<WebElement> elementList = driver.findElements(xpath("//table//tbody//td[3]"));
        String[] externalIds = new String[elementList.size()];
        for (int i = 0; i < elementList.size(); i++) {
            externalIds[i] = elementList.get(i).getText();
        }

        String[] sortedIds = externalIds.clone();
        Arrays.sort(sortedIds);
        System.out.println(Arrays.toString(externalIds));
        System.out.println(Arrays.toString(sortedIds));
        Assertions.assertTrue(Arrays.toString(externalIds).equals(Arrays.toString(sortedIds)));
        driver.quit();
    }
}