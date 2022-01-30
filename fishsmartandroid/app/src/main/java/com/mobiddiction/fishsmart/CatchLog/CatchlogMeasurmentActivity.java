package com.mobiddiction.fishsmart.CatchLog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtMeasureBean;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;
import java.util.ListIterator;

public class CatchlogMeasurmentActivity extends AppCompatActivity {
    private ImageView imgBack;
    private EditText edtKept;
    private EditText edtReleased;
    private LinearLayout measureKept;
    private LinearLayout measureReleased;
    private Typeface typeface;
    private ArrayList<CatchLogSpeciesCaughtMeasureBean> measureList;
    private int numberInputKept;
    private int numberInputReleased;
    private int currentNumberKept;
    private int currentNumberReleased;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchlog_measure);

        typeface = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        imgBack = findViewById(R.id.imgBack);
        edtKept = findViewById(R.id.edtKept);
        edtReleased = findViewById(R.id.edtReleased);
        measureKept = findViewById(R.id.measureKept);
        measureReleased = findViewById(R.id.measureReleased);
        measureList = new ArrayList<>();

        Intent intent = getIntent();
        currentNumberKept = intent.getIntExtra("getInitialNumberKept", 0);
        currentNumberReleased = intent.getIntExtra("getInitialNumberReleased", 0);
        numberInputKept = intent.getIntExtra("getNumberInputKept", 0);
        numberInputReleased = intent.getIntExtra("getNumberInputReleased", 0);
        measureList = (ArrayList<CatchLogSpeciesCaughtMeasureBean>) getIntent().getSerializableExtra("getMeasureList");

        if (measureList.size() > 0) {
            for (int i = 0; i < currentNumberKept + currentNumberReleased; i++) {
                measureList.get(i).isKept = i < currentNumberKept;
            }
            if(numberInputKept == currentNumberKept && numberInputReleased == currentNumberReleased){
                generateCatchesUI();
            }else{
                addOrRemoveViews();
            }
        } else {
            generateDummyView();
        }
        edtKept.setHint("" + numberInputKept);
        edtReleased.setHint("" + numberInputReleased);

        edtKept.addTextChangedListener(new EdtKeptReleasedTextWatcher(edtKept));
        edtReleased.addTextChangedListener(new EdtKeptReleasedTextWatcher(edtReleased));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On Activity result Pass value.
                System.out.println("====== MEASURE SIZE ======" + measureList.size());
                Intent intent = getIntent();
                intent.putExtra("MeasureList", measureList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();
    }

    private void addOrRemoveViews(){
        if (currentNumberKept < numberInputKept) {
            int viewToAdd = numberInputKept - currentNumberKept;
            for (int i = 0; i < viewToAdd; i++) {
                CatchLogSpeciesCaughtMeasureBean catchLogSpeciesCaughtMeasureBean = new CatchLogSpeciesCaughtMeasureBean();
                catchLogSpeciesCaughtMeasureBean.isKept = true;
                catchLogSpeciesCaughtMeasureBean.weight = 0.0;
                catchLogSpeciesCaughtMeasureBean.length = 0.0;
                measureList.add(currentNumberKept, catchLogSpeciesCaughtMeasureBean);
                currentNumberKept++;
            }
        } else if (currentNumberKept > numberInputKept && numberInputKept != -1) {
            int viewToRemove = currentNumberKept - numberInputKept;
            int index = 0;
            ListIterator<CatchLogSpeciesCaughtMeasureBean> li = measureList.listIterator(measureList.size());
            CatchLogSpeciesCaughtMeasureBean mesaureBean;
            // Iterate in reverse.
            while(li.hasPrevious() && index < viewToRemove) {
                mesaureBean = li.previous();
                if (mesaureBean.isKept) {
                    li.remove();
                    index++;
                    currentNumberKept--;
                }
            }
        } if (currentNumberReleased < numberInputReleased) {
            int viewToAdd = numberInputReleased - currentNumberReleased;
            for (int i = 0; i < viewToAdd; i++) {
                CatchLogSpeciesCaughtMeasureBean catchLogSpeciesCaughtMeasureBean = new CatchLogSpeciesCaughtMeasureBean();
                catchLogSpeciesCaughtMeasureBean.isKept = false;
                catchLogSpeciesCaughtMeasureBean.weight = 0.0;
                catchLogSpeciesCaughtMeasureBean.length = 0.0;
                measureList.add(currentNumberKept + currentNumberReleased, catchLogSpeciesCaughtMeasureBean);
                currentNumberReleased++;
            }
        } else if (currentNumberReleased > numberInputReleased && numberInputReleased != -1) {
            int viewToRemove = currentNumberReleased - numberInputReleased;
            int index = 0;
            ListIterator<CatchLogSpeciesCaughtMeasureBean> li = measureList.listIterator(measureList.size());
            CatchLogSpeciesCaughtMeasureBean mesaureBean;
            // Iterate in reverse.
            while(li.hasPrevious() && index < viewToRemove) {
                mesaureBean = li.previous();
                if (!mesaureBean.isKept) {
                    li.remove();
                    index++;
                    currentNumberReleased--;
                }
            }
        }
        generateCatchesUI();
    }

    private void generateDummyView() {
        for (int i = 0; i < numberInputKept; i++) {
            CatchLogSpeciesCaughtMeasureBean catchLogSpeciesCaughtMeasureBean = new CatchLogSpeciesCaughtMeasureBean();
            catchLogSpeciesCaughtMeasureBean.isKept = true;
            catchLogSpeciesCaughtMeasureBean.weight = 0.0;
            catchLogSpeciesCaughtMeasureBean.length = 0.0;
            measureList.add(catchLogSpeciesCaughtMeasureBean);
        }
        for (int i = 0; i < numberInputReleased; i++) {
            CatchLogSpeciesCaughtMeasureBean catchLogSpeciesCaughtMeasureBean = new CatchLogSpeciesCaughtMeasureBean();
            catchLogSpeciesCaughtMeasureBean.isKept = false;
            catchLogSpeciesCaughtMeasureBean.weight = 0.0;
            catchLogSpeciesCaughtMeasureBean.length = 0.0;
            measureList.add(catchLogSpeciesCaughtMeasureBean);
        }
        currentNumberKept = numberInputKept;
        currentNumberReleased = numberInputReleased;
        generateCatchesUI();
    }

    private void generateCatchesUI() {
        EditText edtWeight;
        EditText edtCms;
        View convertView;
        int index = 0;
        CatchLogSpeciesCaughtMeasureBean measureBean;

        try {
            measureKept.removeAllViews();
            measureReleased.removeAllViews();
            if (measureList.size() > 0) {
                for (int i = 0; i < measureList.size(); i++) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.row_add_more_mesaurment, null, true);
                    edtWeight = convertView.findViewById(R.id.edtWeight);
                    edtCms = convertView.findViewById(R.id.edtCms);

                    edtWeight.setTypeface(typeface);
                    edtCms.setTypeface(typeface);

                    measureBean = measureList.get(i);

                    edtWeight.setTag(index);
                    if (measureBean.weight != 0.0) {
                        edtWeight.setText("" + measureBean.weight);
                    }
                    edtWeight.addTextChangedListener(new MyTextWatcher(edtWeight));

                    edtCms.setTag(index);
                    if (measureBean.length != 0.0) {
                        edtCms.setText("" + measureBean.length);
                    }
                    edtCms.addTextChangedListener(new MyTextWatcher(edtCms));

                    if (measureBean.isKept) {
                        measureKept.addView(convertView);
                    } else {
                        measureReleased.addView(convertView);
                    }
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            int pos = (int) view.getTag();
            CatchLogSpeciesCaughtMeasureBean measureBean = measureList.get(pos);

            switch (view.getId()) {
                case R.id.edtWeight:
                    if ((!text.equals("")) && (!text.equals(".")))
                        measureBean.weight = Double.parseDouble(text);
                    break;
                case R.id.edtCms:
                    if ((!text.equals("")) && (!text.equals(".")))
                        measureBean.length = Double.parseDouble(text);
                    break;
            }
        }
    }

    private class EdtKeptReleasedTextWatcher implements TextWatcher {
        private View view;

        private EdtKeptReleasedTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.edtKept:
                    if (!text.equals("")) {
                        numberInputKept = Integer.parseInt(text);
                        addOrRemoveViews();
                    } else {
                        numberInputKept = -1;
                        edtKept.setHint("");
                    }
                    break;
                case R.id.edtReleased:
                    if (!text.equals("")) {
                        numberInputReleased = Integer.parseInt(text);
                        addOrRemoveViews();
                    } else {
                        numberInputReleased = -1;
                        edtReleased.setHint("");
                    }
                    break;
            }
        }
    }
}