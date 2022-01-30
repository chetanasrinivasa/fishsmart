package com.mobiddiction.fishsmart.Network;

import com.google.gson.JsonObject;
import com.mobiddiction.fishsmart.CatchLog.OceanName;
import com.mobiddiction.fishsmart.EmptyBodyRequest;
import com.mobiddiction.fishsmart.RegistrationModel;
import com.mobiddiction.fishsmart.SignIn.SignInBody;
import com.mobiddiction.fishsmart.Species.FeaturedSpeciesRequestBody;
import com.mobiddiction.fishsmart.bean.CatchLogBean;
import com.mobiddiction.fishsmart.bean.CatchLogImageBean;
import com.mobiddiction.fishsmart.bean.MethodTypesBean;
import com.mobiddiction.fishsmart.bean.TermsAndConditionsBean;
import com.mobiddiction.fishsmart.bean.UserBean;
import com.mobiddiction.fishsmart.dao.DeviceData;
import com.mobiddiction.fishsmart.dao.Gallery;
import com.mobiddiction.fishsmart.dao.LoginResponse;
import com.mobiddiction.fishsmart.dao.NEWSpeciesData;
import com.mobiddiction.fishsmart.dao.NewGuide;
import com.mobiddiction.fishsmart.dao.NewLicence;
import com.mobiddiction.fishsmart.dao.NewMap;
import com.mobiddiction.fishsmart.dao.Onboarding;
import com.mobiddiction.fishsmart.dao.SignUp;
import com.mobiddiction.fishsmart.dao.TermAndCondition;
import com.mobiddiction.fishsmart.dao.Notification;
import com.mobiddiction.fishsmart.notification.PostDeviceRegistration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AI on 16/06/2017.
 */

public interface FishService {

    /**
     * NEW API CHANGES
     */
    @POST("login")
    Call<LoginResponse> doLogin(@Body SignInBody signInBody);

    @POST("contentNoAuth/findAllByExample")
    Call<List<NEWSpeciesData>> getFeaturedSpecies(@Body FeaturedSpeciesRequestBody mRequestBody);

    @GET("onboarding")
    Call<List<Onboarding>> getOnBoarding(@Header("Content-Type") String contentType);

    @GET("terms/latest")
    Call<TermAndCondition> getTermAndCondition(@Header("Content-Type") String contentType,
                             @Header("Authorization") String authorization);

    @POST("user/registration")
    Call<SignUp> doRegistration(@Header("Content-Type") String contentType,
                        @Body RegistrationModel registrationModel);

    @GET("contentNoAuth/byTypeName?type=species")
    Call<List<NEWSpeciesData>> getSpecies();

    @GET("contentNoAuth/byTypeName?type=map")
    Call<List<NewMap>> getMap();

    @GET("contentNoAuth/byTypeName?type=guide")
    Call<List<NewGuide>> getGuide();

    @GET("contentNoAuth/byTypeName?type=ContactLicensing")
    Call<List<NewLicence>> getLicense();

    /*@PATCH("/user/acceptTerms/{id}")
    Call<ResponseBody> doAcceptTerms(@Header("Content-Type") String contentType,
                       @Header("Authorization") String authorization,
                       @Path("id") String id,
                       @Body String body);*/

    @GET("galleryImage/approved/1")
    Call<List<Gallery>> getGalleryImage();

    @GET("contentNoAuth/featured/byTypeName?type=species")
    Call<List<Gallery>> getFeaturedSpecies(@Header("Content-Type") String contentType);

    @GET("catch/byUser/{userid}")
    Call<ArrayList<CatchLogBean>> getCatchLog(@Header("Content-Type") String contentType,
                     @Header("Authorization") String authorization,
                     @Path("userid") String userid);

    @GET("contentNoAuth/byTypeName?type=CatchSpecies")
    Call<List<CatchLogBean>> getAllCatchSpecies();

    @GET("image/listByCatchId")
    Call<ArrayList<CatchLogImageBean>> getCatchImageList(@Header("Authorization") String authorization,
                           @Query("id") String id);

