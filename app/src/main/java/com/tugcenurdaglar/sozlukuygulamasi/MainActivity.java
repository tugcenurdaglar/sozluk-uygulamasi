package com.tugcenurdaglar.sozlukuygulamasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener  {
    private Toolbar toolbar;
    private RecyclerView rv;

    private ArrayList<Kelimeler> kelimelerArrayList;

    private KelimelerAdapter adapter;

    private KelimelerDaoInterface kelimelerDIF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);

        toolbar.setTitle("Sözlük Uygulaması");
        setSupportActionBar(toolbar);

        kelimelerDIF = ApiUtils.getKisilerDaoInterface();

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        tumKisiler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu); //hangi menüyü göstermek istediğimi belirttim

        MenuItem item = menu.findItem(R.id.action_ara); //arama iconuna tıklanıldığında;

        SearchView searchView = (SearchView) item.getActionView(); //down casting yaptım

        searchView.setOnQueryTextListener(this); //this, bu sınıfa bağla

        /*bu 3 satır kodla search iconunu aktifleştirdim*/

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) { //arama iconuna bastığımda veriyi buraya gönderir
        Log.e("Gönderilen Arama", query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) { //her harf harf girdiğim anda ya da her sildiğim anda veriyi gönderiyor
        Log.e("Harf Girdikçe", newText);

        kelimeAra(newText);

        return false;
    }


    public void tumKisiler(){
        kelimelerDIF.tumKelimeler().enqueue(new Callback<KelimelerCevap>() {
            @Override
            public void onResponse(Call<KelimelerCevap> call, Response<KelimelerCevap> response) {

                List<Kelimeler> listeTemp = response.body().getKelimeler();

                adapter = new KelimelerAdapter(MainActivity.this, listeTemp);

                rv.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<KelimelerCevap> call, Throwable t) {

            }
        });
    }

    public void kelimeAra(String arananKelime){
        kelimelerDIF.kelimeAra(arananKelime).enqueue(new Callback<KelimelerCevap>() {
            @Override
            public void onResponse(Call<KelimelerCevap> call, Response<KelimelerCevap> response) {
                List<Kelimeler> listeTemp = response.body().getKelimeler();

                adapter = new KelimelerAdapter(MainActivity.this, listeTemp);

                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<KelimelerCevap> call, Throwable t) {

            }
        });
    }
}