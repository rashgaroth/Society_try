package com.example.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("success")
    public String success;
    @SerializedName("message")
    public String message;

    public String isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
