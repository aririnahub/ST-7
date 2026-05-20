package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Task2 {
    public static String getClientIP(WebDriver webDriver) {
        String ip = "не определён";

        try {
            webDriver.get("https://api.ipify.org/?format=json");
            WebElement preElem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = preElem.getText();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            ip = (String) obj.get("ip");

        } catch (Exception e) {
            System.out.println("[Task2] Ошибка при получении IP: " + e.getMessage());
        }

        return ip;
    }

    public static void run(WebDriver webDriver) {
        System.out.println("Задание №2: Получение внешнего IP-адреса");
        String ip = getClientIP(webDriver);
        System.out.println("Ваш внешний IPv4-адрес: " + ip);
        System.out.println();
    }
}
