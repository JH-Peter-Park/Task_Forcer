package com.plamy.taskforcer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    ArrayList<TaskInfo> items = new ArrayList<TaskInfo>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        private boolean isContentOpen;

        private TextView titleText;
        private TextView startDateText;
        private TextView endDateText;
        private TextView contentText;
        private ToggleButton taskState;

        private LinearLayout contentContainer;


        public ViewHolder(View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.title_text);
            startDateText = itemView.findViewById(R.id.start_date_text);
            endDateText = itemView.findViewById(R.id.end_date_text);
            contentText = itemView.findViewById(R.id.content_text);
            taskState = itemView.findViewById(R.id.state_button);

            isContentOpen = false;

            LinearLayout titleContainer = itemView.findViewById(R.id.title_container);
            contentContainer = itemView.findViewById(R.id.content_container);

            titleContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isContentOpen) {
                        closeContent();
                    } else { // when content is closed
                        showContent();
                    }
                }
            });
        }

        private void showContent() {
            contentContainer.setVisibility(View.VISIBLE);
            isContentOpen = true;
        }

        private void closeContent() {
            contentContainer.setVisibility(View.GONE);
            isContentOpen = false;
        }

        public void setItem(TaskInfo item) {
            titleText.setText(item.getTitle());
            startDateText.setText(item.getStartDate());
            endDateText.setText(item.getEndDate());
            contentText.setText(item.getContent());
            taskState.setChecked(item.getCompletedFlag() != 0); //boolean type
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.task_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TaskInfo item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(TaskInfo item) {
        items.add(item);
    }

    public void setItem(int position, TaskInfo item) {
        items.set(position, item);
    }

    public void setItems(ArrayList<TaskInfo> items) {
        this.items = items;
    }

    public TaskInfo getItem(int position) {
        return items.get(position);
    }


}
