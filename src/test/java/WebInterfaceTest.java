import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Web interface tests (index.html and confirmation.html).
 * Each test case is documented with its Gherkin scenario.
 * Several assertions in one test are grouped with assertAll, so that every
 * failing check is reported, not only the first one.
 */
public class WebInterfaceTest extends PlaywrightAbstractTester {

    // Computed CSS colors of the result message
    private static final String GREEN = "rgb(0, 128, 0)";
    private static final String RED = "rgb(255, 0, 0)";

    private static final String ERROR_MESSAGE = "Veuillez remplir tous les champs obligatoires !";
   

    /*
     * TC1: Verification of home page title and initial elements
     * Priority: High
     *
     * Feature: Home page loading
     * Scenario: The main page displays the expected title
     *   Given the page index.html is opened in the browser
     *   When the page is fully loaded
     *   Then the tab title is "Formulaire Inscription"
     *   And the main heading displays "Inscription"
     */
    @Test
    @DisplayName("TC1 - Home page title and heading")
    void tc1_homePageTitleAndHeading() {
        // Given / When
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // Then / And
        assertAll("Home page",
            () -> assertEquals("Formulaire Inscription", index.getTitle(), "Tab title"),
            () -> assertEquals("Inscription", index.getHeadingText(), "Main heading")
        );
    }

    /*
     * TC2: Valid registration form submission (checkbox checked)
     * Priority: Critical
     *
     * Feature: Form submission
     * Scenario: Valid entry of user information
     *   Given the page index.html is opened in the browser
     *   When the user enters "Alice" in the username field
     *   And selects the city "Berlin" from the dropdown list
     *   And checks the "Subscribe to alerts" checkbox
     *   And clicks the "Submit" button
     *   Then the message "Inscription enregistree pour Alice a Berlin !" is displayed on screen
     *   And the message is displayed in green
     *   And the message "Vous recevrez nos alertes." is displayed
     */
    @Test
    @DisplayName("TC2 - Valid registration form submission")
    void tc2_validFormSubmission() {
        // Given
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // When
        index.saisirNom("Alice");
        // And
        index.selectionnerVille("Berlin");
        // And
        index.cocherNewsletter();
        // And
        index.cliquerValider();

        // Then
        assertEquals("Inscription enregistree pour Alice a Berlin !", index.recupererMessage());
        // Additional check: the checkbox is indeed checked
        assertTrue(index.isNewsletterChecked());
    }/*
     * -------------------------------------------------------------------------
     * PARTNER CONTRIBUTION (WORK IN PROGRESS)
     * Feature: Registration form submission options
     * Scenario: TC2b - Successful form submission without newsletter subscription
     *   Given the user opens the registration form
     *   When the user enters the name "Bob"
     *     And selects the city "Metz"
     *     And unchecks the newsletter checkbox
     *     And clicks the submit button
     *   Then a success message "Inscription enregistree pour Bob a Metz !" is displayed
     *     And the newsletter checkbox remains unchecked
     * -------------------------------------------------------------------------
     * NOTE: Implemented for the pair-programming part. Functional logic is ready,
     * but pending joint integration testing before activation.
     * -------------------------------------------------------------------------
     * */
    @Test
    @DisplayName("TC2b - Valid submission without newsletter subscription")
    void tc2b_submissionWithoutNewsletter() {
        // Given
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // When
        index.saisirNom("Bob");
        // And
        index.selectionnerVille("Metz");
        // And
        index.cocherNewsletter();
        // And
        index.decocherNewsletter();
        // And
        index.cliquerValider();

        // Then
        assertEquals("Inscription enregistree pour Bob a Metz !", index.recupererMessage());
        org.junit.jupiter.api.Assertions.assertFalse(index.isNewsletterChecked());
    }
    
    
    /*
     * TC2c: Submission failure due to missing mandatory username
     * Priority: High
     *
     * Feature: Form validation and error handling
     * Scenario: The username field is left empty
     *   Given the page index.html is opened in the browser
     *   When the user selects the city "Berlin"
     *   And leaves the username input field empty
     *   And clicks the submit button
     *   Then the message "Veuillez remplir tous les champs obligatoires !" is displayed
     *   And the message is displayed in red
     *   And the user remains on the index page
     */
    @Test
    @DisplayName("TC2c - Error message in red when the username is missing")
    void tc2c_missingUsernameError() {
        // Given
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // When
        index.selectionnerVille("Berlin");
        // And (username left empty)
        // And
        index.cliquerValider();

        // Then / And
        assertAll("Incomplete form",
            () -> assertEquals(ERROR_MESSAGE, index.recupererMessage(), "Error message"),
            () -> assertEquals(RED, index.getMessageColor(), "Message color"),
            () -> assertEquals("", index.recupererMessageAlertes(), "No alerts message on error"),
            () -> assertTrue(index.getUrl().endsWith("index.html"), "Still on index page")
        );
    }

