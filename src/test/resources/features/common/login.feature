Feature: Realiza Login de usuario

  Scenario:
    Given url baseUrl
    And path 'auth/login'
    And request { username: 'user', password: '1234' }
    When method post
    Then status 200
    And match response.accessToken != null
    And match response.refreshToken != null
    * def accessToken = response.accessToken
    * def refreshToken = response.refreshToken