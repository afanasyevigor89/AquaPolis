package pages.web;

import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CategoryPage extends BasePage {
    protected SelenideElement landingCategories = $("div[class='app-landing-categories']");
    protected SelenideElement secondCategory = $(".app-landing-categories__list-item:nth-child(2)");

    @Step("Открытие страницы категории")
    public void openCategory (){
        landingCategories.shouldBe(visible);
        secondCategory.shouldBe(visible).click();
    }
}
