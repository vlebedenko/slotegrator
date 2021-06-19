Feature: Players page checks

  Scenario: Players page checks
    When Authorization page loaded
    Then Enter login
    Then Enter password
    Then Click "Sign in" input
    Then Click "Users" and go to "Players" page
    Then Check players table visible

  Scenario: Sorting validation
    When Authorization page loaded
    Then Enter login
    Then Enter password
    Then Click "Sign in" input
    Then Click "Users" and go to "Players" page
    Then Click on column "External ID" for sorting table
    Then Sorting is ended
    Then Then validate table sorting
