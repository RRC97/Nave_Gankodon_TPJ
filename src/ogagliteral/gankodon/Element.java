/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ogagliteral.gankodon;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 *
 * @author Matheus
 */
public interface Element
{
    public void onDraw(Canvas canvas);
    public void onUpdate();
}
