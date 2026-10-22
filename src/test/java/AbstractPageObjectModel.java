import com.microsoft.playwright.Page;

/**
 * Base class for all Page Objects.
 * Contains the Playwright Page instance and common shared methods.
 */
public abstract class AbstractPageObjectModel {

    protected final Page page;

    public AbstractPageObjectModel(Page page) {
        this.page = page;
    }

    // Path to the project root directory, e.g., C:/Users/wea/Web-Anwendung-Test
    protected String getProjectRoot() {
        String root = System.getProperty("user.dir").replace("\\", "/");
        // Remove the leading slash only if followed by a Windows drive letter (e.g., /C:)
        if (root.matches("^/[a-zA-Z]:.*")) {
            root = root.substring(1);
        }
        return root;
    }

    // Tab title (<title> tag in the HTML)
    public String getTitle() {
        return page.title();
    }

    // Current URL of the page
    public String getUrl() {
        return page.url();
    }
}