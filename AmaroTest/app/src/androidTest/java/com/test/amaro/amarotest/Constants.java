package com.test.amaro.amarotest;

/**
 * Created by rodri on 16/04/2018.
 */

public class Constants {
    public static final int GENERAL_JSON_RESPONSE = 0;
    public static final int SINGLE_ITEM_JSON_RESPONSE = 1;
    public static final int FILTER_JSON_RESPONSE = 2;
    public static final int FAIL_JSON_RESPONSE = 3;

    public static final String TEXT_MATCH_LOWER_PRICE = "LOWER PRICE ITEM";
    public static final String TEXT_MATCH_HIGHER_PRICE = "HIGHER PRICE ITEM";
    public static final String TEXT_MATCH_ON_SALE = "ON SALE ITEM";
    public static final String TEXT_MATCH_SINGLE_ITEM = "SINGLE ITEM JSON FOR TEST";

    public static final int DEFAULT_SLEEP = 1000;

    /*
        Definition of jSon files used for the tests.
        Each json is designed for a type of test.
     */

    // General Json, the same as the REST response.
    public static final String JSON_MOCK = "jsonamaro.json";

    // Single item just to test if the Click and Show products details is working.
    public static final String SINGLE_ITEM_JSON_MOCK = "sigle_item_json.json";

    // Designed to test the application filters by checking always the first
    // item of the recyclerView
    public static final String FILTER_ITEMS_JSON_MOCK = "filter_items_json.json";
}
