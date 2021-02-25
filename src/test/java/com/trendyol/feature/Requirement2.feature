Feature: Requirement-2

  Scenario: Search with id

    Given Set the API
    When Search "Harry+Potter"
    Then Check the status code 200
    And Check the "Harry Potter and the Sorcerer's Stone" and get id

    When Search by id
    Then Check the title "Harry Potter and the Sorcerer's Stone"