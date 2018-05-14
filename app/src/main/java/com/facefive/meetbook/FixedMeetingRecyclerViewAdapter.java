package com.facefive.meetbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FixedMeetingRecyclerViewAdapter  extends RecyclerView.Adapter<FixedMeetingRecyclerViewAdapter.ViewHolder>{


    private Context mContext;
    private ArrayList<Integer> mImageIDs;
    private ArrayList<String> mNames;
    private ArrayList<String> mDescriptions;

    public FixedMeetingRecyclerViewAdapter(Context mContext, ArrayList<Integer> mImageIDs, ArrayList<String> mNames, ArrayList<String> mDescriptions) {
        this.mContext = mContext;
        this.mImageIDs = mImageIDs;
        this.mNames = mNames;
        this.mDescriptions = mDescriptions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_fixed_meetings_home,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.image.setImageResource(mImageIDs.get(position));
        holder.name.setText(mNames.get(position));
        holder.description.setText(mDescriptions.get(position));

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image_rv);
            name = itemView.findViewById(R.id.name_rv);
            description = itemView.findViewById(R.id.descrotion_rv);



        }
    }
}
