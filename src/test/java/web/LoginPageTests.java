package web;

import core.base.AbstractBaseTest;
import core.base.BasePage;
import io.qameta.allure.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.AccountPage;
import pages.web.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.TestData.Credentials.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Страница авторизации")
class LoginPageTests extends AbstractBaseTest {

    private static LoginPage loginPage;

    @BeforeEach
    void prepared() {
        loginPage = new LoginPage();

        open(baseUrl);

        BasePage basePage = new BasePage();
        basePage.allowCookie();// закрываем плашку с cookie
        basePage.openLoginPage();
    }

    @Story("TC-005: Пользователь может авторизоваться")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void loginByEmail(){
        AccountPage accountPage = new AccountPage();

        loginPage.loginWithCredentials(validEmail, validPassword);

        String expectedPageTitle = "МОЙ КАБИНЕТ";
        String actualPageTitle = accountPage.getPageTitle();
        assertEquals(expectedPageTitle, actualPageTitle, "Заголовок не совпадает");

    }

    @Story("TC-006: Валидация Email при авторизации")
    @Severity(SeverityLevel.MINOR)
    @Test
    void loginWithWrongEmail() {

        loginPage.loginWithCredentials(invalidEmail, validPassword);
        assertTrue(loginPage.isErrorLoginMessageVisible(), "Сообщение об ошибке не отображается");

        String expectEmailMessageError = "Пожалуйста, введите действительный адрес электронной почты.";
        String actualEmailMessageError = loginPage.getErrorLoginMessage();
        assertEquals(expectEmailMessageError, actualEmailMessageError, "Сообщение об ошибке не совпадает");
    }

    @Story("TC-007: Ошибка при вводе некорректного пароля")
    @Severity(SeverityLevel.MINOR)
    @Test
    void loginWithWrongPassword() {
        EasyRandom easyRandom = new EasyRandom();

        String randomWrongPass = easyRandom.nextObject(String.class)
                .replaceAll("[^a-zA-Z0-9]", "")
                .substring(0, Math.min(15, 10));

        loginPage.loginWithCredentials(validEmail, randomWrongPass);

        String expectLoginMessage = "Неверный логин или пароль.";
        String actualLoginMessage = loginPage.getErrorNotifLoginMessage();

        assertAll(() -> {
            step("Прверка отображения сообщения об ошибке", () ->
                    assertTrue(loginPage.isErrorNotifLoginMessageVisible(), "Сообщение об ошибке не отображается"));
            step("Проверка текста сообщения об ошибке", () ->
                    assertEquals(expectLoginMessage, actualLoginMessage, "Сообщение от ошибке не совпадает"));
        });
    }
}
