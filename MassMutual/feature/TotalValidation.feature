Feature: Verify the total balance matches to the sum of the values 

  @test
  Scenario: total match
    Given launch the application
    Then verify the count of values
    Then verify values are greater than zero
    And verify the format of the values
    Then verify Total Balance
