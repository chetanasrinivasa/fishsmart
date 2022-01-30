package com.mobiddiction.fishsmart.CatchLog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mindorks.paracamera.Camera;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.Network.UploadPicAPI;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.bean.CatchLogCaptureBean;
import com.mobiddiction.fishsmart.bean.CatchLogImageBean;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtBean;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtMeasureBean;
import com.mobiddiction.fishsmart.bean.MethodContentBean;
import com.mobiddiction.fishsmart.bean.MethodTypesBean;
import com.mobiddiction.fishsmart.bll.CatchLogBll;
import com.mobiddiction.fishsmart.util.ScalingUtilities;
import com.mobiddiction.fishsmart.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.TimeZone;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Krunal on 30/11/2018.
 */
public class AddCatchlogActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private ScrollView scrollView;
    private ImageButton backbtn;
    private TextView nav_title;
    private ImageButton imgDeleteBtn;

    private RelativeLayout relLocation;
    private TextView txtLocationName;
    private TextView txtLatitude;
    private TextView txtLongitude;

    private RelativeLayout relDatePick;
    private TextView txtDate;
    private RelativeLayout relStartTimePick;
    private TextView txtStartTime;
    private RelativeLayout relEndTimePick;
    private TextView txtEndTime;
    private RadioGroup rdGroup;
    private EditText catchlogComment;

    private LinearLayout llMoreCatches;
    private TextView txtAddMoreCatches;

    private RelativeLayout relShare;
    private TextView txtShareOptions;

    private RelativeLayout relLogCatch;
    private TextView txtLogCatch;

    private RelativeLayout relBottomSlider;
    private RelativeLayout layoutPrivate;
    private RelativeLayout layoutResearch;
    private RelativeLayout layoutPublic;
    private TextView txtPrivate;
    private TextView txtResearch;
    private TextView txtPublic;
    private ImageView imgPrivate;
    private ImageView imgResearch;
    private ImageView imgPublic;

    //
    private RelativeLayout relPhotoSlider;
    private TextView txtCamera;
    private TextView txtGallery;
    private TextView txtCancel;

    //
    private RelativeLayout relImage1;
    private RelativeLayout relImage2;
    private RelativeLayout relImage3;

    private RelativeLayout titleCommentImage1Layout;
    private RelativeLayout titleCommentImage2Layout;
    private RelativeLayout titleCommentImage3Layout;

    private ImageView imgCapImage1;
    private ImageView imgCapImage2;
    private ImageView imgCapImage3;

    private TextView titleImage1;
    private TextView titleImage2;
    private TextView titleImage3;

    private TextView commentImage1;
    private TextView commentImage2;
    private TextView commentImage3;

    private ImageView editImage1;
    private ImageView editImage2;
    private ImageView editImage3;

    private int image1Id;
    private int image2Id;
    private int image3Id;

    private View includePhotoTitleComment;
    private ImageView imgCaptureImage;
    private EditText edtTitle;
    private EditText edtComment;
    private TextView txtCancelPhoto;
    private TextView txtUse;

    private View includeSeePhoto;
    private ImageView imgBck;
    private ImageView imgSeeImage;
    //
    private int year;
    private int month;
    private int day;

    private int sHours;
    private int sMinutes;

    private int eHours;
    private int eMinutes;

    //
    private int existingYear;
    private int existingMonth;
    private int existingDay;

    private int existingSHours;
    private int existingSMinutes;

    private int existingEHours;
    private int existingEMinutes;

    private boolean isDateSelected = false;

    //
    // private String selectedMethods;
    private ArrayList<MethodTypesBean> methodsList;
    private boolean isBoatSelected = true;
    private Animation slideIn;
    private Animation slideOut;
    private ArrayList<CatchLogSpeciesCaughtBean> speciesCaughtList;
    private int position;
    public double catchLatitude;
    public double catchLongitude;
    public static final int REQUEST_CHOOSE_LOCATION = 1001;
    private static final int REQUEST_SELECT_SPECIES = 2001;
    private static final int REQUEST_SELECT_MEASURE = 3001;
    private static final int REQUEST_GALLERY = 4001;
    private static final int REQUEST_SELECT_METHOD = 7001;

    public static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";

    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private final static int ALL_PERMISSIONS_RESULT = 107;
    // private Uri picUri;
    private ArrayList<CatchLogCaptureBean> capturePhotoList = new ArrayList<>();
    private int photoNumber = 1;
    private int isRecordExist;
    private CatchLogBean existCatchLogBean;
    private int whichPrivacy = 2;
    private int totalCapturePhotoSize = 0;

    private String assignId;
    private RelativeLayout relProgress;

    // Camera Code
    private Camera camera;
    private Uri imageUri;
    File file;
    private Bitmap bitmap;
    private static final int GALLERY_PICTURE = 5001;

    private TextView txtSelectSpecies, catchlogMeasurments;
    private EditText mainEdtKept, mainEdtReleased;
    private RelativeLayout relKepReleasedDetails;
    private RelativeLayout selectSpeciesLayout;
    private ArrayList<String> methodNames;
    private StringBuilder measurementDetails = new StringBuilder();
    private CatchLogCaptureBean titleCommentBean;
    private Bitmap photoBitmap;
    private Bitmap photoTitleCommentBitmap;
    private CatchLogBean catchLogBean;
    private int numberPosition;
    private boolean isImage1IdDone;
    private boolean isImage2IdDone;
    private boolean isImage3IdDone;
    private int numberInputKept;
    private int numberInputReleased;
    private String oceanName = "";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catchlog);
        isRecordExist = getIntent().getIntExtra("isRecordExist", 0);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        scrollView = findViewById(R.id.catchLogScrollView);
        backbtn = findViewById(R.id.backbtn);
        nav_title = findViewById(R.id.nav_title);
        imgDeleteBtn = findViewById(R.id.imgDeleteBtn);
        relLocation = findViewById(R.id.relLocation);
        txtLocationName = findViewById(R.id.txtLocationName);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        relDatePick = findViewById(R.id.relDatePick);
        txtDate = findViewById(R.id.txtDate);
        relStartTimePick = findViewById(R.id.relStartTimePick);
        txtStartTime = findViewById(R.id.txtStartTime);
        relEndTimePick = findViewById(R.id.relEndTimePick);
        txtEndTime = findViewById(R.id.txtEndTime);
        rdGroup = findViewById(R.id.rdGroup);
        catchlogComment = findViewById(R.id.catchlogComment);
        llMoreCatches = findViewById(R.id.llMoreCatches);
        txtAddMoreCatches = findViewById(R.id.txtAddMoreCatches);
        relShare = findViewById(R.id.relShare);
        txtShareOptions = findViewById(R.id.txtShareOptions);
        relLogCatch = findViewById(R.id.relLogCatch);
        txtLogCatch = findViewById(R.id.txtLogCatch);
        relBottomSlider = findViewById(R.id.relBottomSlider);
        layoutPrivate = findViewById(R.id.layoutPrivate);
        layoutResearch = findViewById(R.id.layoutResearch);
        layoutPublic = findViewById(R.id.layoutPublic);
        txtPrivate = findViewById(R.id.txtPrivate);
        txtResearch = findViewById(R.id.txtResearch);
        txtPublic = findViewById(R.id.txtPublic);
        imgPrivate = findViewById(R.id.imgPrivate);
        imgResearch = findViewById(R.id.imgResearch);
        imgPublic = findViewById(R.id.imgPublic);
        relPhotoSlider = findViewById(R.id.relPhotoSlider);
        txtCamera = findViewById(R.id.txtCamera);
        txtGallery = findViewById(R.id.txtGallery);
        txtCancel = findViewById(R.id.txtCancel);
        relImage1 = findViewById(R.id.relImage1);
        relImage2 = findViewById(R.id.relImage2);
        relImage3 = findViewById(R.id.relImage3);
        titleCommentImage1Layout = findViewById(R.id.titleCommentImage1Layout);
        titleCommentImage2Layout = findViewById(R.id.titleCommentImage2Layout);
        titleCommentImage3Layout = findViewById(R.id.titleCommentImage3Layout);
        imgCapImage1 = findViewById(R.id.imgCapImage1);
        imgCapImage2 = findViewById(R.id.imgCapImage2);
        imgCapImage3 = findViewById(R.id.imgCapImage3);
        titleImage1 = findViewById(R.id.titleImage1);
        titleImage2 = findViewById(R.id.titleImage2);
        titleImage3 = findViewById(R.id.titleImage3);
        commentImage1 = findViewById(R.id.commentImage1);
        commentImage2 = findViewById(R.id.commentImage2);
        commentImage3 = findViewById(R.id.commentImage3);
        editImage1 = findViewById(R.id.editImage1);
        editImage2 = findViewById(R.id.editImage2);
        editImage3 = findViewById(R.id.editImage3);
        includePhotoTitleComment = findViewById(R.id.includePhotoTitleComment);
        imgCaptureImage = includePhotoTitleComment.findViewById(R.id.imgCaptureImage);
        edtTitle = includePhotoTitleComment.findViewById(R.id.edtTitle);
        edtComment = includePhotoTitleComment.findViewById(R.id.edtComment);
        txtCancelPhoto = includePhotoTitleComment.findViewById(R.id.txtCancelPhoto);
        txtUse = includePhotoTitleComment.findViewById(R.id.txtUse);
        includeSeePhoto = findViewById(R.id.includeSeePhoto);
        imgBck = includeSeePhoto.findViewById(R.id.imgBck);
        imgSeeImage = includeSeePhoto.findViewById(R.id.imgSeeImage);
        relProgress = findViewById(R.id.relProgress);

        catchlogComment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        catchlogComment.setRawInputType(InputType.TYPE_CLASS_TEXT);
        edtComment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtComment.setRawInputType(InputType.TYPE_CLASS_TEXT);

        slideIn = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        slideOut = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);

        backbtn.setOnClickListener(this);
        imgDeleteBtn.setOnClickListener(this);
        relLocation.setOnClickListener(this);
        relDatePick.setOnClickListener(this);
        relStartTimePick.setOnClickListener(this);
        relEndTimePick.setOnClickListener(this);
        txtAddMoreCatches.setOnClickListener(this);
        relLogCatch.setOnClickListener(this);
        rdGroup.setOnCheckedChangeListener(this);
        relShare.setOnClickListener(this);
        layoutPrivate.setOnClickListener(this);
        layoutResearch.setOnClickListener(this);
        layoutPublic.setOnClickListener(this);
        txtCamera.setOnClickListener(this);
        txtGallery.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        relImage1.setOnClickListener(this);
        relImage2.setOnClickListener(this);
        relImage3.setOnClickListener(this);
        txtCancelPhoto.setOnClickListener(this);
        txtUse.setOnClickListener(this);
        imgBck.setOnClickListener(this);

        getMethodAPI();
        speciesCaughtList = new ArrayList<>();

        if (isRecordExist != 1) {
            nav_title.setText("ADD CATCHLOG");
            catchLatitude = Config.latitude;
            catchLongitude = Config.longitude;
            setDateTime();
        }else {
            nav_title.setText("UPDATE CATCHLOG");
        }

        catchlogComment.setOnEditorActionListener(new ExtendedEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){

                    InputMethodManager imm = (InputMethodManager) AddCatchlogActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    View view = AddCatchlogActivity.this.getCurrentFocus();
                    //If no view currently has focus, create a new one, just so we can grab a window token from it
                    if (view == null) {
                        view = new View(AddCatchlogActivity.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    return true;
                }
                else{
                    return false;
                }
            }
        });

        edtComment.setOnEditorActionListener(new ExtendedEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){

                    InputMethodManager imm = (InputMethodManager) AddCatchlogActivity.this.getSystemService(INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    View view = AddCatchlogActivity.this.getCurrentFocus();
                    //If no view currently has focus, create a new one, just so we can grab a window token from it
                    if (view == null) {
                        view = new View(AddCatchlogActivity.this);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    includePhotoTitleComment.setVisibility(View.GONE);
                    if(file == null){
                        photoNumber = numberPosition;
                    }

                    if (photoNumber == 1) {
                        titleCommentImage1Layout.setVisibility(View.VISIBLE);
                        if (file != null) {
                            titleCommentBean = new CatchLogCaptureBean();
                            titleCommentBean.imageNumber = photoNumber;
                            titleCommentBean.title = edtTitle.getText().toString();
                            titleCommentBean.comments = edtComment.getText().toString();
                            titleCommentBean.imageLocation = file.toString();
                            capturePhotoList.add(titleCommentBean);
                            photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            imgCapImage1.setImageBitmap(photoBitmap);
                            imgCapImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    includeSeePhoto.setVisibility(View.VISIBLE);
                                    scrollView.setVisibility(View.GONE);
                                    Glide.with(AddCatchlogActivity.this)
                                            .load(capturePhotoList.get(0).imageLocation)
                                            .into(imgSeeImage);
                                }
                            });
                        } else {
                            capturePhotoList.remove(0);
                            titleCommentBean = new CatchLogCaptureBean();
                            titleCommentBean.imageNumber = photoNumber;
                            titleCommentBean.title = edtTitle.getText().toString();
                            titleCommentBean.comments = edtComment.getText().toString();
                            titleCommentBean.imageLocation = catchLogBean.catchLogImageList.get(0).url;
                            capturePhotoList.add(0, titleCommentBean);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(capturePhotoList.get(0).imageLocation)
                                    .into(imgCapImage1);
                            imgCapImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    includeSeePhoto.setVisibility(View.VISIBLE);
                                    scrollView.setVisibility(View.GONE);
                                    Glide.with(AddCatchlogActivity.this)
                                            .load(capturePhotoList.get(0).imageLocation)
                                            .into(imgSeeImage);
                                }
                            });
                        }
                        titleImage1.setText(capturePhotoList.get(0).title);
                        commentImage1.setText(capturePhotoList.get(0).comments);
                        relImage2.setVisibility(View.VISIBLE);
                        editImage1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                numberPosition = 1;
                                includePhotoTitleComment.setVisibility(View.VISIBLE);
                                edtTitle.setText(capturePhotoList.get(0).title);
                                edtComment.setText(capturePhotoList.get(0).comments);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(0).imageLocation)
                                        .into(imgCaptureImage);
                            }
                        });
                    } else if (photoNumber == 2) {
                        relImage3.setVisibility(View.VISIBLE);
                        titleCommentImage2Layout.setVisibility(View.VISIBLE);
                        if (file != null) {
                            titleCommentBean = new CatchLogCaptureBean();
                            titleCommentBean.imageNumber = photoNumber;
                            titleCommentBean.title = edtTitle.getText().toString();
                            titleCommentBean.comments = edtComment.getText().toString();
                            titleCommentBean.imageLocation = file.toString();
                            capturePhotoList.add(titleCommentBean);
                            photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            imgCapImage2.setImageBitmap(photoBitmap);
                            imgCapImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    includeSeePhoto.setVisibility(View.VISIBLE);
                                    scrollView.setVisibility(View.GONE);
                                    Glide.with(AddCatchlogActivity.this)
                                            .load(capturePhotoList.get(1).imageLocation)
                                            .into(imgSeeImage);
                                }
                            });
                        } else {
                            capturePhotoList.remove(1);
                            titleCommentBean = new CatchLogCaptureBean();
                            titleCommentBean.imageNumber = photoNumber;
                            titleCommentBean.title = edtTitle.getText().toString();
                            titleCommentBean.comments = edtComment.getText().toString();
                            titleCommentBean.imageLocation = catchLogBean.catchLogImageList.get(1).url;
                            capturePhotoList.add(1, titleCommentBean);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(capturePhotoList.get(1).imageLocation)
                                    .into(imgCapImage2);
                            imgCapImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    includeSeePhoto.setVisibility(View.VISIBLE);
                                    scrollView.setVisibility(View.GONE);
                                    Glide.with(AddCatchlogActivity.this)
                                            .load(capturePhotoList.get(1).imageLocation)
                                            .into(imgSeeImage);
                                }
                            });
                        }
                        titleImage2.setText(capturePhotoList.get(1).title);
                        commentImage2.setText(capturePhotoList.get(1).comments);
                        editImage2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                numberPosition = 2;
                                includePhotoTitleComment.setVisibility(View.VISIBLE);
                                edtTitle.setText(capturePhotoList.get(1).title);
                                edtComment.setText(capturePhotoList.get(1).comments);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(1).imageLocation)
                                        .into(imgCaptureImage);
                            }
                        });
                    } else if (photoNumber == 3) {
                        titleCommentImage3Layout.setVisibility(View.VISIBLE);
                        if (file != null) {
                            titleCommentBean = new CatchLogCaptureBean();
                            titleCommentBean.imageNumber = photoNumber;
                            titleCommentBean.title = edtTitle.getText().toString();
                            titleCommentBean.comments = edtComment.getText().toString();
                            titleCommentBean.imageLocation = file.toString();
                            capturePhotoList.add(titleCommentBean);
                            photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            imgCapImage3.setImageBitmap(photoBitmap);
                            imgCapImage3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    includeSeePhoto.setVisibility(View.VISIBLE);
                                    scrollView.setVisibility(View.GONE);
                                    Glide.with(AddCatchlogActivity.this)
                                            .load(capturePhotoList.get(2).imageLocation)
                                            .into(imgSeeImage);
                                }
                            });
                        } else {
                            capturePhotoList.remove(2);
                            titleCommentBean = new CatchLogCaptureBean();
                            titleCommentBean.imageNumber = photoNumber;
                            titleCommentBean.title = edtTitle.getText().toString();
                            titleCommentBean.comments = edtComment.getText().toString();
                            titleCommentBean.imageLocation = catchLogBean.catchLogImageList.get(2).url;
                            capturePhotoList.add(2, titleCommentBean);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(capturePhotoList.get(2).imageLocation)
                                    .into(imgCapImage3);
                            imgCapImage3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    includeSeePhoto.setVisibility(View.VISIBLE);
                                    scrollView.setVisibility(View.GONE);
                                    Glide.with(AddCatchlogActivity.this)
                                            .load(capturePhotoList.get(2).imageLocation)
                                            .into(imgSeeImage);
                                }
                            });
                        }
                        titleImage3.setText(capturePhotoList.get(2).title);
                        commentImage3.setText(capturePhotoList.get(2).comments);
                        editImage3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                numberPosition = 3;
                                includePhotoTitleComment.setVisibility(View.VISIBLE);
                                edtTitle.setText(capturePhotoList.get(2).title);
                                edtComment.setText(capturePhotoList.get(2).comments);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(2).imageLocation)
                                        .into(imgCaptureImage);
                            }
                        });
                    }

                    edtTitle.setText("");
                    edtComment.setText("");
                    imgCaptureImage.setImageBitmap(null);

                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

    public String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void generateExistingData(CatchLogBean catchLogBean) throws IOException {
        this.catchLogBean = catchLogBean;
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        Date d = new Date(Long.parseLong(catchLogBean.startDate));
        c.setTime(d);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        sHours = c.get(Calendar.HOUR_OF_DAY);
        sMinutes = c.get(Calendar.MINUTE);

        existingYear = year;
        existingMonth = month;
        existingDay = day;
        existingSHours = sHours;
        existingSMinutes = sMinutes;


        Calendar c2 = Calendar.getInstance(TimeZone.getDefault());
        c2.setTimeInMillis(Long.parseLong(catchLogBean.endDate));
        c2.getTime();
        eHours = c2.get(Calendar.HOUR_OF_DAY);
        eMinutes = c2.get(Calendar.MINUTE);

        existingEHours = eHours;
        existingEMinutes = eMinutes;

        isBoatSelected = catchLogBean.fromBoat;
        catchLatitude = catchLogBean.location.lat;
        catchLongitude = catchLogBean.location.lon;

        txtDate.setText(getDate(Long.parseLong(catchLogBean.startDate), "dd MMM yyyy").replaceAll("\\.", ""));
        txtStartTime.setText(getDate(Long.parseLong(catchLogBean.startDate), "hh:mm a").toLowerCase());
        txtEndTime.setText(getDate(Long.parseLong(catchLogBean.endDate), "hh:mm a").toLowerCase());
        catchlogComment.setText(catchLogBean.comment);

        shareOptionSelection(catchLogBean.privacy);

        if (catchLogBean.fromBoat) {
            rdGroup.check(R.id.rdBoat);
        } else {
            rdGroup.check(R.id.rdShore);
        }
        speciesCaughtList = catchLogBean.speciesCaughtList;
        String[] methodArray;
        for (CatchLogSpeciesCaughtBean bean : speciesCaughtList) {
            if (bean.method.contains(",")) {
                methodArray = bean.method.split(",");
                if (methodArray[0].trim().equals("1")) {
                    bean.isBaitSelcted = true;
                    bean.methodIds.put(methodNames.get(0), "1");
                } else {
                    bean.isLureSelcted = true;
                    bean.methodIds.put(methodNames.get(1), "2");
                }

                if (methodArray[1].trim().equals("2")) {
                    bean.isLureSelcted = true;
                    bean.methodIds.put(methodNames.get(1), "2");
                } else {
                    bean.isBaitSelcted = true;
                    bean.methodIds.put(methodNames.get(0), "1");
                }
            } else {
                if (bean.method.trim().equals("1")) {
                    bean.isBaitSelcted = true;
                    bean.methodIds.put(methodNames.get(0), "1");
                }
                if (bean.method.trim().equals("2")) {
                    bean.isLureSelcted = true;
                    bean.methodIds.put(methodNames.get(1), "2");
                }
            }
        }
        generateCatchesUI();
        int size = catchLogBean.catchLogImageList.size();
        if (size == 1) {

            CatchLogCaptureBean catchLogCaptureBean = new CatchLogCaptureBean();
            catchLogCaptureBean.imageLocation = catchLogBean.catchLogImageList.get(0).url;
            catchLogCaptureBean.title = catchLogBean.catchLogImageList.get(0).photoTitle;
            catchLogCaptureBean.comments = catchLogBean.catchLogImageList.get(0).photoComment;
            capturePhotoList.add(catchLogCaptureBean);

            if (txtShareOptions.getText().toString().trim().equals("Private")) {
                File outPutFile = new File(catchLogBean.catchLogImageList.get(0).url);
                Bitmap bitmap = BitmapFactory.decodeFile(outPutFile.getAbsolutePath());
                photoTitleCommentBitmap = bitmap;
                imgCapImage1.setImageBitmap(bitmap);
                imgCapImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 0) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(0).url)
                                    .into(imgSeeImage);
                        }
                    }
                });
                titleImage1.setText(catchLogBean.catchLogImageList.get(0).photoTitle);
                commentImage1.setText(catchLogBean.catchLogImageList.get(0).photoComment);
                editImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 1;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(0).photoTitle);
                        edtComment.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(0).photoComment);
                        imgCaptureImage.setImageBitmap(photoTitleCommentBitmap);
                    }
                });
            } else {
                Glide.with(this)
                        .load(catchLogBean.catchLogImageList.get(0).url)
                        .into(imgCapImage1);

                imgCapImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 0) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(0).url)
                                    .into(imgSeeImage);
                        }
                    }
                });
                titleImage1.setText(catchLogBean.catchLogImageList.get(0).name);
                commentImage1.setText(catchLogBean.catchLogImageList.get(0).note);
                editImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 1;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(catchLogBean.catchLogImageList.get(0).name);
                        edtComment.setText(catchLogBean.catchLogImageList.get(0).note);
                        Glide.with(AddCatchlogActivity.this)
                                .load(catchLogBean.catchLogImageList.get(0).url)
                                .into(imgCaptureImage);
                    }
                });
            }

            titleCommentImage1Layout.setVisibility(View.VISIBLE);
            relImage1.setEnabled(false);
            relImage2.setVisibility(View.VISIBLE);
        } else if (size == 2) {

            CatchLogCaptureBean catchLogCaptureBean = new CatchLogCaptureBean();
            catchLogCaptureBean.imageLocation = catchLogBean.catchLogImageList.get(0).url;
            catchLogCaptureBean.title = catchLogBean.catchLogImageList.get(0).photoTitle;
            catchLogCaptureBean.comments = catchLogBean.catchLogImageList.get(0).photoComment;
            capturePhotoList.add(catchLogCaptureBean);

            catchLogCaptureBean = new CatchLogCaptureBean();
            catchLogCaptureBean.imageLocation = catchLogBean.catchLogImageList.get(1).url;
            catchLogCaptureBean.title = catchLogBean.catchLogImageList.get(1).photoTitle;
            catchLogCaptureBean.comments = catchLogBean.catchLogImageList.get(1).photoComment;
            capturePhotoList.add(catchLogCaptureBean);

            if (txtShareOptions.getText().toString().trim().equals("Private")) {
                File outPutFile = new File(catchLogBean.catchLogImageList.get(0).url);
                Bitmap bitmap = BitmapFactory.decodeFile(outPutFile.getAbsolutePath());
                photoTitleCommentBitmap = bitmap;
                imgCapImage1.setImageBitmap(bitmap);
                imgCapImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 0) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(0).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                File outPutFile2 = new File(catchLogBean.catchLogImageList.get(1).url);
                Bitmap bitmap2 = BitmapFactory.decodeFile(outPutFile2.getAbsolutePath());
                imgCapImage2.setImageBitmap(bitmap2);
                imgCapImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 1) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(1).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                titleImage1.setText(catchLogBean.catchLogImageList.get(0).photoTitle);
                commentImage1.setText(catchLogBean.catchLogImageList.get(0).photoComment);
                titleImage2.setText(catchLogBean.catchLogImageList.get(1).photoTitle);
                commentImage2.setText(catchLogBean.catchLogImageList.get(1).photoComment);

                editImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 1;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(0).photoTitle);
                        edtComment.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(0).photoComment);
                        imgCaptureImage.setImageBitmap(photoTitleCommentBitmap);
                    }
                });
                editImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 2;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(1).photoTitle);
                        edtComment.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(1).photoComment);
                        imgCaptureImage.setImageBitmap(photoTitleCommentBitmap);
                    }
                });

            } else {
                Glide.with(this)
                        .load(catchLogBean.catchLogImageList.get(0).url)
                        .into(imgCapImage1);
                imgCapImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 0) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(0).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                Glide.with(this)
                        .load(catchLogBean.catchLogImageList.get(1).url)
                        .into(imgCapImage2);
                imgCapImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 1) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(1).url)
                                    .into(imgSeeImage);
                        }
                    }
                });
                titleImage1.setText(catchLogBean.catchLogImageList.get(0).name);
                commentImage1.setText(catchLogBean.catchLogImageList.get(0).note);
                titleImage2.setText(catchLogBean.catchLogImageList.get(1).name);
                commentImage2.setText(catchLogBean.catchLogImageList.get(1).note);

                editImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 1;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(catchLogBean.catchLogImageList.get(0).name);
                        edtComment.setText(catchLogBean.catchLogImageList.get(0).note);
                        Glide.with(AddCatchlogActivity.this)
                                .load(catchLogBean.catchLogImageList.get(0).url)
                                .into(imgCaptureImage);
                    }
                });
                editImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 2;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(catchLogBean.catchLogImageList.get(1).name);
                        edtComment.setText(catchLogBean.catchLogImageList.get(1).note);
                        Glide.with(AddCatchlogActivity.this)
                                .load(catchLogBean.catchLogImageList.get(1).url)
                                .into(imgCaptureImage);
                    }
                });
            }
            relImage2.setVisibility(View.VISIBLE);
            relImage3.setVisibility(View.VISIBLE);
            relImage1.setEnabled(false);
            relImage2.setEnabled(false);

            titleCommentImage1Layout.setVisibility(View.VISIBLE);
            titleCommentImage2Layout.setVisibility(View.VISIBLE);

        } else if (size == 3) {

            CatchLogCaptureBean catchLogCaptureBean = new CatchLogCaptureBean();
            catchLogCaptureBean.imageLocation = catchLogBean.catchLogImageList.get(0).url;
            catchLogCaptureBean.title = catchLogBean.catchLogImageList.get(0).photoTitle;
            catchLogCaptureBean.comments = catchLogBean.catchLogImageList.get(0).photoComment;
            capturePhotoList.add(catchLogCaptureBean);

            catchLogCaptureBean = new CatchLogCaptureBean();
            catchLogCaptureBean.imageLocation = catchLogBean.catchLogImageList.get(1).url;
            catchLogCaptureBean.title = catchLogBean.catchLogImageList.get(1).photoTitle;
            catchLogCaptureBean.comments = catchLogBean.catchLogImageList.get(1).photoComment;
            capturePhotoList.add(catchLogCaptureBean);

            catchLogCaptureBean = new CatchLogCaptureBean();
            catchLogCaptureBean.imageLocation = catchLogBean.catchLogImageList.get(2).url;
            catchLogCaptureBean.title = catchLogBean.catchLogImageList.get(2).photoTitle;
            catchLogCaptureBean.comments = catchLogBean.catchLogImageList.get(2).photoComment;
            capturePhotoList.add(catchLogCaptureBean);

            if (txtShareOptions.getText().toString().trim().equals("Private")) {
                // Image 1
                File outPutFile = new File(catchLogBean.catchLogImageList.get(0).url);
                Bitmap bitmap = BitmapFactory.decodeFile(outPutFile.getAbsolutePath());
                photoTitleCommentBitmap = bitmap;
                imgCapImage1.setImageBitmap(bitmap);
                imgCapImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 0) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(0).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                // Image 2
                File outPutFile2 = new File(catchLogBean.catchLogImageList.get(1).url);
                Bitmap bitmap2 = BitmapFactory.decodeFile(outPutFile2.getAbsolutePath());
                imgCapImage2.setImageBitmap(bitmap2);
                imgCapImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 1) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(1).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                // Image 3
                File outPutFile3 = new File(catchLogBean.catchLogImageList.get(2).url);
                Bitmap bitmap3 = BitmapFactory.decodeFile(outPutFile3.getAbsolutePath());
                imgCapImage3.setImageBitmap(bitmap3);
                imgCapImage3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 2) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(2).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                titleImage1.setText(catchLogBean.catchLogImageList.get(0).photoTitle);
                commentImage1.setText(catchLogBean.catchLogImageList.get(0).photoComment);
                titleImage2.setText(catchLogBean.catchLogImageList.get(1).photoTitle);
                commentImage2.setText(catchLogBean.catchLogImageList.get(1).photoComment);
                titleImage3.setText(catchLogBean.catchLogImageList.get(2).photoTitle);
                commentImage3.setText(catchLogBean.catchLogImageList.get(2).photoComment);

                editImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 1;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(0).photoTitle);
                        edtComment.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(0).photoComment);
                        imgCaptureImage.setImageBitmap(photoTitleCommentBitmap);
                    }
                });
                editImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 2;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(1).photoTitle);
                        edtComment.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(1).photoComment);
                        imgCaptureImage.setImageBitmap(photoTitleCommentBitmap);
                    }
                });
                editImage3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 3;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(2).photoTitle);
                        edtComment.setText(AddCatchlogActivity.this.catchLogBean.catchLogImageList.get(2).photoComment);
                        imgCaptureImage.setImageBitmap(photoTitleCommentBitmap);
                    }
                });

            } else {
                Glide.with(this)
                        .load(catchLogBean.catchLogImageList.get(0).url)
                        .into(imgCapImage1);
                imgCapImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 0) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(0).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                Glide.with(this)
                        .load(catchLogBean.catchLogImageList.get(1).url)
                        .into(imgCapImage2);
                imgCapImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 1) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(1).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                Glide.with(this)
                        .load(catchLogBean.catchLogImageList.get(2).url)
                        .into(imgCapImage3);
                imgCapImage3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (existCatchLogBean != null && existCatchLogBean.catchLogImageList.size() > 2) {
                            includeSeePhoto.setVisibility(View.VISIBLE);
                            scrollView.setVisibility(View.GONE);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(existCatchLogBean.catchLogImageList.get(2).url)
                                    .into(imgSeeImage);
                        }
                    }
                });

                titleImage1.setText(catchLogBean.catchLogImageList.get(0).name);
                commentImage1.setText(catchLogBean.catchLogImageList.get(0).note);
                titleImage2.setText(catchLogBean.catchLogImageList.get(1).name);
                commentImage2.setText(catchLogBean.catchLogImageList.get(1).note);
                titleImage3.setText(catchLogBean.catchLogImageList.get(2).name);
                commentImage3.setText(catchLogBean.catchLogImageList.get(2).note);

                editImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 1;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(catchLogBean.catchLogImageList.get(0).name);
                        edtComment.setText(catchLogBean.catchLogImageList.get(0).note);
                        Glide.with(AddCatchlogActivity.this)
                                .load(catchLogBean.catchLogImageList.get(0).url)
                                .into(imgCaptureImage);
                    }
                });
                editImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 2;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(catchLogBean.catchLogImageList.get(1).name);
                        edtComment.setText(catchLogBean.catchLogImageList.get(1).note);
                        Glide.with(AddCatchlogActivity.this)
                                .load(catchLogBean.catchLogImageList.get(1).url)
                                .into(imgCaptureImage);
                    }
                });
                editImage3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numberPosition = 3;
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        edtTitle.setText(catchLogBean.catchLogImageList.get(2).name);
                        edtComment.setText(catchLogBean.catchLogImageList.get(2).note);
                        Glide.with(AddCatchlogActivity.this)
                                .load(catchLogBean.catchLogImageList.get(2).url)
                                .into(imgCaptureImage);
                    }
                });
            }
            relImage2.setVisibility(View.VISIBLE);
            relImage3.setVisibility(View.VISIBLE);
            relImage1.setEnabled(false);
            relImage2.setEnabled(false);
            relImage3.setEnabled(false);

            titleCommentImage1Layout.setVisibility(View.VISIBLE);
            titleCommentImage2Layout.setVisibility(View.VISIBLE);
            titleCommentImage3Layout.setVisibility(View.VISIBLE);
        }
        relShare.setEnabled(false);
        txtLogCatch.setText("Update Catch Log");
        imgDeleteBtn.setVisibility(View.VISIBLE);
    }

    private JSONObject createOfflineJson(long currentTime, int catchId) throws JSONException {
        JSONObject mainJson = new JSONObject();
        String startDate;
        String endDate;

        if (isDateSelected) {
            startDate = changeDateFormat(day + "-" + month + "-" + year + " " + sHours + ":" + sMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
            endDate = changeDateFormat(day + "-" + month + "-" + year + " " + eHours + ":" + eMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
        } else {
            startDate = changeDateFormat(day + "-" + ((month == existingMonth)? existingMonth : (month + 1)) + "-" + year + " " + sHours + ":" + sMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
            endDate = changeDateFormat(day + "-" + ((month == existingMonth)? existingMonth : (month + 1)) + "-" + year + " " + eHours + ":" + eMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
        }

        long startMillis = dateStringToMilliseconds(startDate);
        long endMillis = dateStringToMilliseconds(endDate);

        mainJson.put("id", catchId);
        mainJson.put("userId", Integer.parseInt(ModelManager.getInstance().getLoginResponse().getUserId()));
        mainJson.put("startDate", String.valueOf(startMillis).trim());
        mainJson.put("endDate", String.valueOf(endMillis).trim());
        mainJson.put("hasLicense", 1);
        mainJson.put("createdDate", currentTime);
        mainJson.put("lastModifiedDate", currentTime);
        // mainJson.put("postCode", "2000");
        mainJson.put("comment", catchlogComment.getText().toString().trim());

        if (isBoatSelected)
            mainJson.put("fromBoat", 1);
        else
            mainJson.put("fromBoat", 0);


        if (capturePhotoList.size() > 0)
            mainJson.put("imagePath", capturePhotoList.get(0).imageLocation);
        else
            mainJson.put("imagePath", "");

        JSONObject locationJson = new JSONObject();
        locationJson.put("lat", "" + catchLatitude);
        locationJson.put("lon", "" + catchLongitude);
        locationJson.put("name", txtLocationName.getText().toString().trim());
        locationJson.put("createdDate", currentTime);
        locationJson.put("lastModifiedDate", currentTime);
        mainJson.put("location", locationJson);

        JSONArray speciesCaughtJsonArray = new JSONArray();
        JSONObject speciesCaughtJson;
        CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean;

        CatchLogSpeciesCaughtMeasureBean measurementBean;
        JSONObject measurementJson;
        JSONArray measurementJsonArray;

        for (int i = 0; i < speciesCaughtList.size(); i++) {
            catchLogSpeciesCaughtBean = speciesCaughtList.get(i);
            speciesCaughtJson = new JSONObject();
            speciesCaughtJson.put("species", catchLogSpeciesCaughtBean.species);
            speciesCaughtJson.put("method", catchLogSpeciesCaughtBean.methodIds.values().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            speciesCaughtJson.put("kept", catchLogSpeciesCaughtBean.kept);
            speciesCaughtJson.put("released", catchLogSpeciesCaughtBean.released);

            measurementJsonArray = new JSONArray();
            for (int j = 0; j < speciesCaughtList.get(i).measurements.size(); j++) {
                measurementJson = new JSONObject();
                measurementBean = speciesCaughtList.get(i).measurements.get(j);
                measurementJson.put("length", measurementBean.length);
                measurementJson.put("weight", measurementBean.weight);
                measurementJson.put("kept", measurementBean.isKept);
                measurementJsonArray.put(measurementJson);
            }
            speciesCaughtJson.put("measurements", measurementJsonArray);
            speciesCaughtJsonArray.put(speciesCaughtJson);
        }
        mainJson.put("speciesCaughtList", speciesCaughtJsonArray);

        JSONArray photoCapture = new JSONArray();
        JSONObject photoCaptureJson;
        CatchLogCaptureBean catchLogCaptureBean;
        for (int i = 0; i < capturePhotoList.size(); i++) {
            catchLogCaptureBean = capturePhotoList.get(i);
            photoCaptureJson = new JSONObject();
            photoCaptureJson.put("imageloc", catchLogCaptureBean.imageLocation);
            photoCaptureJson.put("comment", catchLogCaptureBean.comments);
            photoCaptureJson.put("title", catchLogCaptureBean.title);
            photoCapture.put(photoCaptureJson);
        }
        mainJson.put("imageCaptureList", photoCapture);
        mainJson.put("privacy", whichPrivacy);
        Log.i("TestOfflineJsonData", mainJson.toString());
        return mainJson;
    }

    private JsonObject createJson() {
        JsonObject mainJson = new JsonObject();
        String startDate;
        String endDate;
        if (isDateSelected) {
            startDate = changeDateFormat(((day == existingDay)? existingDay - 1 : (day - 1)) + "-" + (month) + "-" + year + " " + ((sHours == existingSHours)? existingSHours + 14 : (sHours + 14)) + ":" + sMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
            endDate = changeDateFormat(((day == existingDay)? existingDay - 1 : (day - 1)) + "-" + (month) + "-" + year + " " + ((eHours == existingEHours)? existingEHours + 14 : (eHours + 14)) + ":" + eMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
        } else {
            startDate = changeDateFormat(((day == existingDay)? existingDay - 1 : (day - 1)) + "-" + ((month == existingMonth)? existingMonth : (month + 1)) + "-" + year + " " + ((sHours == existingSHours)? existingSHours + 14 : (sHours + 14)) + ":" + sMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
            endDate = changeDateFormat(((day == existingDay)? existingDay - 1 : (day - 1)) + "-" + ((month == existingMonth)? existingMonth : (month + 1)) + "-" + year + " " + ((eHours == existingEHours)? existingEHours + 14 : (eHours + 14)) + ":" + eMinutes, "dd-MM-yyyy HH:mm", "yyyy-MM-dd'T'HH:mm:ss'Z'");
        }
        mainJson.addProperty("userId", Integer.parseInt(ModelManager.getInstance().getLoginResponse().getUserId()));
        mainJson.addProperty("startDate", startDate.trim());
        mainJson.addProperty("endDate", endDate.trim());
        mainJson.addProperty("hasLicense", 1);
        // mainJson.put("postCode", "2000");
        mainJson.addProperty("comment", catchlogComment.getText().toString().trim());
        if (isBoatSelected)
            mainJson.addProperty("fromBoat", 1);
        else
            mainJson.addProperty("fromBoat", 0);

        JsonObject locationJson = new JsonObject();
        locationJson.addProperty("lat", "" + catchLatitude);
        locationJson.addProperty("lon", "" + catchLongitude);
        locationJson.addProperty("name", txtLocationName.getText().toString().trim());
        mainJson.add("location", locationJson);

        JsonArray speciesCaughtJsonArray = new JsonArray();
        JsonObject speciesCaughtJson;
        CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean;

        CatchLogSpeciesCaughtMeasureBean measurementBean;
        JsonObject measurementJson;
        JsonArray measurementJsonArray;
        for (int i = 0; i < speciesCaughtList.size(); i++) {
            catchLogSpeciesCaughtBean = speciesCaughtList.get(i);
            speciesCaughtJson = new JsonObject();
            speciesCaughtJson.addProperty("species", catchLogSpeciesCaughtBean.species);
            speciesCaughtJson.addProperty("method", catchLogSpeciesCaughtBean.methodIds.values().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            speciesCaughtJson.addProperty("released", catchLogSpeciesCaughtBean.released);
            speciesCaughtJson.addProperty("kept", catchLogSpeciesCaughtBean.kept);

            measurementJsonArray = new JsonArray();
            for (int j = 0; j < speciesCaughtList.get(i).measurements.size(); j++) {
                measurementJson = new JsonObject();
                measurementBean = speciesCaughtList.get(i).measurements.get(j);
                measurementJson.addProperty("length", measurementBean.length);
                measurementJson.addProperty("weight", measurementBean.weight);
                measurementJson.addProperty("kept", measurementBean.isKept);
                measurementJsonArray.add(measurementJson);
            }
            speciesCaughtJson.add("measurements", measurementJsonArray);
            speciesCaughtJsonArray.add(speciesCaughtJson);
        }
        mainJson.add("speciesCaughtList", speciesCaughtJsonArray);

        /*CatchLogCaptureBean catchLogImagePathBean;
        catchLogImagePathBean.imageLocation = capturePhotoList.get(0).imageLocation;
        if (capturePhotoList.size() > 0)
            mainJson.add("imagePath", capturePhotoList.get(0).imageLocation);
        else
            mainJson.add("imagePath", "");*/

        /*JsonObject photoCaptureJson;
        JsonArray photoCapture = new JsonArray();;
        CatchLogCaptureBean catchLogCaptureBean;
        for (int i = 0; i < capturePhotoList.size(); i++) {
            catchLogCaptureBean = capturePhotoList.get(i);
            photoCaptureJson = new JsonObject();
            photoCaptureJson.addProperty("imageloc", catchLogCaptureBean.imageLocation);
            photoCaptureJson.addProperty("comment", catchLogCaptureBean.comments);
            photoCaptureJson.addProperty("title", catchLogCaptureBean.title);
            photoCapture.add(photoCaptureJson);
        }
        mainJson.add("imageCaptureList", photoCapture);*/

        Bundle bundle = new Bundle();
        if(whichPrivacy == 0)
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "PRIVATE");
        else if(whichPrivacy == 2)
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "PUBLIC");
        else if(whichPrivacy == 3)
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "RESEARCH");

        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Catch log Type");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mainJson.addProperty("privacy", whichPrivacy);
        Log.i("TestJsonData", mainJson.toString());
        return mainJson;
    }

    private JsonObject createAssignImageToCatchJson(String assignmentId, String imageId) {
        JsonObject mainJson = new JsonObject();
        mainJson.addProperty("assignmentId", assignmentId);
        mainJson.addProperty("imageId", imageId);
        return mainJson;
    }

    private JsonObject createUpdateImageJson(String name, String note) {
        JsonObject mainJson = new JsonObject();
        mainJson.addProperty("name", name);
        mainJson.addProperty("note", note);
        return mainJson;
    }

    ResponseListner responseListner = new ResponseListner() {
        @Override
        public void onResponse(String tag, int result, Object obj) {
            if (result == ResponseConfig.API_SUCCESS) {
                if (tag.equals(ResponseConfig.TAG_GET_CATCHLOG)) {

                } else if (tag.equals(ResponseConfig.TAG_ADD_CATCH_LOG)) {
                    JsonObject jsonObject = (JsonObject) obj;
                    String value = new Gson().toJson(jsonObject);
                    JSONObject parseJson;
                    try {
                        parseJson = new JSONObject(value);
                        assignId = parseJson.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (capturePhotoList.size() > 0) {
                        totalCapturePhotoSize = 0;
                        File file = new File(capturePhotoList.get(totalCapturePhotoSize).imageLocation);
                        uploadImage(file.toString(), file.getName());
                    } else {
                        showToastMessage(true);
                        stopProgress();
                        finish();
                    }
                } else if (tag.equals(ResponseConfig.TAG_UPLOAD_IMAGE)) {
                    totalCapturePhotoSize++;
                    CatchLogImageBean catchLogImageBean = (CatchLogImageBean) obj;
                    if (photoNumber == 1) {
                        if (!isImage1IdDone && image1Id == 0) {
                            image1Id = catchLogImageBean.id;
                            isImage1IdDone = true;
                        }
                    } else if (photoNumber == 2) {
                        if (!isImage1IdDone && image1Id == 0) {
                            image1Id = catchLogImageBean.id;
                            isImage1IdDone = true;
                        }
                        else if(!isImage2IdDone && image2Id == 0) {
                            image2Id = catchLogImageBean.id;
                            isImage2IdDone = true;
                        }
                    } else if (photoNumber == 3) {
                        if (!isImage1IdDone && image1Id == 0) {
                            image1Id = catchLogImageBean.id;
                            isImage1IdDone = true;
                        }
                        else if(!isImage2IdDone && image2Id == 0) {
                            image2Id = catchLogImageBean.id;
                            isImage2IdDone = true;
                        }
                        else if(!isImage3IdDone && image3Id == 0) {
                            image3Id = catchLogImageBean.id;
                            isImage3IdDone = true;
                        }
                    }
                    if (totalCapturePhotoSize < capturePhotoList.size()) {
                        File file = new File(capturePhotoList.get(totalCapturePhotoSize).imageLocation);
                        uploadImage(file.toString(), file.getName());
                    }else {
                        if(existCatchLogBean != null){
                            if (existCatchLogBean.catchLogImageList.size() == 0) {
                                totalCapturePhotoSize = 0;
                                // Assign image to catch.
                                assignToCatchAPI(String.valueOf(assignId), String.valueOf(image1Id));
                            }else if (existCatchLogBean.catchLogImageList.size() == 1) {
                                totalCapturePhotoSize = 0;
                                // Assign image to catch.
                                assignToCatchAPI(String.valueOf(assignId), String.valueOf(image2Id));
                            }else if (existCatchLogBean.catchLogImageList.size() == 2){
                                // Assign image to catch.
                                totalCapturePhotoSize = 1;
                                assignToCatchAPI(String.valueOf(assignId), String.valueOf(image3Id));
                            }
                        }else {
                            // Assign image to catch.
                            totalCapturePhotoSize = 0;
                            assignToCatchAPI(String.valueOf(assignId), String.valueOf(image1Id));
                        }
                    }
                } else if (tag.equals(ResponseConfig.TAG_ASSIGN_IMAGES_TO_CATCH_LOG)) {
                    totalCapturePhotoSize++;
                    if (totalCapturePhotoSize < capturePhotoList.size()) {
                        if (totalCapturePhotoSize == 1) {
                            if (image2Id != 0) {
                                assignToCatchAPI(String.valueOf(assignId), String.valueOf(image2Id));
                            }
                        } else if (totalCapturePhotoSize == 2) {
                            if (image3Id != 0) {
                                assignToCatchAPI(String.valueOf(assignId), String.valueOf(image3Id));
                            }
                        }
                    } else {
                        if(existCatchLogBean != null){
                            if (existCatchLogBean.catchLogImageList.size() == 0) {
                                totalCapturePhotoSize = 0;
                                // Update image to catch.
                                updateImageAPI(String.valueOf(image1Id), capturePhotoList.get(0).title, capturePhotoList.get(0).comments);
                            }else if (existCatchLogBean.catchLogImageList.size() == 1) {
                                totalCapturePhotoSize = 0;
                                // Update image to catch.
                                updateImageAPI(String.valueOf(image2Id), capturePhotoList.get(1).title, capturePhotoList.get(1).comments);
                            }else if (existCatchLogBean.catchLogImageList.size() == 2){
                                // Update image to catch.
                                totalCapturePhotoSize = 1;
                                updateImageAPI(String.valueOf(image3Id), capturePhotoList.get(2).title, capturePhotoList.get(2).comments);
                            }
                        } else{
                            // Update Image
                            totalCapturePhotoSize = 0;
                            // name , comment
                            updateImageAPI(String.valueOf(image1Id), capturePhotoList.get(totalCapturePhotoSize).title, capturePhotoList.get(totalCapturePhotoSize).comments);
                        }
                    }
                } else if (tag.equals(ResponseConfig.TAG_UPDATE_IMAGE)) {
                    totalCapturePhotoSize++;
                    if (totalCapturePhotoSize < capturePhotoList.size()) {
                        if (totalCapturePhotoSize == 1) {
                            updateImageAPI(String.valueOf(image2Id), capturePhotoList.get(totalCapturePhotoSize).title, capturePhotoList.get(totalCapturePhotoSize).comments);
                        } else if (totalCapturePhotoSize == 2) {
                            updateImageAPI(String.valueOf(image3Id), capturePhotoList.get(totalCapturePhotoSize).title, capturePhotoList.get(totalCapturePhotoSize).comments);
                        }
                    } else {
                        isImage1IdDone = false;
                        isImage2IdDone = false;
                        isImage3IdDone = false;
                        showToastMessage(true);
                        stopProgress();
                        finish();
                    }
                } else if (tag.equals(ResponseConfig.TAG_DELETE_CATCH)) {
                    stopProgress();
                    finish();
                } else if (tag.equals(ResponseConfig.TAG_UPDATE_CATCH)) {
                    if (capturePhotoList.size() > 0) {
                        assignId = String.valueOf(existCatchLogBean.id);
                        totalCapturePhotoSize = 0;
                        if(existCatchLogBean != null){
                            if (existCatchLogBean.catchLogImageList.size() == 0 && capturePhotoList.size() > 0) {
                                totalCapturePhotoSize = 0;
                                File file = new File(capturePhotoList.get(0).imageLocation);
                                uploadImage(file.toString(), file.getName());
                            }else if (existCatchLogBean.catchLogImageList.size() == 1 && capturePhotoList.size() > 1) {
                                image1Id = existCatchLogBean.catchLogImageList.get(0).id;
                                totalCapturePhotoSize = 1;
                                File file = new File(capturePhotoList.get(totalCapturePhotoSize).imageLocation);
                                uploadImage(file.toString(), file.getName());
                            }else if (existCatchLogBean.catchLogImageList.size() == 2 && capturePhotoList.size() > 2){
                                image1Id = existCatchLogBean.catchLogImageList.get(0).id;
                                image2Id = existCatchLogBean.catchLogImageList.get(1).id;
                                totalCapturePhotoSize = 2;
                                File file = new File(capturePhotoList.get(totalCapturePhotoSize).imageLocation);
                                uploadImage(file.toString(), file.getName());
                            }else{
                                if (existCatchLogBean.catchLogImageList.size() == capturePhotoList.size()) {
                                    for (int i = 0; i < capturePhotoList.size(); i++) {
                                        if(capturePhotoList.get(i).title == null && existCatchLogBean.catchLogImageList.get(i).name != null){
                                            capturePhotoList.get(i).title = existCatchLogBean.catchLogImageList.get(i).name;
                                        }else if(capturePhotoList.get(i).title == null && existCatchLogBean.catchLogImageList.get(i).photoTitle != null){
                                            capturePhotoList.get(i).title = existCatchLogBean.catchLogImageList.get(i).photoTitle;
                                        }else if(capturePhotoList.get(i).title == null && existCatchLogBean.catchLogImageList.get(i).name == null && existCatchLogBean.catchLogImageList.get(i).photoTitle == null){
                                            capturePhotoList.get(i).title = "";
                                        }

                                        if(capturePhotoList.get(i).comments == null && existCatchLogBean.catchLogImageList.get(i).note != null){
                                            capturePhotoList.get(i).comments = existCatchLogBean.catchLogImageList.get(i).note;
                                        }else if(capturePhotoList.get(i).comments == null && existCatchLogBean.catchLogImageList.get(i).photoComment != null){
                                            capturePhotoList.get(i).comments = existCatchLogBean.catchLogImageList.get(i).photoComment;
                                        }else if(capturePhotoList.get(i).comments == null && existCatchLogBean.catchLogImageList.get(i).note == null && existCatchLogBean.catchLogImageList.get(i).photoComment == null){
                                            capturePhotoList.get(i).comments = "";
                                        }
                                    }
                                }

                                if (existCatchLogBean.catchLogImageList.size() == 1){
                                    image1Id = existCatchLogBean.catchLogImageList.get(0).id;
                                    updateImageAPI(String.valueOf(image1Id), capturePhotoList.get(totalCapturePhotoSize).title, capturePhotoList.get(totalCapturePhotoSize).comments);
                                }else if (existCatchLogBean.catchLogImageList.size() == 2){
                                    image1Id = existCatchLogBean.catchLogImageList.get(0).id;
                                    image2Id = existCatchLogBean.catchLogImageList.get(1).id;
                                    updateImageAPI(String.valueOf(image1Id), capturePhotoList.get(totalCapturePhotoSize).title, capturePhotoList.get(totalCapturePhotoSize).comments);
                                }else if(existCatchLogBean.catchLogImageList.size() == 3){
                                    image1Id = existCatchLogBean.catchLogImageList.get(0).id;
                                    image2Id = existCatchLogBean.catchLogImageList.get(1).id;
                                    image3Id = existCatchLogBean.catchLogImageList.get(2).id;
                                    updateImageAPI(String.valueOf(image1Id), capturePhotoList.get(totalCapturePhotoSize).title, capturePhotoList.get(totalCapturePhotoSize).comments);
                                }
                            }
                        }

                    } else {
                        showToastMessage(false);
                        stopProgress();
                        finish();
                    }
                } else if (tag.equals(ResponseConfig.TAG_METHOD_TYPES)) {
                    stopProgress();
                    methodsList = (ArrayList<MethodTypesBean>) obj;
                    methodNames = new ArrayList<>();
                    MethodTypesBean methodTypesBean;
                    for (int i = 0; i < methodsList.size(); i++) {
                        methodTypesBean = methodsList.get(i);
                        methodNames.add(methodTypesBean.name);
                        JSONArray jsonArrays;
                        JSONObject jsonObject;
                        JSONObject jsonContent;
                        try {
                            jsonObject = new JSONObject(methodTypesBean.content);
                            jsonArrays = jsonObject.getJSONArray("content");
                            MethodContentBean methodContentBean;
                            for (int j = 0; j < jsonArrays.length(); j++) {
                                methodContentBean = new MethodContentBean();
                                jsonContent = jsonArrays.getJSONObject(j);
                                methodContentBean.key = jsonContent.getString("key");
                                methodContentBean.type = jsonContent.getString("type");
                                methodContentBean.value = jsonContent.getString("value");
                                methodTypesBean.methodContentList.add(methodContentBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    updateData();
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "API_SUCCESS " + result);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tag);
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Add Catchlog");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                }
            } else {
                // Failure message
                showPopUpErrorMessage();
                stopProgress();
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "API_FAIL " + result);
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, tag);
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Add Catchlog");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            }
        }
    };

    private void stopProgress() {
        AddCatchlogActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                relProgress.setVisibility(View.GONE);
            }
        });
    }

    private void uploadImage(String path, String name) {
        if (isNetworkConnected()) {
            new UploadPicAPI(this, path, name, responseListner).execute();
        } else {
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            //
                        }
                    });
        }
    }

    private void addLogCatchAPI() {
        if (isNetworkConnected()) {
            relProgress.setVisibility(View.VISIBLE);
            // RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), createJson().toString());
            NetworkManager.getInstance().addMyCatch(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", createJson(), responseListner);
        } else {
            stopProgress();
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private void updateCatchAPI() {
        if (isNetworkConnected()) {
            relProgress.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().updateCurrentCatch(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", String.valueOf(existCatchLogBean.id), createJson(), responseListner);
        } else {
            stopProgress();
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private void deleteCatchLogAPI() {
        if (isNetworkConnected()) {
            relProgress.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().deleteThisCatch(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", String.valueOf(existCatchLogBean.id), responseListner);
        } else {
            stopProgress();
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private void getMethodAPI() {
        if (isNetworkConnected()) {
            relProgress.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().getMethodsType(responseListner);
        } else {
            stopProgress();
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private void assignToCatchAPI(String assignmentId, String imageId) {
        // assignToCatch
        if (isNetworkConnected()) {
            relProgress.setVisibility(View.VISIBLE);
            com.mobiddiction.fishsmart.util.Log.print("======== assignToCatchAPI Body =======" + createAssignImageToCatchJson(assignmentId, imageId).toString());
            NetworkManager.getInstance().assignImagesToCatch(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", createAssignImageToCatchJson(assignmentId, imageId), responseListner);
        } else {
            stopProgress();
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private void updateImageAPI(String imageId, String name, String note) {
        if (isNetworkConnected()) {
            try {
                relProgress.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), createUpdateImageJson(name, note).toString());
            NetworkManager.getInstance().updateImagesToCatch(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", imageId, createUpdateImageJson(name, note), responseListner);
        } else {
            stopProgress();
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            // Move to back screen
            case R.id.backbtn:
                finish();
                break;
            // Delete the record
            case R.id.imgDeleteBtn:
                AlertDialog.showDialogWithHeaderTwoButton(AddCatchlogActivity.this, "Alert", "Are you sure you want to delete the catchlog?",
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    if (txtShareOptions.getText().toString().trim().equals("Private")) {
                                        // Delete from the cath log table.
                                        CatchLogBll catchLogBll = new CatchLogBll(AddCatchlogActivity.this);
                                        catchLogBll.deleteCatchLog(existCatchLogBean.id);
                                        finish();
                                    } else {
                                        // API call for delete catch log.
                                        deleteCatchLogAPI();
                                    }
                                }
                            }
                        });
                break;
            // Set Current Location
            case R.id.relLocation:
                intent = new Intent(AddCatchlogActivity.this, ChooseLocationFromListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble(EXTRA_LATITUDE, catchLatitude);
                bundle.putDouble(EXTRA_LONGITUDE, catchLongitude);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CHOOSE_LOCATION);
                break;
            // Set Date
            case R.id.relDatePick:
                openDatePicker();
                break;
            // Set Start Time
            case R.id.relStartTimePick:
                openTimePicker(true);
                break;
            // Set End Time
            case R.id.relEndTimePick:
                openTimePicker(false);
                break;
            // Add More Catches
            case R.id.txtAddMoreCatches:
                setHighLightStatus(getResources().getString(R.string.catch_log_validation_msg));
                break;
            case R.id.relShare:
                // Open Bottom Menu
                relBottomSlider.setVisibility(View.VISIBLE);
                relBottomSlider.startAnimation(slideOut);
                relBottomSlider.setBackgroundColor(Color.parseColor("#30000000"));
                break;
            // Add Log Catch
            case R.id.relLogCatch:
                // Check for validation.
                checkValidation("Please fill empty species data.");
                break;
            // Select CatchLog Private
            case R.id.layoutPrivate:
                shareOptionSelection(0);
                break;
            // Select CatchLog Research
            case R.id.layoutResearch:
                shareOptionSelection(3);
                break;
            // Select CatchLog Public
            case R.id.layoutPublic:
                shareOptionSelection(2);
                break;
            case R.id.txtCamera:
                relPhotoSlider.startAnimation(slideIn);
                relPhotoSlider.setVisibility(View.GONE);
                relPhotoSlider.setBackgroundColor(Color.parseColor("#00000000"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    permissions.add(CAMERA);
                    permissions.add(WRITE_EXTERNAL_STORAGE);
                    permissionsToRequest = findUnAskedPermissions(permissions);
                    if (permissionsToRequest.size() > 0) {
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                    } else {
                        launchCameraOption();
                    }
                } else {
                    launchCameraOption();
                }
                break;
            case R.id.txtGallery:
                relPhotoSlider.startAnimation(slideIn);
                relPhotoSlider.setVisibility(View.GONE);
                relPhotoSlider.setBackgroundColor(Color.parseColor("#00000000"));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    permissions.clear();
                    permissions.add(WRITE_EXTERNAL_STORAGE);
                    permissionsToRequest = findUnAskedPermissions(permissions);

                    if (permissionsToRequest.size() > 0) {
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_GALLERY);
                    } else {
                        chooseFromGallery();
                    }
                } else {
                    chooseFromGallery();
                }
                break;
            case R.id.txtCancel:
                relPhotoSlider.startAnimation(slideIn);
                relPhotoSlider.setVisibility(View.GONE);
                relPhotoSlider.setBackgroundColor(Color.parseColor("#00000000"));
                break;
            case R.id.relImage1:
                photoNumber = 1;
                relPhotoSlider.setVisibility(View.VISIBLE);
                relPhotoSlider.startAnimation(slideOut);
                relPhotoSlider.setBackgroundColor(Color.parseColor("#60000000"));
                break;
            case R.id.relImage2:
                photoNumber = 2;
                relPhotoSlider.setVisibility(View.VISIBLE);
                relPhotoSlider.startAnimation(slideOut);
                relPhotoSlider.setBackgroundColor(Color.parseColor("#60000000"));
                break;
            case R.id.relImage3:
                photoNumber = 3;
                relPhotoSlider.setVisibility(View.VISIBLE);
                relPhotoSlider.startAnimation(slideOut);
                relPhotoSlider.setBackgroundColor(Color.parseColor("#60000000"));
                break;
            case R.id.imgBck:
                includeSeePhoto.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                break;
            case R.id.txtCancelPhoto:
                includePhotoTitleComment.setVisibility(View.GONE);
                if (!edtTitle.getText().toString().equals("") || !edtComment.getText().toString().equals("")) {
                    edtTitle.setText("");
                    edtComment.setText("");
                    imgCaptureImage.setImageBitmap(null);
                }
                break;
            case R.id.txtUse:
                includePhotoTitleComment.setVisibility(View.GONE);
                if(file == null){
                    photoNumber = numberPosition;
                }

                if (photoNumber == 1) {
                    titleCommentImage1Layout.setVisibility(View.VISIBLE);
                    if (file != null) {
                        titleCommentBean = new CatchLogCaptureBean();
                        titleCommentBean.imageNumber = photoNumber;
                        titleCommentBean.title = edtTitle.getText().toString();
                        titleCommentBean.comments = edtComment.getText().toString();
                        titleCommentBean.imageLocation = file.toString();
                        capturePhotoList.add(titleCommentBean);
                        photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imgCapImage1.setImageBitmap(photoBitmap);
                        imgCapImage1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                includeSeePhoto.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(0).imageLocation)
                                        .into(imgSeeImage);
                            }
                        });
                        file = null;
                    } else {
                        titleCommentBean = new CatchLogCaptureBean();
                        titleCommentBean.imageNumber = photoNumber;
                        titleCommentBean.title = edtTitle.getText().toString();
                        titleCommentBean.comments = edtComment.getText().toString();
                        if (catchLogBean != null && catchLogBean.catchLogImageList != null && catchLogBean.catchLogImageList.size() >= 1) {
                            titleCommentBean.imageLocation = catchLogBean.catchLogImageList.get(0).url;
                        } else {
                            titleCommentBean.imageLocation = capturePhotoList.get(0).imageLocation;
                        }
                        capturePhotoList.set(0, titleCommentBean);

                        Glide.with(this)
                                .load(capturePhotoList.get(0).imageLocation)
                                .into(imgCapImage1);
                        imgCapImage1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                includeSeePhoto.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(0).imageLocation)
                                        .into(imgSeeImage);
                            }
                        });
                    }
                    titleImage1.setText(capturePhotoList.get(0).title);
                    commentImage1.setText(capturePhotoList.get(0).comments);
                    relImage2.setVisibility(View.VISIBLE);
                    editImage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            numberPosition = 1;
                            includePhotoTitleComment.setVisibility(View.VISIBLE);
                            edtTitle.setText(capturePhotoList.get(0).title);
                            edtComment.setText(capturePhotoList.get(0).comments);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(capturePhotoList.get(0).imageLocation)
                                    .into(imgCaptureImage);
                        }
                    });
                } else if (photoNumber == 2) {
                    relImage3.setVisibility(View.VISIBLE);
                    titleCommentImage2Layout.setVisibility(View.VISIBLE);
                    if (file != null) {
                        titleCommentBean = new CatchLogCaptureBean();
                        titleCommentBean.imageNumber = photoNumber;
                        titleCommentBean.title = edtTitle.getText().toString();
                        titleCommentBean.comments = edtComment.getText().toString();
                        titleCommentBean.imageLocation = file.toString();
                        capturePhotoList.add(titleCommentBean);
                        photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imgCapImage2.setImageBitmap(photoBitmap);
                        imgCapImage2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                includeSeePhoto.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(1).imageLocation)
                                        .into(imgSeeImage);
                            }
                        });
                        file = null;
                    } else {
                        titleCommentBean = new CatchLogCaptureBean();
                        titleCommentBean.imageNumber = photoNumber;
                        titleCommentBean.title = edtTitle.getText().toString();
                        titleCommentBean.comments = edtComment.getText().toString();

                        if (catchLogBean != null && catchLogBean.catchLogImageList != null && catchLogBean.catchLogImageList.size() >= 2) {
                            titleCommentBean.imageLocation = catchLogBean.catchLogImageList.get(1).url;
                        } else {
                            titleCommentBean.imageLocation = capturePhotoList.get(1).imageLocation;
                        }
                        capturePhotoList.set(1, titleCommentBean);

                        Glide.with(this)
                                .load(capturePhotoList.get(1).imageLocation)
                                .into(imgCapImage2);
                        imgCapImage2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                includeSeePhoto.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(1).imageLocation)
                                        .into(imgSeeImage);
                            }
                        });
                    }
                    titleImage2.setText(capturePhotoList.get(1).title);
                    commentImage2.setText(capturePhotoList.get(1).comments);
                    editImage2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            numberPosition = 2;
                            includePhotoTitleComment.setVisibility(View.VISIBLE);
                            edtTitle.setText(capturePhotoList.get(1).title);
                            edtComment.setText(capturePhotoList.get(1).comments);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(capturePhotoList.get(1).imageLocation)
                                    .into(imgCaptureImage);
                        }
                    });
                } else if (photoNumber == 3) {
                    titleCommentImage3Layout.setVisibility(View.VISIBLE);
                    if (file != null) {
                        titleCommentBean = new CatchLogCaptureBean();
                        titleCommentBean.imageNumber = photoNumber;
                        titleCommentBean.title = edtTitle.getText().toString();
                        titleCommentBean.comments = edtComment.getText().toString();
                        titleCommentBean.imageLocation = file.toString();
                        capturePhotoList.add(titleCommentBean);
                        photoBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        imgCapImage3.setImageBitmap(photoBitmap);
                        imgCapImage3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                includeSeePhoto.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(2).imageLocation)
                                        .into(imgSeeImage);
                            }
                        });
                        file = null;
                    } else {
                        titleCommentBean = new CatchLogCaptureBean();
                        titleCommentBean.imageNumber = photoNumber;
                        titleCommentBean.title = edtTitle.getText().toString();
                        titleCommentBean.comments = edtComment.getText().toString();

                        if (catchLogBean != null && catchLogBean.catchLogImageList != null && catchLogBean.catchLogImageList.size() >= 3) {
                            titleCommentBean.imageLocation = catchLogBean.catchLogImageList.get(2).url;
                        } else {
                            titleCommentBean.imageLocation = capturePhotoList.get(2).imageLocation;
                        }
                        capturePhotoList.set(2, titleCommentBean);

                        Glide.with(this)
                                .load(capturePhotoList.get(2).imageLocation)
                                .into(imgCapImage3);
                        imgCapImage3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                includeSeePhoto.setVisibility(View.VISIBLE);
                                scrollView.setVisibility(View.GONE);
                                Glide.with(AddCatchlogActivity.this)
                                        .load(capturePhotoList.get(2).imageLocation)
                                        .into(imgSeeImage);
                            }
                        });
                    }
                    titleImage3.setText(capturePhotoList.get(2).title);
                    commentImage3.setText(capturePhotoList.get(2).comments);
                    editImage3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            numberPosition = 3;
                            includePhotoTitleComment.setVisibility(View.VISIBLE);
                            edtTitle.setText(capturePhotoList.get(2).title);
                            edtComment.setText(capturePhotoList.get(2).comments);
                            Glide.with(AddCatchlogActivity.this)
                                    .load(capturePhotoList.get(2).imageLocation)
                                    .into(imgCaptureImage);
                        }
                    });
                }

                photoNumber = capturePhotoList.size();
                edtTitle.setText("");
                edtComment.setText("");
                imgCaptureImage.setImageBitmap(null);
                break;
        }
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_PICTURE);
    }

    private void shareOptionSelection(int tag) {
        whichPrivacy = tag;
        if (tag == 0) {
            txtShareOptions.setText("Private");
            imgPrivate.setVisibility(View.VISIBLE);
            imgResearch.setVisibility(View.GONE);
            imgPublic.setVisibility(View.GONE);
        } else if (tag == 3) {
            txtShareOptions.setText("Research");
            imgPrivate.setVisibility(View.GONE);
            imgResearch.setVisibility(View.VISIBLE);
            imgPublic.setVisibility(View.GONE);
        } else if (tag == 2) {
            txtShareOptions.setText("Public");
            imgPrivate.setVisibility(View.GONE);
            imgResearch.setVisibility(View.GONE);
            imgPublic.setVisibility(View.VISIBLE);
        }
        relBottomSlider.startAnimation(slideIn);
        relBottomSlider.setVisibility(View.GONE);
        relBottomSlider.setBackgroundColor(Color.parseColor("#00000000"));
    }

    // ============ DATE TIME PICKER RELATED DATA START ================
    private void openDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonthOfYear, int mdayOfMonth) {
                isDateSelected = true;
                year = myear;
                month = (mmonthOfYear + 1);
                day = mdayOfMonth;
                txtDate.setText(changeDateFormat((day + "-" + month + "-" + year), "dd-MM-yyyy", "dd MMM yyyy").replaceAll("\\.", ""));
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void openTimePicker(final boolean isStartTime) {
        TimePickerDialog timePickerDialog;
        if (isStartTime) {
            timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    sHours = hourOfDay;
                    sMinutes = minute;
                    txtStartTime.setText(time12HourFormat(sHours + ":" + sMinutes, true).toLowerCase());
                    // txtStartTime.setText(changeDateFormat((sHours + ":" + sMinutes), "hh:mm", "hh:mm a").toLowerCase());
                }
            }, sHours, sMinutes, false);
        } else {
            timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    eHours = hourOfDay;
                    eMinutes = minute;
                    if (!getTimeDiff(time12HourFormat(sHours + ":" + sMinutes, true).toLowerCase(), time12HourFormat(eHours + ":" + eMinutes, true).toLowerCase(), txtDate.getText().toString().replaceAll("\\.", "")))
                        // txtEndTime.setText(changeDateFormat((eHours + ":" + eMinutes), "hh:mm", "hh:mm a").toLowerCase());
                        txtEndTime.setText(time12HourFormat(eHours + ":" + eMinutes, true).toLowerCase());
                    else {
                        Toast.makeText(getApplicationContext(), "Select proper time", Toast.LENGTH_SHORT).show();
                        txtEndTime.setText("");
                        AlertDialog.showDialogWithAlertHeaderSingleButton(AddCatchlogActivity.this, "Alert", "End date time cannot be past time. Please select future time. ",
                                new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int position) {

                                    }
                                });
                    }
                }
            }, eHours, eMinutes, false);
        }
        timePickerDialog.show();
    }

    public boolean getTimeDiff(String starttime, String endtime, String date) {
        boolean isinDiff = false;
        try {
            if (endtime.length() > 0) {
                Date from = convertStringToDate(date + " " + starttime);
                Date to = convertStringToDate(date + " " + endtime);
                if (from.after(to)) {
                    // Show message as you want.
                    isinDiff = true;
                    return isinDiff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isinDiff;
    }

    public Date convertStringToDate(String myDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        Date d = null;
        try {
            d = sdf.parse(myDate);
        } catch (ParseException ex) {
        }
        return d;
    }

    public String time12HourFormat(String time, boolean isSpace) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdfs;
        if (isSpace) {
            sdfs = new SimpleDateFormat("hh:mm a");
        } else {
            sdfs = new SimpleDateFormat("hh:mma");
        }
        Date dt;
        try {
            dt = sdf.parse(time);
            return sdfs.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void setDateTime() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        sHours = c.get(Calendar.HOUR_OF_DAY);
        sMinutes = c.get(Calendar.MINUTE);
        eHours = c.get(Calendar.HOUR_OF_DAY);
        eMinutes = c.get(Calendar.MINUTE);

        txtDate.setText(changeDateFormat((day + "-" + (month + 1) + "-" + year), "dd-MM-yyyy", "dd MMM yyyy").replaceAll("\\.", ""));
        txtStartTime.setText(changeDateFormat((sHours + ":" + sMinutes), "HH:mm", "hh:mm a").toLowerCase());
        txtEndTime.setText(changeDateFormat((eHours + ":" + eMinutes), "HH:mm", "hh:mm a").toLowerCase());
    }

    private String changeDateFormat(String date, String currentFormat, String parseFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(currentFormat);
        SimpleDateFormat parseDateFormat = new SimpleDateFormat(parseFormat);
        String parsableDate = "";

        try {
            parsableDate = parseDateFormat.format(currentDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsableDate;
    }

    private String millisToDate(long millis, String format) {
        return new SimpleDateFormat(format).format(new Date(millis));
    }

    public Date convertStringToDate(String strDate, String parseFormat) {
        try {
            return new SimpleDateFormat(parseFormat).parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    public long dateStringToMilliseconds(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date mDate = sdf.parse(date);
            long timeInMilliseconds = mDate.getTime();
            return timeInMilliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ============ DATE TIME PICKER RELATED DATA END ================

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        Utils.freeMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(BasicEvent event) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.rdBoat) {
            // Boat Selected
            isBoatSelected = true;
        } else if (checkedId == R.id.rdShore) {
            // Shore Selected
            isBoatSelected = false;
        }
    }

    // ============ ALL MAP RELATED DATA START ================

//    private void setGoogleMapData(double latitude, double longitude) {
//        LatLng latLng;
//        if (latitude == 0.0)
//            latLng = new LatLng(-33.865143, 151.209900);
//        else
//            latLng = new LatLng(latitude, longitude);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
//        googleMap.clear();
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//        googleMap.addMarker(markerOptions);
//
//        new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                        @Override
//                        public boolean onMarkerClick(Marker marker) {
//                            Intent intent = new Intent(AddCatchlogActivity.this, ChooseLocationActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putDouble(EXTRA_LATITUDE, catchLatitude);
//                            bundle.putDouble(EXTRA_LONGITUDE, catchLongitude);
//                            intent.putExtras(bundle);
//                            startActivityForResult(intent, REQUEST_CHOOSE_LOCATION);
//                            return true;
//                        }
//                    });
//            }
//        }, 2000);
//
//        catchLatitude = latLng.latitude;
//        catchLongitude = latLng.longitude;
//        if (latitude == 0.0) {
//            txtLocationName.setText(getAddressFromLatLon(-33.865143, 151.209900).trim());
//            txtLatitude.setText("Latitude: " + -33.865143);
//            txtLongitude.setText("Longitude: " + 151.209900);
//        } else {
//            txtLatitude.setText("Latitude: " + latitude);
//            txtLongitude.setText("Longitude: " + longitude);
//            txtLocationName.setText(getAddressFromLatLon(latitude, longitude).trim());
//        }
//    }
//
//    private void setExistGoogleMapData(){
//        LatLng latLng;
//        latLng = new LatLng(this.existCatchLogBean.location.lat, this.existCatchLogBean.location.lon);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
//        googleMap.clear();
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//        googleMap.addMarker(markerOptions);
//
//        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Intent intent = new Intent(AddCatchlogActivity.this, ChooseLocationActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putDouble(EXTRA_LATITUDE, catchLatitude);
//                bundle.putDouble(EXTRA_LONGITUDE, catchLongitude);
//                intent.putExtras(bundle);
//                startActivityForResult(intent, REQUEST_CHOOSE_LOCATION);
//                return true;
//            }
//        });
//
//        txtLocationName.setText(this.existCatchLogBean.location.name.trim());
//        txtLatitude.setText("Latitude: " + this.existCatchLogBean.location.lat);
//        txtLongitude.setText("Longitude: " + this.existCatchLogBean.location.lon);
//    }
//
    private String getAddressFromLatLon(double LATITUDE, double LONGITUDE) {
        System.out.println("======== Latitude ====" + LATITUDE);
        System.out.println("======== Longitude ====" + LONGITUDE);
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
                Toast.makeText(this, "No Address found for this location.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!strAdd.equals("")){
            return strAdd;
        }else {
            NetworkManager.getInstanceForOceanName("http://api.geonames.org/").getOceanName(LATITUDE, LONGITUDE, new ResponseListner() {
                @Override
                public void onResponse(String tag, int result, Object obj) {
                    if (tag.equals(ResponseConfig.TAG_OCEAN_NAME)) {
                        OceanName oceanNames = (OceanName) obj;
                        if (oceanNames != null) {
                            Ocean ocean = oceanNames.getOcean();
                            if (ocean != null) {
                                oceanName = ocean.getName();
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtLocationName.setText(oceanName);
                            }
                        });
                    }
                }
            });
            return oceanName;
        }

    }

    // ============ ALL MAP RELATED DATA END ================

    // ============ SPECIES RELATED DATA START ================
    private void generateCatchesUI() {
        ImageView imgDelete;
        TextView txtSelectSpecies;
        TextView txtEditDetails;
        TextView catchlogMeasurments;
        //TextView txtSelectMethod;
        CheckBox chkBait;
        CheckBox chkLure;
        View convertView;
        RelativeLayout relKepReleasedDetails;
        RelativeLayout selectSpeciesLayout;
        CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean;
        int index = 0;

        try {
            llMoreCatches.removeAllViews();
            if (speciesCaughtList.size() > 0) {
                for (int i = 0; i < speciesCaughtList.size(); i++) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.row_add_more_catches, null, true);
                    imgDelete = convertView.findViewById(R.id.imgDelete);
                    txtSelectSpecies = convertView.findViewById(R.id.txtSelectSpecies);
                    catchlogMeasurments = convertView.findViewById(R.id.catchlogMeasurments);
                    final EditText mainEdtKept = convertView.findViewById(R.id.mainEdtKept);
                    final EditText mainEdtReleased = convertView.findViewById(R.id.mainEdtReleased);
                    mainEdtKept.setInputType(InputType.TYPE_CLASS_NUMBER);
                    mainEdtReleased.setInputType(InputType.TYPE_CLASS_NUMBER);
                    chkBait = convertView.findViewById(R.id.chkBait);
                    chkLure = convertView.findViewById(R.id.chkLure);
                    txtEditDetails = convertView.findViewById(R.id.txtEditDetails);
                    // txtSelectMethod = (TextView) convertView.findViewById(R.id.txtSelectMethod);
                    relKepReleasedDetails = convertView.findViewById(R.id.relKepReleasedDetails);
                    selectSpeciesLayout = convertView.findViewById(R.id.selectSpeciesLayout);

                    if (i == speciesCaughtList.size()-1) {
                        this.txtSelectSpecies = txtSelectSpecies;
                        this.catchlogMeasurments = catchlogMeasurments;
                        this.mainEdtKept = mainEdtKept;
                        this.mainEdtReleased = mainEdtReleased;
                        this.relKepReleasedDetails = relKepReleasedDetails;
                        this.selectSpeciesLayout = selectSpeciesLayout;


                    }

                    catchLogSpeciesCaughtBean = speciesCaughtList.get(i);

                    txtSelectSpecies.setTag(index);
                    if (catchLogSpeciesCaughtBean.speciesName != null &&
                            !catchLogSpeciesCaughtBean.speciesName.equals("")) {
                        txtSelectSpecies.setText(catchLogSpeciesCaughtBean.speciesName);
                    } else {
                        txtSelectSpecies.setText("Select Species");
                    }

                    txtSelectSpecies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            position = (int) view.getTag();
                            Intent intent = new Intent(AddCatchlogActivity.this, CatchlogSpeciesActivity.class);
                            startActivityForResult(intent, REQUEST_SELECT_SPECIES);
                        }
                    });

                    mainEdtKept.setTag(index);
                    mainEdtKept.addTextChangedListener(new MyTextWatcher(mainEdtKept));
                    if (catchLogSpeciesCaughtBean.kept == 0) {
                        mainEdtKept.setText("");
                    } else {
                        mainEdtKept.setText("" + catchLogSpeciesCaughtBean.kept);
                    }

                    mainEdtReleased.setTag(index);
                    mainEdtReleased.addTextChangedListener(new MyTextWatcher(mainEdtReleased));
                    if (catchLogSpeciesCaughtBean.released == 0) {
                        mainEdtReleased.setText("");
                    } else {
                        mainEdtReleased.setText("" + catchLogSpeciesCaughtBean.released);
                    }

                    relKepReleasedDetails.setTag(index);
                    relKepReleasedDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            position = (int) view.getTag();
                            if (!mainEdtKept.getText().toString().equals("")) {
                                numberInputKept = Integer.parseInt(mainEdtKept.getText().toString());
                            } else {
                                numberInputKept = 0;
                            }
                            if (!mainEdtReleased.getText().toString().equals("")) {
                                numberInputReleased = Integer.parseInt(mainEdtReleased.getText().toString());
                            } else {
                                numberInputReleased = 0;
                            }

                            Intent intent = new Intent(AddCatchlogActivity.this, CatchlogMeasurmentActivity.class);
                            intent.putExtra("getInitialNumberKept", speciesCaughtList.get(position).kept);
                            intent.putExtra("getInitialNumberReleased", speciesCaughtList.get(position).released);
                            intent.putExtra("getNumberInputKept", numberInputKept);
                            intent.putExtra("getNumberInputReleased", numberInputReleased);
                            intent.putExtra("getMeasureList", speciesCaughtList.get(position).measurements);
                            startActivityForResult(intent, REQUEST_SELECT_MEASURE);
                        }
                    });

//                    txtSelectMethod.setTag(index);
//                    txtSelectMethod.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            position = (int) view.getTag();
//                            Intent intent = new Intent(AddCatchlogActivity.this, CatchlogMethodsActivity.class);
//                            intent.putExtra("getMethodList", methodsList);
//                            startActivityForResult(intent, REQUEST_SELECT_METHOD);
//                        }
//                    });

                    if (catchLogSpeciesCaughtBean.isBaitSelcted) {
                        chkBait.setChecked(true);
                    } else {
                        chkBait.setChecked(false);
                    }

                    if (catchLogSpeciesCaughtBean.isLureSelcted) {
                        chkLure.setChecked(true);
                    } else {
                        chkLure.setChecked(false);
                    }

                    txtEditDetails.setTag(index);
                    if (catchLogSpeciesCaughtBean.kept != 0 || catchLogSpeciesCaughtBean.released != 0) {
                        txtEditDetails.setText("Tap to edit measurements");
                    } else {
                        txtEditDetails.setText("Tap to add measurements");
                    }

                    catchlogMeasurments.setTag(index);
                    if (catchLogSpeciesCaughtBean.measurements.size() > 0) {
                        CatchLogSpeciesCaughtMeasureBean mesaureBean;
                        if (measurementDetails.length() > 0) {
                            measurementDetails.delete(0, measurementDetails.length());
                        }

                        for (int j = 0; j < catchLogSpeciesCaughtBean.measurements.size(); j++) {
                            mesaureBean = catchLogSpeciesCaughtBean.measurements.get(j);
                            if (mesaureBean.weight != 0.0 && mesaureBean.length != 0.0 && mesaureBean.isKept) {
                                measurementDetails.append(mesaureBean.weight + " kgs" + " - " + mesaureBean.length + " cms" + "\n");
                            }else if (mesaureBean.weight == 0.0 && mesaureBean.length != 0.0 && mesaureBean.isKept) {
                                measurementDetails.append(mesaureBean.length + " cms" + "\n");
                            }else if (mesaureBean.weight != 0.0 && mesaureBean.length == 0.0 && mesaureBean.isKept) {
                                measurementDetails.append(mesaureBean.weight + " kgs" + "\n");
                            }
                        }

                        for (int j = 0; j < catchLogSpeciesCaughtBean.measurements.size(); j++) {
                            mesaureBean = catchLogSpeciesCaughtBean.measurements.get(j);
                            if (mesaureBean.weight != 0.0 && mesaureBean.length != 0.0 && !mesaureBean.isKept) {
                                measurementDetails.append(mesaureBean.weight + " kgs" + " - " + mesaureBean.length + " cms" + "\n");
                            }else if (mesaureBean.weight == 0.0 && mesaureBean.length != 0.0 && !mesaureBean.isKept) {
                                measurementDetails.append(mesaureBean.length + " cms" + "\n");
                            }else if (mesaureBean.weight != 0.0 && mesaureBean.length == 0.0 && !mesaureBean.isKept) {
                                measurementDetails.append(mesaureBean.weight + " kgs" + "\n");
                            }
                        }
                    }
                    if (measurementDetails.length() > 0) {
                        catchlogMeasurments.setVisibility(View.VISIBLE);
                        txtEditDetails.setPadding(0,0,0,0);
                        catchlogMeasurments.setText(measurementDetails);
                        measurementDetails.delete(0, measurementDetails.length());
                    } else {
                        catchlogMeasurments.setVisibility(View.GONE);
                        txtEditDetails.setPadding(0,48,0,48);
                    }

                    imgDelete.setTag(index);
                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int pos = (int) view.getTag();
                            if (speciesCaughtList.size() != 1) {
                                speciesCaughtList.remove(pos);
                                generateCatchesUI();
                            }
                        }
                    });

                    chkBait.setTag(index);
                    chkBait.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                            int pos = (int) compoundButton.getTag();
                            if (ischecked) {
                                speciesCaughtList.get(pos).isBaitSelcted = true;
                                speciesCaughtList.get(pos).methodIds.put(methodNames.get(0), "1");
                            } else {
                                speciesCaughtList.get(pos).isBaitSelcted = false;
                                speciesCaughtList.get(pos).methodIds.remove(methodNames.get(0));
                            }
                        }
                    });

                    chkLure.setTag(index);
                    chkLure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                            int pos = (int) compoundButton.getTag();
                            if (ischecked) {
                                speciesCaughtList.get(pos).isLureSelcted = true;
                                speciesCaughtList.get(pos).methodIds.put(methodNames.get(1), "2");
                            } else {
                                speciesCaughtList.get(pos).isLureSelcted = false;
                                speciesCaughtList.get(pos).methodIds.remove(methodNames.get(1));
                            }
                        }
                    });
                    llMoreCatches.addView(convertView);
                    index++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private class MyTextWatcher implements TextWatcher {
//        private View view;
//
//        private MyTextWatcher(View view) {
//            this.view = view;
//        }
//
//        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//        }
//
//        public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
//        }
//
//        public void afterTextChanged(Editable editable) {
//            String text = editable.toString();
//            int pos = (int) view.getTag();
//            CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = speciesCaughtList.get(pos);
//
//            switch (view.getId()) {
//                case R.id.edtKept:
//                    if (!text.equals(""))
//                        catchLogSpeciesCaughtBean.kept = Integer.parseInt(text);
//                    break;
//                case R.id.catchlogMeasurments:
//                    if (!text.equals(""))
//                        catchLogSpeciesCaughtBean.released = Integer.parseInt(text);
//                    break;
//            }
//        }
//    }

    private void generateDummyView() {
        CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = new CatchLogSpeciesCaughtBean();
        catchLogSpeciesCaughtBean.speciesName = "Select Species";
        catchLogSpeciesCaughtBean.isSpeciesName = false;
        catchLogSpeciesCaughtBean.kept = 0;
        catchLogSpeciesCaughtBean.released = 0;
        catchLogSpeciesCaughtBean.isMethodSelcted = false;
        speciesCaughtList.add(catchLogSpeciesCaughtBean);
        generateCatchesUI();
    }
    // ============ SPECIES RELATED DATA END ================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_CHOOSE_LOCATION) {
                    String lat = data.getStringExtra("lat").trim();
                    String lon = data.getStringExtra("lon").trim();
//                    String clearLat;
//                    String clearLon;
//                    if (lat.startsWith("-")) {
//                        clearLat = lat.substring(0, Math.min(lat.length(), 10));
//                    } else {
//                        clearLat = lat.substring(0, Math.min(lat.length(), 9));
//                    }
//
//                    if (lon.startsWith("-")) {
//                        clearLon = lon.substring(0, Math.min(lon.length(), 10));
//                    } else {
//                        clearLon = lon.substring(0, Math.min(lon.length(), 9));
//                    }
//                    catchLatitude = Double.parseDouble(lat);
//                    catchLongitude = Double.parseDouble(lon);
//                    setGoogleMapData(catchLatitude, catchLongitude);
                    getAddressFromLatLon(catchLatitude, catchLongitude);
                } else if (requestCode == REQUEST_SELECT_SPECIES) {
                    String id = data.getStringExtra("species_id");
                    String speciesName = data.getStringExtra("species_name");
                    speciesCaughtList.get(position).speciesName = speciesName;
                    speciesCaughtList.get(position).species = id;
                    speciesCaughtList.get(position).isSpeciesName = true;
                    generateCatchesUI();
                } else if (requestCode == REQUEST_SELECT_MEASURE) {
                    ArrayList<CatchLogSpeciesCaughtMeasureBean> measureListGet = (ArrayList<CatchLogSpeciesCaughtMeasureBean>) data.getSerializableExtra("MeasureList");
                    speciesCaughtList.get(position).measurements.clear();
                    speciesCaughtList.get(position).measurements.addAll(measureListGet);

                    speciesCaughtList.get(position).kept = 0;
                    speciesCaughtList.get(position).released = 0;
                    if (speciesCaughtList.get(position).measurements.size() > 0) {
                        CatchLogSpeciesCaughtMeasureBean mesaureBean;
                        for (int i = 0; i < speciesCaughtList.get(position).measurements.size(); i++) {
                            mesaureBean = speciesCaughtList.get(position).measurements.get(i);
                            if (mesaureBean.isKept) {
                                speciesCaughtList.get(position).kept++;
                            } else {
                                speciesCaughtList.get(position).released++;
                            }
                        }
                    }
                    generateCatchesUI();
                } else if (requestCode == REQUEST_SELECT_METHOD) {
                    generateCatchesUI();
                    System.out.println("====== OVER HERE ===========" + position);
                }
                // =========================================== CAMERA ======================
                /*
                 * Camera.REQUEST_TAKE_PHOTO is Library request code.
                 * */
                else if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
                    try {
                        Bitmap bitmap = camera.getCameraBitmap();
                        imgCaptureImage.setImageBitmap(bitmap);
                        includePhotoTitleComment.setVisibility(View.VISIBLE);
                        /**
                         * prepare file to send to backend
                         */
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "JPEG_" + timeStamp + "_";

                        File storageDir = Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES);
                        try {
                            file = File.createTempFile(
                                    imageFileName,  /* prefix */
                                    ".jpg",         /* suffix */
                                    storageDir      /* directory */
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FileOutputStream fos = null;
                        //Convert bitmap to byte array
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        //write the bytes in file
                        try {
                            fos = new FileOutputStream(file);
                            fos.write(bitmapdata);
                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == GALLERY_PICTURE) {
                    FileOutputStream fos = null;
                    imageUri = data.getData();

                    ExifInterface exif = null;
                    try {
                        exif = new ExifInterface(getPaths(this, imageUri));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                    try {
                        bitmap = getThumbnail(imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap rotateBitmap = null;
                    Matrix matrix = new Matrix();
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                        matrix.setRotate(90);
                        rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        this.bitmap = rotateBitmap;
                        imgCaptureImage.setImageBitmap(bitmap);
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                        matrix.setRotate(180);
                        rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        this.bitmap = rotateBitmap;
                        imgCaptureImage.setImageBitmap(bitmap);
                    } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                        matrix.setRotate(270);
                        rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        this.bitmap = rotateBitmap;
                        imgCaptureImage.setImageBitmap(bitmap);
                    } else {
                        imgCaptureImage.setImageBitmap(bitmap);
                    }
                    includePhotoTitleComment.setVisibility(View.VISIBLE);
                    /**
                     * prepare file to send to backend
                     */
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "_";
                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    try {
                        file = File.createTempFile(
                                imageFileName,  /* prefix */
                                ".jpg",         /* suffix */
                                storageDir      /* directory */
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Convert bitmap to byte array
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    //write the bytes in file
                    try {
                        fos = new FileOutputStream(file);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Do nothing for now.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============ PERMISSION RELATED DATA START ================
    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_GALLERY:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), REQUEST_GALLERY);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    txtGallery.performClick();
                }
                break;
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    txtCamera.performClick();
                }
                break;
        }
    }
    // ============ PERMISSION RELATED DATA END ================

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //
    private void checkValidation(String message) {
        if (txtEndTime.getText().toString().trim().equals("")) {
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Alert", "Please select end time",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
        if (!txtSelectSpecies.getText().toString().equals("Select Species") && (!mainEdtKept.getText().toString().equals("") || !mainEdtReleased.getText().toString().equals(""))) {
            selectSpeciesLayout.setBackgroundResource(R.drawable.catchlog_normal_state);
            relKepReleasedDetails.setBackgroundResource(R.drawable.catchlog_normal_state);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, txtShareOptions.getText().toString());
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "PRIVACY");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Catch log Type");
            if (txtLogCatch.getText().toString().trim().equals("Update Catch Log")) {
                if (txtShareOptions.getText().toString().trim().equals("Private")) {
                    // Store in local DB.
                    long millis = System.currentTimeMillis();
                    CatchLogBll catchLogBll = new CatchLogBll(AddCatchlogActivity.this);
                    CatchLogBean catchLogBean = new CatchLogBean();
                    try {
                        catchLogBean.id = existCatchLogBean.id;
                        catchLogBean.myJson = createOfflineJson(System.currentTimeMillis(), catchLogBean.id).toString();
                        catchLogBll.update(catchLogBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showToastMessage(false);
                    finish();
                } else {
                    updateCatchAPI();
                }
            } else {
                if (txtShareOptions.getText().toString().trim().equals("Private")) {
                    // Store in local DB.
                    long millis = System.currentTimeMillis();
                    CatchLogBll catchLogBll = new CatchLogBll(this);
                    CatchLogBean catchLogBean = new CatchLogBean();
                    int maxId = (catchLogBll.getNextId()) + 1;

                    try {
                        catchLogBean.myJson = createOfflineJson(System.currentTimeMillis(), maxId).toString();
                        catchLogBll.insert(catchLogBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showToastMessage(true);
                    finish();
                } else {
                    FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    addLogCatchAPI();
                }
            }
        } else if(!txtSelectSpecies.getText().toString().equals("Select Species") && mainEdtKept.getText().toString().equals("") && mainEdtReleased.getText().toString().equals("")){
            relKepReleasedDetails.setBackgroundResource(R.drawable.catchlog_highlight_state);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "", message,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        } else if(txtSelectSpecies.getText().toString().equals("Select Species") && (!mainEdtKept.getText().toString().equals("") || !mainEdtReleased.getText().toString().equals(""))){
            selectSpeciesLayout.setBackgroundResource(R.drawable.catchlog_highlight_state);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "", message,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        } else {
            selectSpeciesLayout.setBackgroundResource(R.drawable.catchlog_highlight_state);
            relKepReleasedDetails.setBackgroundResource(R.drawable.catchlog_highlight_state);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "", message,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private boolean isEmptySpecies() {
        boolean isEmpty;
        int totalTrue = 0;
        CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean;
        for (int i = 0; i < speciesCaughtList.size(); i++) {
            catchLogSpeciesCaughtBean = speciesCaughtList.get(i);
            if (catchLogSpeciesCaughtBean.isSpeciesName && catchLogSpeciesCaughtBean.isBaitSelcted || catchLogSpeciesCaughtBean.isLureSelcted
                    && (catchLogSpeciesCaughtBean.kept != 0 || catchLogSpeciesCaughtBean.released != 0)) {
                // Validate true
                totalTrue = totalTrue + 1;
            } else {
                totalTrue = totalTrue - 1;
                // validate false
            }
        }

        isEmpty = totalTrue == speciesCaughtList.size();
        return isEmpty;
    }

    // ===================== CAMERA CODE ===========================
    private void launchCameraOption() {
        camera = new Camera.Builder()
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(80)
                .setImageHeight(500)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("fishcatch_" + System.currentTimeMillis())
                .build(this);
        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getThumbnail(Uri uri) throws IOException {
        final int THUMBNAIL_SIZE = 768;
        InputStream input = getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;// optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1)
                || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight
                : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL_SIZE) ? (originalSize / THUMBNAIL_SIZE)
                : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;// optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    public static String getPaths(final Context context, final Uri uri) {

        //check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://<span id=\"IL_AD1\" class=\"IL_AD\">downloads</span>/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private String decodeFile(String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/TMMFOLDER");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            String s = "tmp.png";

            File f = new File(mFolder.getAbsolutePath(), s);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0)
            return 1;
        else
            return k;
    }

    private void setHighLightStatus(String message){
        if (!txtSelectSpecies.getText().toString().equals("Select Species") && (!mainEdtKept.getText().toString().equals("") || !mainEdtReleased.getText().toString().equals(""))) {
            selectSpeciesLayout.setBackgroundResource(R.drawable.catchlog_normal_state);
            relKepReleasedDetails.setBackgroundResource(R.drawable.catchlog_normal_state);
            generateDummyView();
        } else if(!txtSelectSpecies.getText().toString().equals("Select Species") && mainEdtKept.getText().toString().equals("") && mainEdtReleased.getText().toString().equals("")){
            relKepReleasedDetails.setBackgroundResource(R.drawable.catchlog_highlight_state);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "", message,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        } else if(txtSelectSpecies.getText().toString().equals("Select Species") && (!mainEdtKept.getText().toString().equals("") || !mainEdtReleased.getText().toString().equals(""))){
            selectSpeciesLayout.setBackgroundResource(R.drawable.catchlog_highlight_state);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "", message,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        } else {
            selectSpeciesLayout.setBackgroundResource(R.drawable.catchlog_highlight_state);
            relKepReleasedDetails.setBackgroundResource(R.drawable.catchlog_highlight_state);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "", message,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
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
            CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = speciesCaughtList.get(pos);
            int currentNumberKept = catchLogSpeciesCaughtBean.kept;
            int currentNumberReleased = catchLogSpeciesCaughtBean.released;
            int numberInputKept = 0;
            int numberInputReleased = 0;

            switch (view.getId()) {
                case R.id.mainEdtKept:
                    if (!text.equals("") && !text.equals("" + catchLogSpeciesCaughtBean.kept)) {
                        catchLogSpeciesCaughtBean.kept = Integer.parseInt(text);
                        numberInputKept = Integer.parseInt(text);
                        if (currentNumberKept < numberInputKept) {
                            int viewToAdd = numberInputKept - currentNumberKept;
                            for (int i = 0; i < viewToAdd; i++) {
                                CatchLogSpeciesCaughtMeasureBean catchLogSpeciesCaughtMeasureBean = new CatchLogSpeciesCaughtMeasureBean();
                                catchLogSpeciesCaughtMeasureBean.isKept = true;
                                catchLogSpeciesCaughtMeasureBean.weight = 0.0;
                                catchLogSpeciesCaughtMeasureBean.length = 0.0;
                                catchLogSpeciesCaughtBean.measurements.add(currentNumberKept, catchLogSpeciesCaughtMeasureBean);
                            }
                        } else if (currentNumberKept > numberInputKept && numberInputKept != -1) {
                            int viewToRemove = currentNumberKept - numberInputKept;
                            int index = 0;
                            ListIterator<CatchLogSpeciesCaughtMeasureBean> li = catchLogSpeciesCaughtBean.measurements.listIterator(catchLogSpeciesCaughtBean.measurements.size());
                            CatchLogSpeciesCaughtMeasureBean mesaureBean;
                            // Iterate in reverse.
                            while(li.hasPrevious() && index < viewToRemove) {
                                mesaureBean = li.previous();
                                if (mesaureBean.isKept) {
                                    li.remove();
                                    index++;
                                }
                            }
                        }
                    }
                    break;
                case R.id.mainEdtReleased:
                    if (!text.equals("") && !text.equals("" + catchLogSpeciesCaughtBean.released)) {
                        catchLogSpeciesCaughtBean.released = Integer.parseInt(text);
                        numberInputReleased = Integer.parseInt(text);
                        if (currentNumberReleased < numberInputReleased) {
                            int viewToAdd = numberInputReleased - currentNumberReleased;
                            for (int i = 0; i < viewToAdd; i++) {
                                CatchLogSpeciesCaughtMeasureBean catchLogSpeciesCaughtMeasureBean = new CatchLogSpeciesCaughtMeasureBean();
                                catchLogSpeciesCaughtMeasureBean.isKept = false;
                                catchLogSpeciesCaughtMeasureBean.weight = 0.0;
                                catchLogSpeciesCaughtMeasureBean.length = 0.0;
                                catchLogSpeciesCaughtBean.measurements.add(currentNumberKept + currentNumberReleased, catchLogSpeciesCaughtMeasureBean);
                            }
                        } else if (currentNumberReleased > numberInputReleased && numberInputReleased != -1) {
                            int viewToRemove = currentNumberReleased - numberInputReleased;
                            int index = 0;
                            ListIterator<CatchLogSpeciesCaughtMeasureBean> li = catchLogSpeciesCaughtBean.measurements.listIterator(catchLogSpeciesCaughtBean.measurements.size());
                            CatchLogSpeciesCaughtMeasureBean mesaureBean;
                            // Iterate in reverse.
                            while(li.hasPrevious() && index < viewToRemove) {
                                mesaureBean = li.previous();
                                if (!mesaureBean.isKept) {
                                    li.remove();
                                    index++;
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void updateData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isRecordExist == 1) {
                    CatchLogBean catchLogBean = (CatchLogBean) getIntent().getSerializableExtra("CatchLogData");
                    existCatchLogBean = catchLogBean;
                    try {
                        generateExistingData(catchLogBean);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    generateDummyView();
                }

//                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
//                if (mapFragment != null) {
//                    mapFragment.getMapAsync(new OnMapReadyCallback() {
//                        @Override
//                        public void onMapReady(GoogleMap map) {
//                            googleMap = map;
//                            googleMap.getUiSettings().setAllGesturesEnabled(false);
//                            if (isRecordExist == 1) {
//                                setExistGoogleMapData();
//                            } else {
//                                setGoogleMapData(Config.latitude, Config.longitude);
//                            }
//
//                            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                                @Override
//                                public void onMapClick(LatLng latLng) {
//                                    Intent intent = new Intent(AddCatchlogActivity.this, ChooseLocationActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putDouble(EXTRA_LATITUDE, catchLatitude);
//                                    bundle.putDouble(EXTRA_LONGITUDE, catchLongitude);
//                                    intent.putExtras(bundle);
//                                    startActivityForResult(intent, REQUEST_CHOOSE_LOCATION);
//                                }
//                            });
//                        }
//                    });
//
//                    if (mapFragment.getView() != null) {
//                        mapFragment.getView().setClickable(false);
//                    }
//                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (relBottomSlider.getVisibility() == View.VISIBLE) {
            relBottomSlider.startAnimation(slideIn);
            relBottomSlider.setVisibility(View.GONE);
            relBottomSlider.setBackgroundColor(Color.parseColor("#00000000"));
        }else if(includePhotoTitleComment.getVisibility() == View.VISIBLE){
            includePhotoTitleComment.setVisibility(View.GONE);
        }else{
            finish();
        }
    }

    private void showPopUpErrorMessage(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddCatchlogActivity.this);
                builder.setMessage("Sorry! There seems to be an error, please try again.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, final int id) {
                                finish();
                            }
                        });
                final androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void showToastMessage(boolean isAdd){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isAdd) {
                    Toast.makeText(AddCatchlogActivity.this, "Catchlog Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddCatchlogActivity.this, "Catchlog Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}