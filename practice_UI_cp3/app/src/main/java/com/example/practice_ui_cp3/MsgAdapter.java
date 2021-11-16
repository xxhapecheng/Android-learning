package com.example.practice_ui_cp3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{

    private List<Msg> mMsglist;
    static class ViewHolder extends  RecyclerView.ViewHolder{
        LinearLayout leftlayout;
        LinearLayout rightlaout;
        TextView leftmsg;
        TextView rightmsg;

        public ViewHolder(View view){
            super(view);
            leftlayout=(LinearLayout) view.findViewById(R.id.left_laout);
            rightlaout=(LinearLayout) view.findViewById(R.id.right_laout);
            leftmsg=(TextView) view.findViewById(R.id.left_msg);
            rightmsg=(TextView) view.findViewById(R.id.right_msg);
        }
    }


    public MsgAdapter(List<Msg> msglist){
        mMsglist=msglist;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg=mMsglist.get(position);
        if(msg.getType()==Msg.Type_received){
            holder.leftlayout.setVisibility(View.VISIBLE);
            holder.rightlaout.setVisibility(View.GONE);
            holder.leftmsg.setText(msg.getContent());
        }
        else if(msg.getType()==Msg.Type_sent){
            holder.leftlayout.setVisibility(View.GONE);
            holder.rightlaout.setVisibility(View.VISIBLE);
            holder.rightmsg.setText(msg.getContent());
        }
    }




    @Override
    public int getItemCount() {
        return mMsglist.size();
    }
}
