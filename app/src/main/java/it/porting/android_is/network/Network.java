package it.porting.android_is.network;


import it.porting.android_is.firebaseArchive.bean.RequestBean;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Network {

    @POST("createPDF")
    Call<Void> createPDF(@Body RequestBean requestBean);

    @POST("createApprovedExcel")
    Call<Void> createApprovedExcel();

}
