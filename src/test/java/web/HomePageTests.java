package web;

import core.base.AbstractBaseTest;
import core.base.BasePage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Главная страница")
class HomePageTests extends AbstractBaseTest {
    private static HomePage homePage;
    private static BasePage basePage;

    @BeforeEach
    void prepared() {
        homePage = new HomePage();

        open(baseUrl);

        basePage = new BasePage();
        basePage.allowCookie();// закрываем плашку с cookie

    }
    @Story("TC-003: Пользователь может пролистать баннер")
    @Severity(SeverityLevel.NORMAL)
    @Test
    void swipeHomePageBannerTest() {

        homePage.swipeBanner();
        //Проверяем что активна 3-я точка пагинации
        step("Проверяем что активна 3-я точка пагинации", () ->
                assertTrue(homePage.verifyBulletIsActive(4), "Баннер слайдера не прокрутился"));
    }

    @Story("TC-004: Пользователь может добавить товар в корзину из слайдера Новинки")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void addProductToCartFromHomePage(){
        executeJavaScript("window.scrollTo(0, 600)");
        homePage.addProductToCart();

        String expectMessage = "Товар добавлен в корзину";
        String actualMessage = basePage.getMessageText();
        String actualCount = basePage.getCountProductInHeader();
        assertAll(() -> {
            step("Проверка нотифа добавления товара в корзину", () ->
                    assertEquals(expectMessage, actualMessage, "Текст сообщения не совпадает"));
            step("Проверка изменения кол-ва товаров в корзину в хэдере", () ->
                    assertThat(actualCount).as("Количество товаров должно быть 1")
                            .isEqualTo("1"));

        });

    }
}
