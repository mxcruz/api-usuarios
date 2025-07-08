Feature: Registro de usuario

  Background:
    * url baseUrl

  Scenario: Registro exitoso con datos válidos
    Given path 'auth/register'
    And request { username: 'nuevo', password: '1234', email: 'nuevo@maxicruz.com' }
    When method post
    Then status 201
    And match response == { refreshToken: '#string', accessToken: '#string' }
  @ignore
  Scenario: Registro fallido con username existente
    Given path 'auth/register'
    And request { username: 'admin', password: '1234', email: 'admin@maxicruz.com' }
    When method post
    Then status 400
    And match response == { error: 'El usuario ya existe' }
  @ignore
  Scenario: Registro fallido con email existente
    Given path 'auth/register'
    And request { username: 'otro', password: '1234', email: 'admin@maxicruz.com' }
    When method post
    Then status 400
    And match response == { error: 'El email ya está registrado' }

  Scenario: Registro fallido con username vacío
    Given path 'auth/register'
    And request { username: '', password: '1234', email: 'nuevo@maxicruz.com' }
    When method post
    Then status 400
    And match response == { error: 'Username, password and email is required' }

  Scenario: Registro fallido con password vacío
    Given path 'auth/register'
    And request { username: 'nuevo', password: '', email: 'nuevo@maxicruz.com' }
    When method post
    Then status 400
    And match response == { error: 'Username, password and email is required' }

  Scenario: Registro fallido con email vacío
    Given path 'auth/register'
    And request { username: 'nuevo', password: '1234', email: '' }
    When method post
    Then status 400
    And match response == { error: 'Username, password and email is required' }

  Scenario: Registro fallido con todos los campos vacíos
    Given path 'auth/register'
    And request { username: '', password: '', email: '' }
    When method post
    Then status 400
    And match response == { error: 'Username, password and email is required' }
