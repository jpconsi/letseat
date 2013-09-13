
package com.example.android.apis.app;

import com.considine.letseat.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TitlesFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        String[] shakespeare = getResources().getStringArray(R.array.titles);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, shakespeare);
        setListAdapter(adapter);
        /*
         * Button button = (Button)view.findViewById(R.id.button1);
         * button.setOnClickListener(new View.OnClickListener() {
         * @Override public void onClick(View v) { updateDetail(); } });
         */
        return view; // super.onCreateView(inflater, container,
                     // savedInstanceState);
    }
    /*
     * @Override public void onAttach(Activity activity) {
     * super.onAttach(activity); if (activity instanceof OnItemSelectedListener)
     * { listener = (OnItemSelectedListener)activity; } else { throw new
     * ClassCastException(activity.toString() +
     * " must implemenet MyListFragment.OnItemSelectedListener"); } }
     * @Override public void onSaveInstanceState(Bundle outState) {
     * super.onSaveInstanceState(outState); outState.putInt("curChoice",
     * mCurCheckPosition); }
     * @Override public void onListItemClick(ListView l, View v, int position,
     * long id) { showDetails(position); } // May also be triggered from the
     * Activity public void updateDetail() { // Create fake data String newTime
     * = String.valueOf(System.currentTimeMillis()); // Send data to Activity
     * listener.onItemSelected(newTime); }
     *//**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a whole new
     * activity in which it is displayed.
     */
    /*
     * void showDetails(int index) { mCurCheckPosition = index; if (mDualPane) {
     * // We can display everything in-place with fragments, so update // the
     * list to highlight the selected item and show the data. //
     * getListView().setItemChecked(index, true); // Check what fragment is
     * currently shown, replace if needed. DetailsFragment details =
     * (DetailsFragment)getFragmentManager().findFragmentById( R.id.details); if
     * (details == null || details.getShownIndex() != index) { // Make new
     * fragment to show this selection. details =
     * DetailsFragment.newInstance(index); // Execute a transaction, replacing
     * any existing fragment // with this one inside the frame.
     * FragmentTransaction ft = getFragmentManager().beginTransaction(); if
     * (index == 0) { ft.replace(R.id.details, details); } else {
     * ft.replace(R.id.a_item, details); }
     * ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); ft.commit();
     * } } else { // Otherwise we need to launch a new activity to display //
     * the dialog fragment with selected text. Intent intent = new Intent();
     * intent.setClass(getActivity(), DetailsActivity.class);
     * intent.putExtra("index", index); startActivity(intent); } }
     */
}
