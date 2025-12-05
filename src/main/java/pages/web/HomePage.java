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
    protected SelenideElement addProductNotif = $("div[role='alert']");
    protected SelenideElement countProductInCart = $("a[href='/checkout-spa'] .v-badge__badge span");

    @Step("Добавление товара в корзину")
    public void addProductToCart() {
        buttonAddProductToCart.shouldBe(visible).click();
    }

    @Step("Пролистывание баннера свайпом")
    public void swipeBanner() {
        bannerHomePage.shouldBe(visible);
        actions().dragAndDropBy(bannerHomePage, 50, 0).perform(); // свайп вправо на 100px
    }

    @Step("Проверка активной точки пагинации баннера")
    public boolean verifyBulletIsActive (int indexBullet) {
        return paginationBullet.get(indexBullet).shouldHave(cssClass("flicking-pagination-bullet-active")).exists();
    }

    @Step("Проверка изменения кол-ва товаров в корзине")
    public boolean verifyCountProductInHeader(int count) {
        return countProductInCart.shouldBe(visible)
                .shouldHave(text(String.valueOf(count)))
                .exists();
    }

    @Step("Получение нотифа после добавления товара в корзину")
    public String getMessageText() {
        return addProductNotif.should(exist, Duration.ofSeconds(5)).getText();
    }
}