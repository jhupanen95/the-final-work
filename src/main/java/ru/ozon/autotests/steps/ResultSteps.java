package ru.ozon.autotests.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qameta.allure.Attachment;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.ozon.autotests.pages.ResultPage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ResultSteps {

    public ResultPage resultPage = new ResultPage();
    public static Map<String, String> products = new HashMap();

    @When("загрузилась страница с результатом поиска")
    public void loadPageStep() throws Exception {
        resultPage.loadPage();
    }

    @When("ограничить значение поля \"(.*)\" - (.*) \"(.*)\"")
    public void fillFieldStep(String category, String field, String value) {
        resultPage.fillFieldFilter(category, field, value);
    }

    @When("выбрать чекбокс \"(.*)\"")
    public void selectCheckBoxStep(String field) {
        resultPage.selectCheckBox(field);
    }

    @When("открыть список вариантов у категории чекбоксов - \"(.*)\"")
    public void openListCheckBoxStep(String category) throws Exception {
        resultPage.openListCheckBox(category);
    }

    @When("выбрать в категории \"(.*)\" чекбокс \"(.*)\"")
    public void selectCheckBoxInCatStep(String category, String value) {
        resultPage.selectCheckBox(category, value);
    }

    @When("добавление (\\d+) товаров по порядку, четные - \"(.*)\"")
    public void addProducts(int count, String parity) throws Exception {

        ResultPage page = new ResultPage();
        if ("да".equalsIgnoreCase(parity)) {
            products = page.addProducts(true, count);
        }
        else {
            if ("нет".equalsIgnoreCase(parity)) {
                products = page.addProducts(false, count);
            }
            else Assert.fail("Не правильно задан парамерт \"четные\" пожалуйста испозьзуйте - да/нет");
        }

        saveInfo("test.txt");
    }

    @Then("число товаров в корзине равно \"(.*)\"")
    public void checkCountProductsStep(String expectValue) throws Exception {
        WebElement cart = resultPage.getElement("Корзина");
        resultPage.scrollToElement(cart);
        String beValue = cart
                .findElement(By.xpath(".//span[1]"))
                .getText()
                .replaceAll(" \n", "");
        Assert.assertEquals("Число товаров в корзине не совпадает. Ожидалось \"" + expectValue + "\" Получили \"" + beValue +"\"", expectValue, beValue);
    }

    @When("переход в корзину")
    public void goToCartStep() throws Exception {
        resultPage.click("Корзина");
    }

    @Attachment(value = "Вложение", fileExtension = ".txt")
    public static byte[] getBytes(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources", resourceName));
    }

    public void saveInfo(String file) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("src/main/resources/" + file), "UTF-8"));
            for(String product: products.keySet()) {
                writer.write(product + " | Цена - " + products.get(product) + "\n");
                writer.flush();
            }
            writer.write("\nТовар с наибольшей ценой: \n" + maxPrice() + " | Цена - " + products.get(maxPrice()) + "\n");
            writer.flush();
            getBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String maxPrice() {
        String keyMax = "";
        long valueMax = 0;
        for (String key: products.keySet()) {
            long now = Long.parseLong(products.get(key).replaceAll("\\D", ""));
            if (now > valueMax) {
                valueMax = now;
                keyMax = key;
            }
        }
        return keyMax;
    }
}
