package com.techinnovationltd.jabenriderapp.retrofit;

public class ServerResponse {

        String success;

        String message, user_id;

    public ServerResponse(String success, String message, String user_id) {
        this.success = success;
        this.message = message;
        this.user_id = user_id;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
