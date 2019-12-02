package ru.ozon.autotests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.ozon.autotests.annotation.FieldName;

public class MainPage extends BasePage{

    @FindBy(xpath = "//input[@placeholder='Искать на Ozon']")
    @FieldName(name = "Поле поиска")
    public WebElement searchBar;

    @FindBy(xpath = "//div[@class='search-button-wrap']/button")
    @FieldName(name = "Кнопка поиска")
    public WebElement searchButton;

    @Override
    public WebElement getElement(String name) throws Exception {
        return getField(name, "ru.ozon.autotests.pages.MainPage");
    }
}
