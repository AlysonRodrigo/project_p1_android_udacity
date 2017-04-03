package alysonrodrigo.com.br.filmepopular.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import alysonrodrigo.com.br.filmepopular.R;
import alysonrodrigo.com.br.filmepopular.domain.Movie;

/**
 * Created by alyson on 24/03/17.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Activity context, List<Movie> movieList){
        super(context,0,movieList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie,parent,false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_movie);
        Picasso.with(getContext()).load(movie.getUrl_movie()).into(imageView);

        return convertView;

    }
}
