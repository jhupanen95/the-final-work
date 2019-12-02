package ru.ozon.autotests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.ozon.autotests.annotation.FieldName;
import ru.ozon.autotests.utils.DriverManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultPage extends BasePage {

    @FindBy(xpath = "//label[text()='от']/../input")
    @FieldName(name = "от")
    public WebElement downToPrice;

    @FindBy(xpath = "//label[text()='до']/../input")
    @FieldName(name = "до")
    public WebElement upToPrice;

    @FindBy(xpath = "//span[text()='Высокий рейтинг']/../input")
    @FieldName(name = "Высокий рейтинг")
    public WebElement highRating;

    @FindBy(xpath = "//div[text()='По запросу ']")
    @FieldName(name = "Запрос")
    public WebElement request;

    @FindBy(xpath = "//a[@href='/cart']")
    @FieldName(name = "Корзина")
    public WebElement cart;

    public void loadPage() throws Exception{
        wait.until(ExpectedConditions.visibilityOf(getElement("Запрос")));
    }

    public void openListCheckBox(String nameCategory) throws Exception{
        WebElement checkBoxCategory = DriverManager.getDriver().findElement(By.xpath("//div[contains(text(), '" + nameCategory + "')]/.."));
        click(checkBoxCategory.findElement(By.xpath(".//span[contains(text(), 'Посмотреть все')]")));
        wait.until(ExpectedConditions.elementToBeClickable(checkBoxCategory.findElement(By.xpath(".//span[contains(text(), 'Свернуть')]"))));
    }

    public void selectCheckBox (String category, String value) {
        WebElement checkBox = DriverManager.getDriver().findElement(By.xpath("//div[contains(text(), '" + category + "')]/..//span[contains(text(), '" + value + "')]/../input"));
        scrollToElement(checkBox);
        click(checkBox);
        wait.until(ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.xpath("//div[@class='column__item_remove-margin']//span[text()='" + value + "']"))));
    }

    public void selectCheckBox (String value) {
        WebElement checkBox = DriverManager.getDriver().findElement(By.xpath("//span[text()='" + value + "']"));
        scrollToElement(checkBox);
        click(checkBox);
        wait.until(ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.xpath("//div[@class='column__item_remove-margin']//span[text()='" + value + "']"))));
    }

    public Map<String, String> addProducts(boolean parity, int count) throws Exception{
        List<WebElement> productList = DriverManager.getDriver().findElements(By.xpath("//div[text()='В корзину ']/ancestor::a"));
        Map<String, String> products = new HashMap();
        int j;
        if (parity) j=1;
        else j=0;
        for (int i = 0; i<count; j = j+2, i++) {
            WebElement product = productList.get(j);
            scrollToElement(product);
            products.put(product.findElement(By.xpath(".//span[@data-test-id='tile-name']")).getText(),
                        product.findElement(By.xpath(".//span[@data-test-id='tile-price']")).getText());
            click(product.findElement(By.xpath(".//button[@data-test-id='tile-buy-button']")));
            wait.until(ExpectedConditions.visibilityOf(product.findElement(By.xpath(".//span[contains(text(), 'шт')]"))));
        }
        return products;
    }

    @Override
    public void fillField(WebElement field, String value) {
        super.fillField(field, value);
        field.sendKeys(Keys.ENTER);
        DriverManager.getDriver().findElement(By.xpath("//div[@class='column__item_remove-margin']//div[contains(text(), 'Цена')]"));
    }

    @Override
    public WebElement getElement(String name) throws Exception {
        return getField(name, "ru.ozon.autotests.pages.ResultPage");
    }

}
