package it.porting.android_is.network;


import retrofit2.Call;
import retrofit2.http.GET;

public interface Network {

    @GET("users")
    Call<Void> getUtenti();
}
