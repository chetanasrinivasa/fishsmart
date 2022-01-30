package com.mobiddiction.fishsmart.CustomAlert;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiddiction.fishsmart.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * iOSAlertViewController
 */
public class AlertView {
    public enum Style{
        ActionSheet,
        Alert
    }
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    public static final int HORIZONTAL_BUTTONS_MAXCOUNT = 2;
    public static final int CANCELPOSITION = -1;

    private String title;
    private String msg;
    private String[] destructive;
    private String[] others;
    private List<String> mDestructive;
    private List<String> mOthers;
    private String cancel;
    private ArrayList<String> mDatas = new ArrayList<String>();

    private WeakReference<Context> contextWeak;
    private ViewGroup contentContainer;
    private ViewGroup decorView;//activity的根View
    private ViewGroup rootView;//AlertView 的 根View
    private ViewGroup loAlertHeader;//窗口headerView

    private Style style = Style.Alert;

    private OnDismissListener onDismissListener;
    private OnItemClickListener onItemClickListener;
    private boolean isShowing;

    private Animation outAnim;
    private Animation inAnim;
    private int gravity = Gravity.CENTER;
    private boolean isHeaderShowing;
    private boolean isSingleButton;
    private boolean bold;
    private boolean isFingerPrint;

    public AlertView(Builder builder) {
        this.contextWeak = new WeakReference<>(builder.context);
        this.style = builder.style;
        this.title = builder.title;
        this.msg = builder.msg;
        this.cancel = builder.cancel;
        this.destructive = builder.destructive;
        this.others = builder.others;
        this.onItemClickListener = builder.onItemClickListener;
        this.isHeaderShowing = builder.isHeaderShowing;
        this.isSingleButton = builder.isSingleButton;

        initData(title, msg, cancel, destructive, others);
        initViews(isHeaderShowing,isSingleButton);
        init();
        initEvents();
    }

    public AlertView(String title, String msg, String cancel, String[] destructive, String[] others, Context context, Style style, boolean isHeaderShowing,boolean isSingleButton,OnItemClickListener onItemClickListener,boolean bold, boolean isFingerPrint){
        this.contextWeak = new WeakReference<>(context);
        if(style != null)this.style = style;
        this.onItemClickListener = onItemClickListener;
        this.bold = bold;
        this.isFingerPrint = isFingerPrint;

        initData(title, msg, cancel, destructive, others);
        initViews(isHeaderShowing,isSingleButton);
        init();
        initEvents();
    }


    public AlertView(String title, String msg, String cancel, String[] destructive, String[] others, Context context, Style style, boolean isHeaderShowing,boolean isSingleButton,OnItemClickListener onItemClickListener,String textColor,boolean isBold){
        this.contextWeak = new WeakReference<>(context);
        if(style != null)this.style = style;
        this.bold = isBold;
        this.onItemClickListener = onItemClickListener;
        initData(title, msg, cancel, destructive, others);
        initViewsColor(isHeaderShowing,isSingleButton,textColor,isBold);
        init();
        initEvents();
    }

    public AlertView(String title, String msg, String cancel, String[] destructive, String[] others, Context context, Style style, boolean isHeaderShowing,boolean isSingleButton,OnItemClickListener onItemClickListener){
        this.contextWeak = new WeakReference<>(context);
        if(style != null)this.style = style;
        this.onItemClickListener = onItemClickListener;

        initData(title, msg, cancel, destructive, others);
        initViews(isHeaderShowing,isSingleButton);
        init();
        initEvents();
    }


