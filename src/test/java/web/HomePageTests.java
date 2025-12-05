package web;

import core.base.BasePage;
import core.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.web.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTests extends BaseTest {
    private static HomePage homePage;
    private static BasePage basePage;

    @BeforeEach
    public void prepared() {
        homePage = new HomePage();

        open(baseUrl);

        basePage = new BasePage();
        basePage.allowCookie();// закрываем плашку с cookie

    }
    @Test
    @DisplayName("TC-003: Прокручивание слайдера с баннерами свайпом")
    @Feature("Главная страница")
    @Story("Пользователь может пролистать баннер")
    @Severity(SeverityLevel.NORMAL)
    public void swipeHomePageBannerTest() {

        homePage.swipeBanner();
        //Проверяем что активна 3-я точка пагинации
        assertTrue(homePage.verifyBulletIsActive(2), "Баннер слайдера не прокрутился");
    }

    @Test
    @DisplayName("TC-004: Добавление товара в корзину из слайдера Новинки")
    @Feature("Главная страница")
    @Story("Пользователь может добавить товар в корзину из слайдера Новинки")
    @Severity(SeverityLevel.CRITICAL)
    public void addProductToCartFromHomePage(){
        executeJavaScript("window.scrollTo(0, 600)");
        homePage.addProductToCart();

        //Проверяем что появился нотиф добавления товара в корзину
        String expectMessage = "Товар добавлен в корзину";
        String actualMessage = homePage.getMessageText();
        assertEquals(expectMessage, actualMessage, "Текст сообщения не совпадает");

        //Проверяем что изменился счетчик товаров в хэдере
        assertTrue(homePage.verifyCountProductInHeader(1), "Кол-во товаров в хэдере указано неверно");
    }
}
