package de.hansknoechel.holyweather.utils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import de.hansknoechel.holyweather.activities.MainActivity;
import de.hansknoechel.holyweather.interfaces.RequestCallbackListener;

/**
 * Created by hans on 07.05.15.
 */
public class RequestManager {
    RequestQueue queue;

    /**
     * Sets the activity context be used inside Volley (https://github.com/mcxiaoke/android-volley).
     *
     * @param context  The activity context to be set.
     *
     */
    public RequestManager(MainActivity context) {
        this.queue = Volley.newRequestQueue(context);
    }

    /**
     * Manages simple POST and GET requests.
     *
     * @param method    The request method.
     * @param url       The remote api url.
     * @param callback  The callback on success
     *
     */
    public void load(final int method, final String url, final RequestCallbackListener callback) {
        final JsonObjectRequest request = new JsonObjectRequest(method, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                queue.stop();

                try {
                    callback.onResponseFinished(response);
                } catch (JSONException e) {

                    callback.onResponseError(e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onResponseError(error.getLocalizedMessage());
            }
        });

        this.queue.add(request);
        this.queue.start();
    }
}
