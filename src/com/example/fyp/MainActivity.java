package com.example.fyp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends ActionBarActivity {
	
	 private MediaRecorder myAudioRecorder;
	    private String outputFile = null;
	    private Button start, stop, play;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button3);
        
        stop.setEnabled(false);
        play.setEnabled(false);
        /*
         * Title: Audio capture without overwriting the same the same file in eclipse
         * Author: Carlos Robles
         * Site: Stackoverflow.com
         * Date 2014
         * Code version Jan 5 '14 at 15:06
         * Availability http://stackoverflow.com/questions/20934924/audio-capture-without-overwriting-the-same-file-in-eclipse (Accessed 28 Feb 2015) 
         */
        
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();

        Long tsLong = System.currentTimeMillis() / 1000;

        outputFile += "/app/appRecording_" + tsLong.toString() + ".3gpp";
        //end here

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        myAudioRecorder.setAudioChannels(1);
        myAudioRecorder.setAudioSamplingRate(8000);
        myAudioRecorder.setAudioEncodingBitRate(16000);
        myAudioRecorder.setOutputFile(outputFile);
	}
	   //method to start recording the audio
    public void start(View view) {
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
    }
    //method to stop recording the audio
    public void stop(View view) {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_LONG).show();

    }
    //method to play recorded audio
    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException {

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
