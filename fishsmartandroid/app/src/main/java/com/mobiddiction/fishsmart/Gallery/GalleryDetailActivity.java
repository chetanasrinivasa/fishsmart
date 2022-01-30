package com.mobiddiction.fishsmart.Gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.R;
import com.mobiddiction.fishsmart.dao.GalleryImage;
import com.mobiddiction.fishsmart.util.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GalleryDetailActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    private RelativeLayout loader;

    ViewPager mViewPager;
    List<GalleryImage> items;
    int position;

    TextView navTitle;
    Handler handler = new Handler();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);

        loader = findViewById(R.id.loader);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#232323")));

        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_slider_actionbar, null);
        mActionBarView.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        navTitle = mActionBarView.findViewById(R.id.nav_title);

        // ((TextView) mActionBarView.findViewById(R.id.nav_title)).setText("August 2015");

        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loader.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {

                    @Override

                    public void run() {


                        File img = null;
                        try {
                            URL url = new URL(ModelManager.getInstance().getGalleryImage().get(mViewPager.getCurrentItem()).getUrl());
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(input);

                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            String imageFileName = "JPEG_" + timeStamp + "_";
                            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                            // Delete file if already exists
                            File check = new File(storageDir, imageFileName + ".jpg");
                            if (check.exists()) {
                                check.delete();
                            }

                            img = File.createTempFile(
                                    imageFileName,
                                    ".jpg",
                                    storageDir
                            );

                            final OutputStream outStream;

                                outStream = new FileOutputStream(img);
                                if(bitmap != null) {
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                                }
                                outStream.flush();
                                outStream.close();

                                handler.post(new Runnable() {

                                @Override
                                public void run() {

                                    loader.setVisibility(View.GONE);

                                }
                            });


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(img != null) {
                            if(img.exists()) {
                                if(img.length() != 0) {
                                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                                    StrictMode.setVmPolicy(builder.build());

                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("image/*");

                                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(img));
                                    startActivity(Intent.createChooser(share, "Share via"));
                                }
                            }
                        }

                    }

                }).start();
            }
        });


        Intent intent = getIntent();

        items = ModelManager.getInstance().getGalleryImage();
        position = Integer.parseInt(intent.getStringExtra("position"));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(position);
//        navTitle.setText((position+1) + "/" + items.size());

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navTitle.setText((position + 1) + "/" + items.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

   /* public void showZoomView(Bitmap bitmap) {

        zoomView.setImage(ImageSource.cachedBitmap(bitmap));
        zoomView.setVisibility(View.VISIBLE);
        zoomView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);
    }*/

    @Override
    public void onBackPressed() {
        /*if (zoomView != null)
        {
            if (zoomView.getVisibility() == View.VISIBLE)
            {
                zoomView.setVisibility(View.GONE);
            }else
            {
                super.onBackPressed();
            }
        }else
        {
            super.onBackPressed();
        }*/
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position, items.get(position), items.size());
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return items.size();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        int total_number;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_NUMBER2 = "section_number2";
        int position;
        List<GalleryImage> item;
        ImageView profile_image;
        Handler handler = new Handler();

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, GalleryImage item, int total_number) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, "" + sectionNumber);
            args.putSerializable(ARG_SECTION_NUMBER2, item);
            args.putInt("total_number", total_number);

            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_gallery_detail, container, false);
            Bundle args = getArguments();

            position = Integer.parseInt(args.getString(ARG_SECTION_NUMBER));
            item = ModelManager.getInstance().getGalleryImage();
            total_number = args.getInt("total_number");
            TextView title = rootView.findViewById(R.id.section_label);
            TextView txtComment = rootView.findViewById(R.id.txtComment);

            if (item.get(position).getName() != null && (!item.get(position).getName().equals("")
                    || !item.get(position).getName().equals("null") || !item.get(position).getName().equals(null))) {
                title.setVisibility(View.VISIBLE);
                title.setText(item.get(position).getName());
            } else {
                title.setVisibility(View.GONE);
            }

            if (item.get(position).getNote() != null && (!item.get(position).getNote().equals("")
                    || !item.get(position).getNote().equals("null") || !item.get(position).getNote().equals(null))) {
                txtComment.setVisibility(View.VISIBLE);
                txtComment.setText(item.get(position).getNote());
            } else {
                txtComment.setVisibility(View.GONE);
            }
            //TextView via = (TextView) rootView.findViewById(R.id.via);
            //via.setText(item.get(position).getName() + ": via " + item.get(position).getAdminNote());
            // via.setText(item.getType());

            final ImageView image = rootView.findViewById(R.id.section_image);
            Glide.with(getActivity())
                    .load(item.get(position).getUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(image);
            return rootView;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.freeMemory();
    }
}