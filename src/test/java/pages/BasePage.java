package pages;

import helpers.ResultOutput;
import helpers.drivers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class BasePage extends WebDriverManager {

    protected BasePage() {
        checkDriverInitialization();
        PageFactory.initElements(driver, this);
    }

    /**
     * Вводит текст в указанный элемент.
     *
     * @param element элемент, в который будет введен текст.
     * @param text текст, который будет введен в элемент.
     * @param elementName имя элемента для логирования.
     */
    protected void enterText(WebElement element, String text, String elementName) {
        elementVisible(element);
        element.clear();
        ResultOutput.log("Выполняется ввод " + elementName);
        element.sendKeys(text);
    }

    /**
     * Нажимает на указанный элемент.
     *
     * @param element элемент, на который будет выполнен клик.
     * @param elementName имя элемента для логирования.
     */
    protected void clickElement(WebElement element, String elementName) {
        elementVisible(element);
        ResultOutput.log("Выполняется клик по " + elementName);
        element.click();
        ResultOutput.log("Возвращение страницы: " + driver.getCurrentUrl());
    }

    /**
     * Проверяет, отображается ли указанный элемент.
     *
     * @param element элемент, который будет проверен на отображение.
     * @throws AssertionError если элемент не отображается.
     */
    protected void displayedText(WebElement element) {
        elementVisible(element);
        ResultOutput.log("Проверка отображение блока");
        if (!element.isDisplayed()) {
            throw new AssertionError("Блок не отображается");
        }
    }

    /**
     * Наводит курсор на элемент карты с указанным символом.
     *
     * @param element элемент-контейнер, содержащий символы карт.
     * @param symbol текст, который соответствует значению атрибута data-content элемента карты.
     */
    protected void hoverElement(WebElement element, String symbol) {
        elementVisible(element);
        ResultOutput.log("Наведение курсора на символ карты");
        WebElement card = element.findElement(By.xpath(String.format(".//a[@data-content='%s']", symbol)));
        new Actions(driver).moveToElement(card).perform();
    }

    /**
     * Получает содержимое поповера для символа карты.
     *
     * @param element элемент-контейнер, содержащий символы карт.
     * @param symbol текст, который соответствует значению атрибута data-content элемента карты.
     * @return содержимое атрибута data-content для указанного символа карты.
     */
    protected String getTextContent(WebElement element, String symbol) {
        ResultOutput.log("Получаем содержимое поповера для символа карты");
        WebElement card = element.findElement(By.xpath(String.format(".//a[@data-content='%s']", symbol)));
        return card.getAttribute("data-content");
    }

    private void elementVisible(WebElement element){
        waitForElementVisible(element, 10);
    }
}
