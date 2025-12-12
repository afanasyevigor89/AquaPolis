package pages.web;

import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {

    protected SelenideElement titleLoginPage = $("h2[class='app-account-form__title text-lg-center']");
    protected SelenideElement fieldInputEmail = $("input[autocomplete='username']");
    protected SelenideElement fieldInputPassword = $("input[autocomplete='current-password']");
    protected SelenideElement buttonLogin = $("button[type='submit']");
    protected SelenideElement errorNotifLoginMessage = $("div[class='Vue-Toastification__toast-body']");
    protected SelenideElement errorLoginMessage = $("div[class='v-messages__message']");

    @Step("Авторизация пользователя с валидными данными")
    public void loginWithCredentials(String userLogin, String userPass) {
        fieldInputEmail.shouldBe(visible).setValue(userLogin);
        fieldInputPassword.shouldBe(visible).setValue(userPass);
        buttonLogin.shouldBe(visible).click();
    }

    @Step("Проверяем видимость поп-апа с сообщением об ошибке")
    public boolean isErrorNotifLoginMessageVisible() {
        return errorNotifLoginMessage.shouldBe(visible).exists();
    }

    @Step("Получение текста в поп-апе сообщения об ошибке")
    public String getErrorNotifLoginMessage() {
        return errorNotifLoginMessage.shouldBe(visible).getText();
    }

    @Step("Проверяем видимость сообщения об ошибке под полем Email")
    public boolean isErrorLoginMessageVisible() {
        return errorLoginMessage.shouldBe(visible).exists();
    }

    @Step("Получение текста сообщения об ошибке под полем Email")
    public String getErrorLoginMessage() {
        return errorLoginMessage.shouldBe(visible).getText();

    }
}