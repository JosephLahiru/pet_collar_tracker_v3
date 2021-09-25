package com.example.pet_collar_tracker_v3;

import java.util.ArrayList;
import java.util.List;

public class user {

    public String usrName,deviceCode;
    public List<String> deviceCodes = new ArrayList<>();

    public user(){

    }

    public user(String usrName, String deviceCode){
        this.usrName = usrName;
        this.deviceCodes.add(deviceCode);

    }

}
