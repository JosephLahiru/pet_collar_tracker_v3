package com.example.pet_collar_tracker_v3;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
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
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {

        UserList user = list.get(position);

        try {
            holder.viewUserParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,UserDetailsActivity.class);
                    String.valueOf(intent.putExtra("usrID",String.valueOf(user.getUsrID())));
                    String.valueOf(intent.putExtra("usrName",String.valueOf(user.usrName)));
                    String.valueOf(intent.putExtra("usrDevices",String.valueOf(user.deviceCodes)));
                    String.valueOf(intent.putExtra("usrEmail",String.valueOf(user.usrEmail)));
                    Log.d("User ID",String.valueOf(user.getUsrID()));
                    Log.d("Position", String.valueOf(position));
                    context.startActivity(intent);
                }
            });
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
        View viewUserParent;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            viewUser = itemView.findViewById(R.id.userList_usrName);
            viewDevice = itemView.findViewById(R.id.userList_devicelist);
            viewUserParent = itemView.findViewById(R.id.userList_ParentLayout);

        }


    }

}
