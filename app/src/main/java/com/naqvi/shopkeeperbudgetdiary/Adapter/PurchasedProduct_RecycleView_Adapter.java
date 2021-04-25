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
import com.naqvi.shopkeeperbudgetdiary.Activities.Edit_Product_Activity;
import com.naqvi.shopkeeperbudgetdiary.DataBase.DataBaseHelper;
import com.naqvi.shopkeeperbudgetdiary.Models.Product;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.ImageUtil;

import java.util.ArrayList;


public class PurchasedProduct_RecycleView_Adapter extends RecyclerView.Adapter<PurchasedProduct_RecycleView_Adapter.ViewHolder> {
    private ArrayList<Product> products_list = new ArrayList<Product>();
    private Context mContext;
    private DataBaseHelper db;


    public PurchasedProduct_RecycleView_Adapter(Context mContext, ArrayList<Product> products_list) {
        this.products_list = products_list;
        this.mContext = mContext;
        db = new DataBaseHelper(mContext);
    }

    @Override
    public PurchasedProduct_RecycleView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_product_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final PurchasedProduct_RecycleView_Adapter.ViewHolder holder, final int position) {
        final Product s = products_list.get(position);
        holder.tv_Title.setText(s.Title);
        holder.tv_Price.setText(s.Price);
        holder.tv_Quantity.setText(s.Quantity);
        holder.tv_Date.setText(s.Date);
        holder.tv_Time.setText(s.Time);
        Bitmap bm = ImageUtil.convertToImage(s.Image);
        holder.image.setImageBitmap(bm);


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ViewUpdate = mContext.getResources().getString(R.string.ViewUpdate);
                String Sell = mContext.getResources().getString(R.string.Sell);
                String Delete = mContext.getResources().getString(R.string.Delete);
                String Cancel = mContext.getResources().getString(R.string.Cancel);

                final CharSequence[] options = {ViewUpdate, Sell, Delete, Cancel};

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.ProductOptions);

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            Intent intent = new Intent(mContext, Edit_Product_Activity.class);
                            intent.putExtra("Id", products_list.get(position).ID + "");
                            mContext.startActivity(intent);
                            dialog.dismiss();
                        } else if (item == 1) {
                            Double remainingItems = Double.parseDouble(products_list.get(position).Quantity);
                            if (remainingItems > 0) {
                                Intent intent = new Intent(mContext, BuyerForm_Activity.class);
                                intent.putExtra("Id", products_list.get(position).ID + "");
                                mContext.startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "No Item Left", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        } else if (item == 2) {
                            int isDeleted = db.delete_Product(products_list.get(position).ID + "");
                            if (isDeleted == 1) {
                                products_list.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        } else if (item == 3) {
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
        return products_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Title, tv_Price, tv_Quantity, tv_Date, tv_Time;
        ImageView image;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Title = itemView.findViewById(R.id.tv_Title);
            tv_Price = itemView.findViewById(R.id.tv_price);
            tv_Quantity = itemView.findViewById(R.id.tv_quantiy);
            tv_Date = itemView.findViewById(R.id.tv_date);
            tv_Time = itemView.findViewById(R.id.tv_time);
            image = itemView.findViewById(R.id.new_profile_Img);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}

