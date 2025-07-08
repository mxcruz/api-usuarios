Feature: Login de usuario

  Background:
    * url baseUrl

  Scenario: Login exitoso con credenciales válidas
    Given path 'auth/login'
    And request { username: 'user', password: '1234' }
    When method post
    Then status 200
    And match response == { refreshToken: '#string', accessToken: '#string' }

  Scenario: Login fallido con credenciales inválidas
    Given path 'auth/login'
    And request { username: 'user', password: 'malapass' }
    When method post
    Then status 401
    And match response == { error: 'Bad credentials' }

  Scenario: Login fallido con usuario inexistente
    Given path 'auth/login'
    And request { username: 'noexiste', password: '1234' }
    When method post
    Then status 401
    And match response == { error: 'Bad credentials' }

  Scenario: Login fallido con password vacío
    Given path 'auth/login'
    And request { username: 'user', password: '' }
    When method post
    Then status 400
    And match response == { error: 'Username and password is required' }

  Scenario: Login fallido con username vacío
    Given path 'auth/login'
    And request { username: '', password: '1234' }
    When method post
    Then status 400
    And match response == { error: 'Username and password is required' }

  Scenario: Login fallido con username y password vacíos
    Given path 'auth/login'
    And request { username: '', password: '' }
    When method post
    Then status 400
    And match response == { error: 'Username and password is required' }
