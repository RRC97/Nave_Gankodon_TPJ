package ogagliteral.gankodon;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity
{
    private GameView view;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        view = new GameView(this);
        setContentView(view);
    }
}
