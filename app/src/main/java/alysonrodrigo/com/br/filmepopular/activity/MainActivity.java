package alysonrodrigo.com.br.filmepopular.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import alysonrodrigo.com.br.filmepopular.R;
import alysonrodrigo.com.br.filmepopular.fragment.MainFragment;
import alysonrodrigo.com.br.filmepopular.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            MainFragment fragment = new MainFragment();
            transaction.add(R.id.layout_main_activity,fragment);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Util.isConnected(MainActivity.this)){
            Toast.makeText(MainActivity.this,"Your device is without internet",Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
