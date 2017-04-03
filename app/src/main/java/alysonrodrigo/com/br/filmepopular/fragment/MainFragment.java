package alysonrodrigo.com.br.filmepopular.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import alysonrodrigo.com.br.filmepopular.R;
import alysonrodrigo.com.br.filmepopular.activity.DetailActivity;
import alysonrodrigo.com.br.filmepopular.activity.MainActivity;
import alysonrodrigo.com.br.filmepopular.adapter.MovieAdapter;
import alysonrodrigo.com.br.filmepopular.domain.Movie;
import alysonrodrigo.com.br.filmepopular.util.Util;

/**
 * Created by alyson on 24/03/17.
 */

public class MainFragment extends Fragment {

    private GridView gridView;
    private MovieAdapter movieAdapter;
    private ProgressDialog progressDialog;
    private List<Movie> movies = new ArrayList<>();

    public MainFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void updateMovie(String sortBy){
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute(sortBy);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!Util.isConnected(getActivity())){
            Toast.makeText(getActivity(),"Your device is without internet",Toast.LENGTH_SHORT).show();
            return;
        }
        updateMovie("discover/movie");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_movie,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_movie_popular){
            updateMovie("movie/popular");
            return true;
        }else if (id == R.id.action_movie_top_rated){
            updateMovie("movie/top_rated");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);

        gridView = (GridView) view.findViewById(R.id.gridview_movie);
        movieAdapter = new MovieAdapter(getActivity(),movies);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = (Movie) gridView.getAdapter().getItem(i);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("movie",movie);
                startActivity(intent);
            }
        });

        return view;
    }

    public class FetchMoviesTask extends AsyncTask<String,Void,List<Movie>>{

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Carregando...");
            progressDialog.setMessage("Carregando os filmes");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        private List<Movie> getMoviesDataFromJson(String json) throws JSONException {

                List<Movie> movies = new ArrayList<Movie>();

                JSONObject root = new JSONObject(json);
                JSONArray jsonFilmes = root.getJSONArray("results");

                for(int i = 0; i < jsonFilmes.length(); i++){
                    JSONObject jsonMovie = jsonFilmes.getJSONObject(i);
                    Movie movie = new Movie();

                    movie.setTitle_movie(jsonMovie.optString("title"));
                    movie.setRelease_date(jsonMovie.optString("release_date"));
                    movie.setUser(jsonMovie.optString("vote_average"));
                    movie.setUrl_movie("http://image.tmdb.org/t/p/w185/"+jsonMovie.optString("backdrop_path"));
                    movie.setSynopsis_movie(jsonMovie.optString("overview"));

                    movies.add(movie);
                }

            return movies;

        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

            if(movies != null){
                movieAdapter.clear();
                for (Movie movie_search: movies){
                    movieAdapter.add(movie_search);
                }
            }

            progressDialog.dismiss();

        }

        @Override
        protected List<Movie> doInBackground(String... paramns) {

            if(paramns.length == 0){
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String foreCastJsonStr = null;
            String type_search = paramns[0];


            String appKey = "My appkey has been removed from this variable";

            try{

                final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/" + type_search + "?";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                                .appendQueryParameter(APPID_PARAM,appKey)
                                .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null){

                    return  null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                foreCastJsonStr = buffer.toString();
            }catch (IOException e){
                Log.e(LOG_TAG, "Error",e);

                return null;
            }finally {
                if(urlConnection == null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG,"Error closing stream ",e);
                    }
                }
            }

            try {
                return getMoviesDataFromJson(foreCastJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }
    }
}
