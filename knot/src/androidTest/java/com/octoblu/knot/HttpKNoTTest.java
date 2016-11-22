package com.octoblu.knot;

import android.app.Application;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;

import com.github.nkzawa.emitter.Emitter;
import com.octoblu.sanejsonobject.SaneJSONObject;

import org.cesar.knot.HttpKNoT;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpKNoTTest extends AndroidTestCase {
    private HttpKNoT knot;

    private SaneJSONObject client;

    public HttpKNoTTest() {
        super();

        SaneJSONObject config = new SaneJSONObject();
        config.putOrIgnore("server", "localhost");
        config.putIntOrIgnore("port", 3000);

        knot = new HttpKNoT(config, getContext());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        knot.on(HttpKNoT.REGISTER, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject json = (JSONObject) args[0];

                try {
                    knot.uuid = json.getString("uuid");
                    knot.token = json.getString("token");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        knot.register(new SaneJSONObject());
    }

    public void testWhoAmI() {
        knot.on(HttpKNoT.WHOAMI, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject json = (JSONObject) args[0];

            }
        });

        knot.whoami();
    }
}