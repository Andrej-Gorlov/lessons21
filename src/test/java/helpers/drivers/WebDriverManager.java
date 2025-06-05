package helpers.drivers;

import helpers.ResultOutput;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class WebDriverManager {
    protected static WebDriver driver;

    public WebDriverManager(){
        driver = initializeDriver();
    }

    private WebDriver initializeDriver() {
        if(driver == null) {
            //System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/linux/chromedriver");
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/windows/chromedriver.exe");
            driver = new ChromeDriver();

            ResultOutput.log("Загрузка страницы: https://idemo.bspb.ru");
            driver.get("https://idemo.bspb.ru");
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    webDriver -> Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"), "complete"));

        }
        return driver;
    }

    /**
     * Ожидание, пока указанный элемент не станет видимым.
     *
     * @param element элемент который нужно ожидать.
     * @param timeout время ожидания в секундах.
     */
    protected void waitForElementVisible(WebElement element, int timeout) {
        ResultOutput.log("Проверка видимости элемента: " + element);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(element));
    }

    protected void checkDriverInitialization() {
        if (driver == null) {
            throw new IllegalStateException("Отсутствует подключение драйвера ChromeDriver. Пожалуйста, убедитесь, что драйвер инициализирован.");
        }
    }

    public static void closeDriver(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
