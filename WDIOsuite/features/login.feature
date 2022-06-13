Feature: Login to www.advantageonlineshopping.com

  Scenario: Login attempt
    Given I am on the homepage
    When I click on the user icon on the homepage
    And I enter "WillPQA" into the username field on the sign in popup
    And I enter "Hoy87111" into the password field on the sign in popup
    And I click on sign in
    Then Sign in success should be valid