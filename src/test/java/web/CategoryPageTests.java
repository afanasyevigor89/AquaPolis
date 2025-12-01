package web;

import core.base.BasePage;
import core.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.web.CategoryPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    @DisplayName("TC-001: Проверка фильтрации по цене")
    @Feature("Страница категории")
    @Story("Пользователь может фильтровать товары по цене")
    @Severity(SeverityLevel.CRITICAL)
    public void filterByPriceTest(){
        categoryPage.setMaxPrice(5000); //фильтруем по максимальной цене товара 5000 руб.
        sleep(3000);
        executeJavaScript("window.scrollTo(0, 1000)"); //прокручиваем страницы для подгрузки следующей страницы
        assertTrue(categoryPage.areAllPricesLessOrEqual(5000), "После фильтрации остались товары дороже 5000");
    }

    @Test
    @DisplayName("TC-002: Проверка сортировки по цене")
    @Feature("Страница категории")
    @Story("Пользователь может сортировать товары по цене")
    @Severity(SeverityLevel.CRITICAL)
    public void cortByPriceAsc(){
        categoryPage.applySortAsc();
        sleep(3000);
        assertTrue(categoryPage.arePricesSortedAscending(), "Товары не отсортированы");
    }
}