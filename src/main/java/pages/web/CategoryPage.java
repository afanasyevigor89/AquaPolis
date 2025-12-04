package pages.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import core.base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.util.List;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryPage extends BasePage {
    protected SelenideElement landingCategories = $("div[class='app-landing-categories']");
    protected SelenideElement secondCategory = $(".app-landing-categories__list-item:nth-child(2)");
    protected SelenideElement inputMaxPrice = $x("//div[contains(@class, 'c-aggregation__field_max')]//input");
    protected SelenideElement buttonApplyFilter = $x("//button[.//span[text()='Применить']]");
    protected ElementsCollection priceElements = $$(".c-amount__value");
    protected SelenideElement buttonSortAsc = $x("(//button[.//span[text()='Дешевле']])[2]");

    @Step("Открытие страницы категории")
    public void openCategory() {
        landingCategories.shouldBe(visible);
        secondCategory.shouldBe(visible).click();
    }

    @Step("Применение фильтра по макс. цене = {productPrice}")
    public void setMaxPrice(int productPrice) {
        inputMaxPrice.shouldBe(visible).click();
        inputMaxPrice.sendKeys(
                Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                Keys.DELETE, Keys.DELETE, Keys.DELETE
        );
        inputMaxPrice.setValue(String.valueOf(productPrice));
        $("body").click();
        buttonApplyFilter.click();
    }

    @Step("Получение цен товаров на странице")
    public boolean areAllPricesLessOrEqual(int maxPrice) {
        return priceElements.asFixedIterable().stream()
                .map(el -> Integer.parseInt(el.getText().replaceAll("\\D", "")))
                .allMatch(price -> price <= maxPrice);
    }

    @Step("Проверка что цены отсортированы по возрастанию")
    public boolean arePricesSortedAscending() {
        List<Integer> prices = priceElements.asFixedIterable().stream()
                .map(el -> Integer.parseInt(el.getText().replaceAll("\\D", "")))
                .toList();

        return IntStream.range(0, prices.size() - 1)
                .allMatch(i -> prices.get(i) <= prices.get(i + 1));
    }

    @Step("Применение сортировки по возрастанию")
    public void applySortAsc () {
        buttonSortAsc.shouldBe(visible).click();
    }
}