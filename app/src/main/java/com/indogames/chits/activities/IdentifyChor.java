package com.indogames.chits.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.ArrayList;

public class IdentifyChor extends AppCompatActivity {

    private int chorIndex;
    private int policeIndex;
    private boolean isChorGuessed = false;
    private Button scoresButton;
    private Button thrwChits;
//    private int rounds;
    ArrayList<Button> playerButtons;
    private CountDownTimer countDown;
    private long remainingTimeLeft = GameConstants.CATCH_CHOR_COUNTDOWN;
    TextView timer;


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
        startCountDown(players, policeIndex, chorIndex);
    }

    // Creating buttons with the names of players
    private void createButtonsForPlayers(final Player[] players) {
        LinearLayout linear = findViewById(R.id.rootLayout);
        scoresButton = (Button) findViewById(R.id.showScores);
        playerButtons = new ArrayList<Button>();

            for (int i=0; i<players.length; i++) {
                Button button = setGeneralButtonParams(players, i);
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
                playerButtons.add(button);
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

    private Button setGeneralButtonParams(Player[] players, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(35, 10, 35, 10);
        Button button = new Button(getApplicationContext());
        button.setId(players[index].getViewId());
        button.setText(players[index].getPlayerName());
        button.setLayoutParams(params);
        button.setHeight(130);
        button.setWidth(130);
        return button;
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
                button.setBackground(createRectangleBorder(Color.GREEN, 10f));
                viewResult.setText(GameConstants.RIGHT_GUESS);
            } else {
                // wrong guess fella
                interchangePoliceChorScores(players, policeIndex, chorIndex);
                button.setBackground(createRectangleBorder(Color.RED, 10f));
                viewResult.setText(GameConstants.WRONG_GUESS);
            }
        }
        countDown.cancel();
        showHiddenButtons();
        disableAllButtons(playerButtons);
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

    private void startCountDown(final Player[] players, final int policeIndex, final int chorIndex) {
        countDown = new CountDownTimer(GameConstants.CATCH_CHOR_COUNTDOWN, GameConstants.COUNT_DOWN_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timer.setText("Police lost it!");
                doOnCountdownEnd(players, policeIndex, chorIndex);
            }
        }.start();
    }

    private void updateTimer() {
        timer = (TextView) findViewById(R.id.timer);
        int timeRemaining = (int) (remainingTimeLeft/1000); // to display in seconds: remainingTimeLeft is in milliseconds
        String remainingTimeText = "" + timeRemaining;
        timer.setText(remainingTimeText);
    }

    private void doOnCountdownEnd(Player[] players, int policeIndex, int chorIndex) {
        players = interchangePoliceChorScores(players, policeIndex, chorIndex);
        Button button = playerButtons.get(chorIndex);
        button.setBackground(createRectangleBorder(Color.RED, 10f));
        disableAllButtons(playerButtons);
        showHiddenButtons();
    }

    private void disableAllButtons(ArrayList<Button> buttons) {
        Button button;
        for(int i=0; i<buttons.size(); i++) {
            button = buttons.get(i);
            button.setEnabled(false);
        }
    }

    private void showHiddenButtons() {
        scoresButton.setVisibility(View.VISIBLE);
        thrwChits.setVisibility(View.VISIBLE);
    }

    private Player[] interchangePoliceChorScores(Player[] players, int policeIndex, int chorIndex) {
        players[policeIndex].getRole().setPoints(GameConstants.SCORE_0);
        players[policeIndex].setScore(players[policeIndex].getRole().getPoints());
        players[chorIndex].getRole().setPoints(GameConstants.SCORE_500);
        players[chorIndex].setScore(players[chorIndex].getRole().getPoints());
        return players;
    }

    private ShapeDrawable createRectangleBorder(int color, float thickness) {
        ShapeDrawable redBorderShape = new ShapeDrawable();
        redBorderShape.setShape(new RectShape());
        redBorderShape.getPaint().setColor(color);
        redBorderShape.getPaint().setStrokeWidth(thickness);
        redBorderShape.getPaint().setStyle(Paint.Style.STROKE);

        return redBorderShape;
    }

}