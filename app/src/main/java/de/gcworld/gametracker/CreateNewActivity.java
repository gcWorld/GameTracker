package de.gcworld.gametracker;

import android.app.Application;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class CreateNewActivity extends AppCompatActivity {
    EditText title;
    Spinner platform;
    CheckBox startedbox, storycompletedbox, allachievementsbox, m100percentbox;
    boolean started, storycompleted, allachievements, m100percent, owned, want;
    RadioButton ownedbox, wantbox;
    Button addGame;

    Box gamesBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);

        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        gamesBox = boxStore.boxFor(Game.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addGame = findViewById(R.id.button);
        title = findViewById(R.id.editText);
        title.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addGame.setEnabled(enable);
                addGame.setBackgroundColor(ContextCompat.getColor(CreateNewActivity.this,R.color.secondaryColor));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        platform = findViewById(R.id.spinner);

        startedbox = findViewById(R.id.checkBox);
        storycompletedbox = findViewById(R.id.checkBox2);
        allachievementsbox = findViewById(R.id.checkBox3);
        m100percentbox = findViewById(R.id.checkBox4);

        ownedbox = findViewById(R.id.radioButton);
        wantbox = findViewById(R.id.radioButton2);
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

        Game game = new Game(title.getText().toString(), platform.getSelectedItem().toString(),started, storycompleted, allachievements, m100percent, owned, want);
        game.setAddedDate(new Date());
        gamesBox.put(game);
        finish();
    }
}
