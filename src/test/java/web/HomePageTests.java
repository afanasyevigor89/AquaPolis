package web;

import core.base.BasePage;
import core.base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.CategoryPage;
import pages.web.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTests extends BaseTest {
    private static HomePage homePage;

    @BeforeEach
    public void prepared() {
        BasePage basePage = new BasePage();
        homePage = new HomePage();

        open(baseUrl);

        basePage.allowCookie();
    }
    @Test
    public void swipeHomePageBannerTest() {

        homePage.swipeBanner();
        //Проверяем что активна 3-я точка пагинации
        assertTrue(homePage.verifyBulletIsActive(2));
    }


}
