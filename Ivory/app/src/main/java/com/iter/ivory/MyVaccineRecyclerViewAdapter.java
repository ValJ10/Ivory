package com.iter.ivory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iter.ivory.VaccineFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyVaccineRecyclerViewAdapter extends RecyclerView.Adapter<MyVaccineRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private ArrayList<Vaccines> vaccinations;


    public MyVaccineRecyclerViewAdapter(ArrayList<Vaccines> vaccinations, OnListFragmentInteractionListener listener) {
        this.vaccinations = vaccinations;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vaccine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameView.setText(vaccinations.get(position).getVaccineName());
        Calendar calvac = Calendar.getInstance();
        calvac.setTime(vaccinations.get(position).getStartDate());
        Calendar calnow = Calendar.getInstance();
        calnow.setTime(new Date());
        int diffyear = calnow.get(Calendar.YEAR) - calvac.get(Calendar.YEAR);
        if (diffyear == 0) {
            holder.lastView.setText("This Year");
        }else if (diffyear == 1){
            holder.lastView.setText("1 Year Ago");
        }else{
            holder.lastView.setText(diffyear + " Years Ago");

        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(
                            vaccinations.get(position).getVaccineName(),
                            vaccinations.get(position).getSubName(),
                            vaccinations.get(position).getStartDate(),
                            vaccinations.get(position).getRemindDate(),
                            holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vaccinations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameView;
        public final TextView lastView;
        public final ImageView vacicon;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            vacicon = view.findViewById(R.id.vacicon);
            nameView = view.findViewById(R.id.name);
            lastView = view.findViewById(R.id.lastvac);
        }
    }
}
