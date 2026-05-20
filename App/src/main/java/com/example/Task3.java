package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Task3 {


    private static final String FORECAST_URL =
            "https://api.open-meteo.com/v1/forecast"
            + "?latitude=56&longitude=44"
            + "&hourly=temperature_2m,rain"
            + "&current=cloud_cover"
            + "&timezone=Europe%2FMoscow"
            + "&forecast_days=1"
            + "&wind_speed_unit=ms";


    public static void run(WebDriver webDriver, String resultPath) {
        System.out.println("=== Задание №3: Прогноз погоды для Нижнего Новгорода ===");

        try {
            webDriver.get(FORECAST_URL);
            WebElement preElem = webDriver.findElement(By.tagName("pre"));
            String jsonStr = preElem.getText();
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(jsonStr);

            JSONObject hourly = (JSONObject) root.get("hourly");
            JSONArray times        = (JSONArray) hourly.get("time");
            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            JSONArray rains        = (JSONArray) hourly.get("rain");

            String header = String.format("%-5s %-20s %-15s %-15s",
                    "№", "Дата/время", "Температура °C", "Осадки (мм)");
            String separator = "-".repeat(58);

            StringBuilder table = new StringBuilder();
            table.append("Прогноз погоды для Нижнего Новгорода (56°N, 44°E)\n");
            table.append(separator).append("\n");
            table.append(header).append("\n");
            table.append(separator).append("\n");

            for (int i = 0; i < times.size(); i++) {
                String time  = (String) times.get(i);
                Object temp  = temperatures.get(i);
                Object rain  = rains.get(i);

                String row = String.format("%-5d %-20s %-15s %-15s",
                        i + 1,
                        time,
                        formatNumber(temp),
                        formatNumber(rain));
                table.append(row).append("\n");
            }

            table.append(separator).append("\n");
            System.out.println(table);
            saveToFile(table.toString(), resultPath);

        } catch (Exception e) {
            System.out.println("[Task3] Ошибка при получении прогноза: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String formatNumber(Object value) {
        if (value instanceof Double) {
            return String.format("%.1f", (Double) value);
        } else if (value instanceof Long) {
            return Long.toString((Long) value);
        }
        return value != null ? value.toString() : "—";
    }

    private static void saveToFile(String content, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, false))) {
            pw.print(content);
            System.out.println("[Task3] Таблица сохранена в файл: " + filePath);
        } catch (IOException e) {
            System.out.println("[Task3] Не удалось сохранить файл: " + e.getMessage());
        }
    }
}
