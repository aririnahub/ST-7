package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class App {
    private static final String RESULT_FILE_PATH = "result/forecast.txt";

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        WebDriver webDriver = new ChromeDriver(options);

        try {
            runTask1(webDriver);
            Task2.run(webDriver);
            Task3.run(webDriver, RESULT_FILE_PATH);

        } finally {
            webDriver.quit();
        }
    }


    private static void runTask1(WebDriver webDriver) {
        System.out.println("Задание №1: Генератор паролей");

        try {
            webDriver.get("https://www.calculator.net/password-generator.html");
            WebElement passwordArea = webDriver.findElement(
                    By.cssSelector("textarea#pgenarea, #pgenarea"));

            String passwords = passwordArea.getText().trim();

            if (passwords.isEmpty()) {
                System.out.println("[Task1] Область с паролями пуста — "
                        + "возможно, изменилась структура страницы.");
            } else {
                String firstPassword = passwords.split("\\n")[0].trim();
                System.out.println("Сгенерированный пароль: " + firstPassword);
            }

        } catch (Exception e) {
            System.out.println("[Task1] Error");
            System.out.println(e.toString());
        }

        System.out.println();
    }
}
