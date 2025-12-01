package web;

import core.base.BasePage;
import core.base.BaseTest;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public class HomePageTests extends BaseTest {

    @BeforeEach
    public void prepared() {
        BasePage basePage = new BasePage();

        open(baseUrl);
        basePage.allowCookie();
    }


}
