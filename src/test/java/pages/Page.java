package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import config.UserConfig;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.By.xpath;

public class Page {
    public SelenideElement base = $(xpath("//body"));
    public SelenideElement headerLogo = $("[id='header-logo']");
    public SelenideElement currentUser = $(".nav-profile span");
    public SelenideElement menuButton = $(".fa-bars");
    public ElementsCollection menuOptions = $$(xpath("//ul[@id='nav']//a[@data-target]/span"));
    private String menuItem = "//ul[@id='nav']//a[@data-target]/span[contains(text(),'%s')]";
    private String menuSubItem = "(//a[contains(text(), '%s')])[1]";


    // Блок таблицы пользователей
    public SelenideElement mainBlock = $(xpath("//div[@class='panel-body']"));
    public SelenideElement createPlayerBtn = $(xpath("//a[@href='/user/player/create']"));
    public SelenideElement playersTable = $("[id='payment-system-transaction-grid']");
    public SelenideElement resultsMarker = $(".summary");
    private String columnName = "//a[@class='sort-link' and contains(., '%s')]";

    @Step("Проверка основных элементов страницы")
    public void checkElementsOnPage() {
        menuButton.waitUntil(visible, 10000);
        currentUser.should(visible, text(UserConfig.USER_LOGIN));
        assertThat(getMenuItems())
                .asList().contains("Agent Info", "Settings", "Games", "Money", "Content", "SEO", "Users", "Bonuses",
                "Jackpots", "Messaging", "FAQ", "Shop", "Logs", "Reports")
                .as("Проверяем список опций в меню, предполагая, что именно этот набор соответствует админ-бару");
    }

    @Step("Получить список всех опций из админ-бара")
    public List<String> getMenuItems() {
        if (base.is(cssClass(".body__en nav-min"))) {
            menuButton.click();
        }
        return menuOptions.texts();
    }

    @Step("Нажать на раздел {tab} И и перейти в подраздел {subTab} ")
    public void goTo(String tab, String subTab) {
        menuButton.waitUntil(visible, 10000);
        if (base.is(cssClass(".body__en nav-min"))) {
            menuButton.click();
        }
        $(xpath(String.format(menuItem, tab))).click();
        $(xpath(String.format(menuSubItem, subTab))).click();
    }

    @Step("Проверить, что таблица с игроками отобразилась")
    public void checkPlayersPage() {
        createPlayerBtn.should(visible);
        playersTable.should(visible);
        resultsMarker.should(visible);
        assertThat(resultsMarker.getText()).startsWith("Displaying ").endsWith(" results.");
    }

    @Step("Выполнить сортировку по столбцу {column}")
    public void sortingByColumn(String column) {
        $(xpath(String.format(columnName, column))).click();
        $(xpath("//div[contains(@class,'grid-view-loading')]")).should(visible);
        $(xpath("//div[contains(@class,'grid-view-loading')]")).should(not(visible));
        }

    @Step("Проверить сортировку в таблице")
    public void validateSorting() {
        ElementsCollection elementList = $$(xpath("//table//tbody//td[3]"));
        String[] columnValues = new String[elementList.size()];
        for (int i = 0; i < elementList.size(); i++) {
            columnValues[i] = elementList.get(i).getText();
        }
        String[] sortedIds = columnValues.clone();
        Arrays.sort(sortedIds);
        Assertions.assertTrue(Arrays.toString(columnValues).equals(Arrays.toString(sortedIds)));
    }
}
