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

Voor het gebruik van de endpoints is authenticatie vereist (Bearer token). Je kunt een bearer token
bemachtigen door een gebruiker te registreren (`http://localhost:8082/api/auth/register`) en daarna in te 
loggen (`http://localhost:8082/api/auth/login`)met je credentials.

## Implementeren microservice

De implementatie van deze microservice is op dezelfde manier uitgevoerd als de Product Service.

- **Kopiëren bestaande user functionaliteiten**
- **Applicatie configuratie**: deze applicatie draait op poort `8082`
- **Database configuratie**:  database draait op poort `5434`.
- **Proxy/API-Gateway configuratie**

Zorg ervoor dat je goed rekening houdt met de configuratie van de applicatie en database, die op andere poorten moeten draaien dan andere services.

### Inter-servicecommunicatie opzetten

Om de microservice te integreren met de monolithische applicatie, moet de inter-servicecommunicatie worden opgezet.

De User Service moet kunnen communiceren met de Cart Service om een persoonlijke winkelmand aan te maken op het moment 
dat een nieuwe gebruiker zich registreert.

Hiervoor moet de Cart Service een extra endpoint hebben voor het creëren van een winkelmand voor een nieuwe gebruiker. 
Dit kan bijvoorbeeld een POST-request zijn naar `/api/cart` met een gebruikers-ID als parameter.

Om te communiceren met de Cart Service moet de User Service een HTTP-client hebben die requests kan sturen naar de Cart Service. 
Ik heb hiervoor gebruik gemaakt van de `RestTemplate` klasse van Spring.

Deze code is terug te vinden in `src/main/java/com/example/userservice/application/cart/CartService.class`:

```java
@Service
public class CartService implements CreateCart {
    @Value("${microservices.cart.url}")
    private String cartServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public CartId create(UserId userId) {
        final var request = CreateCartRequest.builder()
                .userId(userId.value().intValue())
                .build();

        final var response = restTemplate.postForEntity(
                String.format("%s/cart/create", cartServiceUrl),
                request,
                CartId.class);

        return response.getBody();
    }
}
```

Hierbij voer ik een POST request uit naar de Cart service met een `CreateCartRequest` als request body en 
ik geef aan dat ik een `CartId` object verwacht als response.

### Microservice code refactoren

De User Service is afhankelijk van de Cart microservice en moet hiermee dus kunnen communiceren. Ook zijn er een aantal 
zaken die niet langer nodig zijn in de User Service. Zo is in de UserEntity de verwijzing naar de CartEntity niet langer nodig 
aangezien we in de database de carts per gebruiker niet meer bijhouden. Dat moet nu de taak van de Cart Service worden.

Daarnaast kunnen we de security configuratie aanpassen. Aangezien we nu een eigen User Service hebben, kunnen we alle inkomende 
requests toestaan zonder dat er authenticatie nodig is, met uitzondering van de /auth/me endpoint.
