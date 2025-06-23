### 📘 Ta instrukcja jest również dostępna w [🇬🇧 wersji angielskiej](./contribution-en.md)


# Wkład w projekt

Dzięki, że chcesz pomóc! Zanim zgłosisz swój kod, przeczytaj i stosuj się do poniższych zasad, aby wszystko pozostało spójne i przejrzyste.

## 1. Wymagania wstępne

- Upewnij się, że Twoja gałąź jest zsynchronizowana z najnowszą wersją `develop` zanim rozpoczniesz pracę.

---

## 2. Śledzenie zadań – GitHub & Linear

- **Zgłoszenia (issues) tworzone są w GitHubie**, a następnie **synchronizowane automatycznie do Lineara**.
- Przypisz się do zadania w **Linearze**.
- W Linearze każde zadanie ma sugerowaną nazwę gałęzi. **Skopiuj ją** i utwórz branch **lokalnie**.
- Zawsze używaj **prefiksu ID zadania** (np. `rsb-123`) na początku tytułu PR.

Przykład:

`rsb-123-implement-instructor-registration`

---

## 3. Nazewnictwo gałęzi

Gałęzie powinny być nazwane wg formatu:

`<typ>/<id-zadania>-krótki-opis>`

Gdzie `<typ>` to jedna z wartości:
- `feature/` – dla nowych funkcjonalności
- `fix/` – dla poprawek błędów
- `chore/` – dla prac porządkowych i refaktoryzacji

**Przykłady:**
- `feature/rsb-10-setup-ci-pipeline-with-sonarqube`
- `fix/rsb-12-correct-token-expiry-logic`
- `chore/rsb-15-cleanup-unused-dependencies`

**Skopiuj nazwę bezpośrednio z Lineara.**  

**Uwaga:** Format nazw gałęzi wkrótce będzie sprawdzany automatycznie przez GitHub Actions.

---

## 4. Wiadomości commitów

- **Opcjonalnie**: używaj formatu [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

Przykłady:
```
feat: add instructor registration endpoint  
fix: correct session time overlap validation  
chore: update dependency versions  
```

---

## 5. Pull Requesty

- **Jeden PR = jedno zadanie w Linearze.**
- Tytuł PR powinien zaczynać się od ID zadania, np. `rsb-10: implement CI pipeline with SonarQube`.
- Przejdź przez checklistę z `.github/code_review_template.md`.
- Używaj `RESOLVE` w komentarzach PR do potwierdzenia, że feedback został wdrożony.

---

## 6. Komentarze w kodzie

- **Nie** dodawaj komentarzy wyjaśniających oczywisty kod.
- Komentarze używaj tylko, gdy:
    - Logika jest nieoczywista lub może zostać źle zinterpretowana
    - Chcesz zostawić konkretne **TODO**

Przykład:
```java
// TODO: validate edge case when user is already activated
```

---

## 7. Deprecjacja kodu

Jeśli coś oznaczasz jako przestarzałe, dodaj adnotację i uzasadnienie:

```java
@Deprecated("Use NewFeature instead")
public void oldMethod() {}
```

Dodatkowo:
- Wyjaśnij powód w opisie PR
- Zacznij dyskusję: kiedy i jak planujesz usunąć starą implementację

---

## 8. CI / Automatyzacja

Stopniowo wprowadzamy reguły CI, które będą:
- Wymagać poprawnych nazw gałęzi (np. `feature/rsb-XX-description`)
- Wymuszać format tytułu PR
- Przerywać buildy w razie niezaliczonych testów lub krytycznych problemów jakościowych (np. SonarQube)

---

## Pytania?

- Zadawaj pytania w komentarzach do PR
- Oznacz recenzenta lub maintenera w razie wątpliwości
