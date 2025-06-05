package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage  {

    @FindBy(id = "username")
    private WebElement  usernameField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement  loginButton;

    public LoginPage(){
        super();
    }

    /**
     * Вводит имя пользователя в соответствующее поле.
     *
     * @param username имя пользователя, которое будет введено.
     * @return текущий объект LoginPage для использования Fluent Interface.
     */
    public LoginPage enterUsername(String username) {
        enterText(usernameField, username, "username");
        return this;
    }

    /**
     * Вводит пароль в соответствующее поле.
     *
     * @param password пароль, который будет введен.
     * @return текущий объект LoginPage для использования Fluent Interface.
     */
    public LoginPage enterPassword(String password) {
        enterText(passwordField, password, "password");
        return this;
    }

    /**
     * Нажимает на кнопку входа и возвращает текущий URL страницы.
     *
     * @return URL текущей страницы после нажатия на кнопку входа.
     */
    public String clickLogin() {
        clickElement(loginButton, "кнопка входа");
        return driver.getCurrentUrl();
    }
}
