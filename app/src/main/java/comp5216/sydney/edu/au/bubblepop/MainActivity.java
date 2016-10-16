package comp5216.sydney.edu.au.bubblepop;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.mabboud.android_tone_player.OneTimeBuzzer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements Balloon.BalloonListener {

    private static final String TAG = "MainActivity";

    private static final int TOTAL_BALLOONS = 30;


    private static final int MIN_ANIMATION_DELAY = 500;
    private static final int MAX_ANIMATION_DELAY = 1500;
    private static final int MIN_ANIMATION_DURATION = 1000;
    private static final int MAX_ANIMATION_DURATION = 8000;
    //private static final String ACTION_NEXT_LEVEL = "action_next_level";
    private static final String ACTION_RESTART_GAME = "action_restart_game";

    private ViewGroup mContentView;
    private SoundHelper mSoundHelper;
    private List<Balloon> mBalloons = new ArrayList<>();
    private Button mGoButton;
    private String mNextAction = ACTION_RESTART_GAME;
    private boolean mPlaying;
    private int[] mBalloonColors = new int[3];
    private int mNextColor, mBalloonsPopped,
            mScreenWidth, mScreenHeight, balloonsLaunched,
            numOfBalloons = 0,
            mScore = 0, mLevel = 1;
    public String testOneResult = "";
    public String testTwoResult = "";
    public String testThreeResult = "";
    public String testFourResult = "";
    public String testFiveResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.modern_background2);

        //Load the activity layout, which is an empty canvas
        setContentView(R.layout.professionals_test);

        //Get background reference.
        mContentView = (ViewGroup) findViewById(R.id.content_view);
        if (mContentView == null) throw new AssertionError();
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                }
                return false;
            }
        });


        //After the layout is complete, get screen dimensions from the layout.
        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mScreenWidth = mContentView.getWidth();
                    mScreenHeight = mContentView.getHeight();
                }
            });
        }

        //Initialize sound helper class that wraps SoundPool for audio effects
        mSoundHelper = new SoundHelper(this);
        mSoundHelper.prepareMusicPlayer(this);

