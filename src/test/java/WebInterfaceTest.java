import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests de l'interface web (index.html et confirmation.html).
 * Chaque test est documenté avec son scénario Gherkin.
 */
public class WebInterfaceTest extends PlaywrightAbstractTester {

    /*
     * TC1 : Vérification du titre et des éléments initiaux de la page d'accueil
     * Priorité : Haute
     *
     * Feature: Chargement de la page d'accueil
     * Scenario: La page principale affiche le titre attendu
     *   Given la page index.html est ouverte dans le navigateur
     *   When la page est entièrement chargée
     *   Then le titre de l'onglet est "Formulaire Inscription"
     *   And l'en-tête principal affiche "Inscription"
     */
    @Test
    @DisplayName("TC1 - Titre et en-tête de la page d'accueil")
    void tc1_titreEtEnTetePageAccueil() {
        // Given / When
        IndexPageObjectModel index = new IndexPageObjectModel(page);
        index.open();

        // Then
        assertEquals("Formulaire Inscription", index.getTitle());
        // And
        assertEquals("Inscription", index.getHeadingText());
    }

    /*
     * TC2 : Soumission valide du formulaire d'inscription
     * Priorité : Critique
     *
     * Feature: Soumission du formulaire
     * Scenario: Saisie valide des informations utilisateur
     *   Given la page index.html est ouverte dans le navigateur
     *   When l'utilisateur saisit "Alice" dans le champ nom d'utilisateur
     *   And sélectionne la ville "Berlin" dans la liste déroulante
     *   And coche la case "S'abonner aux alertes"
     *   And clique sur le bouton "Valider"
     *   Then le message "Inscription enregistree pour Alice a Berlin !" est affiché à l'écran
     */
    @Test
    @DisplayName("TC2 - Soumission valide du formulaire d'inscription")
    void tc2_soumissionFormulaireValide() {
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
        // Vérification complémentaire : la case est bien cochée
        assertTrue(index.isNewsletterChecked());
    }

    /*
     * TC3 : Navigation vers la page de confirmation (Lien aller)
     * Priorité : Haute
     *
     * Feature: Navigation inter-pages
     * Scenario: Redirection vers la page de confirmation
     *   Given l'utilisateur est sur la page index.html
     *   When il clique sur le lien "Aller à la page de confirmation"
     *   Then la page confirmation.html s'ouvre
     *   And le titre de la page devient "Confirmation"
     *   And l'en-tête affiche "Page de confirmation"
     */
    @Test
    @DisplayName("TC3 - Navigation vers la page de confirmation")
    void tc3_navigationVersConfirmation() {
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
     * TC4 : Navigation retour vers l'accueil (Lien retour)
     * Priorité : Moyenne
     *
     * Feature: Navigation de retour
     * Scenario: Retour à l'accueil depuis la confirmation
     *   Given l'utilisateur est sur la page confirmation.html
     *   When il clique sur le lien "Retour à l'accueil"
     *   Then la page index.html s'affiche à nouveau
     *   And le titre de la page redevient "Formulaire Inscription"
     */
    @Test
    @DisplayName("TC4 - Retour à l'accueil depuis la confirmation")
    void tc4_retourVersAccueil() {
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