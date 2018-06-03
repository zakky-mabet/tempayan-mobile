package id.tempayan.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_TEMPAYAN_MOBILE = "spTempayanApp";

    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_PHOTO = "spPhoto";
    public static final String SP_ID_USER = "spIdUser";

    public static final String SP_HANDPHONE = "spHandphone";
    public static final String SP_TANGGALDAFTAR = "spTanggallahir";
    public static final String SP_NIK = "spNik";
    public static final String SP_NOKK = "spNokk";
    public static final String SP_KDPOS = "spKdpos";
    public static final String SP_TTL = "spTtl";
    public static final String SP_TMPLAHIR = "spTmplahir";
    public static final String SP_TGLLAHIR = "spTgllahir";
    public static final String SP_JNSKELAMIN = "spJnsKelamin";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_RT = "spRt";
    public static final String SP_RW = "spRw";
    public static final String SP_RTRW = "spRtrw";
    public static final String SP_DESA = "spDesa";
    public static final String SP_KECAMATAN = "spKecamatan";
    public static final String SP_AGAMA = "spAgama";
    public static final String SP_PEKERJAAN = "spPekerjaan";
    public static final String SP_KEWARGANEGARAAN = "spKewarganegaraan";
    public static final String SP_STATUSKAWIN = "spStatuskawin";
    public static final String SP_GOLDARAH = "spGoldarah";
    public static final String SP_STATUSKK = "spStatuskk";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    public SharedPrefManager (Context context){
        sp = context.getSharedPreferences(SP_TEMPAYAN_MOBILE, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }
    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }
    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }
    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }
    public String getSPPhoto(){
        return sp.getString(SP_PHOTO, "");
    }
    public int getSPIdUser(){
        return sp.getInt(SP_ID_USER, 0);
    }
    public String getSPIdUserSting(){
        return sp.getString(SP_ID_USER, "");
    }
    public String getSpHandphone(){
        return sp.getString(SP_HANDPHONE, "");
    }
    public String getSpTanggaldaftar(){
        return sp.getString(SP_TANGGALDAFTAR, "");
    }
    public String getSpNik(){
        return sp.getString(SP_NIK, "");
    }
    public String getSpNokk(){
        return sp.getString(SP_NOKK, "");
    }
    public String getSpKdpos(){
        return sp.getString(SP_KDPOS, "");
    }
    public String getSpTtl(){
        return sp.getString(SP_TTL, "");
    }
    public String getSpTmplahir(){
        return sp.getString(SP_TMPLAHIR, "");
    }
    public String getSpTgllahir(){
        return sp.getString(SP_TGLLAHIR, "");
    }
    public String getSpJnskelamin(){
        return sp.getString(SP_JNSKELAMIN, "");
    }
    public String getSpAlamat(){
        return sp.getString(SP_ALAMAT, "");
    }
    public String getSpRt(){
        return sp.getString(SP_RT, "");
    }
    public String getSpRw(){
        return sp.getString(SP_RW, "");
    }
    public String getSpRtrw(){
        return sp.getString(SP_RTRW, "");
    }
    public String getSpDesa(){
        return sp.getString(SP_DESA, "");
    }
    public String getSpKecamatan(){
        return sp.getString(SP_KECAMATAN, "");
    }
    public String getSpAgama(){
        return sp.getString(SP_AGAMA, "");
    }
    public String getSpPekerjaan(){
        return sp.getString(SP_PEKERJAAN, "");
    }
    public String getSpKewarganegaraan(){
        return sp.getString(SP_KEWARGANEGARAAN, "");
    }
    public String getSpStatuskawin(){
        return sp.getString(SP_STATUSKAWIN, "");
    }
    public String getSpGoldarah(){
        return sp.getString(SP_GOLDARAH, "");
    }
    public String getSpStatuskk(){
        return sp.getString(SP_STATUSKK, "");
    }
    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }



}
