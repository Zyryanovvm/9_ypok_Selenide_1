package qa.guru.github;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

@DisplayName("Тестовый класс Selenide#1")
public class SelenideRepositoryTest {

    @DisplayName("Поиск репозитория и проверка его имени")
    @Test
    void shouldFindeSelenideAsFirstRepository() {
        open("https://github.com/");
        $("[data-test-selector=nav-search-input]").setValue("Selenide").pressEnter();
        $$("ul.repo-list li a").findBy(text("selenide/selenide")).click();  // ul - имя элемента, li, a - дети элемента
        $("h2").shouldHave(text("selenide / selenide"));

        //ARRANGE - начальные условия
        //ACT - действия
        //ASSERT - проверка
    }


}
