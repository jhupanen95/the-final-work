package ru.ozon.autotests.steps;

import cucumber.api.java.en.When;
import io.qameta.allure.Step;
import ru.ozon.autotests.pages.MainPage;

public class MainSteps {

    MainPage page = new MainPage();

    @Step("заполненно поле поиска - \"{0}\"")
    public void fillSearchFieldStep(String nameProduct) throws Exception {
        page.fillField("Поле поиска", nameProduct);
    }

    @Step("нажата кнопка поиска")
    public void pressButtonSearchStep() throws Exception {
        page.click("Кнопка поиска");
    }

    @When("выполнен поиск продукта \"(.*)\"")
    public void productSearch(String nameProduct) throws Exception {
        fillSearchFieldStep(nameProduct);
        pressButtonSearchStep();
    }



}
