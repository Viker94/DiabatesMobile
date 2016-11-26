package com.utp.projekt.Controller;

import org.json.JSONObject;

/**
 * Created by Marcin on 26.11.2016.
 */

public interface OnJSONResponseCallback {
    public void onJSONResponse(boolean success, JSONObject response);
}