    protected void initData(String title, String msg, String cancel, String[] destructive, String[] others) {

        this.title = title;
        this.msg = msg;
        if (destructive != null){
            this.mDestructive = Arrays.asList(destructive);
            this.mDatas.addAll(mDestructive);
        }
        if (others != null){
            this.mOthers = Arrays.asList(others);
            this.mDatas.addAll(mOthers);
        }
        if (cancel != null){
            this.cancel = cancel;
            if(style == Style.Alert && mDatas.size() < HORIZONTAL_BUTTONS_MAXCOUNT){
                this.mDatas.add(0,cancel);
            }
        }

    }
    protected void initViews(boolean isHeaderShowing, boolean isSingleButton){
        Context context = contextWeak.get();
        if(context == null) return;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = ((AppCompatActivity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        contentContainer = rootView.findViewById(R.id.content_container);
        int margin_alert_left_right = 0;
        switch (style){
            case ActionSheet:
                params.gravity = Gravity.BOTTOM;
                margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_actionsheet_left_right);
                params.setMargins(margin_alert_left_right,0,margin_alert_left_right,margin_alert_left_right);
                contentContainer.setLayoutParams(params);
                gravity = Gravity.BOTTOM;
                initActionSheetViews(layoutInflater,isHeaderShowing,isSingleButton);
                break;
            case Alert:
                params.gravity = Gravity.CENTER;
                margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_alert_left_right);
                params.setMargins(margin_alert_left_right,0,margin_alert_left_right,0);
                contentContainer.setLayoutParams(params);
                gravity = Gravity.CENTER;
                initAlertViews(layoutInflater,isHeaderShowing,isSingleButton);
                break;
        }
    }

    protected void initViewsColor(boolean isHeaderShowing, boolean isSingleButton,String textColor,boolean isBold){
        Context context = contextWeak.get();
        if(context == null) return;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = ((AppCompatActivity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        contentContainer = rootView.findViewById(R.id.content_container);
        int margin_alert_left_right = 0;
        switch (style){
            case ActionSheet:
                params.gravity = Gravity.BOTTOM;
                margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_actionsheet_left_right);
                params.setMargins(margin_alert_left_right,0,margin_alert_left_right,margin_alert_left_right);
                contentContainer.setLayoutParams(params);
                gravity = Gravity.BOTTOM;
                initActionSheetViews(layoutInflater,isHeaderShowing,isSingleButton);
                break;
            case Alert:
                params.gravity = Gravity.CENTER;
                margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_alert_left_right);
                params.setMargins(margin_alert_left_right,0,margin_alert_left_right,0);
                contentContainer.setLayoutParams(params);
                gravity = Gravity.CENTER;
                initAlertViewsColor(layoutInflater,isHeaderShowing,isSingleButton,textColor,isBold);
                break;
        }
    }


    protected void initHeaderViewTextColor(ViewGroup viewGroup,boolean isHeaderShowing,String textCOlor,boolean isBold){
        loAlertHeader = viewGroup.findViewById(R.id.loAlertHeader);
        TextView tvAlertTitle = viewGroup.findViewById(R.id.tvAlertTitle);
        TextView tvAlertMsg = viewGroup.findViewById(R.id.tvAlertMsg);
        if(isHeaderShowing){
            tvAlertTitle.setVisibility(View.VISIBLE);
        } else{
            tvAlertTitle.setVisibility(View.GONE);
        }
        if(title != null) {
            if(isBold == true){
                tvAlertTitle.setTypeface(tvAlertTitle.getTypeface(),Typeface.BOLD);
            }
            tvAlertTitle.setText(title);
            tvAlertTitle.setTextColor(Color.parseColor(textCOlor));
        }else{
            tvAlertTitle.setVisibility(View.GONE);
        }
        if(msg != null) {
            tvAlertMsg.setText(msg);
        }else{
            tvAlertMsg.setVisibility(View.GONE);
        }
    }

