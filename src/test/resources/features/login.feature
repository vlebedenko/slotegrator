Feature: User authorization
  User authorization

  Scenario: successful authorization
    When Authorization page loaded
    Then Enter login
    Then Enter password
    Then Click "Sign in" input
    Then Check successful page loading as admin user