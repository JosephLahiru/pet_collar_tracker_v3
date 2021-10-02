package com.example.pet_collar_tracker_v3;

import java.util.List;
import java.util.Map;

public class UserList {

    String usrName;
    List <String> deviceCodes;
    String usrType;
    String usrID;

    public void setUsrID(String usrID) {
        this.usrID = usrID;
    }

    public String getUsrID() {
        return usrID;
    }

    public String getUsrType() {
        return usrType;
    }

    public List<String> getDeviceCodes() {
        return deviceCodes;
    }

    public String getUsrName() {
        return usrName;
    }
}
