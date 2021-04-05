import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestDeliveryForm {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    public String DefinitionDate(int days) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(days);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(dateFormat);
    }

    @Test
    void shouldTestStandardData() {
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id='city'] input").setValue("Казань");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue(DefinitionDate(3));
        form.$("[data-test-id='name'] input").setValue("Василий");
        form.$("[data-test-id='phone'] input").setValue("+79990001122");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        String date=$("[data-test-id='date'] input").getValue();
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + date)).shouldBe(visible);

    }

    @Test
    void shouldTestCityAndNameWithSpace() {
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue(DefinitionDate(4));
        form.$("[data-test-id='name'] input").setValue("Иванов Василий");
        form.$("[data-test-id='phone'] input").setValue("+79990001122");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        String date=$("[data-test-id='date'] input").getValue();
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + date)).shouldBe(visible);
    }

    @Test
    void shouldTestCityAndNameWithDash() {
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[data-test-id='city'] input").setValue("Санкт-Петербург");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue(DefinitionDate(5));
        form.$("[data-test-id='name'] input").setValue("Иванов-Василий");
        form.$("[data-test-id='phone'] input").setValue("+79990001122");
        form.$("[data-test-id='agreement']").click();
        form.$$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        String date=$("[data-test-id='date'] input").getValue();
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + date)).shouldBe(visible);
    }
}
