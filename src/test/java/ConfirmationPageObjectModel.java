import com.microsoft.playwright.Page;

/**
 * represent confirmation.html.
 * Inspiré de HelloPageObjectModel.java.
 */
public class ConfirmationPageObjectModel extends AbstractPageObjectModel {

    //selector confirmation.html
    private static final String RELATIVE_URL = "/src/main/webapp/confirmation.html";
    private static final String HEADING = "#confirmation_heading";
    private static final String TEXT = "#confirmation_text";
    private static final String LINK_BACK = "#link_back";

    // Constructor for abstractPageObjectModel
    public ConfirmationPageObjectModel(Page page) {
        super(page);
    }

    //Directly open confirmation.html page
    public void open() {
        String projectRoot = getProjectRoot();
        String url = "file:///" + projectRoot + RELATIVE_URL;
        page.navigate(url);
    }

    //inspection content
    public String getHeadingText() {
        return page.locator(HEADING).innerText();
    }

    public String getConfirmationMessage() {
        return page.locator(TEXT).innerText();
    }

    // return button to IndexPageObjectModel
    public IndexPageObjectModel cliquerRetour() {
        page.locator(LINK_BACK).click();
        return new IndexPageObjectModel(page);
    }
}