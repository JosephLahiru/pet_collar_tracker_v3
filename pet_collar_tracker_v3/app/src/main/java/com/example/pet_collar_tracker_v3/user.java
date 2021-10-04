package com.example.pet_collar_tracker_v3;

import java.util.ArrayList;
import java.util.List;

public class user {

    public String usrName,usrType,usrEmail;
    public List<String> deviceCodes = new ArrayList<>();

    public user(){

    }

    public user(String usrName, String deviceCode , String usrType, String usrEmail){
        this.usrName = usrName;
        this.deviceCodes.add(deviceCode);
        this.usrType = usrType;
        this.usrEmail =  usrEmail;

    }

}
