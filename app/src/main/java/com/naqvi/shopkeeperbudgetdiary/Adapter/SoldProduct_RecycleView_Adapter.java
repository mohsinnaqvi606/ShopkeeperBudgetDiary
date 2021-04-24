package com.naqvi.shopkeeperbudgetdiary.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.Models.SellProduct;
import com.naqvi.shopkeeperbudgetdiary.R;

import java.util.ArrayList;

public class SoldProduct_RecycleView_Adapter extends RecyclerView.Adapter<SoldProduct_RecycleView_Adapter.ViewHolder> {
    private ArrayList<SellProduct> products_list = new ArrayList<SellProduct>();
    private Context mContext;


    public SoldProduct_RecycleView_Adapter(Context mContext, ArrayList<SellProduct> products_list) {
        this.products_list = products_list;
        this.mContext = mContext;
    }

    @Override
    public SoldProduct_RecycleView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final SoldProduct_RecycleView_Adapter.ViewHolder holder, final int position) {
        final SellProduct s = products_list.get(position);
//        holder.tv_Event_Title.setText(s.Title);
//        holder.tv_Date.setText(s.Starting_Date);
//        holder.tv_Location.setText(s.Venue);
//        holder.tv_Time.setText(s.Time);

        holder.edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, PostAgain_Event_Activity.class);
//                intent.putExtra("EventId", s.SJE_Detail_Id);
//                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Event_Title, tv_Date, tv_Location, tv_Time;
        ImageView edit_image, delete_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            tv_Event_Title = itemView.findViewById(R.id.tv_Event_Title);
//            tv_Date = itemView.findViewById(R.id.tv_Date);
//            tv_Location = itemView.findViewById(R.id.tv_Location);
//            tv_Time = itemView.findViewById(R.id.tv_Time);
//
//            edit_image = itemView.findViewById(R.id.edit_image);
//            delete_image = itemView.findViewById(R.id.delete_image);
        }
    }
}

