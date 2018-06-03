package id.tempayan.apihelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://192.168.1.2/tempayan-mobile-codeigniter/";

    public static final String BASE_URL_IMAGE = "http://192.168.1.2/tempayan-mobile-codeigniter/assets/images/profil/";

    public static final String BASE_URL_FILE_SKKB = "http://192.168.1.2/tempayan-mobile-codeigniter/assets/surat/skkb/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
