package comp5216.sydney.edu.au.bubblepop;

/**
 * Created by cnin0770 on 16/10/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * A class used to create layout for main menu
 * and contains intents for other activities
 */
public class MainMenu extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) { // activity is strarted
        // here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu); // main menu view is loaded first
    }

    public void onTest(View v) {
        Intent intent = new Intent(this, MainActivity.class); // intent used to launch activity
        startActivity(intent);
    }

    public void onTutorial(View v) {
        Intent intent = new Intent(MainMenu.this, Tutorial.class);
        startActivity(intent);
    }

}
