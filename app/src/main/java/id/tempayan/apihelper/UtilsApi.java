package id.tempayan.apihelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://192.168.42.76/tempayan-mobile-codeigniter/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
