package com.ynov.h.blizzardreader.data.remote;

public class ApiUtils {

    public static final String BASE_URL = "https://omgvamp-hearthstone-v1.p.mashape.com/";

    public static HSService getHSService() {
        return RetrofitClient.getClient(BASE_URL).create(HSService.class);

    }
}
