import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

/**
 * Modèle de page (Page Object Pattern) représentant index.html.
 * Encapsule les sélecteurs et les interactions utilisateur.
 */
public class IndexPageObjectModel extends AbstractPageObjectModel {

    // 1. Définition des sélecteurs (IDs de la page index.html)
    private static final String RELATIVE_URL = "/src/main/webapp/index.html";
    private static final String INPUT_NOM = "#input_nom";
    private static final String SELECT_VILLE = "#select_ville";
    private static final String CHECKBOX_NEWSLETTER = "#checkbox_newsletter";
    private static final String BUTTON_VALIDER = "#button_valider";
    private static final String MESSAGE_RESULTAT = "#message";
    private static final String LINK_CONFIRMATION = "#link_confirmation";
    private static final String HEADING = "#heading";

    // 2. Constructeur reprenant le modèle d'héritage d'AbstractPageObjectModel
    public IndexPageObjectModel(Page page) {
        super(page);
    }

    // 3. Méthode d'ouverture de l'URL locale en file:///
    public void open() {
        String projectRoot = getProjectRoot();
        String url = "file:///" + projectRoot + RELATIVE_URL;
        page.navigate(url);
    }

    // 4. Actions d'interaction (champs, liste déroulante, case à cocher, bouton)
    public void saisirNom(String nom) {
        page.locator(INPUT_NOM).fill(nom);
    }

    public void selectionnerVille(String ville) {
        page.locator(SELECT_VILLE).selectOption(new SelectOption().setValue(ville));
    }

    public void cocherNewsletter() {
        page.locator(CHECKBOX_NEWSLETTER).check();
    }

    public void decocherNewsletter() {
        page.locator(CHECKBOX_NEWSLETTER).uncheck();
    }

    public boolean isNewsletterChecked() {
        return page.locator(CHECKBOX_NEWSLETTER).isChecked();
    }

    public void cliquerValider() {
        page.locator(BUTTON_VALIDER).click();
    }

    public String recupererMessage() {
        return page.locator(MESSAGE_RESULTAT).innerText();
    }

    public String getHeadingText() {
        return page.locator(HEADING).innerText();
    }

    // 5. Transition vers la deuxième page (retourne le Page Object suivant)
    public ConfirmationPageObjectModel cliquerVersConfirmation() {
        page.locator(LINK_CONFIRMATION).click();
        return new ConfirmationPageObjectModel(page);
    }
}