package org.alexander.project.utilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonResponceUtils {
    @JsonProperty("items")
    private List<Item> items;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }

    static class Item {
        @JsonProperty("ИП")
        private InnInfo innInfo;

        @Override
        public String toString() {
            return innInfo.toString();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class InnInfo {
        @JsonProperty("ФИОПолн")
        private String fullName;

        @JsonProperty("ИННФЛ")
        private String inn;

        @JsonProperty("ОГРНИП")
        private String ogrnIp;

        @JsonProperty("ДатаОГРН")
        private String dateOgrn;

        @JsonProperty("Статус")
        private String status;

        @Override
        public String toString() {
            return String.format(
                    "ФИО: %s\nИНН: %s\nОГРНИП: %s\nДата ОГРН: %s\nСтатус: %s\n",
                    fullName, inn, ogrnIp, dateOgrn, status
            );
        }
    }
}