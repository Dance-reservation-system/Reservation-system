> 📘 Ten poradnik jest również dostępny w [🇬🇧 wersji angielskiej](manual-client-secret-setup.md)

# Ręczna konfiguracja `client_secret` w Keycloak (od wersji 25)

Począwszy od **Keycloak w wersji 25**, możliwość automatycznego importowania wartości `client_secret` podczas importu realm została **celowo wyłączona**. Zmiana ta dotyczy klientów typu confidential (OIDC), dla których sekret musi być teraz **ręcznie ustawiany w panelu administracyjnym** po imporcie.

Ten poradnik zawiera instrukcję krok po kroku, jak ręcznie wygenerować i skonfigurować `client_secret` w interfejsie administracyjnym Keycloak. Jest on przeznaczony dla deweloperów i administratorów, którzy korzystają z automatycznych wdrożeń lub skryptów i muszą teraz wykonać dodatkowy ręczny krok po imporcie klienta.

## 🔰 Pierwsze uruchomienie Keycloak

Podczas pierwszego uruchomienia instancji Keycloak przy użyciu obecnej konfiguracji projektu (na dzień **18 czerwca 2025**), system inicjalizuje się z predefiniowaną konfiguracją realm. Zawiera ona domyślnie zdefiniowane klienty, role i użytkowników, jednak — ze względu na ograniczenia wprowadzone w Keycloak 25+ — **nie zawiera wartości `client_secret` dla klientów typu confidential**.

Aby uruchomić instancję Keycloak, należy wykonać skrypt `docker-compose-keycloak`, który znajduje się w katalogu `.docker/`.

Jeśli korzystasz z IntelliJ IDEA, możesz to zrobić wygodnie, klikając jeden przycisk z panelu „Services” lub z poziomu edytora – jak pokazano na poniższym screenie:

📸 *Przycisk uruchamiający Keycloak w IntelliJ:*
![Keycloak startup screenshot](./images/run-keycloak-instance.png)

Aby sprawdzić, czy inicjalizacja Keycloak przebiegła pomyślnie, możesz otworzyć przeglądarkę i przejść pod adres:

[http://localhost:8081](http://localhost:8081)

Jeśli wszystko działa poprawnie, powinieneś zobaczyć ekran logowania do konsoli administracyjnej Keycloak.

📸 *Keycloak login form:*<br>
![Keycloak console](./images/keycloak-console-login-form.png)

Po uruchomieniu kontenera:

1. Realm zostaje zaimportowany za pomocą mechanizmu `--import-realm` , co konfiguruje lokalnie działającą instancję Keycloak.
2. Klienci confidential pojawią się w panelu administracyjnym, ale pole `client_secret` będzie **puste**.
3. Konieczne jest ręczne włączenie uwierzytelniania klienta oraz wygenerowanie sekretu przez interfejs graficzny.

## 🔐 Ręczne ustawienie `client_secret` w Keycloak

1. Zaloguj się do panelu administracyjnego Keycloak z wartościami username: admin , password: password
2. Wybierz odpowiedni realm (jeśli nie jest jeszcze aktywny). ![Manage realms](./images/manage-realms.png)
3. Przejdź do sekcji **Clients** (klienci) z lewego menu.
4. Wybierz klienta typu confidential, którego konfigurację chcesz uzupełnić. ![Manage clients](./images/manage-clients.png)
5. Przejdź do zakładki **Credentials**. ![Client credentials](./images/client-credentials.png)
6. Wygeneruj nowe poświadczenie klikając **Regenerate secret**. ![Regenerate client secret](./images/regenerate-client-secret.png)
7. Skopiuj wartość `client_secret` i użyj jej w konfiguracji swojej aplikacji.

## 🧩 Konfiguracja aplikacji do współpracy z lokalnym Keycloak

Aby aplikacja mogła poprawnie komunikować się z lokalnie działającą instancją Keycloak, należy skonfigurować w niej odpowiednie wartości połączeniowe.

W obecnym stanie projektu:
- Środowisko **`prod`** nie posiada jeszcze kompletnej infrastruktury umożliwiającej działanie.
- Środowisko **`dev`** wymaga dodatkowej konfiguracji i uruchomienia zależnych usług.
- Dlatego w celu lokalnego uruchomienia aplikacji, dostępne i wspierane jest środowisko **`local`**.

Konfiguracja środowiska `local` może być dostosowana indywidualnie — zgodnie z osobistymi preferencjami środowiska developerskiego np. inne porty.

Dla wygody, w repozytorium znajduje się przykładowy plik konfiguracyjny zawierający propozycję kompletnej konfiguracji, którą można w całości skopiować do własnego pliku `application-local.yaml`, aby szybko uruchomić aplikację lokalnie.

### 🔑 Ustawienie `client_secret` w konfiguracji aplikacji

Po wygenerowaniu sekretu klienta (`client_secret`) w panelu administracyjnym Keycloak (zgodnie z instrukcją powyżej), należy zaktualizować lokalny plik konfiguracyjny aplikacji, aby umożliwić jej poprawną autoryzację.

Zmień wartość właściwości **`spring.security.oauth2.client.registration.kamann.client-secret`**: ![Update application-local](./images/update-application-local.png)

✅ To wszystkie wymagane kroki, które należy wykonać, aby aplikacja mogła poprawnie komunikować się z lokalnie działającą instancją Keycloak.

## ℹ️ Dodatkowe informacje

- W celu ułatwienia lokalnego uruchamiania aplikacji, w pliku `application.yaml` ustawiono domyślny profil aktywacyjny:

  ```yaml
  spring:
    application:
      name: Reservation-system

    profiles:
      default: local

Dzięki temu — o ile konfiguracja środowiska uruchomieniowego (np. w IntelliJ IDEA) nie nadpisuje jawnie profilu (spring.profiles.active) — aplikacja uruchomi się domyślnie w trybie local.

⚠️ Warto pamiętać, że niektóre konfiguracje IDE (szczególnie wcześniej zapisane konfiguracje „Run/Debug”) mogą ustawiać własne wartości profilu środowiskowego — czasem nieświadomie.
Może to skutkować przypadkowym uruchomieniem aplikacji z innym profilem, np. dev, mimo że domyślnie wskazany jest local.

✅ Zalecane jest, aby w konfiguracji uruchamiania IDE upewnić się, że pole Active profile jest puste (jeśli chcemy skorzystać z domyślnego) lub świadomie ustawione, jeśli wymuszamy inny kontekst środowiskowy.

## 🧪 Test manualny

W projekcie znajduje się automatyczny test integracyjny, który weryfikuje poprawność całej konfiguracji autoryzacji i komunikacji z Keycloak.

Jeśli jednak chcesz **manualnie sprawdzić**, czy lokalna konfiguracja została poprawnie zastosowana, po uruchomieniu aplikacji otwórz przeglądarkę i przejdź pod adres:  
   [http://localhost:8080/test](http://localhost:8080/test)

Jeśli konfiguracja jest poprawna, powinieneś zobaczyć wygenerowane tokeny `access_token` w odpowiedzi — oznacza to, że aplikacja lokalnie poprawnie komunikuje się z instancją Keycloak.
