package ru.ozon.autotests.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ozon.autotests.annotation.FieldName;
import ru.ozon.autotests.utils.DriverManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class BasePage {

    public WebDriverWait wait  = new WebDriverWait(DriverManager.getDriver(), 60);
    JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

    public BasePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public void fillField(WebElement field, String value){
        js.executeScript("return arguments[0].style.border='1px solid magenta';", field);
        js.executeScript("arguments[0].value=''", field);
        field.sendKeys(value);
        js.executeScript("return arguments[0].style.border='1px solid black';", field);
    }

    public void click(WebElement element){
        //wait.until(ExpectedConditions.elementToBeClickable(element));
        js.executeScript("arguments[0].click();", element);
    }

    public void selectMenuItem(List<WebElement> menuItems, String itemName){
        for (WebElement item : menuItems ){
            if (item.getText().equalsIgnoreCase(itemName)){
                item.click();
                return;
            }
        }
        Assert.fail("Не найден элмент коллеции - " + itemName);
    }

    public void scrollToElement(WebElement element) {
        js.executeScript("return arguments[0].scrollIntoView(false);", element);
    }

    public void fillField(String name, String value) throws Exception {
        WebElement element = getElement(name);
        fillField(element, value);
    }

    public void click(String name) throws Exception {
        WebElement element = getElement(name);
        scrollToElement(element);
        js.executeScript("arguments[0].click();", element);
    }

    public WebElement getField(String name, String className) {
        Class example = null;
        try {
            example = Class.forName(className);
        } catch (ClassNotFoundException e) {
            Assert.fail("Не найден класс PageObject " + className);
        }
        List<Field> fields = Arrays.asList(example.getFields());
        for (Field field : fields){
            if (field.getAnnotation(FieldName.class).name().equals(name)){
                return DriverManager.getDriver().findElement(By.xpath(field.getAnnotation(FindBy.class).xpath()));
            }
        }
        Assert.fail("Не объявлен элемент с наименованием " + name);
        return null;
    }

    public abstract WebElement getElement(String name) throws Exception;


}