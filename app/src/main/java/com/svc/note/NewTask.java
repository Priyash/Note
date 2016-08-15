package com.svc.note;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.svc.note.Adapter.NotificationAdapter;
import com.svc.note.Adapter.PriorityAdapter;
import com.svc.note.Database.NoteDataSource;
import com.svc.note.Database.TaskData;
import com.svc.note.Utility.ConstantUtil;
import com.thebluealliance.spectrum.SpectrumDialog;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.svc.note.NewTask.TaskFragment.datePicker;
import static com.svc.note.NewTask.TaskFragment.timePicker;

public class NewTask extends AppCompatActivity implements INote{

    private SectionPagerAdapter mSectionPagerAdapter;
    private ViewPager viewPager;
    private EditText edit_task;
    private HashMap<String,String>imap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        edit_task = (EditText)findViewById(R.id.task);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imap = new HashMap<String,String>();
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.container);
        viewPager.setAdapter(mSectionPagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        NoteDataSource.getInstance(this).open();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       switch(item.getItemId())
       {
           case android.R.id.home:
               Intent intent = new Intent(NewTask.this, Home.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
               startActivity(intent);
               finish();
               return true;
           case R.id.save:
                saveToDatabase();
                return true;
       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
       launch_Home();
        //moveTaskToBack(true);
    }

    public void saveToDatabase()
    {
        TaskData tdata = new TaskData();
        String task = edit_task.getText().toString();
        if(!task.isEmpty()) {
        tdata.setTASK(task);
        String desc = imap.get(ConstantUtil.Description);
        tdata.setTASK_DESCRIPTION(desc);
        NoteDataSource.getInstance(this).createTaskData(tdata);
        launch_Home();
        }
        else
        {
            return;
        }
    }

    public void shrinkContent()
    {
        edit_task.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = edit_task.getText().toString().length();
                if (length >= 25 && length < 35) {
                    edit_task.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
                } else if (length >= 35 && length < 45) {
                    edit_task.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
                } else if (length >= 45 && length < 55) {
                    edit_task.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                } else if (length <= 15) {
                    edit_task.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void disableAppbarScrolling()
    {
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams();
        /*if(ViewCompat.isLaidOut(appBarLayout)) {
            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
            behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                @Override
                public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                    return false;
                }
            });
        }*/

    }

    @Override
    public void onResume()
    {
        super.onResume();
        shrinkContent();
        //disableAppbarScrolling();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        NoteDataSource.getInstance(this).close();
    }

    @Override
    public void onDescription(String desc) {
        imap.put(ConstantUtil.Description,desc);
    }

    public static class TaskFragment extends Fragment implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener
    {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ImageButton btn_cal;
        private ImageButton btn_time;
        private SwitchCompat btn_notif;

        final int[] notif_icon_id = {R.drawable.ic_content_mail_icon,R.drawable.ic_communication_message_icon};

        SharedPreferences sharedPref;

        private static TextView tv_date;
        private TextView tv_desc;
        private static TextView tv_time;
        private TextView tv_notif;

        private TextView tv_label_value;

        private View view_circle;

        private Spinner priority_spinner;

        ImageView iv_notif_icon;

        ShapeDrawable sd = new ShapeDrawable();
        int image_position;

        EditText edit_desc;
        private INote iNote;

        public TaskFragment()
        {
            sd.getPaint().setColor(Color.parseColor(ConstantUtil.BLUE));
        }

        public static TaskFragment newInstance(int sectionNumber) {
            TaskFragment fragment = new TaskFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onAttach(Activity activity)
        {
            super.onAttach(activity);
            try
            {
                iNote = (INote)activity;
            }catch (ClassCastException e)
            {
                throw new ClassCastException(activity.toString() + "must implement INote");
            }
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
        {
            View task_view = inflater.inflate(R.layout.fragment_task, container, false);
            task_view.playSoundEffect(SoundEffectConstants.CLICK);
            btn_cal = (ImageButton)task_view.findViewById(R.id.btn_cal);
            btn_cal.setOnClickListener(this);

            btn_time = (ImageButton)task_view.findViewById(R.id.btn_time);
            btn_time.setOnClickListener(this);

            btn_notif = (SwitchCompat)task_view.findViewById(R.id.btn_notif);
            btn_notif.setChecked(false);
            btn_notif.setOnCheckedChangeListener(this);

            edit_desc = (EditText)task_view.findViewById(R.id.desc);
            edit_desc.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(iNote!=null) {
                        iNote.onDescription(edit_desc.getText().toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            tv_date = (TextView)task_view.findViewById(R.id.DueDateValue);
            tv_desc = (TextView)task_view.findViewById(R.id.desc);
            tv_time = (TextView)task_view.findViewById(R.id.TimeValue);
            tv_notif = (TextView)task_view.findViewById(R.id.NotificationValue);
            tv_notif.setEnabled(false);
            tv_notif.setOnClickListener(this);
            tv_notif.setTextColor(Color.parseColor(ConstantUtil.GRAY));

            tv_label_value = (TextView)task_view.findViewById(R.id.LabelValue);

            view_circle = (View)task_view.findViewById(R.id.circle);
            view_circle.setOnClickListener(this);

            iv_notif_icon = (ImageView)task_view.findViewById(R.id.notif_icon);



            priority_spinner = (Spinner)task_view.findViewById(R.id.priority_spinner);
            String[] priority_array = {"Low","Medium","High"};
            int[] priority_id = {R.drawable.ic_social_whatshot_low,R.drawable.ic_social_whatshot_med,R.drawable.ic_social_whatshot_high};
            //ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,priority_array);

            PriorityAdapter priorityAdapter = new PriorityAdapter(getActivity().getApplicationContext(),priority_id,priority_array);

            priority_spinner.setAdapter(priorityAdapter);
            priority_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            if(savedInstanceState!=null) {
                String date = savedInstanceState.getString(ConstantUtil.DateKey);
                String desc = savedInstanceState.getString(ConstantUtil.DescKey);
                String time = savedInstanceState.getString(ConstantUtil.TimeKey);
                String label = savedInstanceState.getString(ConstantUtil.LabelKey);
                int color = savedInstanceState.getInt(ConstantUtil.ColorKey);
                String notif = savedInstanceState.getString(ConstantUtil.NotificationKey);


                int pos = Integer.parseInt(getSavedData("Position"));


                tv_date.setText(date);
                tv_desc.setText(desc);
                tv_time.setText(time);
                tv_label_value.setText(label);
                sd.setShape(new OvalShape());
                sd.getPaint().setColor(color);
                view_circle.setBackgroundDrawable(sd);
                tv_notif.setText(notif);
                if(pos==-1)
                {
                    iv_notif_icon.setImageResource(R.drawable.ic_social_notifications_on);
                }
                else {
                    iv_notif_icon.setImageResource(notif_icon_id[pos]);
                }
            }
            return task_view;
        }



        @Override
        public void onSaveInstanceState(Bundle savedInstanceState)
        {
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString(ConstantUtil.DateKey, tv_date.getText().toString());
            savedInstanceState.putString(ConstantUtil.DescKey, tv_desc.getText().toString());
            savedInstanceState.putString(ConstantUtil.TimeKey, tv_time.getText().toString());
            savedInstanceState.putString(ConstantUtil.LabelKey, tv_label_value.getText().toString());
            savedInstanceState.putInt(ConstantUtil.ColorKey, sd.getPaint().getColor());
            savedInstanceState.putString(ConstantUtil.NotificationKey, tv_notif.getText().toString());
            //savedInstanceState.putInt(ConstantUtil.PositionKey, image_position);

        }

        public void init_date_picker()
        {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            android.support.v4.app.DialogFragment selectDate = new DatePickerFragment();
            selectDate.show(fm, "timePicker");
        }


        public void saveData(String key,String value)
        {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(key, Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.apply();
            editor.commit();
        }

        public String getSavedData(String key)
        {
            sharedPref = getActivity().getSharedPreferences(key,Context.MODE_PRIVATE);
            return sharedPref.getString(key,"");
        }

        public void init_time_picker()
        {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            android.support.v4.app.DialogFragment selectDate = new TimePickerFragment();
            selectDate.show(fm, "datePicker");
        }

        private static String getMonthName(int i)
        {
            String month_name="";
            switch (i)
            {
                case Calendar.JANUARY:
                     month_name = "January";
                     break;
                case Calendar.FEBRUARY:
                    month_name = "February";
                    break;
                case Calendar.MARCH:
                    month_name = "March";
                    break;
                case Calendar.APRIL:
                    month_name = "April";
                    break;
                case Calendar.MAY:
                    month_name = "May";
                    break;
                case Calendar.JUNE:
                    month_name = "June";
                    break;
                case Calendar.JULY:
                    month_name = "July";
                    break;
                case Calendar.AUGUST:
                    month_name = "August";
                    break;
                case Calendar.SEPTEMBER:
                    month_name = "September";
                    break;
                case Calendar.OCTOBER:
                    month_name = "October";
                    break;
                case Calendar.NOVEMBER:
                    month_name = "November";
                    break;
                case Calendar.DECEMBER:
                    month_name = "January";
                    break;

            }

            return month_name;
        }

        public static void datePicker(int year, int month, int day)
        {
            String month_name = getMonthName(month -  1);
            String date = month_name + " " + day + ", " + String.valueOf(year);
            tv_date.setText(date);

        }

        public static void timePicker(int hour , int min)
        {
            String[]qualifier={"AM","PM"};
            String time = "";
            String _min = "";

            if(min<10)
            {
                _min = "0" + String.valueOf(min);
            }
            else
            {
                _min = String.valueOf(min);
            }


            if(hour<12) {
                time = String.valueOf(hour) + ":" + _min + " " + qualifier[0];
            }
            else
            {
                time = String.valueOf(hour) + ":" + _min + " " + qualifier[1];
            }
            tv_time.setText(time);
        }


        public void colorPicker()
        {
            new SpectrumDialog.Builder(getContext())
                    .setColors(R.array.demo_colors)
                    .setSelectedColorRes(R.color.ACCENT)
                    .setDismissOnColorSelected(false)
                    .setOutlineWidth(1)
                    .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                            if (positiveResult) {
                                //Toast.makeText(getContext(), "Color selected: #" + Integer.toHexString(color).toUpperCase(), Toast.LENGTH_SHORT).show();

                                sd.setShape(new OvalShape());
                                sd.getPaint().setColor(color);

                                String hexString = Integer.toHexString(color);
                                hexString = hexString.substring(2).toUpperCase();
                                hexString = "#" + hexString;
                                String colorName = ConstantUtil.getColorName(hexString);
                                tv_label_value.setText(colorName);
                                view_circle.setBackgroundDrawable(sd);


                                //view_circle.setBackgroundColor(color);
                            } else {
                                //Toast.makeText(getContext(), "Dialog cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).build().show(getFragmentManager(), "Color_Palette");

        }


        public void notif_picker()
        {
            final int[] notif_id = {R.drawable.ic_content_mail,R.drawable.ic_communication_message};

            final String[] notifs = {"Email","SMS"};
            final NotificationAdapter notificationAdapter = new NotificationAdapter(getActivity().getApplicationContext(),notif_id,notifs);

            ListView lv = new ListView(getActivity());
            lv.setAdapter(notificationAdapter);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Notification");
            builder.setMessage("Choose Notification type");
            builder.setCancelable(true);
            builder.setView(lv);


            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            final AlertDialog alertDialog = builder.create();
            lv.setOnItemClickListener(new ListView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tv_notif.setText("via " + notifs[position]);
                    iv_notif_icon.setImageResource(notif_icon_id[position]);
                    saveData("Position",String.valueOf(position));
                    //tv_notif.setCompoundDrawablesWithIntrinsicBounds(notif_icon_id[position], 0, 0, 0);
                    alertDialog.dismiss();

                }
            });
            alertDialog.show();
        }
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.btn_cal:
                    v.playSoundEffect(SoundEffectConstants.CLICK);
                    init_date_picker();
                    break;

                case R.id.btn_time:
                    v.playSoundEffect(SoundEffectConstants.CLICK);
                    init_time_picker();
                    break;
                case R.id.NotificationValue:
                     //Log.e("Clicked","Notif Clicked");
                    notif_picker();
                    break;

                case R.id.circle:
                    colorPicker();
                    break;
            }

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(isChecked)
            {
                tv_notif.setEnabled(true);
                tv_notif.setTextColor(Color.parseColor(ConstantUtil.BLACK));
            }
            else {
                tv_notif.setEnabled(false);
                iv_notif_icon.setImageResource(R.drawable.ic_social_notifications_on);
                tv_notif.setText("No Notification");
                saveData("Position","-1");
                tv_notif.setTextColor(Color.parseColor(ConstantUtil.GRAY));
            }

        }
    }





    public void launch_Home()
    {
        Intent intent = new Intent(NewTask.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }





    public static class CommentFragment extends Fragment
    {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public CommentFragment()
        {

        }

        public static CommentFragment newInstance(int sectionNumber) {
            CommentFragment fragment = new CommentFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View comment_view = inflater.inflate(R.layout.comment_fragment,container,false);
            TextView comment_text = (TextView)comment_view.findViewById(R.id.comment);
            comment_text.setText("Comment");
            return comment_view;
        }

    }


    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return TaskFragment.newInstance(1);
                case 1:
                   return CommentFragment.newInstance(2);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Task";
                case 1:
                    return "Comments";
            }
            return null;
        }
    }



    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstances)
        {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),this,year,month,day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            datePicker(year,monthOfYear+1,dayOfMonth);
        }
    }


    public static class TimePickerFragment extends android.support.v4.app.DialogFragment implements TimePickerDialog.OnTimeSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstances)
        {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(),this,hour,min,false);
        }


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timePicker(hourOfDay, minute);
        }
    }
}