    @POST("catch")
    Call<JsonObject> addCatch(@Header("Authorization") String authorization,
                  @Header("Content-Type") String contentType,
                  @Body JsonObject params);

    @PATCH("document/assignToCatch")
    Call<JsonObject> assignImageToCatch(@Header("Authorization") String authorization,
                            @Header("Content-Type") String contentType,
                            @Body JsonObject params);

    @PUT("image/{id}")
    Call<JsonObject> updateCatchLogImage(@Header("Authorization") String authorization,
                             @Header("Content-Type") String contentType,
                             @Path("id") String id,
                             @Body JsonObject params);

    @DELETE("catch/{id}")
    Call<Void> deleteCatch(@Header("Authorization") String authorization,
                               @Header("Content-Type") String contentType,
                               @Path("id") String id);

    @PUT("catch/{id}")
    Call<JsonObject> updateCatchLog(@Header("Authorization") String authorization,
                        @Header("Content-Type") String contentType,
                        @Path("id") String id,
                        @Body JsonObject params);

    @GET("user/{userid}")
    Call<UserBean> getProfile(@Header("Authorization") String authorization,
                    @Header("Content-Type") String contentType,
                    @Path("userid") String id);

    @PUT("user/{userid}")
    Call<JsonObject> updateProfile(@Header("Authorization") String authorization,
                       @Header("Content-Type") String contentType,
                       @Path("userid") String id,
                       @Body JsonObject params);

    @POST("user/updatePassword")
    Call<JsonObject> updatePassword(@Header("Authorization") String authorization,
                        @Header("Content-Type") String contentType,
                        @Query("id") String id,
                        @Body JsonObject params);

    @POST("user/resetPassword")
    Call<Void> resetPassword(@Query("email") String email,
                       @Body EmptyBodyRequest request);

    @PATCH("document/assignToUser")
    Call<JsonObject> assignImageToUser(@Header("Authorization") String authorization,
                           @Header("Content-Type") String contentType,
                           @Body JsonObject params);

    @DELETE("user/{id}")
    Call<Void> deleteUser(@Header("Authorization") String authorization,
                    @Header("Content-Type") String contentType,
                    @Path("id") String id);

    @GET("terms/latest")
    Call<TermsAndConditionsBean> getProfileTermAndCondition(@Header("Content-Type") String contentType,
                                    @Header("Authorization") String authorization);

    @GET("contentNoAuth/byTypeName?type=method")
    Call<ArrayList<MethodTypesBean>> getMethodTypes();

    @POST("device/add")
    Call<DeviceData> postDeviceRegistrationForPushNotification(@Header("Content-Type") String contentType,
                                                   @Header("Authorization") String authorization,
                                                   @Body PostDeviceRegistration postDeviceRegistration);

    @PUT("device/update/{id}")
    Call<DeviceData> updateDeviceRegistrationForPushNotification(@Header("Content-Type") String contentType,
                                                     @Header("Authorization") String authorization,
                                                     @Path("id") String id,
                                                     @Body PostDeviceRegistration postDeviceRegistration);

    @GET("notification/byUserId")
    Call<List<Notification>> getNotification(@Header("Content-Type") String contentType,
                         @Header("Authorization") String authorization,
                         @Query("id") String id);

    @PATCH("notification/markAsRead/{id}")
    Call<Void> markPushAlertToBeRead(@Header("Content-Type") String contentType,
                               @Header("Authorization") String authorization,
                               @Body EmptyBodyRequest emptyBodyRequest,
                               @Path("id") String id);

    @GET("notification/byDeviceId/{deviceId}")
    Call<List<Notification>> getNotificationByDevice(@Header("Content-Type") String contentType,
                                 @Header("Authorization") String authorization,
                                 @Path("deviceId") String deviceId);

    @GET("oceanJSON")
    Call<OceanName> getOceanName(@Header("Content-Type") String contentType,
                      @Query("lat") double latitude,
                      @Query("lng") double longitude,
                      @Query("username") String username);

}