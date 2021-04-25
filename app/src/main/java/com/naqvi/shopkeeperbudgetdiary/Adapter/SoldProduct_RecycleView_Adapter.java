
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
import com.naqvi.shopkeeperbudgetdiary.Models.SellProduct;
import com.naqvi.shopkeeperbudgetdiary.R;
import com.naqvi.shopkeeperbudgetdiary.Utils.ImageUtil;

import java.util.ArrayList;

public class SoldProduct_RecycleView_Adapter extends RecyclerView.Adapter<SoldProduct_RecycleView_Adapter.ViewHolder> {

    private ArrayList<SellProduct> products_list = new ArrayList<SellProduct>();
    private Context mContext;
    DataBaseHelper db;


    public SoldProduct_RecycleView_Adapter(Context mContext, ArrayList<SellProduct> products_list) {
        this.products_list = products_list;
        this.mContext = mContext;
        db = new DataBaseHelper(mContext);
    }

    @Override
    public SoldProduct_RecycleView_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sold_product_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final SoldProduct_RecycleView_Adapter.ViewHolder holder, final int position) {

        final SellProduct s = products_list.get(position);
        holder.tv_buyerName.setText(s.BuyerName);
        holder.tv_buyerNumber.setText(s.BuyerNumber);
        holder.tv_sellingPrice.setText(s.SellingPrice);
        holder.tv_date.setText(s.Date);
        holder.tv_time.setText(s.Time);
        Bitmap bm = ImageUtil.convertToImage(s.Image);
        holder.image.setImageBitmap(bm);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Delete = mContext.getResources().getString(R.string.Delete);
                String Cancel = mContext.getResources().getString(R.string.Cancel);

                final CharSequence[] options = {Delete, Cancel};

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(R.string.ProductOptions);

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            Product p = db.get_ProductById(products_list.get(position).ProductID + "");
                            double totalQuantity = Double.parseDouble(p.Quantity);
                            double newQuantity = Double.parseDouble(products_list.get(position).Quantity);

                            p.Quantity = (totalQuantity + newQuantity) + "";
                            db.update_Product(p);
                            int isDeleted = db.delete_SellProduct(products_list.get(position).ID + "");
                            if (isDeleted == 1) {
                                products_list.remove(position);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();
                            }
                            dialog.dismiss();
                        } else if (item == 1) {
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

        TextView tv_buyerName, tv_buyerNumber, tv_sellingPrice, tv_date, tv_time;
        LinearLayout parent;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_buyerName = itemView.findViewById(R.id.tv_buyerName);
            tv_buyerNumber = itemView.findViewById(R.id.tv_buyerNumber);
            tv_sellingPrice = itemView.findViewById(R.id.tv_sellingPrice);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            parent = itemView.findViewById(R.id.parent);
            image = itemView.findViewById(R.id.image);
        }
    }
}
