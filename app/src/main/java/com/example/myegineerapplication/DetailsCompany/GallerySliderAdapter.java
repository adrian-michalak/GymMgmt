package com.example.myegineerapplication.DetailsCompany;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myegineerapplication.R;
import com.example.myegineerapplication.model.ImageModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class GallerySliderAdapter extends RecyclerView.Adapter<GallerySliderAdapter.ImageViewHolder> {
  private Context mContext;
  private List<ImageModel> mImageModelList;

     GallerySliderAdapter(Context context, List<ImageModel> imageModelList) {
        mContext = context;
         mImageModelList = imageModelList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
         ImageModel uploadImage = mImageModelList.get(position);
       // holder.textViewName.setText(uploadImage.getName());
        Glide.with(mContext).load(uploadImage.getImageUrl()).into(holder.imageView);
       // Glide.with(context).load(imageModel.getImageUrl()).into(holder.imageView);
       // Picasso.with(mContext).load(uploadImage.getImageUrl()).fit().centerCrop().into(holder.imageView);
       // holder.imageView.setImageURI(Uri.parse(imageModel.getImageUrl()));

    }

    @Override
    public int getItemCount() {
        return mImageModelList.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        ImageView imageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.ImageNameTextView);
            imageView = itemView.findViewById(R.id.imageViewItem);

        }
    }
}
