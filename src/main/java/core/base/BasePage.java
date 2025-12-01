package core.base;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    protected SelenideElement headerLogo = $("a[name='Aquapolis Logo']");
    protected SelenideElement searchField = $("#search-form-with-menu");
    protected SelenideElement searchDropDown = $(".app-search-dropdown");
    protected SelenideElement categoryMenuButton = $("#app-header__navigation-activator");
    protected SelenideElement categoryDropMenu = $(".app-navigation-desktop-menu__content-wrapper");
    protected SelenideElement trubiCategory = $("a[href='/truby-i-fitingi']");
    protected SelenideElement cookieNotice = $(".c-cookie-notice__wrapper");
    protected SelenideElement cookieNoticeButton = $("button[class$='c-cookie-notice__btn']");

    @Step ("Выполняем поиск по запросу {query}")
    public void search (String query) {
        searchField.shouldBe(visible).click();
        searchField.setValue(query).pressEnter();
    }

    @Step("Выполняем переход на главную страницу")
    public void clickLogo() {
        headerLogo.shouldBe(visible).click();
    }

    @Step("Переход на страницу категории")
    public void openCatalog () {
        categoryMenuButton.shouldBe(visible).click();
        categoryDropMenu.shouldBe(visible);
        trubiCategory.shouldBe(visible).click();
    }

    @Step("Согласие на сбор кук")
    public void allowCookie() {
        cookieNotice.shouldBe(visible, Duration.ofSeconds(5));
        cookieNoticeButton.shouldBe(visible).click();
    }

}