package com.example.crud_demo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud_demo.R;
import com.example.crud_demo.entity.UserEntity;
import com.example.crud_demo.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<UserEntity> users;

    public ListUserAdapter(Activity activity, List<UserEntity> users) {
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_list_user, parent, false);
        ListUserViewHolder holder = new ListUserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListUserViewHolder viewHolder = (ListUserViewHolder) holder;
        UserEntity model = users.get(position);
        viewHolder.tvId.setText(String.valueOf(model.getId()));
        viewHolder.tvUsername.setText(model.getUsername());
        viewHolder.tvSex.setText(model.getSex());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ListUserViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvUsername, tvSex;
        public ListUserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvSex = itemView.findViewById(R.id.tvSex);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserEntity user = users.get(getAdapterPosition());
                    EventBus.getDefault().post(new MessageEvent.Event(user));
                }
            });
        }
    }
}
