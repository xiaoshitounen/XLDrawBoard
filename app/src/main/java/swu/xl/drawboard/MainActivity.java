package swu.xl.drawboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    XLDrawBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = findViewById(R.id.board);

        findViewById(R.id.size_20).setOnClickListener(this);
        findViewById(R.id.size_40).setOnClickListener(this);
        findViewById(R.id.color_red).setOnClickListener(this);
        findViewById(R.id.color_green).setOnClickListener(this);
        findViewById(R.id.pen).setOnClickListener(this);
        findViewById(R.id.undo).setOnClickListener(this);
        findViewById(R.id.resume).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
        findViewById(R.id.eraser).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.size_20:
                board.setLineWidth(20);
                break;

            case R.id.size_40:
                board.setLineWidth(40);

                break;

            case R.id.color_red:
                board.setLineColor(Color.RED);
                break;

            case R.id.color_green:
                board.setLineColor(Color.GREEN);

                break;

            case R.id.pen:
                board.setBoard_state(XLDrawBoard.BOARD_STATE_PEN);
                break;

            case R.id.eraser:
                board.setBoard_state(XLDrawBoard.BOARD_STATE_ERASER);

                break;

            case R.id.undo:
                board.removeLast();
                break;

            case R.id.resume:
                board.resumeLast();

                break;

            case R.id.clear:
                board.removeAll();
                break;

        }
    }
}
