package comp5216.sydney.edu.au.ekho;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by cnin0770 on 16/10/16.
 */
public class TestResult extends Activity {

//    private Button mSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_results);

//        mSave = (Button) findViewById(R.id.saveButton);
//
//        if (mSave == null) throw new AssertionError();
//        mSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TestResult.this, MainMenu.class);
//                startActivity(intent);
//            }
//        });
    }

//    public void onSave() {
//        Intent intent = new Intent(TestResult.this, MainMenu.class);
//        startActivity(intent);
//    }
}
