package ru.ozon.autotests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ozon.autotests.annotation.FieldName;
import ru.ozon.autotests.utils.DriverManager;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(xpath = "//div[contains(text(), 'Корзина')]")
    @FieldName(name = "Корзина")
    public WebElement cartTitle;

    @FindBy(xpath = "//span[contains(text(), 'Удалить выбранные')]")
    @FieldName(name = "Удалить выбранные")
    public WebElement buttonDeleteProducts;

    @FindBy(xpath = "//div[text()='Удалить ']/..")
    @FieldName(name = "Подтвердить удаление")
    public WebElement confirmDelete;

    @FindBy(xpath = "//h1[contains(text(), 'Корзина пуста')]")
    @FieldName(name = "Корзина пуста")
    public WebElement clearCart;

    public void loadPage() throws Exception{
        wait.until(ExpectedConditions.visibilityOf(getElement("Корзина")));
    }

    public boolean checkProduct(String productName) {
        List<WebElement> productsInCart = DriverManager.getDriver().findElements(By.xpath("//div[contains(@class, 'column__item_remove-margin')]/div/div/a/span"));
        for (WebElement element: productsInCart) {
            if(element.getText().contains(productName)) return true;
        }
        return false;
    }

    @Override
    public WebElement getElement(String name) throws Exception {
        return getField(name, "ru.ozon.autotests.pages.CartPage");
    }
}
