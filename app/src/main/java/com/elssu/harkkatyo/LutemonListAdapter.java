package com.elssu.harkkatyo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LutemonListAdapter extends RecyclerView.Adapter<LutemonViewHolder> {

    private ArrayList<Lutemon> lutemons;

    private boolean showButtons = true;

    private int Choice;
    public LutemonListAdapter(ArrayList<Lutemon> lutemons) {

        this.lutemons = lutemons;
    }

    public void showSelectButtons() {
        showButtons = false;
        notifyDataSetChanged();
    }
    public void setRGChoice(int Choice) {
        this.Choice = Choice;
    }



    @NonNull
    @Override
    public LutemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lutemonView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lutemon_view, parent, false);
        return new LutemonViewHolder(lutemonView);
    }

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

        holder.selectLutemon.setVisibility(showButtons? View.GONE : View.VISIBLE);
        holder.selectLutemon.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                 int pos = holder.getAdapterPosition();
                 Lutemon selectedLutemon = lutemons.get(pos);
                 int lutemonId = selectedLutemon.getId();

                notifyItemRangeChanged(0, getItemCount());
                switch(Choice) {
                    case 1:
                        Storage.getInstance().getTrainingArea().addLutemon(Storage.getInstance().getHome().getLutemon(lutemonId));
                        break;
                    case 2:
                        Storage.getInstance().getBattleField().addLutemon(Storage.getInstance().getHome().getLutemon(lutemonId));
                        break;
                    case 3:
                        Storage.getInstance().getHome().getLutemon(lutemonId);
                        break;
                    case 0:
                        return;
                }
                lutemons.clear();
                lutemons.addAll(Storage.getInstance().getHome().listLutemons());
                showButtons = true; // <-- should be true, not false
                notifyDataSetChanged();
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
