package com.spx.entity;

import com.google.gson.JsonObject;

/**
 * Created by mikael0 on 13.04.17.
 */
public interface Parcelable {
    JsonObject toJson();
}
