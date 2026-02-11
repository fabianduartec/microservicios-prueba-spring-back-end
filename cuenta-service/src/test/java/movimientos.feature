Feature: F2-F3 - Movimientos (8081)

Background:
  * url 'http://localhost:8081/movimientos'
  * header Content-Type = 'application/json'

Scenario: F2 - POST movimientos/create (Deposito ✓)
  Given path 'create'
  And request
    """
    {
      "cuentaNumero": 585545,
      "movimientoValor": 100,
      "movimientoTipo": "DEPOSITO"
    }
    """
  When method POST
  Then status 201

Scenario: F3 - POST movimientos/create (Saldo Insuficiente)
  Given path 'create'
  And request
    """
    {
      "cuentaNumero": 585545,
      "movimientoValor": 5000,
      "movimientoTipo": "RETIRO"
    }
    """
  When method POST
  Then status 400
