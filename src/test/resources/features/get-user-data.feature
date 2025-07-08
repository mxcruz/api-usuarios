Feature: Obtener información del usuario

  Background:
    * url baseUrl
    * def loginResponse = call read('classpath:features/common/login.feature')
    * def authToken = 'Bearer ' + loginResponse.accessToken

  Scenario: Obtener información del usuario autenticado
    Given path 'api/v1/users/me'
    And header Authorization = authToken
    When method get
    Then status 200
    And match response.username == 'user'
    And match response.email == 'user@maxicruz.com'
    And match response.createdOn == '#string'

  Scenario: Obtener información del usuario sin autenticación
    Given path 'api/v1/users/me'
    When method get
    Then status 403
