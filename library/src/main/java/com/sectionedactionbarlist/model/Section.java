package com.sectionedactionbarlist.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vgrec, created on 9/2/14.
 */
public class Section {

    private String sectionTitle;
    private List<String> items = new ArrayList<String>();

    public Section(String categoryName) {
        this.sectionTitle = categoryName;
    }

    public void add(String item) {
        items.add(item);
    }

    public String getName() {
        return sectionTitle;
    }

    public List<String> getItems() {
        return items;
    }
}
