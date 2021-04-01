import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestDeliveryForm {

    @Test
    void shouldTestStandardData() {
        open("http://localhost:9999");
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id='city'] input").setValue("Казань");
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue(date.format(dateFormat));
        form.$("[data-test-id='name'] input").setValue("Василий");
        form.$("[data-test-id='phone'] input").setValue("+79990001122");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestCityAndNameWithSpace() {
        open("http://localhost:9999");
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        LocalDate date = LocalDate.now();
        date = date.plusDays(4);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue(date.format(dateFormat));
        form.$("[data-test-id='name'] input").setValue("Иванов Василий");
        form.$("[data-test-id='phone'] input").setValue("+79990001122");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestCityAndNameWithDash() {
        open("http://localhost:9999");
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id='city'] input").setValue("Санкт-Петербург");
        LocalDate date = LocalDate.now();
        date = date.plusDays(5);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue(date.format(dateFormat));
        form.$("[data-test-id='name'] input").setValue("Иванов-Василий");
        form.$("[data-test-id='phone'] input").setValue("+79990001122");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