//        //Initialize instructions
//        TextView  instructions = (TextView) findViewById(R.id.textView);
//        instructions.setText(R.string.instructions);



        //Initialize balloon colors: red, white and blue
        mBalloonColors[0] = Color.argb(255, 255, 0, 0);
        mBalloonColors[1] = Color.argb(255, 0, 255, 0);
        mBalloonColors[2] = Color.argb(255, 0, 0, 255);

        //Get button references
        mGoButton = (Button) findViewById(R.id.go_button);

        //Handle button click
        if (mGoButton == null) throw new AssertionError();
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlaying) {
                    stopTest();
                } else {
                    startGame();

                    }
                }
            });

    }

    @Override
    public void onBackPressed() {
        stopTest();
        super.onBackPressed();
    }


    private void startGame() {
        //Reset score and level
        mScore = 0;
        mLevel = 1;

        //Update display
        mGoButton.setText(R.string.stop_game);

        //Start the test
        startTest();
    }

    private void stopTest() {
        mGoButton.setText(R.string.play_game);
        mPlaying = false;

        if (mBalloonsPopped <= 3)  {
            MyAlertDialog dialog = MyAlertDialog.newInstance("Recommendation: ",
                    getString(R.string.bad_recommendation));
            dialog.show(getSupportFragmentManager(), null);
        } else if (mBalloonsPopped == 4 | mBalloonsPopped == 5){
            MyAlertDialog dialog = MyAlertDialog.newInstance("Recommendation: ",
                    getString(R.string.good_recommendation));
            dialog.show(getSupportFragmentManager(), null);
        } else if (mBalloonsPopped > 6){
            MyAlertDialog dialog = MyAlertDialog.newInstance("Recommendation: ",
                    getString(R.string.try_again_recommendation));
            dialog.show(getSupportFragmentManager(), null);
        }

    }

    private void startTest() {

        //Display the hearing test
        mGoButton.setText(R.string.stop_game);

        //Reset flags for new level
        mPlaying = true;
        mBalloonsPopped = 0;
        numOfBalloons = 0;
        balloonsLaunched = 0;


        //integer arg for BalloonLauncher indicates the level
        BalloonLauncher mLauncher = new BalloonLauncher();
        mLauncher.execute(mLevel);

        //testResults();

    }

    private void finishTest() {
        //probably not going to use this method anymore
        Toast.makeText(MainActivity.this,
                R.string.you_finished,
                Toast.LENGTH_LONG).show();
        mPlaying = false;
    }


    private void launchBalloon(int x) {

        //Balloon is launched from activity upon progress update from the AsyncTask
        //Create new imageview and set its tint color
        Balloon balloon = new Balloon(this, mBalloonColors[mNextColor], 150, mLevel);
        mBalloons.add(balloon);

        //Reset color for next balloon
        if (mNextColor + 1 == mBalloonColors.length) {
            mNextColor = 0;
        } else {
            mNextColor++;
        }

        //Set balloon vertical position and dimensions, add to container
        balloon.setX(x);
        balloon.setY(mScreenHeight + balloon.getHeight());
        mContentView.addView(balloon);

        //Let 'er fly
        int duration = Math.max(MIN_ANIMATION_DURATION, MAX_ANIMATION_DURATION - (mLevel * 1000));
        balloon.releaseBalloon(mScreenHeight, duration);



    }

    @Override
    public void popBalloon(Balloon balloon, boolean userTouch) {

        //Make balloon go away when user touches it
        mContentView.removeView(balloon);
        mBalloons.remove(balloon);
        mBalloonsPopped++;

    }


    public void extraBalloons (Balloon balloon, boolean userTouch){
        //Make balloon go away when it gets to the top of the screen
        mContentView.removeView(balloon);
        mBalloons.remove(balloon);
        numOfBalloons++;

    }

    public void testResults () {
        while (mPlaying) {
            switch (balloonsLaunched) {
                case 9:
                    if (mBalloonsPopped == 1) {
                        testOneResult = "Pass";
                    } else {
                        testOneResult = "Fail";
                    }
            }
        }
    }


    private class BalloonLauncher extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }

            int level = params[0];

            //level 1 = max delay; each ensuing level reduces delay by 500 ms
                //min delay is 250 ms
            int maxDelay = Math.max(MIN_ANIMATION_DELAY, (MAX_ANIMATION_DELAY - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;

            //Keep on launching balloons until either
            //1) we run out or 2) the mPlaying flag is set to false
            balloonsLaunched = 0;
            while (mPlaying && balloonsLaunched < TOTAL_BALLOONS) {

            //Get a random horizontal position for the next balloon
                Random random = new Random(new Date().getTime());
                int xPosition = random.nextInt(mScreenWidth - 200);
                publishProgress(xPosition);
                balloonsLaunched++;

            //Wait a random number of milliseconds before looping
                int delay = random.nextInt(minDelay) + minDelay;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

             //Start hearing test
                OneTimeBuzzer hearingTest = new OneTimeBuzzer();

                if (balloonsLaunched == 7){
                    hearingTest.setVolume(5);
                    hearingTest.setToneFreqInHz(500);
                    hearingTest.setDuration(1);
                    hearingTest.play();

                } else if (balloonsLaunched == 15){
                    hearingTest.setVolume(9);
                    hearingTest.setToneFreqInHz(2000);
                    hearingTest.setDuration(1);
                    hearingTest.play();

                } else if (balloonsLaunched == 19){
                    hearingTest.setVolume(5);
                    hearingTest.setToneFreqInHz(250);
                    hearingTest.setDuration(1);
                    hearingTest.play();

                } else if (balloonsLaunched == 25){
                    hearingTest.setVolume(3);
                    hearingTest.setToneFreqInHz(3500);
                    hearingTest.setDuration(1);
                    hearingTest.play();

                } else if (balloonsLaunched == 28){
                    hearingTest.setVolume(7);
                    hearingTest.setToneFreqInHz(1000);
                    hearingTest.setDuration(1);
                    hearingTest.play();

                }
            }


            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//          This runs on the UI thread, so we can launch a balloon
//            at the randomized horizontal position
            int xPosition = values[0];
            launchBalloon(xPosition);
        }

    }
}

