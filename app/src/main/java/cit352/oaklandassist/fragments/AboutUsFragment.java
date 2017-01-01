package cit352.oaklandassist.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cit352.oaklandassist.R;

public class AboutUsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.aboutus_layout, container, false);

        TextView oaklandTextView = (TextView) rootView.findViewById(R.id.oaklandTextView);
        oaklandTextView.setClickable(true);
        oaklandTextView.setMovementMethod(LinkMovementMethod.getInstance());
        String oakland = "<a href='http://www.oakland.edu'> Oakland University Home Page </a>";
        oaklandTextView.setText(Html.fromHtml(oakland));





        return rootView;
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

}

