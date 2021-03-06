package nmct.jaspernielsmichielhein.watchfriends.helper;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.facebook.login.LoginManager;

public class AuthHelper {

    private static String mToken = "";
    private static Account mAccount;
    private static AccountManager mAccountManager;
    private static AccountAuthenticatorResponse mAccountAuthenticatorResponse;

    public static String getEmail(Context context) {
        mAccountManager = AccountManager.get(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Account[] accounts = mAccountManager.getAccountsByType(Contract.ACCOUNT_TYPE);

        if (accounts.length > 0) {
            return accounts[0].name;
        } else {
            return null;
        }
    }

    public static Account getAccount(Context context) {
        mAccountManager = AccountManager.get(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Account[] accounts = mAccountManager.getAccountsByType(Contract.ACCOUNT_TYPE);

        if (accounts.length > 0) {
            return accounts[0];
        } else {
            return null;
        }
    }

    public static String getAuthToken(Context context) {
        if (mToken.equals("")) {
            mAccountManager = AccountManager.get(context);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Account[] accounts = mAccountManager.getAccountsByType(Contract.ACCOUNT_TYPE);

            if (accounts.length > 0) {
                mAccount = accounts[0];
                try {
                    String token = new TokenTask().execute().get();
                    mToken = token;
                } catch (Exception e) {
                    Log.d("Error: ", e.getMessage());
                    return "";
                }
            } else {
                return null;
            }
        }

        return "Bearer " + mToken;
    }

    private static class TokenTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                return mAccountManager.blockingGetAuthToken(mAccount, "access_token", false);
            } catch (Exception e) {
                Log.d("Error: ", e.getMessage());
                return "";
            }
        }
    }

    public static Boolean isTokenExpired(Context context) {
        return false;
    }

    public static Boolean isUserLoggedIn(Context context) {
        mAccountManager = AccountManager.get(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        Account[] accounts = mAccountManager.getAccountsByType(nmct.jaspernielsmichielhein.watchfriends.helper.Contract.ACCOUNT_TYPE);

        return accounts.length > 0;
    }

    public static void logUserOff(Context context) {
        mAccountManager = AccountManager.get(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Account[] accounts = mAccountManager.getAccountsByType(nmct.jaspernielsmichielhein.watchfriends.helper.Contract.ACCOUNT_TYPE);

        for (Account account : accounts) {
            removeAccount(context, account);
        }
    }

    public static void logUserOff(Context context, Account account) {
        mAccountManager = AccountManager.get(context);

        removeAccount(context, account);
    }

    private static void removeAccount(Context context, Account account) {
        LoginManager.getInstance().logOut();
        if (Build.VERSION.SDK_INT >= 22) {
            mAccountManager.removeAccount(account, (Activity) context, null, null);
        } else {
            mAccountManager.removeAccount(account, null, null);
        }
    }

}