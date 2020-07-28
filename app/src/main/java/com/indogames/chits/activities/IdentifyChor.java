package com.indogames.chits.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.indogames.chits.R;
import com.indogames.chits.ResultActivity;
import com.indogames.chits.action.ThrowChits;
import com.indogames.chits.beans.Player;
import com.indogames.chits.constants.GameConstants;

import java.io.Serializable;

public class IdentifyChor extends AppCompatActivity {

    private int chorIndex;
    private int policeIndex;
    private boolean isChorGuessed = false;
    private Button scoresButton;
    private Button thrwChits;
//    private int rounds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_identify_chor);

        scoresButton = (Button) findViewById(R.id.showScores);
        thrwChits = (Button) findViewById(R.id.thrwchit);
        scoresButton.setVisibility(View.INVISIBLE);
        thrwChits.setVisibility(View.INVISIBLE);

        Player[] players = (Player[]) getIntent().getSerializableExtra("Players");

        ThrowChits chits = new ThrowChits();
            players = chits.throwchits(players);
            createButtonsForPlayers (players);
    }

    // Creating buttons with the names of players
    private void createButtonsForPlayers(final Player[] players) {
            LinearLayout linear = findViewById(R.id.rootLayout);
            scoresButton = (Button) findViewById(R.id.showScores);

            for (int i=0; i<players.length; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 10);
                Button button = new Button(getApplicationContext());
                button.setId(players[i].getViewId());
                button.setText(players[i].getPlayerName());
                button.setLayoutParams(params);

                if(players[i].getRole().getRole().equalsIgnoreCase(GameConstants.TITLE_POLICE)) {
                    policeIndex = i;
                    button = setPoliceParams(button, players, policeIndex);
                } else if (players[i].getRole().getRole().equalsIgnoreCase(GameConstants.TITLE_CHOR)) {
                    chorIndex = i;
                }
                linear.addView(button);
                final Button finalButton = button;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerPicked(finalButton, players);
                    }
                });

            }

            scoresButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showScores(players);
                }
            });

            thrwChits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                throwChitsAgain(players);
                }
            });
    }

    private Button setPoliceParams(Button button, Player[] players,int police) {
        button.setBackgroundColor(0xFFFFD740); // R.color.colorAppBackground);
        button.setTextColor(0xFFE65100);
        button.setText(players[police].getPlayerName() + " => Police");
        button.setEnabled(false); // disable this button for click events
        return button;
    }

    private void throwChitsAgain(Player[] players) {
        ThrowChits throwChit = new ThrowChits();
        Player[] newPlayers = throwChit.throwchits(players);
        Intent intent = new Intent(getApplicationContext(), IdentifyChor.class);
        intent.putExtra("Players", newPlayers);
        startActivity(intent);
        finish();
    }

    private void showScores(Player[] players) {
        Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
        resultIntent.putExtra("Result", players);
        startActivity(resultIntent);
        finish();
    }

    // A guess has been made - users have guessed a player to be chor
    private void playerPicked(Button button, Player[] players) {
        if (!isChorGuessed) {
            TextView viewResult = (TextView) findViewById(R.id.result);
            ThrowChits chits = new ThrowChits();
            isChorGuessed = true;
            if (isChor(button.getId(), players)) {
                // guessed the chor correctly
                viewResult.setText(GameConstants.RIGHT_GUESS);
            } else {
                // wrong guess fella
                players[policeIndex].getRole().setPoints(GameConstants.SCORE_0);
                players[policeIndex].setScore(players[policeIndex].getRole().getPoints());
                players[chorIndex].getRole().setPoints(GameConstants.SCORE_500);
                players[chorIndex].setScore(players[chorIndex].getRole().getPoints());
                viewResult.setText(GameConstants.WRONG_GUESS);
            }
        }
        scoresButton.setVisibility(View.VISIBLE);
        thrwChits.setVisibility(View.VISIBLE);
    }

    private boolean isChor(int clickedButton, Player[] players) {

        int chorIndex = -1;
        int chorViewId = -1;
        for (int in = 0; in < players.length; in++) {
            if (players[in].getRole().getId() == GameConstants.ID_CHOR) {
                chorIndex = in;
                chorViewId = players[in].getViewId();
            }
        }
        if(clickedButton == chorViewId) {
            return true; // identified the chor correct
        } else {
            return false; // guess was wrong fella!
        }
    }

}