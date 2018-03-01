package com.example.spec.cis410_hw5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ViewFragment3_1 extends Fragment {

    int position;
    String ans;
    ViewGroup rootView;
    String q;
    SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //show the view
        rootView = (ViewGroup) inflater.inflate(
                R.layout.q3_1, container, false);

        //access preferences
        prefs = getContext().getSharedPreferences(
                "com.example.spec", getContext().MODE_PRIVATE);

        //get info
        if (getArguments().containsKey("position")) {
            int pos = getArguments().getInt("position");
            position = pos;
        }

        if (getArguments().getString("question") != null) {
            String strtext = getArguments().getString("question");
            TextView question = (TextView) rootView.findViewById(R.id.survey_question);
            question.setText(strtext);

            //get ans if it's available
            int rbid = prefs.getInt(q,-1);
            if(rbid != -1) {
                RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.survey_answer);
                radioGroup.check(rbid);
            }
        }


        //keep track of answer
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.survey_answer);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override //listener detects button choice, auto-saves
         public void onCheckedChanged(RadioGroup group, int checkedId) {
         // simply saves selected text
             RadioGroup radioGroup = (RadioGroup) getView().findViewById(R.id.survey_answer);
             int rbid = radioGroup.getCheckedRadioButtonId();
             RadioButton rb = (RadioButton) rootView.findViewById(rbid);
             if (rb.isChecked()) {
                 ans = rb.getText().toString();
                 TakeSurvey.addAnswer345(position, ans);
             }

             prefs.edit().remove(q + "q").commit();
             prefs.edit().putInt(q, rbid).commit();
             prefs.edit().remove(q + "q").commit();
             prefs.edit().putString(q + "q", rb.getText().toString()).commit();
         }//end onCheckChanged
        });

        return rootView;
    }//end onCreate

}//end class

