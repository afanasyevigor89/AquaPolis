package web;

import core.base.BasePage;
import core.base.BaseTest;
import data.TestData;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.AccountPage;
import pages.web.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Страница авторизации")
public class LoginPageTests extends BaseTest {

    private static LoginPage loginPage;

    String userLogin = TestData.Credentials.VALID_EMAIL;
    String userPass = TestData.Credentials.VALID_PASSWORD;
    String wrongLogin = TestData.Credentials.INVALID_EMAIL;
    String wrongPass = TestData.Credentials.INVALID_PASSWORD;

    @BeforeEach
    public void prepared() {
        loginPage = new LoginPage();

        open(baseUrl);

        BasePage basePage = new BasePage();
        basePage.allowCookie();// закрываем плашку с cookie
        basePage.openLoginPage();
    }

    @Test
    public void loginByEmail() throws InterruptedException {
        AccountPage accountPage = new AccountPage();

        loginPage.loginWithCredentials(userLogin, userPass);
        sleep(3000);

        String expectedPageTitle = "МОЙ КАБИНЕТ";
        String actualPageTitle = accountPage.getPageTitle();
        assertEquals(expectedPageTitle, actualPageTitle, "Заголовок не совпадает");

    }

    @Test
    public void loginWithWrongEmail() {

        loginPage.loginWithCredentials(wrongLogin, userPass);
        assertTrue(loginPage.isErrorLoginMessageVisible(), "Сообщение об ошибке не отображается");

        String expectEmailMessageError = "Пожалуйста, введите действительный адрес электронной почты.";
        String actualEmailMessageError = loginPage.getErrorLoginMessage();
        assertEquals(expectEmailMessageError, actualEmailMessageError, "Сообщение об ошибке не совпадает");
    }

    @Test
    public void loginWithWrongPassword() {

        loginPage.loginWithCredentials(userLogin, wrongPass);
        assertTrue(loginPage.isErrorNotifLoginMessageVisible(), "Сообщение об ошибке не отображается");

        String expectLoginMessage = "Неверный логин или пароль.";
        String actualLoginMessage = loginPage.getErrorNotifLoginMessage();
        assertEquals(expectLoginMessage, actualLoginMessage, "Сообщение от ошибке не совпадает");
    }

}
