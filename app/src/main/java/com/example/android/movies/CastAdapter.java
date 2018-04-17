package com.example.android.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.model.Cast;
import com.example.android.movies.networking.NetworkHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by glm9637 on 18.03.2018 17:24.
 */

/**
 * Displays a List of the Cast for the Movie with a Picture, the real name and the Character's name
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Cast> mData;


    public CastAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CastAdapter.CastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View posterView = mInflater.inflate(R.layout.cast_list_item, parent, false);
        return new CastAdapter.CastHolder(posterView);
    }

    @Override
    public void onBindViewHolder(CastAdapter.CastHolder holder, int position) {
        Cast currentItem = mData.get(position);
        Picasso.with(mInflater.getContext()).load(NetworkHelper.getImageUrl(currentItem.getProfilePath(), NetworkHelper.ImageSize.small)).placeholder(R.drawable.progress_animation).into(holder.mActorImage);
        holder.mCharacterText.setText(currentItem.getCharacter());
        holder.mActorText.setText(currentItem.getName());
    }

    public void setData(ArrayList<Cast> newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }

    class CastHolder extends RecyclerView.ViewHolder {

        ImageView mActorImage;
        TextView mCharacterText;
        TextView mActorText;

        CastHolder(final View itemView) {
            super(itemView);
            mActorImage = itemView.findViewById(R.id.cast_picture);
            mCharacterText = itemView.findViewById(R.id.txt_role);
            mActorText = itemView.findViewById(R.id.txt_actor);

        }
    }
}
