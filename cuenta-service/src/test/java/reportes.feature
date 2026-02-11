Feature: F4 - Reportes Estado Cuenta (8081)

Background:
  * url 'http://localhost:8081'

Scenario: F4 - GET cuentas/reportes/generar
  Given path 'cuentas', 'reportes', 'generar'
  And param cliente = 100000000
  And param fechaInicio = '2026-02-01T00:00:00'
  And param fechaFin = '2026-02-28T23:59:59'
  When method GET
  Then status 200
  * match response.cuentas != null
  * match response.cuentas[0].numeroCuenta == 585545
  * match response.movimientos != null
  * match response.movimientos[0].movimiento == 100.00
