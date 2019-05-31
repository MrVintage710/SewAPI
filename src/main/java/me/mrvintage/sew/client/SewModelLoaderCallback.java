package me.mrvintage.sew.client;

import java.util.HashMap;

public interface SewModelLoaderCallback {

    public void onLoadComplete(HashMap<String, SewModel> models);
}
