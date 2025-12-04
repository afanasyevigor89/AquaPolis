package pages.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {
    protected SelenideElement bannerHomePage = $(".flicking-viewport.app-banner__flicking");
    protected SelenideElement promoCategoryHomePage = $(".app-index-page__categories-list");
    protected SelenideElement buttonAddProductToCart = $x("(//button[contains(@class, 'app-cart-add-item-btn')])[1]");
    protected ElementsCollection paginationBullet = $$(".flicking-pagination-bullet");

    @Step("Добавление товара в корзину")
    public void addProductToCart() {
        buttonAddProductToCart.shouldBe(visible).click();
    }

    @Step("Пролистывание баннера свайпом")
    public void swipeBanner() {
        actions().dragAndDropBy(bannerHomePage, 100, 0).perform(); // свайп вправо на 100px
    }

    @Step("Проверка активной точки пагинации баннера")
    public boolean verifyBulletIsActive (int indexBullet) {
        paginationBullet.get(indexBullet).shouldHave(cssClass("flicking-pagination-bullet-active"));
        return true;
    }
}