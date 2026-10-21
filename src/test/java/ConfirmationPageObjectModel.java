import com.microsoft.playwright.Page;

/**
 * Modèle de page (Page Object Pattern) représentant confirmation.html.
 * Inspiré de HelloPageObjectModel.java.
 */
public class ConfirmationPageObjectModel extends AbstractPageObjectModel {

    // 1. Définition des sélecteurs de confirmation.html
    private static final String RELATIVE_URL = "/src/main/webapp/confirmation.html";
    private static final String HEADING = "#confirmation_heading";
    private static final String TEXT = "#confirmation_text";
    private static final String LINK_BACK = "#link_back";

    // 2. Constructeur reprenant le modèle d'héritage d'AbstractPageObjectModel
    public ConfirmationPageObjectModel(Page page) {
        super(page);
    }

    // 3. Méthode d'ouverture directe (optionnelle, utile pour tester la page isolément)
    public void open() {
        String projectRoot = getProjectRoot();
        String url = "file:///" + projectRoot + RELATIVE_URL;
        page.navigate(url);
    }

    // 4. Méthodes d'inspection du contenu de la page
    public String getHeadingText() {
        return page.locator(HEADING).innerText();
    }

    public String getConfirmationMessage() {
        return page.locator(TEXT).innerText();
    }

    // 5. Navigation retour vers la première page (renvoie IndexPageObjectModel)
    public IndexPageObjectModel cliquerRetour() {
        page.locator(LINK_BACK).click();
        return new IndexPageObjectModel(page);
    }
}