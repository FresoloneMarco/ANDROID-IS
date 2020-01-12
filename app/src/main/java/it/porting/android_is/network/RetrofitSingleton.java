package it.porting.android_is.network;


import it.porting.android_is.firebaseArchive.bean.RequestBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitSingleton {
    private Network apiService;
    private static RetrofitSingleton instance = null;

    private RetrofitSingleton() {
        createConnection();
    }


    public static RetrofitSingleton getInstance() {
        if (instance == null) {
            instance = new RetrofitSingleton();
        }
        return instance;
    }


    /**
     * NEW RETROFIT API IMPLEMENTATION
     */

    public void createConnection() {
        /*if (base_url.equalsIgnoreCase("")) {
            base_url = "https://safework.cloud";
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setDateFormat("dd-MM-yyyy")
                .create();


        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        if(!performLogin) {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer " + SafeWorkApp.token)
                                    .build();
                            return chain.proceed(newRequest);
                        } else
                            return chain.proceed(chain.request());
                    }
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        apiService = retrofit.create(ApiService.class);*/

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.195:3000")
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(Network.class);


    }


    public void performCreatePDF(RequestBean requestBean, Callback<Void> userCallback) {
        Call<Void> response = apiService.createPDF(requestBean);
        response.enqueue(userCallback);
    }

    public void performCreateApprovedExcel(Callback<Void> userCallback){
        Call<Void> response = apiService.createApprovedExcel();
        response.enqueue(userCallback);
    }

    public void performCreateRefusedExcel(Callback<Void> userCallback){
        Call<Void> response = apiService.createRefusedExcel();
        response.enqueue(userCallback);
    }



}

