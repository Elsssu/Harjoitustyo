package com.elssu.harkkatyo;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LutemonViewHolder extends RecyclerView.ViewHolder {
    ImageView lutemonImage;

    TextView lutemonName, lutemonAttack, lutemonDefense, lutemonHealth, lutemonExperience, lutemonWins, lutemonLosses;

    Button selectLutemon;

    public LutemonViewHolder(@NonNull View itemView) {
        super(itemView);
        lutemonImage = itemView.findViewById(R.id.imgLutemon);
        lutemonName = itemView.findViewById(R.id.txtLutemonName);
        lutemonAttack = itemView.findViewById(R.id.txtLutemonAttack);
        lutemonDefense = itemView.findViewById(R.id.txtLutemonDefense);
        lutemonHealth = itemView.findViewById(R.id.txtLutemonHealth);
        lutemonExperience = itemView.findViewById(R.id.txtLutemonExperience);
        lutemonWins = itemView.findViewById(R.id.txtLutemonWins);
        lutemonLosses = itemView.findViewById(R.id.txtLutemonLosses);
        selectLutemon = itemView.findViewById(R.id.SelectLutemonButton);

    }
}
