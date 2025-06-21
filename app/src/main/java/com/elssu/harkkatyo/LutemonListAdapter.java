package com.elssu.harkkatyo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LutemonListAdapter extends RecyclerView.Adapter<LutemonViewHolder> {

    private ArrayList<Lutemon> lutemons;



    private String rGChoice;
    public LutemonListAdapter(ArrayList<Lutemon> lutemons) {
        this.lutemons = lutemons;
    }

    public void showAllButtons() {
        showButtons = true;
        notifyDataSetChanged();
    }
    public void setrGChoice(String rGChoice) {
        this.rGChoice = rGChoice;
    }

    public void setRGChoice(String choice) {

    }

    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lutemonView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lutemon_view, parent, false);
        return new LutemonViewHolder(lutemonView);
    }
    private boolean showButtons = true;
    @Override
    public void onBindViewHolder(@NonNull LutemonViewHolder holder, int position) {

        Lutemon lutemon = lutemons.get(position);

        holder.lutemonName.setText(lutemons.get(position).getName() + "   (" + lutemons.get(position).getColor() + ")");
        holder.lutemonAttack.setText("Attack: " + (String.valueOf(lutemons.get(position).getAttack())));
        holder.lutemonDefense.setText("Defense: " + (String.valueOf(lutemons.get(position).getDefense())));
        holder.lutemonHealth.setText("Health: " + (String.valueOf(lutemons.get(position).getHealth())) + "/" + (String.valueOf(lutemons.get(position).getMaxHealth())));
        holder.lutemonExperience.setText("Experience: " + (String.valueOf(lutemons.get(position).getExperience())));
        holder.lutemonWins.setText("Wins: " + (String.valueOf(lutemons.get(position).getWins())));
        holder.lutemonLosses.setText("Losses: " + (String.valueOf(lutemons.get(position).getLosses())));

        holder.selectLutemon.setVisibility(showButtons? View.VISIBLE : View.GONE);
        holder.selectLutemon.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //get chosen lutemon and set all select buttons to GONE
                boolean showButtons = false;
                 int pos = holder.getAdapterPosition();

                notifyItemRangeChanged(0, getItemCount());
                Storage.getInstance().getHome().getLutemon(pos);
            }
        });

        switch (lutemon.getColor()) {
            case "Orange":
                holder.lutemonImage.setImageResource(R.drawable.orangelutemon);
                break;
            case "Black":
                holder.lutemonImage.setImageResource(R.drawable.blacklutemon);
                break;
            case "White":
                holder.lutemonImage.setImageResource(R.drawable.whitelutemon);
                break;
            case "Green":
                holder.lutemonImage.setImageResource(R.drawable.greenlutemon);
                break;
            case "Pink":
                holder.lutemonImage.setImageResource(R.drawable.pinklutemon);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }
}
