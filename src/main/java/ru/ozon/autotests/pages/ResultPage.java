package ru.ozon.autotests.pages;

import org.junit.Assert;
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

    @FindBy(xpath = "//span[text()='Высокий рейтинг']/../input")
    @FieldName(name = "Высокий рейтинг")
    public WebElement highRating;

    @FindBy(xpath = "//div[text()='По запросу ']")
    @FieldName(name = "По запросу")
    public WebElement titleText;

    @FindBy(xpath = "//a[@href='/cart']")
    @FieldName(name = "Корзина")
    public WebElement cart;

//    @FindBy(xpath = "//div[text()='В корзину ']/ancestor::a")
//    public List<WebElement> productList;

    public void loadPage() throws Exception{
        wait.until(ExpectedConditions.visibilityOf(getElement("По запросу")));
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
        wait.until(ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.xpath("//div[@class='sort']//span[text()='" + value + "']"))));
    }

    public void selectCheckBox (String value) {
        WebElement checkBox = DriverManager.getDriver().findElement(By.xpath("//span[text()='" + value + "']"));
        scrollToElement(checkBox);
        click(checkBox);
        wait.until(ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.xpath("//div[@class='sort']//span[text()='" + value + "']"))));
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

    public void fillFieldFilter(String category, String field, String value) {
        WebElement categoryBox = DriverManager.getDriver().findElement(By.xpath("//div[contains(text(), '" + category + "')]/.."));
        WebElement fieldValue = null;
        if ("от".equalsIgnoreCase(field)) fieldValue = categoryBox.findElement(By.xpath(".//label[text()='от']/../input"));
        else if ("до".equalsIgnoreCase(field)) fieldValue = categoryBox.findElement(By.xpath(".//label[text()='до']/../input"));
            else Assert.fail("отсутствует поле \"" + field + "\" в категории \"" + "\"");
        fillField(fieldValue, value);
        fieldValue.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(DriverManager.getDriver().findElement(By.xpath("//div[@class='sort']//span[text()='" + category + "']"))));
    }

    @Override
    public WebElement getElement(String name) throws Exception {
        return getField(name, "ru.ozon.autotests.pages.ResultPage");
    }

}
