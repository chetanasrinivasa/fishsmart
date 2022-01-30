package com.mobiddiction.fishsmart.CatchLog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.MethodTypesBean;
import com.mobiddiction.fishsmart.util.Utils;

import java.util.ArrayList;

/**
 * Created by Krunal on 18/1/2019.
 */
public class CatchlogMethodsActivity extends AppCompatActivity {
    private ImageView imgBack;
    private LinearLayout llMethod;
    private Typeface typeface;
    private ArrayList<MethodTypesBean> methodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catchlog_method);

        typeface = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        imgBack = findViewById(R.id.imgBack);
        llMethod = findViewById(R.id.llMethod);
        methodsList = new ArrayList<>();

        methodsList = (ArrayList<MethodTypesBean>) getIntent().getSerializableExtra("getMethodList");
        if (methodsList.size() > 0) {
            generateCatchesUI();
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // On Activity result Pass value.
                System.out.println("====== Method Size SIZE ======" + methodsList.size());
                Intent intent = getIntent();
                intent.putExtra("MethodList", methodsList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void generateCatchesUI() {
        ImageView imgTick;
        TextView txtMethod;
        View convertView;
        int index = 0;
        MethodTypesBean methodTypesBean;

        try {
            llMethod.removeAllViews();
            if (methodsList.size() > 0) {
                for (int i = 0; i < methodsList.size(); i++) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.row_method_types, null, true);
                    imgTick = convertView.findViewById(R.id.imgTick);
                    txtMethod = convertView.findViewById(R.id.txtMethod);
                    txtMethod.setTypeface(typeface);
                    methodTypesBean = methodsList.get(i);

                    if (methodTypesBean.isSelected) {
                        imgTick.setVisibility(View.VISIBLE);
                    } else {
                        imgTick.setVisibility(View.GONE);
                    }
                    txtMethod.setText(methodTypesBean.name);
                    txtMethod.setTag(index);
                    txtMethod.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int pos = (int) view.getTag();
                            methodsList.get(pos).isSelected = !methodsList.get(pos).isSelected;
                            generateCatchesUI();
                        }
                    });
                    llMethod.addView(convertView);
                    index++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}