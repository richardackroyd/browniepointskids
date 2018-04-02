package centurion.dev.browniepoints.Services.API;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;
import centurion.dev.browniepoints.DataModel.PointsAccount;
import centurion.dev.browniepoints.Screens.PointsSummary.PointsSummaryAdapter;
import centurion.dev.browniepoints.Services.SharedPreference.PointsSummarySPService;

/**
 * Created by rich on 25/11/2017.
 */

//TODO can this be a general API handler - pass in all objects as interfaces and let process?

public class PointsSummaryAPIService extends AsyncTask<Void, Void, ArrayList<PointsAccount>>{

    private final String mURL = "https://mysterious-forest-42652.herokuapp.com/api/points";

    private final PointsSummaryAdapter pointsSummaryAdapter;

    private final SharedPreferences sharedPreferences;

    public PointsSummaryAPIService(PointsSummaryAdapter adapter, SharedPreferences sharedPreferences) {

        this.pointsSummaryAdapter = adapter;
        this.sharedPreferences = sharedPreferences;

    }

    private InputStream retrieveStream(String url) {

        try {

            URL pointsSummaryEndPoint = new URL(mURL);

            //TODO update to HTTPClient as more robust
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) pointsSummaryEndPoint.openConnection();

            if (myConnection.getResponseCode() == 200) {

                InputStream responseBody = myConnection.getInputStream();

                return responseBody;

            }

        } catch (Exception e) {System.out.println("Exception: " + e); }

        return null;

    }

    //TODO refactor as too large

    @Override
    protected ArrayList<PointsAccount> doInBackground(Void... params) {

        InputStream source =  retrieveStream(mURL);

        ArrayList<PointsAccount> allPointsAccounts = new ArrayList<PointsAccount>();

        PointsSummarySPService pointsSummarySPService = new PointsSummarySPService(sharedPreferences);

        //TODO 1 stops crash if no network connection - should replace with local data caching or an error message

        if ( source == null ) {
            //TODO 1.2 this needs to return the allPointsAccounts array in place of the API call based on last cache
            return pointsSummarySPService.runvalues();
        }

        Gson gson = new GsonBuilder().create();

        JsonReader jsonReader = null;

        try {
            jsonReader = new JsonReader(new InputStreamReader(source, "UTF-8"));

            jsonReader.beginArray();

            PointsAccount pointsAccount;

            while (jsonReader.hasNext()) {

                pointsAccount = gson.fromJson(jsonReader, PointsAccount.class);

                allPointsAccounts.add(pointsAccount);

            }

            jsonReader.close();

        } catch (Exception e){System.out.println("Exception: " + e); return null;}

        //TODO 1.1 this needs to update the SharedPreferences cache with latest data deseriablised to name:value pairs

        pointsSummarySPService.setValues(allPointsAccounts);

        return allPointsAccounts;

    }

    protected void onPostExecute(ArrayList<PointsAccount> allPointsAccounts) {

        Collections.sort(allPointsAccounts);

        pointsSummaryAdapter.upDateEntries(allPointsAccounts);

    }

}
