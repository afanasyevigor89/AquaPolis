package core.base;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    protected SelenideElement headerLogo = $("a[name='Aquapolis Logo']");
    protected SelenideElement searchField = $("#search-form-with-menu");
    protected SelenideElement searchDropDown = $(".app-search-dropdown");
    protected SelenideElement categoryMenuButtom = $("#app-header__navigation-activator");
    protected SelenideElement categoryDropMenu = $(".app-navigation-desktop-menu__content-wrapper");
    protected SelenideElement trubiCategory = $("a[href='/truby-i-fitingi']");

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
        categoryMenuButtom.shouldBe(visible).click();
        categoryDropMenu.shouldBe(visible);
        trubiCategory.shouldBe(visible).click();
    }

}