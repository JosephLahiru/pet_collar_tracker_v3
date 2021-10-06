package com.example.pet_collar_tracker_v3;

import java.util.List;
import java.util.Map;

public class UserList {

    String usrName;
    List <String> deviceCodes;
    String usrType;
    String usrID;
    String usrEmail;

    public String getUsrEmail() {
        return usrEmail;
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

    public String getUsrID() {
        return usrID;
    }

    public void setUsrID(String usrID) {
        this.usrID = usrID;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public void setDeviceCodes(List<String> deviceCodes) {
        this.deviceCodes = deviceCodes;
    }

    public void setUsrType(String usrType) {
        this.usrType = usrType;
    }
}
