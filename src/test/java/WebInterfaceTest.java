import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Web interface tests (index.html and confirmation.html).
 * Each test case is documented with its Gherkin scenario.
 */
public class WebInterfaceTest extends PlaywrightAbstractTester {

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

        // Then
        assertEquals("Formulaire Inscription", index.getTitle());
        // And
        assertEquals("Inscription", index.getHeadingText());
    }

    /*
     * TC2: Valid registration form submission
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
    }
        /*
     * TC2b : Soumission sans abonnement aux alertes
     */
    /*
    @Test
    @DisplayName("TC2b - Soumission valide sans abonnement newsletter")
    void tc2b_soumissionSansNewsletter() {
        // Given
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // When
        index.saisirNom("Bob");
        index.selectionnerVille("Metz");
        index.decocherNewsletter();
        index.cliquerValider();

        // Then
        assertEquals("Inscription enregistree pour Bob a Metz !", index.recupererMessage());
        org.junit.jupiter.api.Assertions.assertFalse(index.isNewsletterChecked());
    }*/
    
    /*
     * -------------------------------------------------------------------------
     * PARTNER CONTRIBUTION (WORK IN PROGRESS)
     * Feature: Form validation and error handling
     * Scenario: TC2c - Submission failure due to missing mandatory username
     *   Given the user opens the registration form
     *   When the user selects the city "Berlin"
     *     And leaves the username input field empty
     *     And clicks the submit button
     *   Then an error message "Veuillez remplir tous les champs obligatoires !" is displayed
     *     And the user remains on the index page
     * -------------------------------------------------------------------------
     * NOTE: Implemented for the pair-programming part. Functional logic is ready,
     * but pending joint integration testing before activation.
     * -------------------------------------------------------------------------
    @Test
    @DisplayName("TC2c - Error display on submission with missing username")
    void tc2c_missingUsernameError() {
        // Given
        IndexPageObjectModel indexPage = new IndexPageObjectModel(page);
        indexPage.open();

        // When
        indexPage.selectionnerVille("Berlin");
        indexPage.cliquerValider();

        // Then
        assertEquals("Veuillez remplir tous les champs obligatoires !", indexPage.recupererMessage());
        assertTrue(indexPage.getUrl().contains("index.html"));
    }
    */

    

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

        // Then
        assertTrue(confirmation.getUrl().endsWith("confirmation.html"));
        // And
        assertEquals("Confirmation", confirmation.getTitle());
        // And
        assertEquals("Page de confirmation", confirmation.getHeadingText());
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

        // Then
        assertTrue(index.getUrl().endsWith("index.html"));
        // And
        assertEquals("Formulaire Inscription", index.getTitle());
    }

}