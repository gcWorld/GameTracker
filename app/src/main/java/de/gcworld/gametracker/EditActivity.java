package de.gcworld.gametracker;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class EditActivity extends AppCompatActivity {

    private Box<Game> gamesBox;
    private Query<Game> gamesQuery;
    private Game game;
    EditText title;
    Spinner platform;
    CheckBox startedbox, storycompletedbox, allachievementsbox, m100percentbox;
    boolean started, storycompleted, allachievements, m100percent, owned, want;
    RadioButton ownedbox, wantbox;
    Button addGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        Bundle extras = getIntent().getExtras();
        long id;

        if (extras != null) {
            id = extras.getLong("id");
            // and get whatever type user account id is
            BoxStore boxStore = ((App) getApplication()).getBoxStore();
            gamesBox = boxStore.boxFor(Game.class);
            game = gamesBox.get(id);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addGame = findViewById(R.id.button);
        title = findViewById(R.id.editText);
        platform = findViewById(R.id.spinner);

        startedbox = findViewById(R.id.checkBox);
        storycompletedbox = findViewById(R.id.checkBox2);
        allachievementsbox = findViewById(R.id.checkBox3);
        m100percentbox = findViewById(R.id.checkBox4);

        ownedbox = findViewById(R.id.radioButton);
        wantbox = findViewById(R.id.radioButton2);

        title.setText(game.title);
        startedbox.setChecked(game.started);
        storycompletedbox.setChecked(game.storycompleted);
        allachievementsbox.setChecked(game.allachievements);
        m100percentbox.setChecked(game.m100percent);
        wantbox.setChecked(game.want);
        ownedbox.setChecked(game.owned);
        //
        title.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addGame.setEnabled(enable);
                addGame.setBackgroundColor(ContextCompat.getColor(EditActivity.this,R.color.secondaryColor));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            gamesBox.remove(game.id);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void onSave(View v){
        started = startedbox.isChecked();
        storycompleted = storycompletedbox.isChecked();
        allachievements = allachievementsbox.isChecked();
        m100percent = m100percentbox.isChecked();

        owned = ownedbox.isChecked();
        want = wantbox.isChecked();

        //Game gamelocal = (title.getText().toString(), platform.getSelectedItem().toString(),started, storycompleted, allachievements, m100percent, owned, want);
        //game.setAddedDate(new Date());
        game.setTitle(title.getText().toString());
        game.setPlatform(game.platform);//platform.getSelectedItem().toString());
        if(started && !game.started) {
            game.setStartedDate(new Date());
            game.started = true;
        }

        if(storycompleted && !game.storycompleted) {
            game.setCompletedDate(new Date());
            game.storycompleted = true;
        }

        if(allachievements && !game.allachievements) {
            game.achievementsDate = new Date();
            game.allachievements = true;
        }

        if(m100percent && !game.m100percent) {
            game.m100percentDate = new Date();
            game.m100percent = true;
        }

        if(want && !game.want) {
            game.want = true;
        }

        if(!want && game.want) {
            game.want = false;
        }

        if(owned && !game.owned) {
            game.owned = true;
        }

        if(!owned && game.owned) {
            game.owned = false;
        }

        gamesBox.put(game);
        finish();
    }
}
