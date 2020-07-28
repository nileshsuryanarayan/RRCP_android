package com.indogames.chits.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.indogames.chits.R;
import com.indogames.chits.action.ThrowChits;
import com.indogames.chits.beans.Player;
import com.indogames.chits.beans.Role;
import com.indogames.chits.constants.GameConstants;
import com.indogames.chits.constants.Roles;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private Button thrwChits;
    private Player one;
    private Player two;
    private Player three;
    private Player four;
    private EditText editText;
    private String noOfRounds;
    private int rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.P1name).requestFocus();

        noOfRounds = findViewById(R.id.rounds).toString();
        if (noOfRounds.matches("")) {

            rounds = GameConstants.DEFAULT_ROUNDS;
        } else {
//            System.out.println("No. of rounds:"+noOfRounds+"Soda");
//            rounds = Integer.parseInt(noOfRounds);
        }
        System.out.println("No. of rounds:"+noOfRounds+"Yoda");
        thrwChits = (Button) findViewById(R.id.throwIt);
        thrwChits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Player[] passPlayers = setPlayer();
                Intent intent = new Intent(getApplicationContext(), IdentifyChor.class);
                intent.putExtra("Players", passPlayers);
                startActivity(intent);
            }
        });
    }

    public Player[] setPlayer() {
        one = new Player();
        two = new Player();
        three = new Player();
        four = new Player();

        editText = (EditText)(findViewById(R.id.P1name));

        if (!editText.getText().toString().matches("") ) {
            one.setPlayerName(editText.getText().toString());
        } else {
            one.setPlayerName(GameConstants.PLAYER_ONE);
        }
        one.setViewId(editText.getId());

        editText = (EditText)(findViewById(R.id.P2name));
        if (!editText.getText().toString().matches("") ) {
            two.setPlayerName(editText.getText().toString());
        } else {
            two.setPlayerName(GameConstants.PLAYER_TWO);
        }
        two.setViewId(editText.getId());

        editText = (EditText)(findViewById(R.id.P3name));
        if (!editText.getText().toString().matches("") ) {
            three.setPlayerName(editText.getText().toString());
        } else {
            three.setPlayerName(GameConstants.PLAYER_THREE);
        }
        three.setViewId(editText.getId());

        editText = (EditText)(findViewById(R.id.P4name));
        if (!editText.getText().toString().matches("") ) {
            four.setPlayerName(editText.getText().toString());
        } else {
            four.setPlayerName(GameConstants.PLAYER_FOUR);
        }
        four.setViewId(editText.getId());

        Player[] players = { one, two, three, four };
        ThrowChits throe = new ThrowChits();
        // Allocates a role to evey player randomly- RAJA RANI CHOR POLICE
        players = throe.throwchits(players);

        return players;
    }
}