package com.ske.snakebaddesign.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ske.snakebaddesign.R;
import com.ske.snakebaddesign.guis.BoardView;
import com.ske.snakebaddesign.models.Game;
import com.ske.snakebaddesign.models.Player;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Observer {

    private BoardView boardView;
    private Button buttonTakeTurn;
    private Button buttonRestart;
    private TextView textPlayerTurn;
    private Game game;
    private final int NUMBER_OF_PLAYERS = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game = new Game(NUMBER_OF_PLAYERS,4);
        game.addObserver(this);
        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetGame();
    }

    private void initComponents() {
        boardView = (BoardView) findViewById(R.id.board_view);
        boardView.setComponents(game.getNumberOfPlayers(),game.getPlayerColors());
        buttonTakeTurn = (Button) findViewById(R.id.button_take_turn);
        buttonTakeTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.rollDice();
            }
        });
        buttonRestart = (Button) findViewById(R.id.button_restart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
        textPlayerTurn = (TextView) findViewById(R.id.text_player_turn);
    }

    private void resetGame() {
        boardView.setBoardSize(game.getBoardSize());
        game.reset();
        for(int i =0;i<game.getNumberOfPlayers();i++)
            boardView.setPosition(game.getPlayers().get(i));
        textPlayerTurn.setText("Player 1's Turn");
    }

    private void moveCurrentPiece(Player player) {
        textPlayerTurn.setText(String.format("Player %d's Turn", 1+(player.getNumber()%NUMBER_OF_PLAYERS)));
        boardView.setPosition(player);
        checkWin();
    }



    private void checkWin() {
        String title = "Game Over";
        String msg = "";
        OnClickListener listener = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
                dialog.dismiss();
            }
        };
        if(game.isEnd()){
            Player winner = game.getWinner();
            msg = String.format("Player %s won" , winner.getNumber());
            displayDialog(title,msg,listener);
        }
    }

    private void displayDialog(String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", listener);
        alertDialog.show();
    }

    @Override
    public void update(Observable observable, Object data) {
        if(data == null) return;
        if(data.getClass() != Player.class) return;
        final Player player = (Player)data;
        OnClickListener listener = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                moveCurrentPiece(player);
                dialog.dismiss();
            }
        };
        displayDialog("You rolled a die","You got " + game.getDiceFace(),listener);
    }
}
