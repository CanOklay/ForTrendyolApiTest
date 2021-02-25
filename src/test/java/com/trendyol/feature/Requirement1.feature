Feature: Requirement-1

  Scenario: Search "Harry Potter" and take the id of "Harry Potter and the Sorcerer's Stone"

    Given Set the API
    When Search "Harry+Potter"
    Then Check the status code 200
    And Check the "Harry Potter and the Sorcerer's Stone" and get id
