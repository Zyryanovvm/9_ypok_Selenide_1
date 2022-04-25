package qa.guru.selenide;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;

import java.io.*;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// this is not a full list, just the most common
public class Snippets {

    void browser_command_examples() {

        open("https://google.com");
        open("/customer/orders");     // -Dselenide.baseUrl=http://123.23.23.1
        open("/", AuthenticationType.BASIC,
                new BasicAuthCredentials("user", "password"));

        Selenide.back();
        Selenide.refresh();

        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        executeJavaScript("sessionStorage.clear();"); // no Selenide command for this yet

        Selenide.confirm(); // OK in alert dialogs
        Selenide.dismiss(); // Cancel in alert dialogs

        Selenide.closeWindow(); // close active tab
        Selenide.closeWebDriver(); // close browser completely

        Selenide.switchTo().frame("new");
        Selenide.switchTo().defaultContent();

        Selenide.switchTo().window("The Internet");
    }

    void selectors_examples() {
        $("div").click();
        element("div").click();  // для языка Kotlin $ = element

        $("div", 2).click(); // находится 3ий по счёту сверху div

        $x("//h1/div").click();
        $(byXpath("//h1/div")).click();

        $(byText("full text")).click(); // Полное вхождение текста
        $(withText("ull tex")).click(); // частичное вхождение

        $(byTagAndText("div","full text")); // Ищет в теге текст
        $(withTagAndText("div","ull text"));

        $("").parent();  // переходим на уровень вверх
        $("").sibling(1); // ищет братьев и сестёр сверху вниз
        $("").preceding(1); // ищет братьев и сестёр снизу вверх
        $("").closest("div"); // синоним ancestor
        $("").ancestor("div"); // ищет вверх по тереву по тегу
        $("div:last-child"); // ищет последнего ребёнка

        $("div").$("h1").find(byText("abc")).click(); // Ищем div, внутри него h1, внутри неготекст

        // very optional
        $(byAttribute("abc", "x")).click();
        $("[abc=x]").click();

        $(byId("mytext")).click();
        $("#mytext").click();

        $(byClassName("red")).click();
        $(".red").click();
    }

    void actions_examples() {
        $("").click();
        $("").doubleClick();
        $("").contextClick(); // правый клик

        $("").hover(); // наведение мышкой

        $("").setValue("text");  //полностью очищает и вводит текст
        $("").append("text"); //вводит текст без очистки поля
        $("").clear(); // очищает поле
        $("").setValue(""); // clear

        $("div").sendKeys("c"); // нажатие клавиши "c" на клавиатуре
        actions().sendKeys("c").perform(); //hotkey c on whole application
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform(); // Ctrl + F
        $("html").sendKeys(Keys.chord(Keys.CONTROL, "f")); // chord - одновременно

        $("").pressEnter();
        $("").pressEscape();
        $("").pressTab();


        // complex actions with keybord and mouse, example
        actions().moveToElement($("div"))   // подвинуть мышку к элементу
                .clickAndHold()                      // нажать и держать мышку
                .moveByOffset(300, 200) // подвинуть мышка на 300 px вниз и 200 вправо
                .release().perform();                 // отпустить клавишу

        // old html actions don't work with many modern frameworks
        $("").selectOption("dropdown_option");
        $("").selectRadio("radio_options");

    }

    void assertions_examples() {
        $("").shouldBe(visible);
        $("").shouldNotBe(visible);
        $("").shouldHave(text("abc"));
        $("").shouldNotHave(text("abc"));
        $("").should(appear);
        $("").shouldNot(appear);


        //longer timeouts
        $("").shouldBe(visible, Duration.ofSeconds(30));  // тайм-аут на проверку


    }

    void conditions_examples() {
        $("").shouldBe(visible); // видный (синоним appear)
        $("").shouldBe(hidden);  // не видный

        $("").shouldHave(text("abc"));
        $("").shouldHave(exactText("abc")); // полное совпадение
        $("").shouldHave(textCaseSensitive("abc"));
        $("").shouldHave(exactTextCaseSensitive("abc"));
        $("").should(matchText("[0-9]abc$"));

        $("").shouldHave(cssClass("red")); // проверяет только имя класса
        $("").shouldHave(cssValue("font-size", "12")); // Физические св-ва элемента

        $("").shouldHave(value("25")); // содержимое поле ввода
        $("").shouldHave(exactValue("25"));
        $("").shouldBe(empty); // поле пустое

        $("").shouldHave(attribute("disabled"));
        $("").shouldHave(attribute("name", "example"));
        $("").shouldHave(attributeMatching("name", "[0-9]abc$"));

        $("").shouldBe(checked); // for checkboxes

        // Warning! Only checks if it is in DOM, not if it is visible! You don't need it in most tests!
        $("").should(exist); // проверяет только то что элемент находится в доме!

        // Warning! Checks only the "disabled" attribute! Will not work with many modern frameworks
        $("").shouldBe(disabled);
        $("").shouldBe(enabled);
    }

    void collections_examples() {

        $$("div"); // does nothing!

        // selections
        $$("div").filterBy(text("123")).shouldHave(size(1));  //Фильтрация
        $$("div").excludeWith(text("123")).shouldHave(size(1));

        $$("div").first().click(); // переходим в первый див из коллекции
        elements("div").first().click(); // синоним $$
        // $("div").click();
        $$("div").last().click(); // последний элемент
        $$("div").get(1).click(); // the second! (start with 0)
        $("div", 1).click(); // same as previous
        $$("div").findBy(text("123")).click(); //  finds first

        // assertions
        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same

        $$("").shouldHave(texts("Alfa", "Beta", "Gamma")); //порядок
        $$("").shouldHave(exactTexts("Alfa", "Beta", "Gamma")); // полное соответсвие плюс порядок

        $$("").shouldHave(textsInAnyOrder("Beta", "Gamma", "Alfa")); // содержание текста
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Beta", "Gamma", "Alfa"));

        $$("").shouldHave(itemWithText("Gamma")); // only one text. Ищет только один элемент

        $$("").shouldHave(sizeGreaterThan(0));
        $$("").shouldHave(sizeGreaterThanOrEqual(1));
        $$("").shouldHave(sizeLessThan(3));
        $$("").shouldHave(sizeLessThanOrEqual(2));


    }

    void file_operation_examples() throws FileNotFoundException {

        File file1 = $("a.fileLink").download(); // only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER)); // more common options, but may have problems with Grid/Selenoid

        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        // don't forget to submit!
        $("uploadButton").click();
    }

    void javascript_examples() {
        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0]+arguments[1])", "abc", 12);
        long fortytwo = executeJavaScript("return arguments[0]*arguments[1];", 6, 7);

    }
}