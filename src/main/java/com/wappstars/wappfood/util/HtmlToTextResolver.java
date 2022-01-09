package com.wappstars.wappfood.util;

import org.jsoup.Jsoup;

public class HtmlToTextResolver {
    public static String HtmlToText(String html) {
        return Jsoup.parse(html).text();
    }
}