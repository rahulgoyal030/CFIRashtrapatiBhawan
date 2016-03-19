package network;

/**
 * Created by Raja on 3/19/2016.
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIExecutor {

    //TODO add server address
    private static final String SERVER_URL = "";

//    private static final String SERVER_URL = "http://localhost:5000";

    public static final String LOGIN_API = SERVER_URL + "/login";

    public static final String REGISTER_API = SERVER_URL + "/register";
    public static final String GET_NEARBY_TOILET = SERVER_URL + "/getNearbyToilet";


    public static final String CHECK_IN_API = SERVER_URL + "/checkin";
    public static final String CHECK_OUT_API = SERVER_URL + "/checkout";

    public static final String REVIEW_API = SERVER_URL + "/review";

    public static final String HISTORY_API = SERVER_URL + "/details";

    public static final String ONLINE_API = SERVER_URL + "/online";

    public static final String POKE_API = SERVER_URL + "/poke";


    public static void get(String url, final APICallback callback)
    {

        new AsyncTask<String, String, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {
                JSONObject result = null;
                try {
                    Log.d("DEBUG::API", "Executing " + params[0]);
                    URL mUrl = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
                    Integer response = connection.getResponseCode();
                    Log.d("DEBUG::API", "Response Code " + response);
                    if(response==200)
                    {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder content = new StringBuilder();
                        while ((line = bufferedReader.readLine()) != null)
                            content.append(line).append("\n");
                        bufferedReader.close();
                        result = new JSONObject(content.toString());
                        Log.d("DEBUG::API", "Response " + content);
                    }
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(JSONObject response) {

                try {
                    Integer responseCode = (Integer) response.get("status");
                    switch (responseCode)
                    {
                        case 200:
                            callback.onResponse(response);
                            break;
                        case 400:
                        case 404:
                            callback.onError(response);
                            break;
                    }
                }
                catch (Exception e)
                {
                    callback.onError(response);
                    e.printStackTrace();
                }

            }
        }.execute(url);

    }


}
