package web;

import core.base.AbstractBaseTest;
import core.base.BasePage;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.CategoryPage;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.with;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Страница категории")
class CategoryPageTests extends AbstractBaseTest {
    private static CategoryPage categoryPage;

    @BeforeEach
    void prepared() {
        BasePage basePage = new BasePage();
        categoryPage = new CategoryPage();

        open(baseUrl);
        basePage.allowCookie();
        basePage.openCatalog();
        categoryPage.openCategory();
    }

    @Story("TC-001: Пользователь может фильтровать товары по цене")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void filterByPriceTest(){
        step("фильтруем по максимальной цене товара 5000 руб.", () -> categoryPage.setMaxPrice(5000));
        with().pollInSameThread().await().atMost(1000, MILLISECONDS).pollInterval(200, MILLISECONDS).until(() -> categoryPage.areAllPricesLessOrEqual(5000));
        assertTrue(categoryPage.areAllPricesLessOrEqual(5000), "После фильтрации остались товары дороже 5000");
    }

    @Story("TC-002: Пользователь может отсортировать товары по цене")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void sortByPriceAsc(){
        categoryPage.applySortAsc();
        with().pollInSameThread().await().atMost(1000, MILLISECONDS).pollInterval(200, MILLISECONDS).until(() -> categoryPage.arePricesSortedAscending());
        assertTrue(categoryPage.arePricesSortedAscending(), "Товары не отсортированы");
    }

    @Story("TC-008: Проверка кол-ва элементов на странице после подгрузки 2-й страницы")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void productPagination(){
        categoryPage.scrollAndLoadMoreProducts();
        int expectedCount = 40;
        int actualCount = categoryPage.getProductCardsCount();

        assertThat(actualCount).as("Количество товаров после прокрутки должно быть: " + expectedCount)
                .isEqualTo(expectedCount);
    }
}