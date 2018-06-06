package de.gcworld.gametracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class MainActivity extends AppCompatActivity {

    private Box<Game> gamesBox;
    private Query<Game> gamesQuery;
    private GamesAdapter gamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        ListView listView = findViewById(R.id.listViewNotes);
        listView.setOnItemClickListener(gameClickListener);

        gamesAdapter = new GamesAdapter();
        listView.setAdapter(gamesAdapter);

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        gamesBox = boxStore.boxFor(Game.class);
        gamesQuery = gamesBox.query().order(Game_.title).build();
        updateGames();
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateGames();
    }

    private void updateGames() {
        List<Game> games = gamesQuery.find();
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
}
