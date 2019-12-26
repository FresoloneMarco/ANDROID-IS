package it.porting.android_is.network;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import it.porting.android_is.R;

public class Network {

    private static final String SERVER = "http://192.168.1.9:3000";

    public static class HttpGetRequest extends AsyncTask<Void, Void, String> {

        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;
        private TextView tvServerResponse;

        @Override
        protected String doInBackground(Void... params){
            String result;
            String inputLine;

            try {
                // connect to the server
                URL myUrl = new URL(SERVER);
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                Log.d("DEBUG", "apertura connessione");
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();
                Log.d("DEBUG","Connesso");


                // get the string from the input stream
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
                Log.d("String", result);

            } catch(IOException e) {
                e.printStackTrace();
                result = "error";
                Log.d("Errore", result);

            }

            return result;
        }

        protected void onPostExecute(String result){
            tvServerResponse = tvServerResponse.findViewById(R.id.res);
            super.onPostExecute(result);
            tvServerResponse.setText(result);
        }
    }


}
