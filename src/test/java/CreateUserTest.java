import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.hse.webdrivertask.LoginPage;
import ru.hse.webdrivertask.MainPage;

public class CreateUserTest extends FunctionalTest {
    private static final String ROOT_LOGIN = "root";
    private static final String ROOT_PASSWORD = "1";
    private static final String RESTRICTED_SPACE = "Restricted character ' ' in the name";
    private static final String UNIQUE = "Value should be unique: <b>login</b>";
    private static final String ROOT = "Removing null is prohibited";
    private static int id = 0;
    private boolean loggedIn = false;

    private String newUsername() {
        return "user" + Integer.toString(id++);
    }

    @Before
    public void login() {
        if (loggedIn)
            return;

        driver.get("http://localhost:8080");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ROOT_LOGIN, ROOT_PASSWORD);
        loggedIn = true;
    }

    @Before
    public void openUsers() {
        driver.get("http://localhost:8080/users");
    }

    @Test
    public void userIsCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertTrue(page.createNewUser(newUsername()));
    }

    @Test
    public void sameNameIsNotCreated() {
        MainPage page = new MainPage(driver);
        String name = newUsername();
        Assert.assertTrue(page.createNewUser(name));
        openUsers();
        Assert.assertFalse(page.createNewUser(name));
        Assert.assertEquals(UNIQUE, page.popupMessage());
    }

    @Test
    public void numberIsCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertTrue(page.createNewUser("0000000"));
    }

    @Test
    public void rootIsNotCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertFalse(page.createNewUser("root"));
        Assert.assertEquals(ROOT, page.popupMessage());
    }

    @Test
    public void cyrillicIsCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertTrue(page.createNewUser("пользователь"));
    }

    private String longString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            builder.append("a");
        }
        return builder.toString();
    }

    @Test
    public void tooLongIsCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertTrue(page.createNewUser(longString()));
    }

    @Test
    public void emojiIsCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertTrue(page.createNewUser("\uD83D\uDE0B"));
    }

    @Test
    public void withSpaceIsNotCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertFalse(page.createNewUser("u ser"));
        Assert.assertEquals(RESTRICTED_SPACE, page.popupMessage());
    }

    @Test
    public void withLeadingSpaceIsNotCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertFalse(page.createNewUser(" user"));
        Assert.assertEquals(RESTRICTED_SPACE, page.popupMessage());
    }

    @Test
    public void withTrailingSpaceIsNotCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertFalse(page.createNewUser("user "));
        Assert.assertEquals(RESTRICTED_SPACE, page.popupMessage());
    }

    @Test
    public void withLineBreakIsCreated() {
        MainPage page = new MainPage(driver);
        Assert.assertTrue(page.createNewUser("us\ner"));
    }
}
