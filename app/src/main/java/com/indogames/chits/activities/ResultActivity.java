package com.indogames.chits.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.indogames.chits.R;
import com.indogames.chits.beans.Player;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

//        Player[] players = (Player[]) getIntent().getSerializableExtra("Result");
//        showScores(players);
    }

    private void showScores(Player[] players) {
        // this will display scores

    }
}