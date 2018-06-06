package de.gcworld.gametracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends BaseAdapter {

    private List<Game> dataset;

    private static class GameViewHolder {

        public TextView title;
        public TextView platform;

        public GameViewHolder(View itemView) {
            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            platform = (TextView) itemView.findViewById(R.id.textViewPlatform);
        }
    }

    public GamesAdapter() {
        this.dataset = new ArrayList<>();
    }

    public void setGames(List<Game> games) {
        dataset = games;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GameViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_game, parent, false);
            holder = new GameViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GameViewHolder) convertView.getTag();
        }

        Game game = getItem(position);
        holder.title.setText(game.getTitle());
        holder.platform.setText(game.getPlatform());

        return convertView;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Game getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}