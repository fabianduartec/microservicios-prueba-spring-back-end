Feature: F1 - Cuentas CRUD (8081)

Background:
  * url 'http://localhost:8081/cuentas'
  * header Content-Type = 'application/json'

  Given path 'create','100000000'
  And request
    """
    {
        "cuentaNumero": 585545,
        "cuentaTipo": "Corriente",
        "cuentaSaldoInicial": 1000,
        "cuentaEstado": "True",
        "clienteNombre": "Jose Lema"
    }
    """
  When method POST
    * def cuentaId = responseStatus == 201 ? response.cuentaId : 1
    * print 'DEBUG - cuentaId:', cuentaId


Scenario: GET cuentas/getAll
  Given path 'getAll'
  When method GET
  Then status 200


Scenario: GET cuentas/get/{id}
  Given path 'get', cuentaId
  When method GET
  Then status 200

Scenario: PATCH cuentas/update/{id}
  Given path 'update', cuentaId
  And request
    """
    {
      "cuentaTipo": "Ahorros",
      "cuentaEstado": "True"
    }
    """
  When method PATCH
  Then status 200