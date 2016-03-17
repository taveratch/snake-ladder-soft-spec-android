package com.ske.snakebaddesign.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ske.snakebaddesign.R;
import com.ske.snakebaddesign.guis.BoardView;
import com.ske.snakebaddesign.models.Game;
import com.ske.snakebaddesign.models.Player;
import com.ske.snakebaddesign.models.Question;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Observer {

    private BoardView boardView;
    private RelativeLayout buttonTakeTurn;
    private TextView textPlayerTurn;
    private Game game;
    private int NUMBER_OF_PLAYERS = 0;
    private int boardSize = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setElevation(0);
        showChoosePlayerDialog();
//        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        resetGame();
        game = new Game(NUMBER_OF_PLAYERS,boardSize);
        game.addObserver(this);
        boardView = (BoardView) findViewById(R.id.board_view);
        boardView.setBoard(game.getBoard());
    }

    private void initComponents() {
        boardView.setComponents(game.getNumberOfPlayers(), game.getPlayerColors());
        buttonTakeTurn = (RelativeLayout) findViewById(R.id.button_take_turn);
        buttonTakeTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.rollDice();
            }
        });
        textPlayerTurn = (TextView) findViewById(R.id.text_player_turn);
    }

    private void resetGame() {
        game = new Game(NUMBER_OF_PLAYERS,boardSize);
        game.addObserver(this);
        game.reset();
        boardView.setBoard(game.getBoard());
        boardView.setComponents(game.getNumberOfPlayers(), game.getPlayerColors());
        initComponents();
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
        if(data.getClass() == Player.class) {
            final Player player = (Player) data;
            OnClickListener listener = new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    moveCurrentPiece(player);
                    dialog.dismiss();
                }
            };
            displayDialog("You rolled a die", "You got " + game.getDiceFace(), listener);
        }else if(data.getClass() == Question.class){
            final Question question = (Question)data;
            final Dialog dialog = new Dialog(this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.question_dialog_layout);
            TextView question_txt = (TextView)dialog.findViewById(R.id.question_txt);
            question_txt.setText(question.getQuestion());
            final EditText answer_txt = (EditText)dialog.findViewById(R.id.answer_txt);
            Button submit_btn = (Button)dialog.findViewById(R.id.submit_btn);
            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int realAnswer = question.getAnswer();
                    int answer = answer_txt.getText().length() == 0 ? 0 : Integer.parseInt(answer_txt.getText().toString());
                    if(realAnswer == answer) dialog.dismiss();
                    else {
                        game.penalty();
                        answer_txt.setText("");
                        answer_txt.setHint("Wrong answer");
                    }
                }
            });
            dialog.show();
        }
    }

    public void showChoosePlayerDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.player_number_dialog_layout);
        Button two_btn = (Button)dialog.findViewById(R.id.two_btn);
        Button three_btn = (Button)dialog.findViewById(R.id.three_btn);
        Button four_btn = (Button)dialog.findViewById(R.id.four_btn);
        dialog.setCancelable(false);
        two_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMBER_OF_PLAYERS = 2;
                resetGame();
                dialog.dismiss();
            }
        });

        three_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMBER_OF_PLAYERS = 3;
                resetGame();
                dialog.dismiss();
            }
        });

        four_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NUMBER_OF_PLAYERS = 4;
                resetGame();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.reset)
            showChoosePlayerDialog();
        return super.onOptionsItemSelected(item);
    }
}
