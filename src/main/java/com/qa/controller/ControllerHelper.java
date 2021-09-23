package com.qa.controller;

import com.jayway.jsonpath.JsonPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ControllerHelper {

    public void createHeaders(Properties config, Map<String, String> headers) {
        headers.put("X-CMC_PRO_API_KEY",config.getProperty("API_KEY"));
    }

    public Map<String,String> getCryptoInfoData(String result,String currencyId) {
        HashMap<String,String> cryptoData =  new HashMap<>();
        cryptoData.put("logo", JsonPath.read(result, "$.data." + currencyId + ".logo" ).toString());
        cryptoData.put("technicalDoc",JsonPath.read(result, "$.data." + currencyId + ".urls.technical_doc" ).toString());
        cryptoData.put("symbol",JsonPath.read(result, "$.data." + currencyId + ".symbol" ).toString());
        cryptoData.put("dateAdded",JsonPath.read(result, "$.data." + currencyId + ".date_added" ).toString());
        cryptoData.put("tags",JsonPath.read(result, "$.data." + currencyId + ".tags" ).toString());
        return cryptoData;
    }

    public Map<String,String> getCurrenciesListWithMineableTag(String result,String currencyId) {
        HashMap<String,String> cryptoData =  new HashMap<>();
        cryptoData.put("tags",JsonPath.read(result, "$.data." + currencyId + ".tags" ).toString());

        Function<String, String> validate = (x) -> {
            if (Arrays.stream(JsonPath.read(result, "$.data." + x + ".tags" ).toString().replaceAll("[^,a-zA-Z0-9]","").split(",")).anyMatch("mineable"::equals)) {
                return x;
            }
            return "";
        };

        Arrays.stream(currencyId.split(",")).map(validate).collect(Collectors.toList()).forEach(System.out::println);

        Arrays.stream(JsonPath.read(result, "$.data." + currencyId + ".tags" ).toString().replaceAll("[^,a-zA-Z0-9]","").split(",")).anyMatch("mineable"::equals);
        return cryptoData;
    }
}
