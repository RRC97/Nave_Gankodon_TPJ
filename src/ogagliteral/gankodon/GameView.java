/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogagliteral.gankodon;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 * @author Matheus
 */
public class GameView extends View implements Runnable
{
    private Handler handler;
    public Tile[] tiles = new Tile[9];
    private float width, height, tileW, tileH;
    private int time;
    private Symbol currentSym;
    public GameView(Context context)
    {
        super(context);
        handler = new Handler();
        handler.postDelayed(this, 1);
        
        Resources res = getResources();
        
        width = res.getDisplayMetrics().widthPixels;
        height = res.getDisplayMetrics().heightPixels;
        tileW = (width - 40) / 3;
        tileH = tileW;
        
        for(int j = 0; j < 3; j++)
        {
            for(int i = 0; i < 3; i++)
            {
                int index = j * 3 + i;
                tiles[index] = new Tile(i * (tileW + 10) + 10,
                        j * (tileH + 10) + 10, tileW, tileH);
            }
        }
    }
    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        
        canvas.drawLine(tileW + 10 + 5, 10, tileW + 10 + 5, (tileH + 10) * 3, paint);
        canvas.drawLine((tileW + 10) * 2 + 5, 10, (tileW + 10) * 2 + 5, (tileH + 10) * 3, paint);
        canvas.drawLine(10, tileH + 10 + 5, (tileW + 10) * 3, tileH + 10 + 5, paint);
        canvas.drawLine(10, (tileH + 10) * 2 + 5, (tileW + 10) * 3, (tileH + 10) * 2 + 5, paint);
        
        for(Tile tile : tiles)
            tile.onDraw(canvas);
    }
    public void onUpdate()
    {
        if(time % 2 == 0)
            currentSym = Symbol.X;
                    
        if(time % 2 == 1)
            currentSym = Symbol.O;
        
        for(Tile tile : tiles)
            tile.onUpdate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        for(Tile tile : tiles)
        {
            if(tile.onTouch(me, currentSym))
            {
                time++;
                return true;
            }
        }
        return true;
    }
    
    @Override
    public void run()
    {
        onUpdate();
        invalidate();
        handler.postDelayed(this, 1);
    }
}
