package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.sectionedactionbarlist.ItemSelectedListener;
import com.sectionedactionbarlist.ListConfiguration;
import com.sectionedactionbarlist.SectionedActionBarList;
import com.sectionedactionbarlist.model.Section;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        List<Section> sections = new ArrayList<Section>();

        Section themes = new Section("Themes");
        themes.add("Design");
        themes.add("Develop");
        themes.add("Distribute");
        sections.add(themes);

        Section topics = new Section("Topics");
        topics.add("Android");
        topics.add("Chrome / Web");
        topics.add("Cloud Services");
        topics.add("Polymer");
        topics.add("Tools");
        topics.add("UX");
        topics.add("Media");
        topics.add("Location");
        topics.add("Performance");
        sections.add(topics);

        Section types = new Section("Types");
        types.add("Sessions");
        types.add("App Reviews");
        types.add("Box Talks");
        types.add("Box Talks");
        types.add("Workshops");
        types.add("Office Hours");
        sections.add(topics);

        ListConfiguration configuration = new ListConfiguration(this);
        configuration.setActionBarItemColorResource(R.color.brown);
        configuration.setIndicatorDrawableResource(R.drawable.spinner_indicator_dark);
        configuration.setSectionTitleColorResource(R.color.teal);
        configuration.setDropdownItemColorResources(R.color.light_blue, R.color.dark_grey);

        SectionedActionBarList actionBarList = new SectionedActionBarList(this).from(sections);
        actionBarList.setItemSelectedListener(new ItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id, String sectionName, String itemName) {
                Toast.makeText(MainActivity.this, "Section: " + sectionName + ", Item: " + itemName, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
