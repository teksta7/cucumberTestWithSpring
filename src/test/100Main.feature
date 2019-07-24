Feature: Check server can handle errors

  Scenario: client makes call to GET /health
    When the client calls /badurl
    Then the client receives error status code of 500
