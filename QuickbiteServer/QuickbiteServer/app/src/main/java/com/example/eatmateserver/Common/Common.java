package com.example.eatmateserver.Common;

import com.example.eatmateserver.Model.Request;
import com.example.eatmateserver.Model.User;

public class Common {
    public static User currentUser;
    public static Request currentRequest;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public static final int PICK_IMAGE_REQUEST = 71;

    public static String convertCodeToStatus(String code){
        if(code.equals("0"))
            return "Placed";
        else if (code.equals("1")) {
            return "On the way";
        }
        else if (code.equals("2")) {
            return "Shipped";
        }
        else
            return "Delivered";
    }
}
