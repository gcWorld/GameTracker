package de.gcworld.gametracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GamesAdapter extends BaseAdapter {

    private List<Game> dataset;

    private static class GameViewHolder {

        public TextView title;
        public TextView platform;
        public ImageView startedimage, storyimage, achievementimage, m100image;
        public TextView started, story, achievement, m100;

        public GameViewHolder(View itemView) {
            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            platform = (TextView) itemView.findViewById(R.id.textViewPlatform);
            started = itemView.findViewById(R.id.started);
            story = itemView.findViewById(R.id.storycompleted);
            achievement = itemView.findViewById(R.id.achievements);
            m100 = itemView.findViewById(R.id.m100);
            startedimage = itemView.findViewById(R.id.startedimage);
            storyimage = itemView.findViewById(R.id.storyimage);
            achievementimage = itemView.findViewById(R.id.achievementsimage);
            m100image = itemView.findViewById(R.id.m100image);
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
        if(!game.started) {
            holder.started.setVisibility(View.INVISIBLE);
            holder.startedimage.setVisibility(View.INVISIBLE);
        }else {
            holder.started.setVisibility(View.VISIBLE);
            holder.startedimage.setVisibility(View.VISIBLE);
        }
        if(!game.storycompleted){
            holder.story.setVisibility(View.INVISIBLE);
            holder.storyimage.setVisibility(View.INVISIBLE);
        }else {
            holder.story.setVisibility(View.VISIBLE);
            holder.storyimage.setVisibility(View.VISIBLE);
        }
        if(!game.allachievements){
            holder.achievement.setVisibility(View.INVISIBLE);
            holder.achievementimage.setVisibility(View.INVISIBLE);
        }else {
            holder.achievement.setVisibility(View.VISIBLE);
            holder.achievementimage.setVisibility(View.VISIBLE);
        }
        if(!game.m100percent){
            holder.m100.setVisibility(View.INVISIBLE);
            holder.m100image.setVisibility(View.INVISIBLE);
        }else {
            holder.m100.setVisibility(View.VISIBLE);
            holder.m100image.setVisibility(View.VISIBLE);
        }

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