package pages.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    protected SelenideElement bannerHomePage = $(".flicking-viewport.app-banner__flicking");
    protected SelenideElement promoCategoryHomePage = $(".app-index-page__categories-list");
    protected SelenideElement buttonAddProductToCart = $x("(//button[.//span[text()='В корзину']])[1]");
    protected ElementsCollection paginationBullet = $$(".flicking-pagination-bullet");

    @Step("Добавление товара в корзину")
    public void addProductToCart() {
        buttonAddProductToCart.shouldBe(visible).click();
    }

    @Step("Пролистывание баннера свайпом")
    public void swipeBanner() {
        bannerHomePage.shouldBe(visible);
        actions().dragAndDropBy(bannerHomePage, 50, 0).perform(); // свайп вправо на 50px
    }

    @Step("Проверка активной точки пагинации баннера")
    public boolean verifyBulletIsActive (int indexBullet) {
        return paginationBullet.get(indexBullet).shouldHave(cssClass("flicking-pagination-bullet-active")).exists();
    }
}