    /*
     * TC3: Navigation to the confirmation page (Forward link)
     * Priority: High
     *
     * Feature: Inter-page navigation
     * Scenario: Redirection to the confirmation page
     *   Given the user is on the index.html page
     *   When they click the link "Aller à la page de confirmation"
     *   Then the page confirmation.html opens
     *   And the page title becomes "Confirmation"
     *   And the heading displays "Page de confirmation"
     */
    @Test
    @DisplayName("TC3 - Navigation to confirmation page")
    void tc3_navigationToConfirmation() {
        // Given
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // When
        ConfirmationPageObjectModel confirmation = index.cliquerVersConfirmation();

        // Then / And
        assertAll("Confirmation page",
            () -> assertTrue(confirmation.getUrl().endsWith("confirmation.html"), "URL"),
            () -> assertEquals("Confirmation", confirmation.getTitle(), "Tab title"),
            () -> assertEquals("Page de confirmation", confirmation.getHeadingText(), "Heading")
        );
    }

    /*
     * TC4: Navigation back to home (Back link)
     * Priority: Medium
     *
     * Feature: Backward navigation
     * Scenario: Return to home from confirmation
     *   Given the user is on the confirmation.html page
     *   When they click the link "Retour à l'accueil"
     *   Then the page index.html is displayed again
     *   And the page title becomes "Formulaire Inscription" again
     */
    @Test
    @DisplayName("TC4 - Return to home from confirmation")
    void tc4_returnToHome() {
        // Given
        ConfirmationPageObjectModel confirmation = new ConfirmationPageObjectModel(page);
        confirmation.open();

        // When
        IndexPageObjectModel index = confirmation.cliquerRetour();

        // Then / And
        assertAll("Back on home page",
            () -> assertTrue(index.getUrl().endsWith("index.html"), "URL"),
            () -> assertEquals("Formulaire Inscription", index.getTitle(), "Tab title")
        );
    }

    /*
     * TC5: Default value of the city dropdown
     * Priority: Medium
     *
     * Feature: Initial state of the form
     * Scenario: "Paris" is selected by default
     *   Given the page index.html is opened in the browser
     *   When the user has not changed the city dropdown
     *   Then the selected city is "Paris"
     */
    @Test
    @DisplayName("TC5 - Paris is the default city")
    void tc5_defaultCityIsParis() {
        // Given
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // When (no interaction)

        // Then
        assertEquals("Paris", index.getVilleSelectionnee(), "Default city");
    }

    /*
     * TC6: Parameterized test of several form combinations
     * Priority: High
     *
     * Feature: Form submission with different inputs
     * Scenario Outline: Submitting the form with <nom>, <ville> and <alertes>
     *   Given the page index.html is opened in the browser
     *   When the user enters <nom> in the username field
     *   And selects the city <ville>
     *   And sets the newsletter checkbox to <alertes>
     *   And clicks the submit button
     *   Then the message <message> is displayed in <couleur>
     *   And the alerts message <messageAlertes> is displayed
     *
     *   Examples: see @CsvSource below
     */
    @ParameterizedTest(name = "TC6 [{index}] nom=''{0}'', ville={1}, alertes={2}")
    @DisplayName("TC6 - Form combinations")
    @CsvSource(delimiter = '|', textBlock = """
        # nom      | ville  | alertes | message                                            | couleur | messageAlertes
        Alice      | Paris  | true    | Inscription enregistree pour Alice a Paris !       | GREEN   | Vous recevrez nos alertes.
        Alice      | Berlin | false   | Inscription enregistree pour Alice a Berlin !      | GREEN   | Vous ne recevrez pas d'alertes.
        Bob        | Metz   | true    | Inscription enregistree pour Bob a Metz !          | GREEN   | Vous recevrez nos alertes.
        Jean-Luc   | Metz   | false   | Inscription enregistree pour Jean-Luc a Metz !     | GREEN   | Vous ne recevrez pas d'alertes.
        ''         | Paris  | true    | Veuillez remplir tous les champs obligatoires !    | RED     | ''
        ''         | Berlin | false   | Veuillez remplir tous les champs obligatoires !    | RED     | ''
        '   '      | Metz   | true    | Veuillez remplir tous les champs obligatoires !    | RED     | ''
        """)
    void tc6_formCombinations(String nom, String ville, boolean alertes,
                              String message, String couleur, String messageAlertes) {
        // Given
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // When
        index.saisirNom(nom);
        // And
        index.selectionnerVille(ville);
        // And
        if (alertes) {
            index.cocherNewsletter();
        } else {
            index.decocherNewsletter();
        }
        // And
        index.cliquerValider();

        // Then / And
        String expectedColor = couleur.equals("GREEN") ? GREEN : RED;
        assertAll("Combination " + nom + " / " + ville + " / " + alertes,
            () -> assertEquals(message, index.recupererMessage(), "Result message"),
            () -> assertEquals(expectedColor, index.getMessageColor(), "Message color"),
            () -> assertEquals(messageAlertes, index.recupererMessageAlertes(), "Alerts message")
        );
    }
}
