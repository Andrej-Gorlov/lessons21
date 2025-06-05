package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthPage extends BasePage  {

    @FindBy(id = "otp-code")
    private WebElement authCodeField;

    @FindBy(id = "login-otp-button")
    private WebElement authButton;

    public AuthPage(){
        super();
    }

    /**
     * Вводит код подтверждения в соответствующее поле.
     *
     * @param code код подтверждения, который будет введен.
     * @return текущий объект AuthPage для использования Fluent Interface.
     */
    public AuthPage enterAuthCode(String code) {
        enterText(authCodeField, code, "коду подтверждения");
        return this;
    }

    /**
     * Нажимает на кнопку подтверждения и возвращает текущий URL страницы.
     *
     * @return URL текущей страницы после нажатия на кнопку подтверждения.
     */
    public String clickAuth() {
        clickElement(authButton, "кнопка подтверждения");
        return driver.getCurrentUrl();
    }
}
