package qa.guru.homework;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class findJunit5Text {

    @Test
    @DisplayName("Поиск кода JUnit5 на странице")
    void findJUnit5() {
        open("https://github.com/");
        $("[data-test-selector=nav-search-input]").setValue("Selenide").pressEnter();
        $$("ul.repo-list li a").find(text("selenide/selenide")).click();
        $("#wiki-tab").click();
        $(".markdown-body").$$("ul li a").findBy(text("Soft assertions")).click();
        $("#wiki-body").shouldHave(text("JUnit5 extension - com.codeborne.selenide.junit5.SoftAssertsExtension"));

    }

    @Test
    @DisplayName("Перетаскивание элемента DragAndDrop")
    void dragAndDropTable() {
        open("https://the-internet.herokuapp.com/drag_and_drop");
        $("#column-a").shouldHave(text("A"));
        $("#column-b").shouldHave(text("B"));
        $("#column-a").dragAndDropTo($("#column-b"));
        $("#column-a").shouldHave(text("B"));
        $("#column-b").shouldHave(text("A"));
    }

    @Test
    @DisplayName("Перетаскивание элемента вариант Перетаскивания")
        // С этим тестом какие-то проблемы.
        // Пробовал запускать и через WebDriver и через Selenoid - не работает(((
    void actionsTable() {
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        open("https://the-internet.herokuapp.com/drag_and_drop");
        $("#column-a").shouldHave(text("A"));
        $("#column-b").shouldHave(text("B"));
        actions().moveToElement($("#column-a"))
                .clickAndHold()
                .moveByOffset(300, 0)
                .release().perform();
        $("#column-a").shouldHave(text("B"));
        $("#column-b").shouldHave(text("A"));
    }
}
