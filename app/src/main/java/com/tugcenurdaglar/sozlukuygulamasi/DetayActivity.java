package com.tugcenurdaglar.sozlukuygulamasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

public class DetayActivity extends AppCompatActivity implements
        TextToSpeech.OnInitListener {
    private TextView textViewIngilizcee, textViewTurkcee;
    private Kelimeler kelime;
    private ImageView imageView;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        textViewIngilizcee = findViewById(R.id.textViewIngilizcee);
        textViewTurkcee = findViewById(R.id.textViewTurkcee);

        imageView = findViewById(R.id.imageView);
        tts = new TextToSpeech(this, this);

        kelime = (Kelimeler) getIntent().getSerializableExtra("nesne");

        textViewIngilizcee.setText(kelime.getIngilizce());
        textViewTurkcee.setText(kelime.getTurkce());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Dil desteklenmemektedir");
            } else {
                imageView.setEnabled(true);
//                speakOut();
            }


        } else {
            Log.e("TTS", "Yüklenemedi");
        }
    }

    private void speakOut() {
        String text = textViewIngilizcee.getText().toString();

        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        } else {
            Toast.makeText(this, "Henüz tts kurulumu tamamlanmadı", Toast.LENGTH_LONG).show();

        }
    }
}