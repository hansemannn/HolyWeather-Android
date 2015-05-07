package de.hansknoechel.holyweather;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hans on 07.05.15.
 */
public class RequestManager {
    RequestQueue queue;

    public RequestManager(MainActivity context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public void load(String url, final MainActivity.RequestCallbackListener callback) {
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                queue.stop();

                try {
                    callback.onResponseFinished(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        this.queue.add(jsObjRequest);
        this.queue.start();
    }
}
