package com.indogames.chits.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        one.setPlayerName(editText.getText().toString());
        one.setViewId(editText.getId());

        editText = (EditText)(findViewById(R.id.P2name));
        two.setPlayerName(editText.getText().toString());
        two.setViewId(editText.getId());

        editText = (EditText)(findViewById(R.id.P3name));
        three.setPlayerName(editText.getText().toString());
        three.setViewId(editText.getId());

        editText = (EditText)(findViewById(R.id.P4name));
        four.setPlayerName(editText.getText().toString());
        four.setViewId(editText.getId());

        Player[] players = { one, two, three, four };
        ThrowChits throe = new ThrowChits();
        // Allocates a role to evey player randomly- RAJA RANI CHOR POLICE
        players = throe.throwchits(players);

        return players;
    }
}