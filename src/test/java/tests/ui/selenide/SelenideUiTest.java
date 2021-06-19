package tests.ui.selenide;

import org.junit.jupiter.api.Test;
import pages.AuthPage;
import pages.Page;

import static com.codeborne.selenide.Selenide.open;
import static config.UserConfig.*;

public class SelenideUiTest {

    AuthPage authPage;
    Page page;

    @Test
    public void uiTest() {
        open(URL);

        authPage = new AuthPage();
        page = new Page();

        authPage.inputLogin(USER_LOGIN);
        authPage.inputPassword(USER_PASSWORD);

        authPage.clickSignButton("Sign in");
        page.checkElementsOnPage();

        page.goTo("Users", "Players");
        page.checkPlayersPage();

        page.sortingByColumn("External ID");
        page.validateSorting();
    }
}