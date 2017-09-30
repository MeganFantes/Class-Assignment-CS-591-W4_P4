package com.example.mlfan.w4_p4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static android.R.attr.id;


public class MainActivity extends AppCompatActivity {

    public TextView textVar;
    ArrayList<String> dictionary = new ArrayList<String>();
    String newWord;
    char[] parts;
    String[] partsA;
    public TextView word;
    public ImageView gallowsImage;
    private int[] imageArray = {
            R.drawable.hang0,
            R.drawable.hang1,
            R.drawable.hang2,
            R.drawable.hang3,
            R.drawable.hang4,
            R.drawable.hang5,
            R.drawable.hang6,

    };
    int count=0;
    int numLetters = 0;
    TextView hint;
    ArrayList<String> hintList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word = (TextView) findViewById(R.id.txtWord);

        // Add words to the dictionary
        dictionary.add("SHAKE");
        dictionary.add("WHALE");
        dictionary.add("SHARK");
        dictionary.add("FRIES");
        dictionary.add("HONEY");
        dictionary.add("GOATS");
        dictionary.add("HORSE");
        dictionary.add("CRABS");
        dictionary.add("STEAK");
        dictionary.add("CHICK");

        // randomly pick a word
        Random random = new Random();
        int wordIndex = random.nextInt(10);

        newWord = dictionary.get(wordIndex);
        Log.v("newWord",newWord);
        parts = newWord.toCharArray();
        partsA = newWord.split("");


        gallowsImage = (ImageView) findViewById(R.id.imageView);

        hint = (TextView) findViewById(R.id.txtHint);

        hintList.add("Food");
        hintList.add("Sea Aninmal");
        hintList.add("Sea Animal");
        hintList.add("Food");
        hintList.add("Food");
        hintList.add("Farm Animal");
        hintList.add("Farm Animal");
        hintList.add("Sea Animal");
        hintList.add("Food");
        hintList.add("Farm Animal");
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            String hintWord = hintList.get(wordIndex);
            hint.setText(hintWord);
        }

    }

    public void onLClick (View v) {
        int id = v.getId();
        Log.v("Id From Getid",Integer.toString(id));
        textVar = (TextView) findViewById(id);
        String getChar = (String) textVar.getText();
        Log.v("textVar",getChar);
        String currentText = (String) word.getText();
        Log.v("textVar",currentText);
        List<String> list = Arrays.asList(partsA);



        if (list.contains(getChar)){
            int index = list.indexOf(getChar);
            Log.v("index",Integer.toString(index));
            char letter = parts[index-1];
            int displayIndex = (index-1) * 2;
            StringBuilder newText = new StringBuilder(currentText);
            newText.setCharAt(displayIndex, letter);
            String k = newText.toString();
            Log.v("yo",k);
            word.setText(k);
            numLetters = numLetters + 1;
            if (numLetters == 5){
                winDialog();
            }
        }else {
            count = count +1;
            if (count < 7){
                changeGallowsImage(count);
            }
        }

        v.setBackgroundColor(Color.GREEN);
    }

    public void changeGallowsImage(int count) {

        Drawable d = getResources().getDrawable(imageArray[count]);
        gallowsImage.setImageDrawable(d);
        if(count == 6) {
            //Toast.makeText(this, "You Loose", Toast.LENGTH_LONG).show();
            loseDialog();
        }
    }

    public void loseDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("You Lose! :(");
        alert.setMessage("Play again?");
        PlayDialogue playAgain = new PlayDialogue();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

    public void winDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("You win! Good job!");
        alert.setMessage("Play again?");
        PlayDialogue playAgain = new PlayDialogue();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

    private class PlayDialogue implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            if (id == -1){
                Intent intent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            } else if (id == -2){
                MainActivity.this.finish();
                System.exit(0);
            }
        }
    }

}