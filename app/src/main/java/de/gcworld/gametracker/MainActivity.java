package de.gcworld.gametracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class MainActivity extends AppCompatActivity {

    private Box<Game> gamesBox;
    private Query<Game> gamesQuery, notstartedQuery, notownedQuery,startedQuery;
    private GamesAdapter gamesAdapter;
    private Toolbar toolbar;
    private int filter = -1;
    final int INPROGRESS = 100;
    final int NOTSTARTED = 50;
    final int STORYFINISHED = 150;
    final int WISHED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setSubtitle(R.string.title_home);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        ListView listView = findViewById(R.id.listViewNotes);
        listView.setOnItemClickListener(gameClickListener);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && firstVisibleItem > 0) {

                    fab.setVisibility(View.INVISIBLE);
                }else {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        gamesAdapter = new GamesAdapter();
        listView.setAdapter(gamesAdapter);

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        gamesBox = boxStore.boxFor(Game.class);
        gamesQuery = gamesBox.query().order(Game_.title).build();
        notstartedQuery = gamesBox.query().equal(Game_.started, false).order(Game_.title).build();
        notownedQuery = gamesBox.query().equal(Game_.want,true).order(Game_.title).build();
        startedQuery = gamesBox.query().equal(Game_.started,true).equal(Game_.storycompleted,false).order(Game_.title).build();
        updateGames();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateGames();
    }

    private void updateGames() {
        List<Game> games;
        if(filter==NOTSTARTED)
            games = notstartedQuery.find();
        else if(filter==WISHED)
            games = notownedQuery.find();
        else if(filter==INPROGRESS)
            games = startedQuery.find();
        else
            games = gamesQuery.find();
        gamesAdapter.setGames(games);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNew(View v){
        Intent intent = new Intent(MainActivity.this,CreateNewActivity.class);
        //intent.putExtra("id",marker.getTitle());
        startActivity(intent);
    }

    AdapterView.OnItemClickListener gameClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Game game = gamesAdapter.getItem(position);
            //gamesBox.remove(game);
            //Log.d(App.TAG, "Deleted note, ID: " + game.getId());
            Intent intent = new Intent(MainActivity.this,EditActivity.class);
            intent.putExtra("id",game.getId());
            startActivity(intent);
            updateGames();
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setSubtitle(R.string.title_home);
                    filter = -1;
                    updateGames();
                    return true;
                case R.id.navigation_started:
                    toolbar.setSubtitle(R.string.title_started);
                    filter = INPROGRESS;
                    updateGames();
                    return true;
                case R.id.navigation_dashboard:
                    toolbar.setSubtitle(R.string.title_wishlist);
                    filter = WISHED;
                    updateGames();
                    return true;
                case R.id.navigation_notifications:
                    toolbar.setSubtitle(R.string.title_new);
                    filter = NOTSTARTED;
                    updateGames();
                    return true;
            }
            return false;
        }
    };
}
