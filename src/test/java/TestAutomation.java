import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.AuthPage;
import pages.LoginPage;
import pages.WelcomePage;

import static org.junit.jupiter.api.Assertions.*;

public class TestAutomation {
    private String nameMethod;
    private final String auth = "https://idemo.bspb.ru/";

    private LoginPage loginPage;
    private AuthPage authPage;
    private WelcomePage welcomePage;

    @BeforeEach
    public void setUp() {
        ResultOutput.log("выполнение тестов класса TestAutomation");
        loginPage = new LoginPage();
        authPage = new AuthPage();
        welcomePage = new WelcomePage();
    }

    @Test
    public void testLoginAndVerifyFinancialFreedom() {
        nameMethod = "testLoginAndVerifyFinancialFreedom";
        ResultOutput.printTestStart(nameMethod);

        String pageLogin = performLogin();
        verifyUrl(auth + "auth/otp", pageLogin);

        String pageAuth = performAuth();
        verifyUrl(auth + "welcome", pageAuth);

        assertDoesNotThrow(() -> welcomePage.checkFinancialFreedomVisible(),
                "Блок 'Финансовая свобода' не отображается или вызвал исключение");

        assertTrue(isValidFinancialFreedomAmount(welcomePage.retrieveAndFormatFinancialFreedomAmount()),
                "Сумма на странице не соответствует ожидаемому формату.");

        verifyCardPopoverContent("Travel *6192");
        verifyCardPopoverContent("Золотая *2224");
    }

    /**
     * Выполняет вход в систему с указанными учетными данными.
     *
     * @return URL страницы после входа
     */
    private String performLogin() {
        return loginPage.enterUsername("demo")
                .enterPassword("demo")
                .clickLogin();
    }

    /**
     * Выполняет аутентификацию с указанным кодом.
     *
     * @return URL страницы после аутентификации
     */
    private String performAuth() {
        return authPage.enterAuthCode("0000")
                .clickAuth();
    }

    /**
     * Проверяет, соответствует ли сумма ожидаемому формату.
     *
     * @param amount сумма для проверки
     * @return true, если сумма соответствует формату, иначе false
     */
    private boolean isValidFinancialFreedomAmount(String amount) {
        return amount.matches("^\\d{1,3}( \\d{3})*(\\.\\d{2})? ₽$");
    }

    /**
     * Проверяет URL на соответствие ожидаемому значению.
     *
     * @param expectedUrl ожидаемый URL
     * @param actualUrl фактический URL
     */
    private void verifyUrl(String expectedUrl, String actualUrl) {
        assertEquals(expectedUrl, actualUrl, "URL страницы не соответствует ожидаемому");
    }

    /**
     * Проверяет содержимое, для указанного имени карты.
     *
     * @param cardName имя карты для проверки
     */
    private void verifyCardPopoverContent(String cardName) {
        String popoverContent = welcomePage.hoverOverCardSymbol(cardName)
                .getCardPopoverContent(cardName);
        assertEquals(cardName, popoverContent, "Надпись для " + cardName + " не отображается корректно");
    }

    @AfterEach
    public void tearDown() {
        ResultOutput.printTestEnd(nameMethod);
        WebDriverManager.closeDriver();
    }
}
