package nmct.jaspernielsmichielhein.watchfriends.database.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import nmct.jaspernielsmichielhein.watchfriends.provider.Contract;

public class SaveWatchedEpisodeToDBTask extends AsyncTask<ContentValues, Void, Void> {

    private Context mContext;
    public SaveWatchedEpisodeToDBTask(Context context) {mContext = context; }

    @Override
    protected Void doInBackground(ContentValues... params) {
        mContext.getContentResolver().insert(Contract.WATCHED_URI, params[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}