package com.indogames.chits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.indogames.chits.action.ThrowChits;
import com.indogames.chits.activities.IdentifyChor;
import com.indogames.chits.activities.MainActivity;
import com.indogames.chits.beans.Player;

public class ResultActivity extends AppCompatActivity {

    private TextView result;
    private Button replay;
    private Player[] passPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final Player[] players = (Player[]) getIntent().getSerializableExtra("Result");
        result = (TextView) findViewById(R.id.resultTitle);
        if (players != null || players.length !=0) {
            result.setText(players[0].getPlayerName());
        }

        replay = (Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThrowChits throwChit = new ThrowChits();
                passPlayers = throwChit.throwchits(players);
                Intent intent = new Intent(getApplicationContext(), IdentifyChor.class);
                intent.putExtra("Players", passPlayers);
                startActivity(intent);
                finish();
            }
        });
    }
}