package com.tugcenurdaglar.sozlukuygulamasi;

public class ApiUtils {

    public static final String BASE_URL = "http://kasimadalan.pe.hu/";

    public static KelimelerDaoInterface getKisilerDaoInterface(){
        return RetrofitClient.getClient(BASE_URL).create(KelimelerDaoInterface.class);
    }
}
