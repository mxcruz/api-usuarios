Feature: Obtener usuarios

  Background:
    * url baseUrl
    * def loginResponse = call read('classpath:features/common/login.feature')
    * def authToken = 'Bearer ' + loginResponse.accessToken

  Scenario: Obtener usuarios con autenticación
    Given path 'api/v1/users'
    And header Authorization = authToken
    When method get
    Then status 200
    And match response == '#[]'

  Scenario: Obtener usuarios sin autenticación
    Given path 'api/v1/users'
    When method get
    Then status 403