    protected void initHeaderView(ViewGroup viewGroup,boolean isHeaderShowing){
        loAlertHeader = viewGroup.findViewById(R.id.loAlertHeader);
        TextView tvAlertTitle = viewGroup.findViewById(R.id.tvAlertTitle);
        TextView tvAlertMsg = viewGroup.findViewById(R.id.tvAlertMsg);
        if(isHeaderShowing){
            tvAlertTitle.setVisibility(View.VISIBLE);
        } else{
            tvAlertTitle.setVisibility(View.GONE);
        }
        if(title != null) {
            tvAlertTitle.setText(title);
        }else{
            tvAlertTitle.setVisibility(View.GONE);
        }
        if(msg != null) {
            tvAlertMsg.setText(msg);
        }else{
            tvAlertMsg.setVisibility(View.GONE);
        }
    }
    protected void initListView(boolean isSingleButton){
        Context context = contextWeak.get();
        if(context == null) return;

        ListView alertButtonListView = contentContainer.findViewById(R.id.alertButtonListView);
        if(cancel != null && style == Style.Alert){
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_alertbutton, null);
            TextView tvAlert = itemView.findViewById(R.id.tvAlert);
            tvAlert.setText(cancel);
            tvAlert.setClickable(true);
            tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
            tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
            tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_bottom);
            tvAlert.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
            alertButtonListView.addFooterView(itemView);
        }
        AlertViewAdapter adapter = new AlertViewAdapter(mDatas,mDestructive,isSingleButton);
        alertButtonListView.setAdapter(adapter);
        alertButtonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(onItemClickListener != null)onItemClickListener.onItemClick(AlertView.this,position);
                dismiss();
            }
        });
    }

    protected void initActionSheetViews(LayoutInflater layoutInflater,boolean isHeaderShowing, boolean isSingleButton) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview_actionsheet,contentContainer);
        initHeaderView(viewGroup,isHeaderShowing);

        initListView(isSingleButton);
        TextView tvAlertCancel = contentContainer.findViewById(R.id.tvAlertCancel);
        if(cancel != null){
            tvAlertCancel.setVisibility(View.VISIBLE);
            tvAlertCancel.setText(cancel);
        }
        tvAlertCancel.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
    }
    protected void initAlertViews(LayoutInflater layoutInflater,boolean isHeaderShowing, boolean isSingleButton) {
        Context context = contextWeak.get();
        if(context == null) return;

        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview_alert, contentContainer);
        initHeaderView(viewGroup,isHeaderShowing);

        int position = 0;
        if(mDatas.size()<=HORIZONTAL_BUTTONS_MAXCOUNT){
            ImageView touchIDImage = contentContainer.findViewById(R.id.touchIdImageView);
            if(isFingerPrint){
                touchIDImage.setVisibility(View.VISIBLE);
            }else {
                touchIDImage.setVisibility(View.GONE);
            }
            ViewStub viewStub = contentContainer.findViewById(R.id.viewStubHorizontal);
            viewStub.inflate();
            LinearLayout loAlertButtons = contentContainer.findViewById(R.id.loAlertButtons);
            for (int i = 0; i < mDatas.size(); i ++) {
                if (i != 0){
                    View divier = new View(context);
                    divier.setBackgroundColor(context.getResources().getColor(R.color.bgColor_divier));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)context.getResources().getDimension(R.dimen.size_divier), LinearLayout.LayoutParams.MATCH_PARENT);
                    loAlertButtons.addView(divier,params);
                }
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_alertbutton, null);
                TextView tvAlert = itemView.findViewById(R.id.tvAlert);
                TextView okButton = itemView.findViewById(R.id.okButton);
                View divider = itemView.findViewById(R.id.divider);
                tvAlert.setClickable(true);

                if(bold && i > 0){
                    tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
                }
                if(isSingleButton){
                    okButton.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.GONE);
                    tvAlert.setVisibility(View.GONE);
                    okButton.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
                    okButton.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
                } else{
                    if(mDatas.size() == 1){
                        tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_bottom);
                    }
                    else if(i == 0){
                        tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_left);
                    }
                    else if(i == mDatas.size() - 1){
                        tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_right);
                    }
                    String data = mDatas.get(i);
                    tvAlert.setText(data);

                    if (data == cancel){
                        tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
                        tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
                        tvAlert.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
                        position = position - 1;
                    }
                    //高亮按钮的样式
                    else if (mDestructive!= null && mDestructive.contains(data)){
                        tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_destructive));
                    }
                    tvAlert.setOnClickListener(new OnTextClickListener(position));
                }
                position++;
                loAlertButtons.addView(itemView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            }
        }
        else{
            ViewStub viewStub = contentContainer.findViewById(R.id.viewStubVertical);
            viewStub.inflate();
            initListView(isSingleButton);
        }
    }

    protected void initAlertViewsColor(LayoutInflater layoutInflater,boolean isHeaderShowing, boolean isSingleButton,String textColor,boolean isBold) {
        Context context = contextWeak.get();
        if(context == null) return;

        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview_alert, contentContainer);
        initHeaderViewTextColor(viewGroup,isHeaderShowing,textColor,isBold);

        int position = 0;
        if(mDatas.size()<=HORIZONTAL_BUTTONS_MAXCOUNT){
            ImageView touchIDImage = contentContainer.findViewById(R.id.touchIdImageView);
            if(isFingerPrint){
                touchIDImage.setVisibility(View.VISIBLE);
            }else {
                touchIDImage.setVisibility(View.GONE);
            }
            ViewStub viewStub = contentContainer.findViewById(R.id.viewStubHorizontal);
            viewStub.inflate();
            LinearLayout loAlertButtons = contentContainer.findViewById(R.id.loAlertButtons);
            for (int i = 0; i < mDatas.size(); i ++) {
                if (i != 0){
                    View divier = new View(context);
                    divier.setBackgroundColor(context.getResources().getColor(R.color.bgColor_divier));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)context.getResources().getDimension(R.dimen.size_divier), LinearLayout.LayoutParams.MATCH_PARENT);
                    loAlertButtons.addView(divier,params);
                }
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_alertbutton, null);
                TextView tvAlert = itemView.findViewById(R.id.tvAlert);
                TextView okButton = itemView.findViewById(R.id.okButton);
                View divider = itemView.findViewById(R.id.divider);
                tvAlert.setClickable(true);

                if(bold && i > 0){
                    tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
                }
                if(isSingleButton){
                    okButton.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.GONE);
                    tvAlert.setVisibility(View.GONE);
                    okButton.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
                    okButton.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
                } else{
                    if(mDatas.size() == 1){
                        tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_bottom);
                    }
                    else if(i == 0){
                        tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_left);
                    }
                    else if(i == mDatas.size() - 1){
                        tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_right);
                    }
                    String data = mDatas.get(i);
                    tvAlert.setText(data);

                    if (data == cancel){
                        tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
                        tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
                        tvAlert.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
                        position = position - 1;
                    }
                    //高亮按钮的样式
                    else if (mDestructive!= null && mDestructive.contains(data)){
                        tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_destructive));
                    }
                    tvAlert.setOnClickListener(new OnTextClickListener(position));
                }
                position++;
                loAlertButtons.addView(itemView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            }
        }
        else{
            ViewStub viewStub = contentContainer.findViewById(R.id.viewStubVertical);
            viewStub.inflate();
            initListView(isSingleButton);
        }
    }
    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }
    protected void initEvents() {
    }
    public AlertView addExtView(View extView){
        loAlertHeader.addView(extView);
        return this;
    }
    private void onAttached(View view) {
        isShowing = true;
        decorView.addView(view);
        contentContainer.startAnimation(inAnim);
    }
    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(rootView);
    }
    public boolean isShowing() {
        return rootView.getParent() != null && isShowing;
    }

    public void dismiss() {
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        contentContainer.startAnimation(outAnim);
    }

    public void dismissImmediately() {
        decorView.removeView(rootView);
        isShowing = false;
        if(onDismissListener != null){
            onDismissListener.onDismiss(this);
        }

    }

    public Animation getInAnimation() {
        Context context = contextWeak.get();
        if(context == null) return null;

        int res = AlertAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        Context context = contextWeak.get();
        if(context == null) return null;

        int res = AlertAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    public AlertView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    class OnTextClickListener implements View.OnClickListener{

        private int position;
        public OnTextClickListener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            if(onItemClickListener != null)onItemClickListener.onItemClick(AlertView.this,position);
            dismiss();
        }
    }
    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    public void setMarginBottom(int marginBottom){
        Context context = contextWeak.get();
        if(context == null) return;

        int margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_alert_left_right);
        params.setMargins(margin_alert_left_right,0,margin_alert_left_right,marginBottom);
        contentContainer.setLayoutParams(params);
    }
    public AlertView setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        }
        else{
            view.setOnTouchListener(null);
        }
        return this;
    }
    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    /**
     * Builder for arguments
     */
    public static class Builder {
        private Context context;
        private Style style;
        private String title;
        private boolean isHeaderShowing;
        private boolean isSingleButton;
        private String msg;
        private String cancel;
        private String[] destructive;
        private String[] others;
        private OnItemClickListener onItemClickListener;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setStyle(Style style) {
            if(style != null) {
                this.style = style;
            }
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIsSingleButton(boolean isSingleButton) {
            this.isSingleButton = isSingleButton;
            return this;
        }

        public Builder setIsHeaderShowing(boolean isHeaderShowing) {
            this.isHeaderShowing = isHeaderShowing;
            return this;
        }

        public Builder setMessage(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setCancelText(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setDestructive(String... destructive) {
            this.destructive = destructive;
            return this;
        }

        public Builder setOthers(String[] others) {
            this.others = others;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public AlertView build() {
            return new AlertView(this);
        }
    }
}
