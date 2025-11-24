package web;

import core.base.BasePage;
import core.base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.CategoryPage;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.open;

public class filterCategoryTest extends BaseTest {
    private static CategoryPage categoryPage;
    private static BasePage basePage;

    @BeforeEach
    public void prepared() {
        basePage = new BasePage();

        open(baseUrl);
        basePage.allowCookie();
        basePage.openCatalog();
    }

    @Test
    public void filterCategory(){
        categoryPage = new CategoryPage();

        categoryPage.openCategory();

    }
}