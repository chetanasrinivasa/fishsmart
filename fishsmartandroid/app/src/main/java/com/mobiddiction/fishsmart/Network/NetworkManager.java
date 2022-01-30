package com.mobiddiction.fishsmart.Network;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.JsonObject;
import com.mobiddiction.fishsmart.CatchLog.OceanName;
import com.mobiddiction.fishsmart.Configuration.Config;
import com.mobiddiction.fishsmart.EmptyBodyRequest;
import com.mobiddiction.fishsmart.MyApplication;
import com.mobiddiction.fishsmart.RegistrationModel;
import com.mobiddiction.fishsmart.SignIn.SignInBody;
import com.mobiddiction.fishsmart.Species.FeaturedSpeciesRequestBody;
import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.bean.CatchLogImageBean;
import com.mobiddiction.fishsmart.bean.CatchLogSpeciesCaughtBean;
import com.mobiddiction.fishsmart.bean.MethodTypesBean;
import com.mobiddiction.fishsmart.bean.TermsAndConditionsBean;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.dao.DeviceData;
import com.mobiddiction.fishsmart.dao.Gallery;
import com.mobiddiction.fishsmart.dao.GalleryImage;
import com.mobiddiction.fishsmart.dao.LoginResponse;
import com.mobiddiction.fishsmart.dao.NEWSpeciesData;
import com.mobiddiction.fishsmart.dao.NewGuide;
import com.mobiddiction.fishsmart.dao.NewLicence;
import com.mobiddiction.fishsmart.dao.NewLicenceData;
import com.mobiddiction.fishsmart.dao.NewListTypeModel;
import com.mobiddiction.fishsmart.dao.NewMap;
import com.mobiddiction.fishsmart.dao.NewMapData;
import com.mobiddiction.fishsmart.dao.NewMapPinsLatLonData;
import com.mobiddiction.fishsmart.dao.NewRulesGuide;
import com.mobiddiction.fishsmart.dao.Onboarding;
import com.mobiddiction.fishsmart.dao.Policies;
import com.mobiddiction.fishsmart.dao.SignUp;
import com.mobiddiction.fishsmart.dao.TermAndCondition;
import com.mobiddiction.fishsmart.dao.Notification;
import com.mobiddiction.fishsmart.dao.NotificationDevice;
import com.mobiddiction.fishsmart.dao.NotificationImage;
import com.mobiddiction.fishsmart.dao.NotificationRoles;
import com.mobiddiction.fishsmart.notification.PostDeviceRegistration;
import com.mobiddiction.fishsmart.dao.UserHistory;
import com.mobiddiction.fishsmart.dao.UserObj;
import com.mobiddiction.fishsmart.util.loadAllSpeciesData;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AI on 16/06/2017.
 */

public class NetworkManager {
    private static final String TAG = NetworkManager.class.getSimpleName();
    private static final int LONG_LOG_CHUNK_SIZE = 3000;
    private static NetworkManager instance;
    private static NetworkManager instanceOceanName;
    private ModelManager manager = ModelManager.getInstance();
    private FishService restService;

    private NetworkManager() {
        createService(Config.HOST);
    }
    private NetworkManager(String oceanName) {
        createService(oceanName);
    }

    public static synchronized NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public static synchronized NetworkManager getInstanceForOceanName(String oceanName) {
        if (instanceOceanName == null) {
            instanceOceanName = new NetworkManager(oceanName);
        }
        return instanceOceanName;
    }

