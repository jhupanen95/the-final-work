package ru.ozon.autotests.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ozon.autotests.pages.CartPage;

public class CartSteps {

    public CartPage cartPage = new CartPage();

    @When("загрузилась страница \"Моя корзина\"")
    public void loadPageStep() throws Exception {
        cartPage.loadPage();
    }

    @When("проверка наличия ранее добавленных товаров в корзине")
    public void checkProducts() throws Exception {
        for (String productName: ResultSteps.products.keySet()) {
            Assert.assertTrue("Продукт \"" + productName + "\" отсутствует в корзине", cartPage.checkProduct(productName));
        }
    }

    @Step("нажатие кнопки \"Удалить выбранные\"")
    public void pressButtonDeleteStep() throws Exception {
        cartPage.getElement("Удалить выбранные").click();
    }

    @Step("подтверждение удаления")
    public void pressConfirmDeleteStep() throws Exception {
        cartPage.wait.until(ExpectedConditions.visibilityOf(cartPage.getElement("Подтвердить удаление"))).click();
    }

    @When("отчистка корзины")
    public void deleteProductsStep() throws Exception {
        pressButtonDeleteStep();
        pressConfirmDeleteStep();
    }

    @Then("проверка, что корзина пуста")
    public void checkClearCartStep() throws Exception {
        cartPage.wait.until(ExpectedConditions.visibilityOf(cartPage.getElement("Корзина пуста")));
        Assert.assertTrue("Отсутствует надпись \"Корзина пуста\"", cartPage.getElement("Корзина пуста").isDisplayed());
    }

}
