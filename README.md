# Web-Anwendung-Test

# Playwright Web-Testing Project - HTW

Projet d'automatisation de tests navigateur avec **Playwright (Java)**, **JUnit 5** et **Maven**, basé sur le design pattern **Page Object Model (POM)** et documenté selon la démarche **BDD (Gherkin)**.

---

## 1. Vue d'ensemble du projet

Ce projet a pour objectif de valider les fonctionnalités interactives et la navigation d'une interface web simple composée de deux pages HTML locales :
- `index.html` : Formulaire d'inscription comprenant un champ texte avec son label, une liste déroulante (combobox), une case à cocher (checkbox), un bouton de validation avec script inline et un lien de navigation.
- `confirmation.html` : Seconde page affichant la confirmation et un lien retour vers l'accueil.

L'architecture sépare rigoureusement les responsabilités conformément aux critères d'évaluation :
- **Page Object Pattern** : Encapsulation des sélecteurs et des interactions navigateur.
- **JUnit 5 & Playwright** : Exécution des assertions sans manipulation directe du DOM dans les méthodes de test.
- **Spécifications Gherkin** : Traçabilité complète entre les exigences et le code de test (`Given / When / Then`).

---

## 2. Structure du projet (Conventions Maven)

```text
playwright-exercise/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── webapp/
    │       ├── index.html                  # Page principale du formulaire
    │       └── confirmation.html           # Seconde page de destination
    └── test/
        └── java/
            ├── AbstractPageObjectModel.java   # Classe de base POM (Playwright Page)
            ├── PlaywrightAbstractTester.java  # Gestion du cycle de vie du navigateur
            ├── IndexPageObjectModel.java      # Page Object pour index.html
            ├── ConfirmationPageObjectModel.java # Page Object pour confirmation.html
            └── WebInterfaceTest.java          # Tests JUnit 5 documentés en Gherkin
```

---

## 3. Spécification des cas de test (Gherkin / BDD)

Conformément au document méthodologique `LeseeckeTestfallbeschreibung.pdf`, chaque cas de test formalise une exigence avec les mots-clés `Given` (Précondition), `When` (Action / Déclencheur) et `Then` (Résultat attendu).

### TC1 : Vérification du titre et des éléments initiaux de la page d'accueil
* **Priorité** : Haute
* **Objectif** : S'assurer que la page d'accueil se charge correctement et que son titre est conforme.
```gherkin
Feature: Chargement de la page d'accueil
  Scenario: La page principale affiche le titre attendu
    Given la page index.html est ouverte dans le navigateur
    When la page est entièrement chargée
    Then le titre de l'onglet est "Formulaire Inscription"
    And l'en-tête principal affiche "Inscription"
```

---

### TC2 : Soumission valide du formulaire d'inscription
* **Priorité** : Critique
* **Objectif** : Vérifier que la saisie d'un nom et la sélection d'une ville affichent le message dynamique attendu.
```gherkin
Feature: Soumission du formulaire
  Scenario: Saisie valide des informations utilisateur
    Given la page index.html est ouverte dans le navigateur
    When l'utilisateur saisit "Alice" dans le champ nom d'utilisateur
    And sélectionne la ville "Berlin" dans la liste déroulante
    And coche la case "S'abonner aux alertes"
    And clique sur le bouton "Valider"
    Then le message "Inscription enregistree pour Alice a Berlin !" est affiché à l'écran
```

---

### TC3 : Navigation vers la page de confirmation (Lien aller)
* **Priorité** : Haute
* **Objectif** : Valider la redirection vers la deuxième page via le lien hypertexte.
```gherkin
Feature: Navigation inter-pages
  Scenario: Redirection vers la page de confirmation
    Given l'utilisateur est sur la page index.html
    When il clique sur le lien "Aller à la page de confirmation"
    Then la page confirmation.html s'ouvre
    And le titre de la page devient "Confirmation"
    And l'en-tête affiche "Page de confirmation"
```

---

### TC4 : Navigation retour vers l'accueil (Lien retour)
* **Priorité** : Moyenne
* **Objectif** : Vérifier que le lien de la seconde page ramène bien sur l'accueil.
```gherkin
Feature: Navigation de retour
  Scenario: Retour à l'accueil depuis la confirmation
    Given l'utilisateur est sur la page confirmation.html
    When il clique sur le lien "Retour à l'accueil"
    Then la page index.html s'affiche à nouveau
    And le titre de la page redevient "Formulaire Inscription"
```

---

## 4. Répartition des rôles dans le binôme

| Membre du binôme | Rôle principal | Responsabilités techniques |
| :--- | :--- | :--- |
| **Binôme 1** | *Architecture Web & Page Objects* | - Intégration et validation des fichiers HTML dans `src/main/webapp/`<br>- Implémentation de `IndexPageObjectModel.java` et `ConfirmationPageObjectModel.java`<br>- Définition des sélecteurs robustes (`#input_nom`, `#button_valider`, etc.) |
| **Binôme 2** | *Spécifications & Automatisation QA* | - Rédaction et maintien des scénarios Gherkin<br>- Implémentation des méthodes `@Test` JUnit 5 dans `WebInterfaceTest.java`<br>- Gestion des assertions et validation du build Maven (`mvn test`) |

---

## 5. Prérequis et Exécution des Tests

### Prérequis
* **Java JDK** : version 17 ou ultérieure recommandée
* **Apache Maven** : version 3.8+
* Accès Internet lors du tout premier lancement pour télécharger les binaires de navigateurs Playwright.

### Commandes utiles

1. **Compiler le projet :**
   ```bash
   mvn clean compile
   ```

2. **Lancer la suite de tests Playwright :**
   ```bash
   mvn test
   ```

3. **Exécuter une classe de test spécifique :**
   ```bash
   mvn test -Dtest=WebInterfaceTest
   ```

### Configuration du mode d'affichage
Pour observer l'exécution dans le navigateur en direct (mode visible) :
* Ouvrir `PlaywrightAbstractTester.java`
* Vérifier les paramètres :
  ```java
  private final static boolean headless = false; // Affiche la fenêtre du navigateur
  private final static Integer setSlowMo = 500;  // Ralentit les actions de 500ms pour la démonstration
  ```