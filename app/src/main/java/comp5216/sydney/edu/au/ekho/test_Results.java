package comp5216.sydney.edu.au.ekho;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Stephie-O on 13/10/2016.
 */

public class test_Results extends AppCompatActivity{

    private int poppedBalloons;
    private int balloonsLaunched;

    public test_Results (){
        //Add name and age from previous menu?
        poppedBalloons = 0;
        balloonsLaunched = 0;
    }

    public test_Results (int popped, int launched){
        poppedBalloons = popped;
        balloonsLaunched = launched;
    }

    public void setBalloonsLaunched(int balloonsLaunched) {
        this.balloonsLaunched = balloonsLaunched;
    }

    public int getBalloonsLaunched() {
        return balloonsLaunched;
    }

    public void setBalloonsPopped(int balloonsPopped) {
        this.poppedBalloons = balloonsPopped;
    }

    /*public int getpoppedBalloons{
        return poppedBalloons;
    }*/
}
