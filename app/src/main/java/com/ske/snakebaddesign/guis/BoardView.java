package com.ske.snakebaddesign.guis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ske.snakebaddesign.models.Board;
import com.ske.snakebaddesign.models.Player;
import com.ske.snakebaddesign.models.Square;

public class BoardView extends View {

    // Graphics variables
    private Paint paint;
    private float viewWidth;
    private float cellSize;
    private float padding;
    private float playerSize;
    private int colorBG = Color.parseColor("#6d8d46");
    private Board board;
    private int colorText = Color.parseColor("#cfe8a6");
    private int numberOfPlayer;
    // These variables will be used to keep track of what to render
    private int boardSize;
    private int[] positions;
    private int[] colors;
    public BoardView(Context context,int numberOfPlayer,int[] colors) {
        super(context);
        init();
    }

    public void setComponents(int numberOfPlayer, int[] colors){
        this.numberOfPlayer = numberOfPlayer;
        positions = new int[numberOfPlayer];
        this.colors = colors;
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        reloadViewDimensions();
        drawBoard(canvas);
        drawSquares(canvas);
        drawPlayerPieces(canvas);
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
        board = new Board(boardSize);
        postInvalidate();
    }

    public void setPosition(Player player){
        int index = player.getNumber()-1;
        positions[index] = player.getPosition();
        postInvalidate();
    }

    private void init() {
        boardSize = 1;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.CENTER);
    }


    private void reloadViewDimensions() {
        viewWidth = getWidth();
        cellSize = (viewWidth / boardSize);
        padding = 0.05f * cellSize;
        playerSize = cellSize/8;
    }

    private void drawBoard(Canvas canvas) {
        paint.setColor(colorBG);
        canvas.drawRect(0, 0, viewWidth, viewWidth, paint);
    }

    private void drawSquares(Canvas canvas) {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                float startX = i * cellSize + padding/2;
                float startY = j * cellSize + padding/2;
                float endX = startX + cellSize - padding;
                float endY = startY + cellSize - padding;
                paint.setColor(board.getSquare(i,j).getColor());
                canvas.drawRect(startX, startY, endX, endY, paint);
                paint.setColor(colorText);
                String label = (j *  boardSize + i + 1) + "";
                canvas.drawText(label, startX + cellSize/2 - padding/2, startY + cellSize/2, paint);
            }
        }
    }

    private void drawPlayerPieces(Canvas canvas) {
        float multiplier = 1/(numberOfPlayer+1f);
        float temp = multiplier;
        for(int i =0;i<numberOfPlayer;i++){
            paint.setColor(colors[i]);
            float p1X = positionToCol(positions[i]) * cellSize + cellSize/2;
            float p1Y = positionToRow(positions[i]) * cellSize + (cellSize * multiplier);
            multiplier += temp;
            canvas.drawCircle(p1X, p1Y, playerSize, paint);
        }
    }

    private int positionToRow(int position) {
        return position / boardSize;
    }

    private int positionToCol(int position) {
        return position % boardSize;
    }

}
