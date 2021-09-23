package com.qa.controller;

import com.qa.rest.HttpResponse;
import com.qa.rest.RestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CoinMarketCapController {

    RestClient restClient;
    ControllerHelper controllerHelper;

    public CoinMarketCapController() {
        restClient = new RestClient();
        controllerHelper = new ControllerHelper();
    }

    public HttpResponse getCryptocurrencyId(Properties config, String currencySymbol){
        Map<String, String> headers = new HashMap<String, String>();
        controllerHelper.createHeaders(config,headers);
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("listing_status","active");
        queryParams.put("symbol",currencySymbol);
        return restClient.doGet(config.getProperty("BASE_URL")
                + config.getProperty("CRYPTOCURRENCY_MAP"), headers, queryParams);
    }

    public HttpResponse priceConversion(Properties config, String currencySymbol,String amount,String id) {
        Map<String, String> headers = new HashMap<String, String>();
        controllerHelper.createHeaders(config,headers);
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("amount",amount);
        queryParams.put("convert",currencySymbol);
        queryParams.put("id",id);
        return restClient.doGet(config.getProperty("BASE_URL")
                + config.getProperty("TOOLS_CONVERSION"), headers, queryParams);
    }

    public HttpResponse getCryptocurrencyInfo(Properties config, String id) {
        Map<String, String> headers = new HashMap<String, String>();
        controllerHelper.createHeaders(config,headers);
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("id",id);
        return restClient.doGet(config.getProperty("BASE_URL")
                + config.getProperty("CRYPTO_INFO"), headers, queryParams);
    }
}
