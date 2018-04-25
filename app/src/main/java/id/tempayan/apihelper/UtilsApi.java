package id.tempayan.apihelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://192.168.1.5/tempayan-mobile-codeigniter/";

    public static final String BASE_URL_IMAGE = "http://192.168.1.5/tempayan-mobile-codeigniter/assets/images/profil/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
