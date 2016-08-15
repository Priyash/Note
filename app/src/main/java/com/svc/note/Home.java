package com.svc.note;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.svc.note.Adapter.TaskAdapter;
import com.svc.note.Database.NoteDataSource;
import com.svc.note.Database.TaskData;

import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Boolean isClicked=false;
    FloatingActionButton fab_add;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NoteDataSource.getInstance(this).open();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_add = (FloatingActionButton)findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);

        List<TaskData> list = NoteDataSource.getInstance(this).getTaskData();

        if(list!=null&&list.size()>0)
        {
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.cardList);
            recyclerView.setHasFixedSize(true);

            LinearLayoutManager ll = new LinearLayoutManager(this);
            ll.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(ll);

            TaskAdapter taskAdapter = new TaskAdapter(list);
            recyclerView.setAdapter(taskAdapter);
        }

    }

    private void launch_new_task() {
        Intent task_intent = new Intent(Home.this,NewTask.class);
        startActivity(task_intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
                NoteDataSource.getInstance(this).open();
                NoteDataSource.getInstance(this).deleteAllTask();
                NoteDataSource.getInstance(this).close();
                break;
            case R.id.fab_add:
                launch_new_task();
                break;
        }
    }
}
