package com.mobiddiction.fishsmart.Profile;

import android.app.Activity;
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
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mindorks.paracamera.Camera;
import com.mobiddiction.fishsmart.CustomAlert.AlertDialog;
import com.mobiddiction.fishsmart.CustomAlert.OnItemClickListener;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.Network.NetworkManager;
import com.mobiddiction.fishsmart.Network.ResponseConfig;
import com.mobiddiction.fishsmart.Network.ResponseListner;
import com.mobiddiction.fishsmart.Network.UploadPicAPI;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.bean.CatchLogImageBean;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.util.Log;
import com.mobiddiction.fishsmart.util.ScalingUtilities;
import com.mobiddiction.fishsmart.util.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Krunal on 12/12/2018.
 */
public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backbtn;
    private RelativeLayout relProfilePhoto;
    private ImageView imgPlaceHolder;
    private ImageView imgProfilePhoto;
    private EditText edtFirstname;
    private EditText edtLastname;
    private ImageView edtFirstnameImg;
    private ImageView edtLastnameImg;
    private EditText edtEmail;
    private TextView txtUpdate;
    private RelativeLayout relProgressBar;
    private UserBean userBean;

    //
    private RelativeLayout relPhotoSlider;
    private TextView txtCamera;
    private TextView txtGallery;
    private TextView txtCancel;

    private Animation slideIn;
    private Animation slideOut;

    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private final static int ALL_PERMISSIONS_RESULT = 107;

    // Camera Code
    private Camera camera;
    private Uri imageUri;
    File file;
    private Bitmap bitmap;
    private static final int GALLERY_PICTURE = 100;
    private static final int REQUEST_GALLERY = 200;

    private String assignId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        Typeface font = Typeface.createFromAsset(getAssets(), "Bariol_Regular.otf");
        Typeface typefaceBold = Typeface.createFromAsset(getAssets(), "Bariol_Bold.otf");
        this.userBean = (UserBean) getIntent().getSerializableExtra("PROFILE_DATA");

        backbtn = findViewById(R.id.backbtn);
        relProfilePhoto = findViewById(R.id.relProfilePhoto);
        imgPlaceHolder = findViewById(R.id.imgPlaceHolder);
        imgProfilePhoto = findViewById(R.id.imgProfilePhoto);
        edtFirstname = findViewById(R.id.edtFirstname);
        edtLastname = findViewById(R.id.edtLastname);
        edtFirstnameImg = findViewById(R.id.edit_first_name_img);
        edtLastnameImg = findViewById(R.id.edit_last_name_img);
        edtEmail = findViewById(R.id.edtEmail);
        txtUpdate = findViewById(R.id.txtUpdate);
        relProgressBar = findViewById(R.id.relProgressBar);

        relPhotoSlider = findViewById(R.id.relPhotoSlider);
        txtCamera = findViewById(R.id.txtCamera);
        txtGallery = findViewById(R.id.txtGallery);
        txtCancel = findViewById(R.id.txtCancel);

        slideIn = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        slideOut = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);

        edtFirstname.setTypeface(font);
        edtLastname.setTypeface(font);
        edtEmail.setTypeface(font);
        txtUpdate.setTypeface(font);

        txtCamera.setTypeface(typefaceBold);
        txtGallery.setTypeface(typefaceBold);
        txtCancel.setTypeface(typefaceBold);

        edtFirstnameImg.setOnClickListener(this);
        edtLastnameImg.setOnClickListener(this);
        relProfilePhoto.setOnClickListener(this);
        backbtn.setOnClickListener(this);
        txtUpdate.setOnClickListener(this);

        txtCamera.setOnClickListener(this);
        txtGallery.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        setProfileData(this.userBean);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.backbtn:
                finish();
                break;
            case R.id.relProfilePhoto:
                relPhotoSlider.setVisibility(View.VISIBLE);
                relPhotoSlider.startAnimation(slideOut);
                relPhotoSlider.setBackgroundColor(Color.parseColor("#60000000"));
                break;
            case R.id.txtUpdate:
                String validate = validation();
                if (validate == null) {
                    updateProfileAPI();

                } else {
                    AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Alert", validate,
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
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
            case R.id.edit_first_name_img:
                edtFirstname.setEnabled(true);
                edtLastname.setEnabled(false);
                break;
            case R.id.edit_last_name_img:
                edtLastname.setEnabled(true);
                edtFirstname.setEnabled(false);
                break;
        }
    }

    private String validation() {
        String validate = null;
        if (edtFirstname.getText().toString().trim().isEmpty()) {
            validate = "First name can not be blank.";
            edtFirstname.requestFocus();
        } else if (edtLastname.getText().toString().trim().isEmpty()) {
            validate = "Last name can not be blank.";
            edtLastname.requestFocus();
        }
        return validate;
    }

    private void updateProfileAPI() {
        if (Utils.isOnline(UpdateProfileActivity.this)) {
            relProgressBar.setVisibility(View.VISIBLE);
            NetworkManager.getInstance().updateUserProfile(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", ModelManager.getInstance().getLoginResponse().getUserId(), generateJsonBody(), responseListner);
        } else {
            relProgressBar.setVisibility(View.GONE);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {

                        }
                    });
        }
    }

    private void assignImageToUserAPI(String assignmentId, String imageId) {
        if (Utils.isOnline(UpdateProfileActivity.this)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    relProgressBar.setVisibility(View.VISIBLE);
                }
            });

            // assignImageToUser
            NetworkManager.getInstance().assignImagesToUsers(ModelManager.getInstance().getLoginResponse().getAuthorization(),
                    "application/json", createAssignImageToUserJson(assignmentId, imageId), responseListner);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    relProgressBar.setVisibility(View.GONE);

                    AlertDialog.showDialogWithAlertHeaderSingleButton(UpdateProfileActivity.this, "Network", getResources().getString(R.string.no_internet_connection),
                            new OnItemClickListener() {
                                @Override
                                public void onItemClick(Object o, int position) {

                                }
                            });
                }
            });
        }
    }

    private void uploadImage(String path, String name) {
        if (Utils.isOnline(UpdateProfileActivity.this)) {
            relProgressBar.setVisibility(View.VISIBLE);
            new UploadPicAPI(this, path, name, responseListner).execute();
        } else {
            relProgressBar.setVisibility(View.GONE);
            AlertDialog.showDialogWithAlertHeaderSingleButton(this, "Network", getResources().getString(R.string.no_internet_connection),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                        }
                    });
        }
    }

    private JsonObject createAssignImageToUserJson(String assignmentId, String imageId) {
        JsonObject mainJson = new JsonObject();
        mainJson.addProperty("assignmentId", assignmentId);
        mainJson.addProperty("imageId", imageId);
        return mainJson;
    }

    ResponseListner responseListner = new ResponseListner() {
        @Override
        public void onResponse(String tag, int result, Object obj) {
            if (result == ResponseConfig.API_SUCCESS) {
                if (tag.equals(ResponseConfig.TAG_UPDATE_PROFILE)) {
                    if (file != null && isProfilePhotoClicked == true) {
                        if (file.exists()) {
                            JsonObject jsonObject = (JsonObject) obj;
                            String value = new Gson().toJson(jsonObject);
                            JSONObject parseJson;
                            try {
                                parseJson = new JSONObject(value);
                                assignId = parseJson.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            uploadImage(file.toString(), file.getName());
                        }
                    } else {
                        UpdateProfileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                relProgressBar.setVisibility(View.GONE);
                            }
                        });

                        if(!isProfilePhotoClicked){
                            /** this is to re- assigned the image **/
                            if (userBean.image.size() > 0) {
                                assignImageToUserAPI(userBean.image.get(0).userId, String.valueOf(userBean.image.get(0).id));
                            }else{
                                finish();
                            }
                        }
                    }
                } else if (tag.equals(ResponseConfig.TAG_UPLOAD_IMAGE)) {
                    CatchLogImageBean catchLogImageBean = (CatchLogImageBean) obj;
                    assignImageToUserAPI(assignId, String.valueOf(catchLogImageBean.id));
                    finish();
                } else if (tag.equals(ResponseConfig.TAG_ASSIGN_IMAGES_TO_USER)) {
                    UpdateProfileActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            relProgressBar.setVisibility(View.GONE);
                            finish();
                        }
                    });
                }
            } else {
                UpdateProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        relProgressBar.setVisibility(View.GONE);
                        finish();
                    }
                });
                // Failure message
                Log.print("=========== API CALL FAIL ============");
            }
        }
    };

    private void setProfileData(UserBean userBean) {
        // Set Profile picture.
        // imgProfilePhoto
        if (userBean.image.size() > 0) {
            imgPlaceHolder.setVisibility(View.GONE);
            Picasso.get()
                    .load(userBean.image.get(0).url)
                    .into(imgProfilePhoto);
        }
        edtFirstname.setText(userBean.firstName);
        edtLastname.setText(userBean.lastName);
        edtEmail.setText(userBean.email);
    }


    boolean isProfilePhotoClicked = false;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
                isProfilePhotoClicked = true;
                try {
                    Bitmap bitmap = camera.getCameraBitmap();
                    imgPlaceHolder.setVisibility(View.GONE);
                    imgProfilePhoto.setImageBitmap(bitmap);
                    /**
                     * prepare file to send to backend
                     */
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "Profile_JPEG_" + timeStamp + "_";

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
                isProfilePhotoClicked = true;
                FileOutputStream fos = null;
                imageUri = data.getData();
                imgPlaceHolder.setVisibility(View.GONE);
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
                    imgProfilePhoto.setImageBitmap(bitmap);
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                    matrix.setRotate(180);
                    rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    this.bitmap = rotateBitmap;
                    imgProfilePhoto.setImageBitmap(bitmap);
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    matrix.setRotate(270);
                    rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    this.bitmap = rotateBitmap;
                    imgProfilePhoto.setImageBitmap(bitmap);
                } else {
                    imgProfilePhoto.setImageBitmap(bitmap);
                }
                /**
                 * prepare file to send to backend
                 */
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "Profile_JPEG_" + timeStamp + "_";
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
        }
    }

    private JsonObject generateJsonBody() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", edtFirstname.getText().toString().trim());
        jsonObject.addProperty("lastName", edtLastname.getText().toString().trim());

        return jsonObject;
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_PICTURE);
    }

    private void launchCameraOption() {
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("profile" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
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

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}