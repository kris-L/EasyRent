package com.rent.kris.easyrent.event;

/**
 * Created by kris on 2019/1/30.
 */
public class UploadSuccessEvent {
    private String message;
    public UploadSuccessEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
