package de.hansknoechel.holyweather.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hans on 07.05.15.
 */
public interface RequestCallbackListener {

    /**
     * Returns the json object requested from the {@link de.hansknoechel.holyweather.utils.RequestManager}
     *
     * @param result    The json result on success.
     *
     * @see             org.json.JSONObject
     * @see             org.json.JSONException
     */
    void onResponseFinished(JSONObject result) throws JSONException;
}
