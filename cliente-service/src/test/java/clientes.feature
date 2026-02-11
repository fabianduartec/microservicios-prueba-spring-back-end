Feature: F1 - Clientes CRUD (8080)

Background:
  * url 'http://localhost:8080/clientes'
  * header Content-Type = 'application/json'

  Given path 'create'
  And request
    """
    {
      "clienteNombre": "Jose Lema",
      "clienteGenero": "M",
      "clienteEdad": "20",
      "clienteIdentificacion": "100000000",
      "clienteDireccion": "Otavalo sn y principal",
      "clienteTelefono": "098254785",
      "clienteContrasena": "1234",
      "clienteEstado": "True"
    }
    """
  When method POST
    * def clienteId = responseStatus == 201 ? response.personaId : 1
    * print 'Cliente ID FINAL:', clienteId

Scenario: GET getAll
  Given path 'getAll'
  When method GET
  Then status 200

Scenario: GET get/{id}
  Given path 'get', clienteId
  When method GET
  Then status 200

Scenario: PUT update/{id}
  Given path 'update', clienteId
  And request
    """
    {
      "clienteNombre": "Jose Lema UPDATE",
      "clienteGenero": "M",
      "clienteEdad": "20",
      "clienteDireccion": "Otavalo sn",
      "clienteTelefono": "098254785",
      "clienteEstado": "True"
    }
    """
  When method PUT
  Then status 200

Scenario: PUT update/{id}/password
  Given path 'update', clienteId, 'password'
  And request
    """
    {
      "clienteContrasenaActual": "1234",
      "clienteContrasenaNueva": "12344"
    }
    """
  When method PUT
  Then status 200

Scenario: DELETE delete/{id}
  Given path 'delete', clienteId
  When method DELETE
  Then status 204