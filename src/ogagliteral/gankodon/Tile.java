/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogagliteral.gankodon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

/**
 *
 * @author Matheus
 */
public class Tile implements Element
{   
    private float x, y, width, height;
    private Symbol symbol = Symbol.None;
    public Tile(float posX, float posY, float w, float h)
    {
        x = posX;
        y = posY;
        width = w;
        height = h;
    }

    public void onDraw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.save();
        canvas.translate(x, y);
        if(symbol == Symbol.O)
        {
            canvas.drawCircle(width / 2, height / 2, (width + height - 30)/4, paint);
        }
        if(symbol == Symbol.X)
        {
            canvas.drawLine(15, 15, width - 15, height - 15, paint);
            canvas.drawLine(width - 15, 15, 15, height - 15, paint);
        }
        canvas.restore();
    }
    
    public Symbol getSymbol()
    {
        return symbol;
    }

    public void onUpdate()
    {
        
    }

    public boolean onTouch(MotionEvent me, Symbol sym)
    {
        if(symbol != Symbol.None)
            return false;
        
        if(me.getAction() == MotionEvent.ACTION_DOWN)
        {
            RectF rect = new RectF(x, y, x + width, y + height);
            float touchX = me.getX();
            float touchY = me.getY();
            
            if(rect.contains(touchX, touchY))
            {
                symbol = sym;
                return true;
            }
        }
        return false;
    }
}
