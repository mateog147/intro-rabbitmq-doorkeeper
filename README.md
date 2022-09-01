
# Reto pratico Introducción Rabbit MQ.

Realizar el ejercicio del "Portero de la unidad".



Cuando el administrador le envía correspondencia a todo el piso
Cuando el administrador le envía correspondencia solo a los pisos impares


Se debe compartir el repositorio con la implementación de la solución, 
el repositorio debe tener todo lo necesario para que el proyecto corra independientemente.






## Referencia de la API
Objeto mensaje:
{
    "message":<mensaje del administrador>
}
#### Enivar 
```http
  POST /broker/fanout
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `body` | `mensaje` | json con el mensaje |


#### Transfer a rider
```http
  POST /broker/topic/?routingKey={routingKey}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `routingKey` | `Request Param` | odd.* impares y even.* pares|
