package alysonrodrigo.com.br.filmepopular.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import alysonrodrigo.com.br.filmepopular.R;
import alysonrodrigo.com.br.filmepopular.domain.Movie;

/**
 * Created by alyson on 02/04/17.
 */

public class FragmentDetail extends Fragment {

    private Movie movie;

    public FragmentDetail(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                movie = (Movie) intent.getSerializableExtra("movie");
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_fragment,container,false);

        TextView textView = (TextView) view.findViewById(R.id.movie_title);
        textView.setText(movie.getTitle_movie());

        ImageView imageView = (ImageView) view.findViewById(R.id.imgViewUrlMovie);
        Picasso.with(getContext()).load(movie.getUrl_movie()).into(imageView);

        TextView textDataLancamento = (TextView) view.findViewById(R.id.tvDataLancamento);
        textDataLancamento.setText(movie.getRelease_date());

        TextView textDataSinopse = (TextView) view.findViewById(R.id.tvDataSinopse);
        textDataSinopse.setText(movie.getSynopsis_movie());

        TextView tvVoteAverage = (TextView) view.findViewById(R.id.tvVoteAverage);
        tvVoteAverage.setText(movie.getUser());



        return view;


    }
}
