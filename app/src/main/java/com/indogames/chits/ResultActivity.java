package com.indogames.chits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.indogames.chits.action.ThrowChits;
import com.indogames.chits.activities.IdentifyChor;
import com.indogames.chits.activities.MainActivity;
import com.indogames.chits.beans.Player;

public class ResultActivity extends AppCompatActivity {

    private Button replay;
    private Player[] passPlayers;

    // Table row params: Dynamic creation
    private TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.MATCH_PARENT);

    // Table cell params: Dynamic creation
    private TableRow.LayoutParams params = new TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            1.0f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final Player[] players = (Player[]) getIntent().getSerializableExtra("Result");

        createScoreBoard(players);

        // calls throw chits and assigns new roles to the players
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

    private void createScoreBoard(Player[] players) {
        TableLayout scoreboard = (TableLayout) findViewById(R.id.resultTable);
        if (players != null && players.length > 0) {
            System.out.println("Inside if:");
            for (int index=0; index<players.length; index++) {

                TextView player = new TextView(getApplicationContext());
                TextView role = new TextView(getApplicationContext());
                TextView score = new TextView(getApplicationContext());

                // Common text params & player name
                player.setLayoutParams(params);
                player.setText(players[index].getPlayerName());
                player.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                player.setTypeface(null, Typeface.NORMAL);
                player.setGravity(Gravity.LEFT);

                // Common text params & role assigned
                role.setLayoutParams(params);
                role.setText(players[index].getRole().getRole());
                role.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                role.setTypeface(null, Typeface.NORMAL);
                role.setGravity(Gravity.LEFT);

                // Common text params & player score
                score.setLayoutParams(params);
                score.setText(players[index].getScore()+"");
                score.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                score.setTypeface(null, Typeface.NORMAL);
                score.setGravity(Gravity.CENTER);

                TableRow row = new TableRow(getApplicationContext());
                row.setLayoutParams(rowParams);
                row.addView(player);
                row.addView(role);
                row.addView(score);

                scoreboard.addView(row);
            }
        } else { System.out.println("Players is empty"); }
    }
}