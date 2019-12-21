package it.porting.android_is.gestioneUtente;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import it.porting.android_is.gestioneStudente.MainActivityStudente;


@TargetApi(Build.VERSION_CODES.M)
public class Fingerprint extends FingerprintManager.AuthenticationCallback {

    private Context context;


    public Fingerprint(Context context) {

        this.context = context;

    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);


    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {


        Intent intent = new Intent(context.getApplicationContext(), MainActivityStudente.class);
        context.startActivity(intent);

    }

    public void onAuthenticationFailed() {

        this.update("Auth Failed. ", false);

    }

    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);

    }

    private void update(String s, boolean b) {

        if (b == false) {

            Toast toast = Toast.makeText(context, "Accesso non autorizzato", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}




