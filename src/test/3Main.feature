Feature: Success response from rest uri

  Scenario: client makes call to GET /health
    When the client calls /url
    Then the client receives response status code of 203
