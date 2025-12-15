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
    protected ElementsCollection productCards = $$(".v-card.v-card--flat.v-theme--light.v-card--density-default.rounded-0.v-card--variant-elevated.h-100.position-relative.app-product-tile.h-100");

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

    @Step("Получение кол-ва товаров на странице")
    public int getProductCardsCount() {
        return productCards.size();
    }

    @Step("Прокрутить и загрузить следующие товары")
    public void scrollAndLoadMoreProducts() {
        int initialCount = getProductCardsCount();

        productCards.last().scrollIntoView("{behavior: 'smooth', block: 'end'}");

        waitForNewProducts(initialCount);
    }

    @Step("Дождаться появления новых товаров после прокрутки")
    private void waitForNewProducts(int initialCount) {
        // Ждем максимум 10 секунд
        long endTime = System.currentTimeMillis() + 10000;

        while (System.currentTimeMillis() < endTime) {
            int currentCount = getProductCardsCount();

            if (currentCount > initialCount) {
                return;
            }

            sleep(500);
        }

        throw new AssertionError("Новые товары не загрузились после прокрутки");
    }
}