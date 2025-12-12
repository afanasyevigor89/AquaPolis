package pages.web;

import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AccountPage extends BasePage {

    protected SelenideElement pageTitle = $("h1[class='text-uppercase d-none d-lg-block mb-4']");

    @Step("Получение текста заголовка страницы")
    public String getPageTitle() {
        return pageTitle.shouldBe(visible).getText();
    }
}
