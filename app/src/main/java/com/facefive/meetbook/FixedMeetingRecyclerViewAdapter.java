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
    private ArrayList<SingleRow> mylist;
   /* private ArrayList<String> mNames;
    private ArrayList<String> mDescriptions;*/

    public FixedMeetingRecyclerViewAdapter(Context mContext,ArrayList<SingleRow> list) {
        this.mContext = mContext;
        this.mylist = list;
      /*  this.mNames = mNames;
        this.mDescriptions = mDescriptions;*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_fixed_meetings_home,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SingleRow temp = mylist.get(position);
        holder.image.setImageResource(temp.image);
        holder.name.setText(temp.name);
        holder.description.setText(temp.description);
        holder.purpose.setText(temp.purpose);

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView description;
        TextView purpose;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image_rv);
            name = itemView.findViewById(R.id.name_rv);
            description = itemView.findViewById(R.id.descrotion_rv);
            purpose = itemView.findViewById(R.id.purpose_rv);



        }
    }
}
