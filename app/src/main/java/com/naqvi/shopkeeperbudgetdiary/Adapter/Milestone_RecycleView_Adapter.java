package com.naqvi.shopkeeperbudgetdiary.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naqvi.shopkeeperbudgetdiary.Activities.BuyerForm_Activity;
import com.naqvi.shopkeeperbudgetdiary.Activities.Edit_Milestone_Activity;
import com.naqvi.shopkeeperbudgetdiary.Activities.Edit_Product_Activity;
import com.naqvi.shopkeeperbudgetdiary.Activities.Milestone_Detail_Activity;
import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Milestone;
import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.ImageUtil;

import java.util.ArrayList;


public class Milestone_RecycleView_Adapter extends RecyclerView.Adapter<Milestone_RecycleView_Adapter.ViewHolder> {
    private ArrayList<Milestone> milestone_list = new ArrayList<Milestone>();
    private Context mContext;
    private DataBaseHelper db;


    public Milestone_RecycleView_Adapter(Context mContext, ArrayList<Milestone> milestone_list) {
        this.milestone_list = milestone_list;
        this.mContext = mContext;
        db = new DataBaseHelper(mContext);
    }

    @Override
    public Milestone_RecycleView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.milestone_item_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Milestone_RecycleView_Adapter.ViewHolder holder, final int position) {
        final Milestone m = milestone_list.get(position);
        holder.tv_startingDate.setText(m.StartDate);
        holder.tv_endingDate.setText(m.EndDate);
        holder.tv_totalPrice.setText(m.TotalPrice);
        holder.tv_status.setText(m.Status);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"View", "Update", "Delete", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Milestone Options");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("View")) {
                            Intent intent = new Intent(mContext, Milestone_Detail_Activity.class);
                            intent.putExtra("Id", milestone_list.get(position).ID + "");
                            mContext.startActivity(intent);
                            dialog.dismiss();
                        } else if (options[item].equals("Update")) {
                            Intent intent = new Intent(mContext, Edit_Milestone_Activity.class);
                            intent.putExtra("Id", milestone_list.get(position).ID + "");
                            mContext.startActivity(intent);
                            dialog.dismiss();
                        } else if (options[item].equals("Delete")) {
                            int isDeleted = db.delete_Milestone(milestone_list.get(position).ID + "");
                            if (isDeleted == 1) {
                                milestone_list.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return milestone_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_startingDate, tv_endingDate, tv_totalPrice, tv_status;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_startingDate = itemView.findViewById(R.id.tv_startingDate);
            tv_endingDate = itemView.findViewById(R.id.tv_endingDate);
            tv_totalPrice = itemView.findViewById(R.id.tv_totalPrice);
            tv_status = itemView.findViewById(R.id.tv_status);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}

