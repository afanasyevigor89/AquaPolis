package web;

import core.base.BasePage;
import core.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.CategoryPage;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Страница категории")
public class CategoryPageTests extends BaseTest {
    private static CategoryPage categoryPage;

    @BeforeEach
    public void prepared() {
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
    public void filterByPriceTest(){
        categoryPage.setMaxPrice(5000); //фильтруем по максимальной цене товара 5000 руб.
        sleep(1000);
        categoryPage.scrollAndLoadMoreProducts();
        assertTrue(categoryPage.areAllPricesLessOrEqual(5000), "После фильтрации остались товары дороже 5000");
    }

    @Story("TC-002: Пользователь может отсортировать товары по цене")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void cortByPriceAsc(){
        categoryPage.applySortAsc();
        sleep(1000);
        assertTrue(categoryPage.arePricesSortedAscending(), "Товары не отсортированы");
    }

    @Story("TC-008: Проверка кол-ва элементов на странице после подгрузки 2-й страницы")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void productPagination(){
        categoryPage.scrollAndLoadMoreProducts();
        int expectedCount = 40;
        int actualCount = categoryPage.getProductCardsCount();

        assertThat(actualCount).as("Количество товаров после прокрутки должно быть: " + expectedCount)
                .isEqualTo(expectedCount);
    }
}