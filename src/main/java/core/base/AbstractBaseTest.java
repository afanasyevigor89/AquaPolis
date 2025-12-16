package core.base;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public abstract class AbstractBaseTest {

    protected static String baseUrl;
    protected static String validEmail;
    protected static String validPassword;
    protected static String invalidEmail;

    @BeforeEach
    public void setUp() {
        baseUrl = determineBaseUrl();
        applyCommonConfiguration();
        loadTestData();
    }

    protected void applyCommonConfiguration() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1000";
    }

    private static String determineBaseUrl() {
        String environment = System.getProperty("env", "test");
        String configFileName = "application-" + environment + ".properties";

        Properties properties = new Properties();
        try (InputStream input = BasePage.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (input == null) {
                throw new IllegalAccessException("Configuration file not found: " + configFileName);
            }
            properties.load(input);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load configuration file: " + configFileName, e);
        }
        return properties.getProperty("baseUrl");
    }

    private void loadTestData() {
        String environment = System.getProperty("env", "test");
        String configFileName = "application-" + environment + ".properties";

        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            if (input != null) {
                properties.load(input);

                // Загружаем тестовые данные
                validEmail = properties.getProperty("validEmail");
                validPassword = properties.getProperty("validPassword");
                invalidEmail = properties.getProperty("invalidEmail");
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки тестовых данных: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }


}
