package com.plamy.taskforcer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TaskAddFragment extends DialogFragment {
    public static final String TAG_EVENT_DIALOG = "dialog_add_diary";

    private TextView editDate;
    private EditText editTitle;
    private EditText editContent;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Calendar cal;

    OnDatabaseCallback callback;

    public TaskAddFragment() { } //Constructor

    public static TaskAddFragment getInstance() {
        TaskAddFragment e = new TaskAddFragment();
        return e;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (OnDatabaseCallback) getActivity();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //region Initialization
        View rootView = inflater.inflate(R.layout.task_add, container);
        Button submitButton = rootView.findViewById(R.id.submitButton);
        Button closeButton = rootView.findViewById(R.id.closeButton);

        editDate = rootView.findViewById(R.id.editDate);
        editTitle = rootView.findViewById(R.id.editTitle);
        editContent = rootView.findViewById(R.id.editContent);
        //endregion

        //region Date Picker
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                // Date Picker에서 선택한 날짜를 TextView에 설정
                editDate.setText(String.format("%d-%d-%d", yy,mm+1,dd));
            }
        };

        cal = Calendar.getInstance();
        editDate.setText(cal.get(Calendar.YEAR)
                +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));
        // 오늘 날짜 설정

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
            }
        });
        //endregion

        //region Submitting Task
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = editDate.getText().toString();
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();

                if( TextUtils.isEmpty(date) || TextUtils.isEmpty(content) ) {
                    Toast.makeText(getContext(), "Fill in the Blank.", Toast.LENGTH_SHORT).show();
                }
                else {
                    callback.insert(title, date, date, content, 0);
                    TaskListFragment frag = (TaskListFragment)getTargetFragment();
                    if(frag != null){
                        frag.refreshList();
                    }
                    dismiss();
                }

            }
        });
        //endregion

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
