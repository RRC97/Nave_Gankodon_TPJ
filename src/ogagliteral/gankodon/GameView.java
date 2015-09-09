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
import android.widget.Toast;

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
    private int score1, score2;
    private Symbol currentSym;
    public GameView(Context context)
    {
        super(context);
        setKeepScreenOn(true);
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
        
        Paint text = new Paint();
        float size = (width + height) / 50;
        text.setColor(Color.WHITE);
        text.setTextSize(size);
        canvas.drawText("Player 1 - " + score1, size * 2, height - size * 10, text);
        canvas.drawText("Player 2 - " + score2, size * 2, height - size * 8, text);
        paint.setColor(time % 2 == 0 ? Color.WHITE : Color.RED);
        canvas.drawCircle(size, height - size * 8 - size / 4, size / 2, paint);
        paint.setColor(time % 2 == 1 ? Color.WHITE : Color.RED);
        canvas.drawCircle(size, height - size * 10 - size / 4, size / 2, paint);
        
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
        
        boolean end = false;
        Symbol focus = Symbol.None;
        boolean test = false;
        int timeColumn = 0;
        for(int i = 0; i < 3; i++)
        {
            Symbol repeatRow = Symbol.None;
            int timeRow = 0;
        	for(int j = 0; j < 3; j++)
            {
            	int idRow = i * 3 + j;
            	if(tiles[idRow].getSymbol() != Symbol.None)
            	{
	            	if(repeatRow == tiles[idRow].getSymbol())
	            	{
	            		timeRow++;
	            	}
	        		else
	        		{
	        			repeatRow = tiles[idRow].getSymbol();
	        			timeRow = 1;
	        		}
            	}
                Symbol repeatColumn = Symbol.None;
            	for(int k = 0; k < 3; k++)
                {
                	int idColumn = k * 3 + j;
	            	if(tiles[idColumn].getSymbol() != Symbol.None)
	            	{
	                	if(repeatColumn == tiles[idColumn].getSymbol())
	                	{
	                		timeColumn++;
	                	}
	            		else
	            		{
	            			repeatColumn = tiles[idColumn].getSymbol();
	            			timeColumn = 1;
	            		}
	            	}
            	}

            	if(timeColumn >= 3)
            	{
        			end = true;
            		break;
            	}
            }
        	if(timeRow >= 3)
        	{
    			end = true;
        		break;
        	}
        }
        int timeMD = 0;
        int timeSD = 0;
        Symbol repeatMD = Symbol.None;
        Symbol repeatSD = Symbol.None;
        for(int i = 0; i < 3; i++)
        {
        	for(int j = 0; j < 3; j++)
            {
        		int id = i * 3 + j;
        		int testMD = i - j;
        		if(testMD == 0)
        		{
        			if(repeatMD != tiles[id].getSymbol()
        			|| tiles[id].getSymbol() == Symbol.None)
        			{
        				repeatMD = tiles[id].getSymbol();
        				timeMD = 0;
        			}
        			timeMD++;
        		}
        		int testSD = i + j;
        		if(testSD == 2)
        		{
        			if(repeatSD != tiles[id].getSymbol()
        			|| tiles[id].getSymbol() == Symbol.None)
        			{
        				repeatSD = tiles[id].getSymbol();
        				timeSD = 0;
        			}
        			timeSD++;
        		}
            }
        	if(timeMD >= 3)
        	{
        		end = true;
        		break;
        	}
        	if(timeSD >= 3)
        	{
        		end = true;
        		break;
        	}
        	
        }
        
        boolean old = false;
        int timeAll = 0;
        for(Tile tile : tiles)
        {
        	if(Symbol.None != tile.getSymbol())
        	{
        		timeAll++;
        	}		
        }
        if(tiles.length == timeAll)
        {
        	end = old = true;
        }
        
        if(end)
        {
        	if(!old)
        	{
	            if(time % 2 == 0)
	            	score2++;
	            if(time % 2 == 1)
	            	score1++;
        	}
        	for(Tile tile : tiles)
            {
        		tile.reset();
    		}
        	time = 0;
        }
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
