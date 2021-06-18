Feature: Login verification check

  @test21
  Scenario: Login test1
    Given user login to application with "username" and "password"
    When user select "location" and do login
    Then verify Msg
    Then Close Window
