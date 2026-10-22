import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Browser lifecycle management for all test cases.
 */
public abstract class PlaywrightAbstractTester {

    // Headless mode works in CI/Codespaces; pass -Dplaywright.headless=false to show the browser UI.
    private final static boolean headless =
        Boolean.parseBoolean(System.getProperty("playwright.headless", "true"));
    private final static Integer setSlowMo = 1000;   // Slows down each Playwright action by 1000 ms

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(setSlowMo));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}