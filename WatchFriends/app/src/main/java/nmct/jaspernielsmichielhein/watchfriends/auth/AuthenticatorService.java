package nmct.jaspernielsmichielhein.watchfriends.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AuthenticatorService extends Service {

    private Authenticator authenticator;

    public AuthenticatorService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.authenticator = new Authenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }

}