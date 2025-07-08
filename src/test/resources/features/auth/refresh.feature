Feature: Refresh token

  Background:
    * url baseUrl
    * def loginResponse = call read('classpath:features/common/login.feature')
    * def validRefreshToken = loginResponse.refreshToken

  Scenario: Refresh con token válido
    Given path 'auth/refresh'
    And request { refreshToken: '#(validRefreshToken)' }
    When method post
    Then status 200
    And match response.accessToken != null
    And match response.refreshToken != null

  Scenario: Refresh con token vacío
    Given path 'auth/refresh'
    And request { refreshToken: '' }
    When method post
    Then status 400
    And match response.error == 'Refresh token is required'

  Scenario: Refresh con token inválido
    Given path 'auth/refresh'
    And request { refreshToken: 'invalid.token.value' }
    When method post
    Then status 401
    And match response.error == 'Invalid refresh token'