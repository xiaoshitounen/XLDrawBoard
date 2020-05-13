package swu.xl.xldrawboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class XLDrawBoard extends View {

    //背景颜色
    private int bg_color = Color.WHITE;

    //画板的状态
    private int board_state = BOARD_STATE_PEN;
    public static final int BOARD_STATE_PEN = 0;
    public static final int BOARD_STATE_ERASER = 1;

    //线条颜色
    private int lineColor = Color.MAGENTA;

    //线条的粗细
    private int lineWidth = 20;

    //临时的路径
    private Path temp_path;

    //存储绘制的线条
    private List<Graph> graphs;

    //用于反撤销功能而存储的线条
    private List<Graph> last_graphs;

    /**
     * 构造方法：Java代码初始化
     * @param context
     */
    public XLDrawBoard(Context context, int bg_color) {
        super(context);

        this.bg_color = bg_color;

        //初始化操作
        init();
    }

    /**
     * 构造方法：Xml代码初始化
     * @param context
     * @param attrs
     */
    public XLDrawBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //解析属性
        if (attrs != null){
            //1.获得所欲属性值的集合
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XLDrawBoard);
            //2.解析属性
            bg_color = typedArray.getColor(R.styleable.XLDrawBoard_bg_color,bg_color);
            //3.释放资源
            typedArray.recycle();
        }

        //初始化操作
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        //初始化集合
        graphs = new ArrayList<>();
        last_graphs = new ArrayList<>();

        //设置背景颜色
        setBackgroundColor(bg_color);
    }

    /**
     * 触摸事件
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取触摸点
        float x = event.getX();
        float y = event.getY();

        //触摸的过程
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //初始化路径
                temp_path = new Path();
                //移动到起点
                temp_path.moveTo(x,y);

                //初始化画笔
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(lineColor);
                paint.setStrokeWidth(lineWidth);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStrokeJoin(Paint.Join.ROUND);

                //确定Graph
                Graph graph = new Graph(temp_path, paint);
                graphs.add(graph);
                last_graphs.add(graph);

                break;
            case MotionEvent.ACTION_MOVE:
                //完善路径
                temp_path.lineTo(x,y);

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        //刷新
        invalidate();

        return true;
    }

    /**
     * 重新绘制的过程
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //遍历Graphs数组
        for (Graph graph : graphs) {
            //绘制图形
            canvas.drawPath(graph.path, graph.paint);
        }
    }

    /**
     * 内部类：管理每一个线条的路径以及画笔
     */
    private static class Graph {
        //路径
        Path path;
        //画笔
        Paint paint;

        //构造方法
        Graph(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }

    }

    /**
     * 管理的方法-撤销功能的实现核心
     * @return
     */
    public void removeLast(){
        //防止抛出异常，判断线条数量
        if (graphs.size() > 0){
            //移除最后一个线条
            graphs.remove(graphs.size()-1);

            //刷新
            invalidate();
        }
    }

    /**
     * 管理的方法-反撤销功能的实现
     */
    public void resumeLast(){
        //防止抛出异常，判断是否能反撤销
        if (graphs.size() < last_graphs.size()){
            //最后的操作相同也不可以反撤销
            if (graphs.get(graphs.size() - 1) != last_graphs.get(last_graphs.size() - 1)) {
                //获取上一步的索引值
                int index = graphs.size() - 1 + 1;
                //取出上一步的线条，加入
                graphs.add(last_graphs.get(index));
                //刷新
                invalidate();

                return;
            }
        }

        //小提示
        Toast.makeText(getContext(), "不可还原", Toast.LENGTH_SHORT).show();
    }

    /**
     * 管理的方法-清空功能的实现核心
     * @return
     */
    public void removeAll(){
        //防止抛出异常，判断线条数量
        if (graphs.size() > 0){
            //移除所有的线条
            graphs.clear();
            last_graphs.clear();

            //刷新
            invalidate();
        }
    }

    //setter / getter方法
    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getBoard_state() {
        return board_state;
    }

    public void setBoard_state(int board_state) {
        this.board_state = board_state;

        if (board_state == BOARD_STATE_ERASER) {
            lineColor = bg_color;

            lineWidth = 50;
        }else {
            lineColor = Color.MAGENTA;

            lineWidth = 20;
        }
    }
}
