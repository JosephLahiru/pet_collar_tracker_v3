package com.example.pet_collar_tracker_v3;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserList_Adaptor extends RecyclerView.Adapter<UserList_Adaptor.UserViewHolder> {

    Context context;

    ArrayList<UserList> list;
//    ArrayList<UserList> devicelist;

    public UserList_Adaptor(Context context, ArrayList<UserList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.user_list_user,parent,false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        UserList user = list.get(position);

        try {
            holder.viewDevice.setText(user.getDeviceCodes().toString());
            holder.viewUser.setText(user.getUsrName());
        }
        catch (Exception e){
            Log.d("firebase", String.valueOf(e));
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView viewUser;
        TextView viewDevice;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            viewUser = itemView.findViewById(R.id.userList_usrName);
            viewDevice = itemView.findViewById(R.id.userList_devicelist);

        }


    }

}
