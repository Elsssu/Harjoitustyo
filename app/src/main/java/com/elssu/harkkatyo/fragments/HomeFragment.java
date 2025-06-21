package com.elssu.harkkatyo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elssu.harkkatyo.Black;
import com.elssu.harkkatyo.Green;
import com.elssu.harkkatyo.Lutemon;
import com.elssu.harkkatyo.LutemonListAdapter;
import com.elssu.harkkatyo.LutemonLocation;
import com.elssu.harkkatyo.Orange;
import com.elssu.harkkatyo.Pink;
import com.elssu.harkkatyo.R;
import com.elssu.harkkatyo.Storage;
import com.elssu.harkkatyo.White;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<Lutemon> lutemons;
    private LutemonListAdapter lutemonListAdapter;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

        }
    }


    @Override
    public void onViewCreated(@NonNull View view,
                  @Nullable Bundle savedInstanceState){
    //Implementing recyclerview in fragment
    //Source: https://www.geeksforgeeks.org/how-to-implement-recylerview-in-a-fragment-in-android/

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvLutemonList);

        RecyclerView recyclerView = view.findViewById(R.id.rvLutemonList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(lutemonListAdapter);




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        view.setBackgroundResource(R.drawable.home_bg);
        Button lutemonsCreateMenuButton = view.findViewById(R.id.LutemonsCreateMenuButton);

        lutemons = new ArrayList<>(Storage.getInstance().getAllLutemon());
        lutemonListAdapter = new LutemonListAdapter(lutemons);

        //AI Copilot helped me make this pop-up menu below
        lutemonsCreateMenuButton.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            dialog.setContentView(R.layout.menu_popup);


            Button confirm = dialog.findViewById(R.id.confrimButton);
            RadioGroup typeRadioGroup = dialog.findViewById(R.id.TypeRadioGroup);
            EditText nameText = dialog.findViewById(R.id.nameText);
            TextView statText = dialog.findViewById(R.id.statsText);
            TextView statName = dialog.findViewById(R.id.statsNameText);
            ImageView imageLutemon = dialog.findViewById(R.id.imgLutemonType);


            //I made this radiobutton change listener with the help of copilot
            typeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                //Displaying stats for selected lutemon radiobutton
                String stats = "";
                String statNameString = "";
                int drawableRes = 0;

                if (checkedId == R.id.orangeRB) {
                    stats = "Attack:      9\n"
                            + "Defense:   0\n"
                            + "HP:          16";
                    statNameString = "Orange";
                    drawableRes = R.drawable.orangelutemon;
                }
                else if (checkedId == R.id.blackRB) {
                    stats = "Attack:      8\n"
                            + "Defense:   1\n"
                            + "HP:          17";
                    statNameString = "Black";
                    drawableRes = R.drawable.blacklutemon;
                }
                else if (checkedId == R.id.whiteRB) {
                    stats = "Attack:      7\n"
                            + "Defense:   2\n"
                            + "HP:          18";
                    statNameString = "White";
                    drawableRes = R.drawable.whitelutemon;
                }
                else if (checkedId == R.id.greenRB) {
                    stats = "Attack:      6\n"
                            + "Defense:   3\n"
                            + "HP:          19";
                    statNameString = "Green";
                    drawableRes = R.drawable.greenlutemon;
                }
                else if (checkedId == R.id.pinkRB) {
                    stats = "Attack:      5\n"
                            + "Defense:   4\n"
                            + "HP:          20";
                    statNameString = "Pink";
                    drawableRes = R.drawable.pinklutemon;
                }

                // update text
                statName.setText(statNameString);
                statText.setText(stats);

                // show & set image
                imageLutemon.setVisibility(View.VISIBLE);
                if (drawableRes != 0) {
                    imageLutemon.setImageResource(drawableRes);
                }
            });

            confirm.setOnClickListener(rg -> {
                int selectedId;
                selectedId = typeRadioGroup.getCheckedRadioButtonId();
                String enteredName = nameText.getText().toString();

                if (selectedId == -1) {
                    statText.setText("Please pick a Lutemon type");
                    return;
                }
                if (enteredName.isEmpty()) {
                    statText.setText("Please enter a name");
                    return;
                }

                Lutemon newLutemon;

                if (selectedId == R.id.orangeRB) {
                    newLutemon = new Orange(enteredName);
                } else if (selectedId == R.id.blackRB) {
                    newLutemon = new Black(enteredName);
                } else if (selectedId == R.id.whiteRB) {
                    newLutemon = new White(enteredName);
                } else if (selectedId == R.id.greenRB) {
                    newLutemon = new Green(enteredName);
                } else {
                    newLutemon = new Pink(enteredName);
                }


                Storage.getInstance().addLutemon(newLutemon);

                lutemons.clear();
                lutemons.addAll(Storage.getInstance().getAllLutemon());
                lutemonListAdapter.notifyDataSetChanged();

                ArrayList<Lutemon> list = Storage.getInstance().getAllLutemon();
                Log.d("HomeFragmenterrory", "Lutemon list size=" + list.size());
                for (Lutemon l : list) {
                    Log.d("HomeFragmenterrory", "  ▶ " + l.getName() + " / " + l.getColor());
                }
                dialog.dismiss();
            });lutemonListAdapter.showAllButtons();
            dialog .show();


        });
        ArrayList<Lutemon> lutemons = Storage.getInstance().getAllLutemon();
        Button moveButton = view.findViewById(R.id.MoveButton);
        moveButton.setOnClickListener(v -> {
            if(lutemons.size() == 0) {
                Toast.makeText(requireContext(), "You don't have any Lutemons yet", Toast.LENGTH_SHORT).show();
                return;
            }
            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            dialog.setContentView(R.layout.manage_popup);


            Button confirmMove = dialog.findViewById(R.id.confirmMoveButton);
            RadioGroup destinationRadioGroup = dialog.findViewById(R.id.DestinationRadioGroup);
            TextView movStatText = dialog.findViewById(R.id.MovStatusText);




            confirmMove.setOnClickListener(rg -> {
                int selectedId;
                selectedId = destinationRadioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    movStatText.setText("You haven't selected anything yeat");
                    return;
                }
                String choice;

                if (selectedId == R.id.trainingAreaRB) {
                    if(Storage.getInstance().getTrainingArea().listLutemons() == null){
                    }

                } else if (selectedId == R.id.battleFieldRB) {
                    Storage.getInstance().getBattleField().addLutemon(lutemon);
                } else if (selectedId == R.id.deleteRB) {

                }
                lutemonListAdapter.setRGChoice(choice);


                lutemons.clear();
                lutemons.addAll(Storage.getInstance().getAllLutemon());
                lutemonListAdapter.notifyDataSetChanged();

                ArrayList<Lutemon> list = Storage.getInstance().getAllLutemon();
                Log.d("HomeFragmenterrory", "Lutemon list size=" + list.size());
                for (Lutemon l : list) {
                    Log.d("HomeFragmenterrory", "  ▶ " + l.getName() + " / " + l.getColor());
                }
                dialog.dismiss();
            });
            dialog .show();
        });
        return view;
    }
}