import com.microsoft.playwright.Page;

/**
 * Classe de base pour tous les Page Objects.
 * Contient la Page Playwright et des méthodes communes.
 */
public abstract class AbstractPageObjectModel {

    protected final Page page;

    public AbstractPageObjectModel(Page page) {
        this.page = page;
    }

    // Chemin du dossier du projet, ex: C:/Users/wea/Web-Anwendung-Test
    protected String getProjectRoot() {
        String root = System.getProperty("user.dir").replace("\\", "/");
        if (root.startsWith("/")) {
            root = root.substring(1);
        }
        return root;
    }

    // Titre de l'onglet (<title> dans le HTML)
    public String getTitle() {
        return page.title();
    }

    // URL actuelle de la page
    public String getUrl() {
        return page.url();
    }
}