# User service

De User Service is een microservice die verantwoordelijk is voor het beheer van gebruikers binnen de webshop.
Deze service biedt API-endpoints voor het registreren, aanmelden en opvragen van een een gebruiker.

## Functionaliteiten

- **Gebruikers registreren**
- **Gebruikers aanmelden**
- **Gegevens van je persoonlijk profiel opvragen**

## API Spec

De API-specificatie is te bekijken via de Swagger UI. Start de applicatie en navigeer 
naar [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html).

Voor het gebruik van de endpoints is authenticatie vereist (Bearer token).

## Implementeren microservice

De implementatie van deze microservice is op dezelfde manier uitgevoerd als de Product Service.

- **Kopiëren bestaande productfunctionaliteit**
- **Applicatie configuratie**
- **Database configuratie**
- **Proxy/API-Gateway configuratie**

Waarbij je nog steeds goed rekening moet houden met de configuratie van de applicatie, database die op andere poorten moeten draaien.

Alleen komt er in deze microservice kijken en dat is de code refactoren.

### Microservice code refactoren

De User service is namelijk afhankelijk van de Cart microservice en moet hiermee dus kunnen communiceren.
Ook zijn er een aantal zaken die niet langer nodig zijn in de User service. In de `UserEntity` de verwijzing
naar de `CartEntity` niet langer nodig aangezien we in de database de carts per gebruiker niet meer bijhouden, 
dat moet de taak van de Cart service worden.

Ook kunnen we de security configuratie aanpassen, aangezien we nu een eigen User service hebben kunnen we alle inkomende requests
toestaan zonder dat er authenticatie nodig is met uitzondering van de `/auth/me` endpoint.

## Conclusie

Het opsplitsen van de gebruikerfunctionaliteiten verliep moeizamer en complexer omdat er gecommuniceerd moet worden met de Cart service.

