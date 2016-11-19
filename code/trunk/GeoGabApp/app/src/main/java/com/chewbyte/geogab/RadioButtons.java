package com.chewbyte.geogab;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chewbyte.geogab.R;
import com.chewbyte.geogab.common.Category;
import com.chewbyte.geogab.common.Session;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Chris on 11/09/2016.
 */
public class RadioButtons {

    TextView Tooltip;
    View catSelected;

    private ArrayList<RadioButton> categories;

    public RadioButtons(TextView Tooltip, ArrayList<RadioButton> categories) {

        this.Tooltip = Tooltip;
        this.categories = categories;

        catSelected = categories.get(0);
        Session.setCategorySelected(Category.EVENT);

        for(RadioButton r: categories) {
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleRadio((RadioButton) view);
                }
            });
        }
    }

    private void handleRadio(RadioButton view) {
        if(catSelected != view) {
            catSelected = view;
            String buttonText = (String) view.getText();
            switch (buttonText) {
                case "EVENT": Tooltip.setText(R.string.radio_event);
                    Session.setCategorySelected(Category.EVENT);
                    break;
                case "DEBATE": Tooltip.setText(R.string.radio_debate);
                    Session.setCategorySelected(Category.DEBATE);
                    break;
                case "AWARENESS": Tooltip.setText(R.string.radio_awareness);
                    Session.setCategorySelected(Category.AWARENESS);
                    break;
                case "CURIOSITY": Tooltip.setText(R.string.radio_curiosity);
                    Session.setCategorySelected(Category.CURIOSITY);
                    break;
            }
            for(RadioButton r: categories) {
                if(catSelected == r) r.setChecked(true);
                else r.setChecked(false);
            }
            System.out.println("Selected Category:" + Session.getCategorySelected());
        }
    }
}
