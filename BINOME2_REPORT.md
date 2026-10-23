# Report Binôme 2 – Spécifications & Automatisation QA

**Projekt:** Web-Anwendung-Test (Playwright Java, JUnit 5, Maven, Page Object Model, Gherkin)
**Rolle:** Binôme 2 – Gherkin-Szenarien, JUnit-5-Tests in `WebInterfaceTest.java`, Assertions, Maven-Build
**Branch:** `tests-binome2` (zwei Pull Requests nach `main`)

---

## 1. Aufgabe

Aufbauend auf den HTML-Seiten und den Page Objects `IndexPageObjectModel` / `ConfirmationPageObjectModel` von Binôme 1: Erstellung der beiden Basisklassen `AbstractPageObjectModel` und `PlaywrightAbstractTester`, Umsetzung aller Testfälle und Validierung des Maven-Builds.

## 2. Erstellte Dateien

| Datei | Zweck |
|---|---|
| `AbstractPageObjectModel.java` | Basisklasse aller Page Objects: `page`, `getProjectRoot()`, `getTitle()`, `getUrl()` |
| `PlaywrightAbstractTester.java` | Browser-Lebenszyklus (`@BeforeAll`, `@BeforeEach`, `@AfterEach`, `@AfterAll`), neuer Browser-Kontext pro Test |
| `WebInterfaceTest.java` | Alle Testfälle, jeweils mit Gherkin-Szenario als Kommentar |
| `.gitignore` | Schließt `target/` und `.vscode/` aus |

## 3. Erweiterte Dateien

- **`IndexPageObjectModel.java`:** `getHeadingText()`, `recupererMessageAlertes()`, `getMessageColor()`, `getVilleSelectionnee()`
- **`index.html`:** Erfolgsmeldung grün / Fehlermeldung rot, zusätzliche Meldung abhängig von der Checkbox
- **`pom.xml`:** Abhängigkeit `junit-jupiter-params` für parametrisierte Tests
- **`README.md`:** Gherkin-Szenarien für alle neuen Testfälle

## 4. Testfälle

| TC | Inhalt |
|---|---|
| TC1 | Titel „Formulaire Inscription“ und Überschrift „Inscription“ |
| TC2 | Gültiges Formular mit Checkbox → grüne Meldung + „Vous recevrez nos alertes.“ |
| TC2b | Checkbox an- und wieder abhaken → „Vous ne recevrez pas d'alertes.“ |
| TC2c | Name leer → rote Fehlermeldung, Nutzer bleibt auf `index.html` |
| TC3 | Navigation zur Bestätigungsseite (URL, Titel, Überschrift) |
| TC4 | Navigation zurück zur Startseite |
| TC5 | „Paris“ ist die Standard-Stadt |
| TC6 | Parametrisierter Test (`@ParameterizedTest` + `@CsvSource`) mit 7 Kombinationen |

**Ergebnis:** `mvn test` → 14 Tests, 0 Failures, BUILD SUCCESS

## 5. Umsetzung des Tutor-Feedbacks

- Checkbox-Verhalten (angehakt / nicht angehakt) getestet → TC2, TC2b
- Standardwert „Paris“ getestet → TC5
- Farbe der Meldung abhängig von der Vollständigkeit des Formulars → TC2c, TC6
- Parametrisierter Test für mehrere Kombinationen → TC6
- Mehrere Assertions pro Test in `assertAll` gebündelt, mit Beschreibung pro Prüfung

## 6. Prinzipien

- **Kein direkter DOM-Zugriff in den Tests:** Alle Selektoren und Interaktionen liegen in den Page Objects.
- **Nachverfolgbarkeit:** Jede Given/When/Then-Zeile entspricht einer Codezeile.
- **Unabhängige Tests:** Jeder Test läuft in einem eigenen Browser-Kontext und kann einzeln gestartet werden.

## 7. Schwierigkeiten

- Erste Version von TC2 griff direkt auf den DOM zu → auf Page-Object-Methoden umgestellt
- Windows-Pfade (`\`) in `file:///`-URLs → Pfad in `getProjectRoot()` angepasst
- Formulierungen wie „die Seite öffnet sich“ in prüfbare Assertions übersetzt (URL + Titel + Überschrift)
