package com.plamy.taskforcer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskListFragment extends Fragment {
    RecyclerView recyclerView;
    TaskAdapter adapter;
    OnDatabaseCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnDatabaseCallback) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.task_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = rootView.findViewById(R.id.task_recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
        ArrayList<TaskInfo> result = callback.selectAll();
        adapter.setItems(result);

        return rootView;
    }

    public void refreshList() {
        ArrayList<TaskInfo> result = callback.selectAll();
        adapter.setItems(result);
        adapter.notifyDataSetChanged();
    }
}