    private OkHttpClient createHTTPClient() {
        GetTrustManager getTrustManager = new GetTrustManager().invoke();
        SSLSocketFactory sslSocketFactory = getTrustManager.getSslSocketFactory();
        X509TrustManager trustManager = getTrustManager.getTrustManager();

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieJar cookieJar = new JavaNetCookieJar(cookieManager);

        // TODO: 5/10/2017 please make the level to NONE when launch the app
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(10);
//        /**
//         *
//         */
//        String hostname = "mobicore.com";
//        CertificatePinner certificatePinner = new CertificatePinner.Builder()
//                .add(hostname, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
//                .build();
//
        CookieManager managerCookie = createCookieManger();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .sslSocketFactory(sslSocketFactory, trustManager)
                .cookieJar(cookieJar)
//                .certificatePinner(certificatePinner)
                .addInterceptor(logging)
                .addInterceptor(new ConnectivityInterceptor(MyApplication.getRestApplication()))
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        String tmpString = message;
                        while (tmpString.length() > LONG_LOG_CHUNK_SIZE) {
                            Log.d(TAG, tmpString.substring(0, LONG_LOG_CHUNK_SIZE));
                            tmpString = tmpString.substring(LONG_LOG_CHUNK_SIZE);
                        }
                        Log.d(TAG, tmpString);
                    }
                }))
                .addInterceptor(new RetryInterceptor())
                .addInterceptor(new CookieBaker(managerCookie.getCookieStore()))
                .dispatcher(dispatcher)
                .build();

        return httpClient;
    }

    private FieldNamingStrategy getGSONStrategy() {
        FieldNamingStrategy strategy = new FieldNamingStrategy() {
            @Override
            public String translateName(Field field) {
                if (field.getName().equalsIgnoreCase("default_location")) {
                    return "default";
                } else {
                    return field.getName();
                }
            }
        };
        return strategy;
    }

    private CookieManager createCookieManger() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return cookieManager;
    }

    // The KentikoService contains no auth headers
    // Only doing this to remove shared pref code
    private void createService(String baseUrl) {
        final OkHttpClient kentikoHttpClient = createHTTPClient();
        try {
            Retrofit restAdapter = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(kentikoHttpClient)
                    .build();

            restService = restAdapter.create(FishService.class);
        } catch (Exception e) {
            Log.d(TAG, "createService: ");
        }
    }


    static public String GetCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date newDate = new Date();
        return dateFormat.format(newDate);
    }

    public void getAllSpecies(Activity mActivity, Handler mHandler) {
        Log.d("NetworkManager","getAllSpecies");
        restService.getSpecies().enqueue(new Callback<List<NEWSpeciesData>>() {

            @Override
            public void onResponse(Call<List<NEWSpeciesData>> call, Response<List<NEWSpeciesData>> response) {
                if((response.body() == null) ) {
                    Log.d("NetworkManager","Response is null");
                    loadAllSpeciesData mloadData = new loadAllSpeciesData(mActivity);
                    mloadData.loadDataFromLocal(true,null,null,mHandler);
                }else{
                    if (response.body().size() <= 0){
                        Log.d("NetworkManager","Response is 0");
                        loadAllSpeciesData mloadData = new loadAllSpeciesData(mActivity);
                        mloadData.loadDataFromLocal(true,null,null,mHandler);
                    }else {
                        Log.d("NetworkManager", "Response is fine");
                        loadAllSpeciesData mloadData = new loadAllSpeciesData(mActivity);
                        response.body();
                        mloadData.loadDataFromLocal(false, response.body().toString(), response, mHandler);
                    }
                }
                //EventBus.getDefault().post(BasicEvent.SPECIES_DOWNLOADED);
            }

            @Override
            public void onFailure(Call<List<NEWSpeciesData>> call, Throwable t) {
                Log.d("NetworkManager","onFailure = " );
                if(t != null) {
                    if(t.getCause() != null)
                        Log.d("NetworkManager", "Cause = " + t.getCause().toString());
                    else
                        Log.d("NetworkManager", "Cause = " + t.toString());
                }

                loadAllSpeciesData mloadData = new loadAllSpeciesData(mActivity);
                mloadData.loadDataFromLocal(true,null,null,mHandler);
                EventBus.getDefault().post(BasicEvent.SPECIES_ERROR);
            }
        });
    }

   /* public void getFeaturedSpecies(Activity mActivity, Handler mHandler) {
        Log.d("NetworkManager","getAllSpecies");
        FeaturedSpeciesRequestBody mRequestBody = new FeaturedSpeciesRequestBody(true,655);
        //restService.getSpecies().enqueue(new Callback<List<NEWSpeciesData>>() {
        restService.getFeaturedSpecies(mRequestBody).enqueue(new Callback<List<NEWSpeciesData>>() {

            @Override
            public void onResponse(Call<List<NEWSpeciesData>> call, Response<List<NEWSpeciesData>> response) {
                //String s = new String(((TypedByteArray) response.getBody()).getBytes());
                //Log.e("res",s);
                if((response.body() == null) || (response.body().size() <= 0)) {
                    loadAllSpeciesData mloadData = new loadAllSpeciesData(mActivity);
                    mloadData.loadDataFromLocal(true,null,null,mHandler);
                }else{
                    loadAllSpeciesData mloadData = new loadAllSpeciesData(mActivity);
                    response.body();
                    mloadData.loadDataFromLocal(false,response.body().toString(),response,mHandler);
                }
                EventBus.getDefault().post(BasicEvent.FEATURED_SPECIES_DOWNLOADED);
            }

            @Override
            public void onFailure(Call<List<NEWSpeciesData>> call, Throwable t) {
                Log.d("NetworkManager","onFailure = " );
                if(t != null) {
                    if(t.getCause() != null)
                        Log.d("NetworkManager", "Cause = " + t.getCause().toString());
                    else
                        Log.d("NetworkManager", "Cause = " + t.toString());
                }

                loadAllSpeciesData mloadData = new loadAllSpeciesData(mActivity);
                mloadData.loadDataFromLocal(true,null,null,mHandler);
                EventBus.getDefault().post(BasicEvent.FEATURED_SPECIES_ERROR);
            }
        });
    }*/

    public void getOnboarding() {
        this.restService.getOnBoarding("application/json").enqueue(new Callback<List<Onboarding>>() {

            @Override
            public void onResponse(Call<List<Onboarding>> call, Response<List<Onboarding>> response) {
                if (response.body() != null) {
                    manager.removeOnboarding();
                    manager.insertOnboarding(response.body());
                    EventBus.getDefault().post(BasicEvent.GET_ONBOARDING_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<List<Onboarding>> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.GET_ONBOARDING_FAILED);
            }

        });
    }


    public void doLogin(SignInBody signInBody) {
        this.restService.doLogin(signInBody).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        manager.removeLogin();
                        manager.insertLoginResponse(response.body());
                        EventBus.getDefault().post(BasicEvent.DO_LOGIN_SUCCESS);
                    }
                } else if (response.code() == 401) {
                    EventBus.getDefault().post(BasicEvent.DO_LOGIN_401);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.DO_LOGIN_FAILED);
            }
        });
    }

    public void getTermAndCondition(String authorisation) {
        this.restService.getTermAndCondition("application/json", authorisation).enqueue(new Callback<TermAndCondition>() {

            @Override
            public void onResponse(Call<TermAndCondition> call, Response<TermAndCondition> response) {
                if (response.body() != null) {
                    manager.removeTermAndCondition();
                    manager.insertTermAndCondition(response.body());

                    for (Policies policies : response.body().policies) {
                        manager.insertPolicies(policies);
                    }
                    EventBus.getDefault().post(BasicEvent.GET_TERM_CONDITION_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<TermAndCondition> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.GET_TERM_CONDITION_FAILED);
            }
        });
    }

    /*public void doAcceptTC(String authorisation, String userId) {
        this.restService.doAcceptTerms("application/json", authorisation, userId, "").enqueue(new Callback<ResponseBody>() {



            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                EventBus.getDefault().post(BasicEvent.ACCEPT_TERMS_SUCCESS);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.ACCEPT_TERMS_FAILED);
            }
        });
    }*/

    public void doRegistration(RegistrationModel registrationModel) {
        this.restService.doRegistration("application/json", registrationModel).enqueue(new Callback<SignUp>() {

            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        manager.removeSignUp();
                        manager.insertSignUp(response.body());
                        //perform login here
                        SignInBody signInBody = new SignInBody(registrationModel.email, registrationModel.password);
                        restService.doLogin(signInBody).enqueue(new Callback<LoginResponse>() {

                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if (response.code() == 200) {
                                    if (response.body() != null) {
                                        manager.removeLogin();
                                        manager.insertLoginResponse(response.body());
                                        EventBus.getDefault().post(BasicEvent.SIGN_UP_SUCCESS);
                                    }
                                } else if (response.code() == 401) {
//                                    EventBus.getDefault().post(BasicEvent.DO_LOGIN_401);
                                    EventBus.getDefault().post(BasicEvent.SIGN_UP_FAILED);
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                EventBus.getDefault().post(BasicEvent.SIGN_UP_FAILED);
                            }
                        });



                    }
                } else if (response.code() == 409) {
                    EventBus.getDefault().post(BasicEvent.SIGN_UP_DUPLICATE);
                } else if (response.code() == 400) {
                    EventBus.getDefault().post(BasicEvent.SIGN_UP_PASSWORD);
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.SIGN_UP_FAILED);
            }
        });
    }

    public void getMap() {
        this.restService.getMap().enqueue(new Callback<List<NewMap>>() {

            @Override
            public void onResponse(Call<List<NewMap>> call, Response<List<NewMap>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    manager.removeNewMaps();
                    manager.removeNewMapPinsLatLonData();
                    manager.removeNewMapData();

                    manager.insertNewMap(response.body());

                    List<NewMapData> newMapDataList = new ArrayList<>();
                    List<NewMapPinsLatLonData> newNewMapPinsLatLonData = new ArrayList<>();

                    for (NewMap data : response.body()) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(data.getContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /**
                         * convert content string into JSON
                         */
                        JSONArray jsonArrays = null;
                        try {
                            jsonArrays = obj.getJSONArray("content");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        NewMapData newMapData = new NewMapData();
                        NewMapPinsLatLonData newMapPinsLatLonData = new NewMapPinsLatLonData();
                        for (int x = 0; x < jsonArrays.length(); x++) {

                            newMapData.setId(data.getId());
                            newMapPinsLatLonData.setId(data.getId());
                            try {
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Map type")) {
                                    newMapData.setMapType(jsonArrays.getJSONObject(x).getString("value"));
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Title")) {
                                    newMapData.setTitle(jsonArrays.getJSONObject(x).getString("value"));
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Description")) {
                                    newMapData.setDescription(jsonArrays.getJSONObject(x).getString("value"));
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Zoom level for Map")) {
                                    if (!jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("null") && !jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("")) {
                                        newMapData.setZoom(Double.parseDouble(jsonArrays.getJSONObject(x).getString("value")));
                                    } else {
                                        newMapData.setZoom(0.0);
                                    }
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Bottom Right Latitude")) {
                                    if (!jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("null") && !jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("")) {
                                        newMapData.setBottomRightLat(Double.parseDouble(jsonArrays.getJSONObject(x).getString("value")));
                                    } else {
                                        newMapData.setBottomRightLat(0.0);
                                    }
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Bottom Right Longitude")) {
                                    if (!jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("null") && !jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("")) {
                                        newMapData.setBottomRightLon(Double.parseDouble(jsonArrays.getJSONObject(x).getString("value")));
                                    } else {
                                        newMapData.setBottomRightLon(0.0);
                                    }
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Top Left Latitude")) {
                                    if (!jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("null") && !jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("")) {
                                        newMapData.setTopLeftLat(Double.parseDouble(jsonArrays.getJSONObject(x).getString("value")));
                                        newMapPinsLatLonData.setLat(Double.parseDouble(jsonArrays.getJSONObject(x).getString("value")));
                                    } else {
                                        newMapData.setTopLeftLat(0.0);
                                        newMapPinsLatLonData.setLat(0.0);
                                    }
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Top Left Longitude")) {
                                    if (!jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("null") && !jsonArrays.getJSONObject(x).getString("value").equalsIgnoreCase("")) {
                                        newMapData.setTopLeftLon(Double.parseDouble(jsonArrays.getJSONObject(x).getString("value")));
                                        newMapPinsLatLonData.setLon(Double.parseDouble(jsonArrays.getJSONObject(x).getString("value")));
                                    } else {
                                        newMapData.setTopLeftLon(0.0);
                                        newMapPinsLatLonData.setLon(0.0);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        newMapDataList.add(newMapData);
                        newNewMapPinsLatLonData.add(newMapPinsLatLonData);
                    }
                    manager.insertNewMapData(newMapDataList);
                    manager.insertnewMapPinsLatLonData(newNewMapPinsLatLonData);
                }
                EventBus.getDefault().post(BasicEvent.MAP_LOADED_SUCCESS);
            }

            @Override
            public void onFailure(Call<List<NewMap>> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.MAP_LOADED_FAILED);
            }
        });
    }

    public void getGuide() {
        this.restService.getGuide().enqueue(new Callback<List<NewGuide>>() {

            @Override
            public void onResponse(Call<List<NewGuide>> call, Response<List<NewGuide>> response) {
                if (response.body() != null) {
                    manager.removeNewGuide();
                    manager.removeNewRulesGuide();
                    manager.removeNewListTypeModel();

                    manager.insertnewGuides(response.body());

                    List<NewRulesGuide> newRulesGuideList = new ArrayList<>();
                    List<NewListTypeModel> newListTypeModelList = new ArrayList<>();

                    for (NewGuide data : response.body()) {
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(data.getContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        /**
                         * convert content string into JSON
                         */
                        JSONArray jsonArrays = null;
                        try {
                            jsonArrays = obj.getJSONArray("content");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        NewRulesGuide newRulesGuide = new NewRulesGuide();
                        NewListTypeModel newListTypeModel = new NewListTypeModel();
                        for (int x = 0; x < jsonArrays.length(); x++) {
                            newRulesGuide.setId(data.getId());
                            newListTypeModel.setId(data.getId());

                            try {
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Fishing guide type")) {
                                    newRulesGuide.setFishingGuideType(jsonArrays.getJSONObject(x).getString("value"));

                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Heading")) {
                                    newListTypeModel.setTitle(jsonArrays.getJSONObject(x).getString("value"));
                                }

                                if (jsonArrays.getJSONObject(x).getString("key").contains("Description")) {
                                    newListTypeModel.setDescription(jsonArrays.getJSONObject(x).getString("value"));
                                }
                                if (jsonArrays.getJSONObject(x).getString("key").contains("Please enter PDF url here")) {
                                    newListTypeModel.setPdfUrl(jsonArrays.getJSONObject(x).getString("value"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        newRulesGuideList.add(newRulesGuide);
                        newListTypeModelList.add(newListTypeModel);
                    }
                    manager.insertNewListType(newListTypeModelList);
                    manager.insertNewRulesGuide(newRulesGuideList);
                    EventBus.getDefault().post(BasicEvent.GUIDE_LOADED_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<List<NewGuide>> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.GUIDE_LOADED_FAILED);
            }
        });
    }

    public void getLicense() {
        this.restService.getLicense().enqueue(new Callback<List<NewLicence>>() {

            @Override
            public void onResponse(Call<List<NewLicence>> call, Response<List<NewLicence>> response) {
                manager.removeNewLicenceData();
                manager.removeNewLicence();

                manager.insertNewLicence(response.body());

                List<NewLicenceData> newLicencesList = new ArrayList<>();

                for (NewLicence data : response.body()) {
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(data.getContent());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /**
                     * convert content string into JSON
                     */
                    JSONArray jsonArrays = null;
                    try {
                        jsonArrays = obj.getJSONArray("content");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NewLicenceData newLicenceData = new NewLicenceData();
                    for (int x = 0; x < jsonArrays.length(); x++) {
                        try {
                            if (jsonArrays.getJSONObject(x).getString("key").contains("Sub-type of Article")) {
                                newLicenceData.setSubType(jsonArrays.getJSONObject(x).getString("value"));
                            }
                            if (jsonArrays.getJSONObject(x).getString("key").contains("Type of Article")) {
                                newLicenceData.setContactAndLicensingType(jsonArrays.getJSONObject(x).getString("value"));
                            }
                            if (jsonArrays.getJSONObject(x).getString("key").contains("Title")) {
                                newLicenceData.setTitle(jsonArrays.getJSONObject(x).getString("value"));
                            }
                            if (jsonArrays.getJSONObject(x).getString("key").contains("Description")) {
                                newLicenceData.setDescription(jsonArrays.getJSONObject(x).getString("value"));
                            }
                            if (jsonArrays.getJSONObject(x).getString("key").contains("Phone")) {
                                newLicenceData.setPhone(jsonArrays.getJSONObject(x).getString("value"));
                            }
                            if (jsonArrays.getJSONObject(x).getString("key").contains("Mobile")) {
                                newLicenceData.setMobile(jsonArrays.getJSONObject(x).getString("value"));
                            }
                            if (jsonArrays.getJSONObject(x).getString("key").contains("Url")) {
                                newLicenceData.setUrl(jsonArrays.getJSONObject(x).getString("value"));
                            }
                        } catch (Exception e) {

                        }
                    }
                    newLicencesList.add(newLicenceData);
                }
                manager.insertNewLicenceData(newLicencesList);
                EventBus.getDefault().post(BasicEvent.LICENCE_SUCCESS);
            }

            @Override
            public void onFailure(Call<List<NewLicence>> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.LICENCE_FAILED);
            }
        });
    }

    public void getGallery() {
        this.restService.getGalleryImage().enqueue(new Callback<List<Gallery>>() {

            @Override
            public void onResponse(Call<List<Gallery>> call, Response<List<Gallery>> response) {
                if(response.body() != null) {
                    manager.removeGallery();
                    manager.removeGalleryImage();

                    List<Gallery> galleryListNew = new ArrayList<>();
                    List<GalleryImage> galleryImagesNewList = new ArrayList<>();
                    for (Gallery g : response.body()) {
                        if (g.image.size() > 0) {
                            for (GalleryImage i : g.image) {
                                galleryImagesNewList.add(i);
                                galleryListNew.add(g);
                            }
                        } else {
                            /**
                             * do nothing / dont add it
                             */
                        }
                    }
                    manager.insertGallery(galleryListNew);
                    manager.insertGalleryImage(galleryImagesNewList);
                    EventBus.getDefault().post(BasicEvent.GALLERY_SUCCESS);
                }else{
                    EventBus.getDefault().post(BasicEvent.GALLERY_FAILED);
                }
            }

            @Override
            public void onFailure(Call<List<Gallery>> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.GALLERY_FAILED);
            }
        });
    }

    //Not being used
   /* public void getFeaturedSpecies() {
       *//* this.restService.getFeaturedSpecies("application/json").enqueue(new Callback<List<Gallery>>() {

            @Override
            public void onResponse(Call<List<Gallery>> call, Response<List<Gallery>> response) {
                getAllSpecies();
                EventBus.getDefault().post(BasicEvent.FEATURED_SUCCESS);
            }

            @Override
            public void onFailure(Call<List<Gallery>> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.FEATURED_FAILED);
            }
        });*//*
    }*/

    public void getCatchLogList(String authorisation, String userId, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.getCatchLog("application/json", authorisation, userId).enqueue(new Callback<ArrayList<CatchLogBean>>() {

            @Override
            public void onResponse(Call<ArrayList<CatchLogBean>> call, Response<ArrayList<CatchLogBean>> response) {
                if (response.code() == 200) {
                    responseListerlocal.onResponse(ResponseConfig.TAG_GET_CATCHLOG, ResponseConfig.API_SUCCESS, response.body());
                }

                // EventBus.getDefault().post(BasicEvent.GET_CATCH_LOG_SUCCESS);
            }

            @Override
            public void onFailure(Call<ArrayList<CatchLogBean>> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_GET_CATCHLOG, ResponseConfig.API_FAIL, t);
                //EventBus.getDefault().post(BasicEvent.GET_CATCH_LOG_FAILED);
            }
        });
    }

    public void getCatchSpecies(ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;
        restService.getAllCatchSpecies().enqueue(new Callback<List<CatchLogBean>>() {

            @Override
            public void onResponse(Call<List<CatchLogBean>> call, Response<List<CatchLogBean>> response) {

                if (response.code() == 200) {
                    ArrayList<ArrayList<CatchLogSpeciesCaughtBean>> allWaterList = new ArrayList<>();
                    ArrayList<CatchLogSpeciesCaughtBean> saltWaterList = new ArrayList<>();
                    ArrayList<CatchLogSpeciesCaughtBean> freshWaterList = new ArrayList<>();
                    for (CatchLogBean catchLogBean : response.body()) {
                        JSONObject obj = null;
                        JSONArray jsonArrays = null;
                        JSONObject objects = null;
                        try {
                            obj = new JSONObject(catchLogBean.content);
                            jsonArrays = obj.getJSONArray("content");
                            objects = jsonArrays.getJSONObject(0);
                            CatchLogSpeciesCaughtBean catchLogSpeciesCaughtBean = new CatchLogSpeciesCaughtBean();
                            if (objects.getString("value").equalsIgnoreCase("SALTWATER")) {
                                catchLogSpeciesCaughtBean.createdBy = catchLogBean.createdBy;
                                catchLogSpeciesCaughtBean.createdDate = catchLogBean.createdDate;
                                catchLogSpeciesCaughtBean.lastModifiedBy = catchLogBean.lastModifiedBy;
                                catchLogSpeciesCaughtBean.lastModifiedDate = catchLogBean.lastModifiedDate;
                                catchLogSpeciesCaughtBean.id = catchLogBean.id;

                                for (int x = 0; x < jsonArrays.length(); x++) {
                                    // Image Path
                                    if (jsonArrays.getJSONObject(x).getString("key").equalsIgnoreCase("Media")) {
                                        catchLogSpeciesCaughtBean.image = jsonArrays.getJSONObject(x).getString("value");
                                    } else {
                                        catchLogSpeciesCaughtBean.image = "";
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").equalsIgnoreCase("Fish name")) {
                                        catchLogSpeciesCaughtBean.species = jsonArrays.getJSONObject(x).getString("value");
                                    }
                                }
                                saltWaterList.add(catchLogSpeciesCaughtBean);
                            } else if (objects.getString("value").equalsIgnoreCase("FRESHWATER")) {
                                catchLogSpeciesCaughtBean.createdBy = catchLogBean.createdBy;
                                catchLogSpeciesCaughtBean.createdDate = catchLogBean.createdDate;
                                catchLogSpeciesCaughtBean.lastModifiedBy = catchLogBean.lastModifiedBy;
                                catchLogSpeciesCaughtBean.lastModifiedDate = catchLogBean.lastModifiedDate;
                                catchLogSpeciesCaughtBean.id = catchLogBean.id;

                                for (int x = 0; x < jsonArrays.length(); x++) {
                                    // Image Path
                                    if (jsonArrays.getJSONObject(x).getString("key").equalsIgnoreCase("Media")) {
                                        catchLogSpeciesCaughtBean.image = jsonArrays.getJSONObject(x).getString("value");
                                    } else {
                                        catchLogSpeciesCaughtBean.image = "";
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").equalsIgnoreCase("Fish name")) {
                                        catchLogSpeciesCaughtBean.species = jsonArrays.getJSONObject(x).getString("value");
                                    }
                                }
                                freshWaterList.add(catchLogSpeciesCaughtBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    allWaterList.add(saltWaterList);
                    allWaterList.add(freshWaterList);
                    responseListerlocal.onResponse(ResponseConfig.TAG_GET_CATCH_SPECIES, ResponseConfig.API_SUCCESS, allWaterList);
                    // EventBus.getDefault().post(BasicEvent.SPECIES_DOWNLOADED);
                }
            }




            @Override
            public void onFailure(Call<List<CatchLogBean>> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_GET_CATCH_SPECIES, ResponseConfig.API_FAIL, t);
                // EventBus.getDefault().post(BasicEvent.SPECIES_ERROR);
            }
        });
    }

    public void getCatchLogImageList(String authorisation, String userId, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.getCatchImageList(authorisation, userId).enqueue(new Callback<ArrayList<CatchLogImageBean>>() {

            @Override
            public void onResponse(Call<ArrayList<CatchLogImageBean>> call, Response<ArrayList<CatchLogImageBean>> response) {
                if (response.code() == 200) {
                    responseListerlocal.onResponse(ResponseConfig.TAG_GET_CATCHLOG_IMAGE, ResponseConfig.API_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CatchLogImageBean>> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_GET_CATCHLOG_IMAGE, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void addMyCatch(String authorisation, String contentType, JsonObject body, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.addCatch(authorisation, contentType, body).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_ADD_CATCH_LOG, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_ADD_CATCH_LOG, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void assignImagesToCatch(String authorisation, String contentType, JsonObject body, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.assignImageToCatch(authorisation, contentType, body).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_ASSIGN_IMAGES_TO_CATCH_LOG, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_ASSIGN_IMAGES_TO_CATCH_LOG, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void updateImagesToCatch(String authorisation, String contentType, String imageId, JsonObject body, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.updateCatchLogImage(authorisation, contentType, imageId, body).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_UPDATE_IMAGE, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_UPDATE_IMAGE, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void deleteThisCatch(String authorisation, String contentType, String catchId, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;
        this.restService.deleteCatch(authorisation, contentType, catchId).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_DELETE_CATCH, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_DELETE_CATCH, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void updateCurrentCatch(String authorisation, String contentType, String catchId, JsonObject body, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.updateCatchLog(authorisation, contentType, catchId, body).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_UPDATE_CATCH, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_UPDATE_CATCH, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void getUserProfile(String authorisation, String contentType, String userId, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.getProfile(authorisation, contentType, userId).enqueue(new Callback<UserBean>() {

            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                if (response.code() == 200) {
                    responseListerlocal.onResponse(ResponseConfig.TAG_GET_USER_PROFILE, ResponseConfig.API_SUCCESS, response.body());
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_GET_USER_PROFILE, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void updateUserProfile(String authorisation, String contentType, String userId, JsonObject body, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.updateProfile(authorisation, contentType, userId, body).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_UPDATE_PROFILE, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_UPDATE_PROFILE, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void changePassword(String authorisation, String contentType, String userId, JsonObject body, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.updatePassword(authorisation, contentType, userId, body).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_CHANGE_PASSWORD, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_CHANGE_PASSWORD, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void resetPassword(String email, EmptyBodyRequest request, ResponseListner responseListner){
        final ResponseListner responseListerlocal = responseListner;
        this.restService.resetPassword(email, request).enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_RESET_PASSWORD, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_RESET_PASSWORD, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void assignImagesToUsers(String authorisation, String contentType, JsonObject body, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.assignImageToUser(authorisation, contentType, body).enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_ASSIGN_IMAGES_TO_USER, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_ASSIGN_IMAGES_TO_USER, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void deleteAccount(String authorisation, String contentType, String userId, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;
        this.restService.deleteUser(authorisation, contentType, userId).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_DELETE_ACCOUNT, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_DELETE_ACCOUNT, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void getListOfTermAndCondition(String authorisation, ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;
        this.restService.getProfileTermAndCondition("application/json", authorisation).enqueue(new Callback<TermsAndConditionsBean>() {

            @Override
            public void onResponse(Call<TermsAndConditionsBean> call, Response<TermsAndConditionsBean> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_TERMS_AND_CONDITIONS, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<TermsAndConditionsBean> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_TERMS_AND_CONDITIONS, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void getMethodsType(ResponseListner responseListner) {
        final ResponseListner responseListerlocal = responseListner;

        this.restService.getMethodTypes().enqueue(new Callback<ArrayList<MethodTypesBean>>() {

            @Override
            public void onResponse(Call<ArrayList<MethodTypesBean>> call, Response<ArrayList<MethodTypesBean>> response) {
                responseListerlocal.onResponse(ResponseConfig.TAG_METHOD_TYPES, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<MethodTypesBean>> call, Throwable t) {
                responseListerlocal.onResponse(ResponseConfig.TAG_METHOD_TYPES, ResponseConfig.API_FAIL, t);
            }
        });
    }

    public void postDeviceRegistration(String authorisation, PostDeviceRegistration deviceRegistration){
        this.restService.postDeviceRegistrationForPushNotification("application/json",authorisation,deviceRegistration).enqueue(new Callback<DeviceData>() {

            @Override
            public void onResponse(Call<DeviceData> call, Response<DeviceData> response) {
                List<DeviceData> notificationDevices = new ArrayList<>();
                notificationDevices.add(response.body());
                manager.insertDeviceData(notificationDevices);

                EventBus.getDefault().post(BasicEvent.NOTIFICATION_ADD_DEVICE);
            }

            @Override
            public void onFailure(Call<DeviceData> call, Throwable t) {

            }
        });
    }

    public void updateDeviceRegistration(String authorisation, String id, PostDeviceRegistration deviceRegistration){
        this.restService.updateDeviceRegistrationForPushNotification("application/json",authorisation, id, deviceRegistration).enqueue(new Callback<DeviceData>() {

            @Override
            public void onResponse(Call<DeviceData> call, Response<DeviceData> response) {
                if (response.code() == 200) {
                    List<DeviceData> notificationDevices = new ArrayList<>();
                    notificationDevices.add(response.body());
                    manager.insertDeviceData(notificationDevices);
                    EventBus.getDefault().post(BasicEvent.NOTIFICATION_UPDATE_DEVICE);
                } else if (response.code() == 404) {
                    EventBus.getDefault().post(BasicEvent.NOTIFICATION_DEVICE_NOT_FOUND);
                }
            }

            @Override
            public void onFailure(Call<DeviceData> call, Throwable t) {

            }
        });
    }

    public void getNotification(String authorisation,String id){
        this.restService.getNotification("application/json",authorisation,id).enqueue(new Callback<List<Notification>>() {

            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                Log.d("TESTSSSS", "getNotification" + response.code());
                if(response.code() == 200) {
                    if (response.body() != null && response.body().size() > 0) {
                        manager.removeNotification();
                        manager.removeNotificationImage();
                        manager.removeNotificationRole();
                        manager.removeNotificationDevice();
                        manager.removeuserObj();
                        manager.removeUserHistory();

                        List<NotificationImage> notificationImages = new ArrayList<>();
                        List<Notification> notificationList = new ArrayList<>();
                        List<NotificationDevice> notificationDevices = new ArrayList<>();
                        List<UserObj> userObjsArray = new ArrayList<>();
                        List<NotificationRoles> notificationRoles = new ArrayList<>();
                        List<UserHistory> userHistories = new ArrayList<>();

                        for (Notification notification : response.body()){
                            notificationList.add(notification);
                            if(notification.device != null) {
                                notificationDevices.add(notification.device);

                                if( notification.device.userObj != null) {
                                    userObjsArray.add(notification.device.userObj);
                                }

                                try {
                                    for(NotificationRoles roles : notification.device.userObj.roles) {
                                        notificationRoles.add(roles);
                                    }
                                    for(UserHistory userHistory : notification.device.userObj.userHistory){
                                        userHistories.add(userHistory);
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }

                            if(notification.image != null) {
                                NotificationImage notificationImage = new NotificationImage();
                                notificationImage.setId(notification.image.getId());
                                notificationImage.setName(notification.image.getName());
                                notificationImage.setUrl(notification.image.getUrl());
                                notificationImage.setFavourite(notification.image.getFavourite());
                                notificationImage.setDeleted(notification.image.getDeleted());
                                notificationImage.setNotification_id(notification.getId());
                                notificationImages.add(notificationImage);

                            }else{
                                NotificationImage notificationImage = new NotificationImage();
                                notificationImage.setId("");
                                notificationImage.setName("");
                                notificationImage.setUrl("");
                                notificationImage.setFavourite("");
                                notificationImage.setDeleted("");
                                notificationImage.setNotification_id(notification.getId());
                                notificationImages.add(notificationImage);
                            }
                        }
                        manager.insertUserHistory(userHistories);
                        manager.insertUserObj(userObjsArray);
                        manager.insertNotificationuserRole(notificationRoles);
                        manager.insertNotificationDevice(notificationDevices);
                        manager.insertNotification(notificationList);
                        manager.insertNotificationImage(notificationImages);
                    }
                    EventBus.getDefault().post(BasicEvent.GET_NOTIFICATION_SUCCESS);
                } else {
                    EventBus.getDefault().post(BasicEvent.GET_NOTIFICATION_FAILED);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                EventBus.getDefault().post(BasicEvent.GET_NOTIFICATION_FAILED);
            }
        });
    }

    public void updateNotificationAlertRead(String authorisation, EmptyBodyRequest emptyBodyRequest, String id){
        this.restService.markPushAlertToBeRead("application/json", authorisation, emptyBodyRequest, id).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void getNotificationByDeviceId(String authorisation, String deviceId){
        this.restService.getNotificationByDevice("application/json", authorisation, deviceId).enqueue(new Callback<List<Notification>>() {

            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                Log.d("TESTSSSS", "getNotificationByDevice" + response.code());
                if(response.code() == 200) {
                    if (response.body() != null && response.body().size() > 0) {
                        manager.removeNotification();
                        manager.removeNotificationImage();
                        manager.removeNotificationRole();
                        manager.removeNotificationDevice();
                        manager.removeuserObj();
                        manager.removeUserHistory();

                        List<NotificationImage> notificationImages = new ArrayList<>();
                        List<Notification> notificationList = new ArrayList<>();
                        List<NotificationDevice> notificationDevices = new ArrayList<>();
                        List<UserObj> userObjsArray = new ArrayList<>();
                        List<NotificationRoles> notificationRoles = new ArrayList<>();
                        List<UserHistory> userHistories = new ArrayList<>();

                        for (Notification notification : response.body()){
                            notificationList.add(notification);
                            if(notification.device != null) {
                                notificationDevices.add(notification.device);

                                if( notification.device.userObj != null) {
                                    userObjsArray.add(notification.device.userObj);
                                }

                                try {
                                    for(NotificationRoles roles : notification.device.userObj.roles) {
                                        notificationRoles.add(roles);
                                    }
                                    for(UserHistory userHistory : notification.device.userObj.userHistory){
                                        userHistories.add(userHistory);
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }

                            if(notification.image != null) {
                                NotificationImage notificationImage = new NotificationImage();
                                notificationImage.setId(notification.image.getId());
                                notificationImage.setName(notification.image.getName());
                                notificationImage.setUrl(notification.image.getUrl());
                                notificationImage.setFavourite(notification.image.getFavourite());
                                notificationImage.setDeleted(notification.image.getDeleted());
                                notificationImage.setNotification_id(notification.getId());
                                notificationImages.add(notificationImage);

                            }else{
                                NotificationImage notificationImage = new NotificationImage();
                                notificationImage.setId("");
                                notificationImage.setName("");
                                notificationImage.setUrl("");
                                notificationImage.setFavourite("");
                                notificationImage.setDeleted("");
                                notificationImage.setNotification_id(notification.getId());
                                notificationImages.add(notificationImage);
                            }
                        }
                        manager.insertUserHistory(userHistories);
                        manager.insertUserObj(userObjsArray);
                        manager.insertNotificationuserRole(notificationRoles);
                        manager.insertNotificationDevice(notificationDevices);
                        manager.insertNotification(notificationList);
                        manager.insertNotificationImage(notificationImages);
                    }
                    EventBus.getDefault().post(BasicEvent.GET_NOTIFICATION_SUCCESS);
                } else {
                    EventBus.getDefault().post(BasicEvent.GET_NOTIFICATION_FAILED);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.d("TESTSSSS", "" + t);
                EventBus.getDefault().post(BasicEvent.GET_NOTIFICATION_FAILED);
            }
        });
    }

    public void getOceanName(double latitude, double longitude, ResponseListner responseListener){
        final ResponseListner responseListenerlocal = responseListener;
        this.restService.getOceanName("application/json", latitude, longitude, "kode").enqueue(new Callback<OceanName>() {

            @Override
            public void onResponse(Call<OceanName> call, Response<OceanName> response) {
                responseListenerlocal.onResponse(ResponseConfig.TAG_OCEAN_NAME, ResponseConfig.API_SUCCESS, response.body());
            }

            @Override
            public void onFailure(Call<OceanName> call, Throwable t) {

            }
        });
    }

    private class GetTrustManager {
        private X509TrustManager trustManager;
        private SSLSocketFactory sslSocketFactory;

        public X509TrustManager getTrustManager() {
            return trustManager;
        }

        public SSLSocketFactory getSslSocketFactory() {
            return sslSocketFactory;
        }

        public GetTrustManager invoke() {

            if (!Config.isStaging) {
                /**
                 * CERT PINNING
                 * todo: need to add the public cert on "AppConstant.CERT_STRING"
                 */
                ByteArrayInputStream derInputStream = new ByteArrayInputStream(Config.CERT_STRING.getBytes());
                CertificateFactory certificateFactory = null;
                try {
                    certificateFactory = CertificateFactory.getInstance("X.509");

                    X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(derInputStream);
                    String alias = cert.getSubjectX500Principal().getName();

                    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    trustStore.load(null);
                    trustStore.setCertificateEntry(alias, cert);
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    kmf.init(trustStore, null);
                    KeyManager[] keyManagers = kmf.getKeyManagers();

                    TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                    tmf.init(trustStore);
                    TrustManager[] trustManagers = tmf.getTrustManagers();

                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(keyManagers, trustManagers, null);

                    sslSocketFactory = sslContext.getSocketFactory();
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (UnrecoverableKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }

            } else {
                TrustManagerFactory trustManagerFactory = null;
                try {
                    trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init((KeyStore) null);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                }
                trustManager = (X509TrustManager) trustManagers[0];
                SSLContext sslContext = null;
                try {
                    sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, new TrustManager[]{trustManager}, null);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                sslSocketFactory = sslContext.getSocketFactory();
            }

            return this;
        }
    }
}