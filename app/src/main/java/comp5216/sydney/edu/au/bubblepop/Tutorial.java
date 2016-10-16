package comp5216.sydney.edu.au.bubblepop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by cnin0770 on 16/10/16.
 */
public class Tutorial extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
    }

    public void loadView1(View v) {
        Intent intent = new Intent(Tutorial.this, InfoView1.class);
        startActivity(intent);
    }

    public void loadView2(View v) {
        Intent intent = new Intent(Tutorial.this, InfoView2.class);
        startActivity(intent);
    }

    public void loadView3(View v) {
        Intent intent = new Intent(Tutorial.this, InfoView3.class);
        startActivity(intent);
    }

    public void loadView4(View v) {
        Intent intent = new Intent(Tutorial.this, InfoView4.class);
        startActivity(intent);
    }
}
