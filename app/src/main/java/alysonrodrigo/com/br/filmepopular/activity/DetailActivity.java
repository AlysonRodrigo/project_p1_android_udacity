package alysonrodrigo.com.br.filmepopular.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import alysonrodrigo.com.br.filmepopular.R;
import alysonrodrigo.com.br.filmepopular.domain.Movie;
import alysonrodrigo.com.br.filmepopular.fragment.FragmentDetail;

public class DetailActivity extends AppCompatActivity {

    private TextView textView;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_detail,new FragmentDetail(),null);
        fragmentTransaction.commit();

    }
}
