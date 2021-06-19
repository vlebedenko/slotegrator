package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.Page;

import static com.codeborne.selenide.Condition.*;

public class MainPageLoaded {

    Page page = new Page();

    @Then("Check successful page loading as admin user")
    public void checkSuccessfulPageLoading() {
        page.checkElementsOnPage();
    }

    @Given("Click {string} and go to {string} page")
    public void clickAndGoToPage(String arg0, String arg1) {
        page.goTo("Users", "Players");
    }

    @Then("Check players table visible")
    public void checkPlayersTableVisible() {
        page.checkPlayersPage();
    }

    @Then("Click on column {string} for sorting table")
    public void clickOnColumnForSortingTable(String arg0) {
        page.sortingByColumn("External ID");
    }

    @Then("Sorting is ended")
    public void sortingIsEnded() {
        page.playersTable.shouldHave(not(cssClass("grid-view grid-view-loading")));
    }

    @Then("Then validate table sorting")
    public void thenValidateTableSorting() {
        page.validateSorting();
    }
}
