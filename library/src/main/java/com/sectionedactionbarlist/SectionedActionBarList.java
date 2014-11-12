package com.sectionedactionbarlist;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sectionedactionbarlist.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vgrec, created on 9/2/14.
 */
public class SectionedActionBarList {

    private ActionBar actionBar;
    private ItemSelectedListener listener;
    private List<SpinnerItem> items = new ArrayList<SpinnerItem>();
    private LayoutInflater inflater;
    private ListConfiguration configuration;

    public SectionedActionBarList(Activity activity) {
        this(activity, new ListConfiguration(activity));
    }

    public SectionedActionBarList(Activity activity, ListConfiguration configuration) {
        this.actionBar = activity.getActionBar();
        this.configuration = configuration;
        this.inflater = activity.getLayoutInflater();
    }

    public SectionedActionBarList from(List<Section> sections) {
        for (Section section : sections) {
            items.add(new SpinnerItem(true, section.getName()));
            for (String item : section.getItems()) {
                items.add(new SpinnerItem(false, item));
            }
        }

        setCustomActionBarSpinner();
        return this;
    }

    private void setCustomActionBarSpinner() {
        View spinnerContainer = LayoutInflater.from(getActionBar().getThemedContext())
                .inflate(R.layout.actionbar_spinner, null);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getActionBar().setCustomView(spinnerContainer, lp);

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.actionbar_spinner);
        spinner.setAdapter(new SpinnerAdapter(items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    String[] result = getSectionAndItemNames(position);
                    listener.onItemSelected(parent, view, position, id, result[0], result[1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (listener != null) {
                    listener.onNothingSelected(parent);
                }
            }
        });

        getActionBar().setDisplayShowCustomEnabled(true);
    }

    /**
     * When a dropdown item is clicked, store the section and the associated item in an array.
     * <p/>
     * array[0] = section name
     * array[1] = item name
     */
    private String[] getSectionAndItemNames(int position) {
        String[] result = new String[2];

        if (items.get(position).isHeader) {
            result[0] = items.get(position).name;
        } else {
            for (int i = position; i >= 0; i--) {
                if (items.get(i).isHeader) {
                    result[0] = items.get(i).name;
                    result[1] = items.get(position).name;
                    break;
                }
            }
        }
        return result;
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public void setItemSelectedListener(ItemSelectedListener listener) {
        this.listener = listener;
    }

    private class SpinnerItem {
        boolean isHeader;
        String name;

        private SpinnerItem(boolean isHeader, String name) {
            this.isHeader = isHeader;
            this.name = name;
        }
    }

    private class SpinnerAdapter extends BaseAdapter {

        private List<SpinnerItem> items;

        private SpinnerAdapter(List<SpinnerItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private boolean isHeader(int position) {
            return position >= 0 && position < items.size() && items.get(position).isHeader;
        }

        @Override
        public View getDropDownView(int position, View view, ViewGroup parent) {
            if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
                view = inflater.inflate(R.layout.explore_spinner_item_dropdown,
                        parent, false);
                view.setTag("DROPDOWN");
            }

            TextView headerTextView = (TextView) view.findViewById(R.id.header_text);
            View dividerView = view.findViewById(R.id.divider_view);
            TextView normalTextView = (TextView) view.findViewById(R.id.normal_text);

            if (isHeader(position)) {
                headerTextView.setText(items.get(position).name);
                headerTextView.setTextColor(configuration.getSectionTitleColorResource());
                headerTextView.setVisibility(View.VISIBLE);
                normalTextView.setVisibility(View.GONE);
                dividerView.setVisibility(View.VISIBLE);
            } else {
                normalTextView.setText(items.get(position).name);

                ColorStateList colorStateList = new ColorStateList(new int[][]{
                        new int[]{android.R.attr.state_activated},
                        new int[]{},
                }, new int[]{
                        configuration.getStateActivatedColor(),
                        configuration.getStateNormalColor(),
                });

                normalTextView.setTextColor(colorStateList);
                headerTextView.setVisibility(View.GONE);
                normalTextView.setVisibility(View.VISIBLE);
                dividerView.setVisibility(View.GONE);
            }

            return view;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
                view = inflater.inflate(R.layout.explore_spinner_item_actionbar, parent, false);
                view.setTag("NON_DROPDOWN");
            }
            TextView textView = (TextView) view.findViewById(R.id.item_actionbar);
            textView.setText(items.get(position).name);
            textView.setTextColor(configuration.getActionBarItemColorResource());
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, configuration.getIndicatorDrawableResource(), 0);
            return view;
        }
    }
}
