package com.mobiddiction.fishsmart.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiddiction.fishsmart.Network.BasicEvent;
import com.mobiddiction.fishsmart.Network.ModelManager;
import com.mobiddiction.fishsmart.dao.AllSpecies;
import com.mobiddiction.fishsmart.dao.AllSpeciesGroup;
import com.mobiddiction.fishsmart.dao.AllSpeciesfishFacts;
import com.mobiddiction.fishsmart.dao.AllSpeciesrules;
import com.mobiddiction.fishsmart.dao.FreshWaterNewData;
import com.mobiddiction.fishsmart.dao.FreshWaterfishFacts;
import com.mobiddiction.fishsmart.dao.FreshWaterfishGroup;
import com.mobiddiction.fishsmart.dao.FreshWaterrules;
import com.mobiddiction.fishsmart.dao.NEWSpeciesData;
import com.mobiddiction.fishsmart.dao.SaltWaterNewData;
import com.mobiddiction.fishsmart.dao.SaltWaterfishFacts;
import com.mobiddiction.fishsmart.dao.SaltWaterfishGroup;
import com.mobiddiction.fishsmart.dao.SaltWaterrules;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class loadAllSpeciesData {

    Activity callingActivty = null;
    ModelManager mModel = ModelManager.getInstance();

    public loadAllSpeciesData(Activity mActivity) {
        this.callingActivty = mActivity;
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.callingActivty.getAssets().open("species.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void loadDataFromLocal(boolean fromlocal, String response, Response<List<NEWSpeciesData>> responseObj, Handler mHandler) {
       // android.util.Log.d("loadDataFromLocal", "Loading from local Data");
       // android.util.Log.d("loadDataFromLocal", "response = " + response);
        if (fromlocal) {
            if (mModel != null) {
                if ((mModel.getFreshWater() != null) && (mModel.getSaltWater() != null)) {
                   // android.util.Log.d("loadDataFromLocal", "mModel.getFresh() != null");
                    if ((mModel.getFreshWater().size() > 0) && (mModel.getSaltWater().size() > 0)) {
                        //Data is already loaded
                        //android.util.Log.d("loadDataFromLocal", "Data is already loaded");
                        return;
                    }
                }
            }
            response = null;
            response = loadJSONFromAsset();
        }

        LoadSpeciesFromLocal task = new LoadSpeciesFromLocal(fromlocal,response,responseObj, mHandler);
        task.execute();
    }

    private static class LoadSpeciesFromLocal extends AsyncTask<String, String, String> {

        boolean mFromlocal = false;
        String mResponse = null;
        Response<List<NEWSpeciesData>> mSpeciesResponse = null;
        Handler loadHandler = null;
        public LoadSpeciesFromLocal(boolean fromlocal, String response, Response<List<NEWSpeciesData>> responseObj, Handler mHandler) {
            super();
            this.mFromlocal = fromlocal;
            this.mResponse = response;
            this.mSpeciesResponse = responseObj;
            this.loadHandler = mHandler;
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
               // android.util.Log.d("LoadSpeciesFromLocal", "Data NOT loaded");
                ModelManager.getInstance().removeAllData();
                ModelManager.getInstance().removeAllNewSpeciesData();
                ModelManager.getInstance().removeAllSaltWater();
                ModelManager.getInstance().removeAllFreshWater();
                ModelManager.getInstance().removeAllSaltWaterfishGroup();
                ModelManager.getInstance().removeAllFreshWaterfishGroup();
                ModelManager.getInstance().removeAllSaltWaterfishFacts();
                ModelManager.getInstance().removeAllFreshWaterfishFacts();
                ModelManager.getInstance().removeAllSaltWaterrules();
                ModelManager.getInstance().removeAllFreshWaterrules();
                ModelManager.getInstance().removeAllSpeciesData();
                ModelManager.getInstance().removeAllSpeciesGroup();
                ModelManager.getInstance().removeAllSpeciesfishFacts();
                ModelManager.getInstance().removeAllSpeciesrules();

                List<AllSpecies> allSpeciesList = new ArrayList<>();
                List<AllSpeciesGroup> allSpeciesGroupList = new ArrayList<>();
                List<AllSpeciesfishFacts> allSpeciesfishFactsList = new ArrayList<>();
                List<AllSpeciesrules> allSpeciesrulesList = new ArrayList<>();
                List<NEWSpeciesData> newSpeciesDataList = new ArrayList<>();
                final Gson gson = new Gson();
                Type category = new TypeToken<List<NEWSpeciesData>>() { }.getType();
                List<NEWSpeciesData> newSpeciesData = null;

                //Log.d("LoadSpeciesFromLocal","response[0,1] = " + mResponse.substring(0,1));
                if(this.mFromlocal) {
                   // Log.d("LoadSpeciesFromLocal","this.mFromlocal = " + String.valueOf(this.mFromlocal));
                    newSpeciesData = gson.fromJson(this.mResponse, category);
                }else{
                    //Log.d("LoadSpeciesFromLocal","this.mFromlocal = " + String.valueOf(this.mFromlocal));
                    newSpeciesData = this.mSpeciesResponse.body();
                }

                for (NEWSpeciesData data : newSpeciesData) {
                    //////Log.d("LoadSpeciesFromLocal","data.getId() = " + data.getId());

                    if (!data.getContent().isEmpty()) {
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

                        JSONObject objects = null;
                        SaltWaterNewData saltWaterNewData = new SaltWaterNewData();
                        SaltWaterfishGroup saltWaterfishGroup = new SaltWaterfishGroup();
                        SaltWaterfishFacts saltWaterfishFacts = new SaltWaterfishFacts();
                        SaltWaterrules saltWaterrules = new SaltWaterrules();
                        FreshWaterNewData freshWaterNewData = new FreshWaterNewData();
                        FreshWaterfishGroup freshWaterfishGroup = new FreshWaterfishGroup();
                        FreshWaterfishFacts freshWaterfishFacts = new FreshWaterfishFacts();
                        FreshWaterrules freshWaterrules = new FreshWaterrules();
                        AllSpecies allSpeciesData = new AllSpecies();
                        AllSpeciesGroup allSpeciesGroup = new AllSpeciesGroup();
                        AllSpeciesfishFacts allSpeciesfishFacts = new AllSpeciesfishFacts();
                        AllSpeciesrules allSpeciesrules = new AllSpeciesrules();

                        try {
                            objects = jsonArrays.getJSONObject(0);
                            if (objects.getString("value").equalsIgnoreCase("SALTWATER")) {

                                allSpeciesGroup.setId(Integer.parseInt(data.getId() + ""));
                                allSpeciesGroup.setFreshID(data.getId() + "");
                                saltWaterfishGroup.setSaltID(data.getId() + "");
                                saltWaterfishGroup.setId(Integer.parseInt(data.getId() + ""));
                                saltWaterfishFacts.setSaltID(data.getId() + "");
                                saltWaterfishFacts.setId(Integer.parseInt(data.getId() + ""));
                                saltWaterrules.setSaltID(data.getId() + "");
                                saltWaterrules.setId(Integer.parseInt(data.getId() + ""));
                                allSpeciesData.setId(data.getId() + "");
                                allSpeciesfishFacts.setId(Integer.parseInt(data.getId() + ""));
                                allSpeciesfishFacts.setFreshID(data.getId() + "");
                                allSpeciesrules.setFreshID(data.getId() + "");
                                allSpeciesrules.setId(Integer.parseInt(data.getId() + ""));
                                saltWaterNewData.setId(data.getId() + "");

                                if (objects.getString("value").equalsIgnoreCase("SALTWATER")) {
                                    saltWaterNewData.setSpeciesType("SALTWATER");
                                    saltWaterfishGroup.setGroupType("SALTWATER");
                                    allSpeciesData.setSpeciesType("SALTWATER");
                                    allSpeciesGroup.setGroupType("SALTWATER");
                                }

                                for (int x = 0; x < jsonArrays.length(); x++) {
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Fish name")) {
                                        saltWaterNewData.setTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        // saltWaterfishGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));
                                        // saltWaterfishGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        // allSpeciesGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));
                                        // allSpeciesGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Group type")) {
                                        saltWaterfishGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));

                                        saltWaterfishGroup.setGroupType(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupType(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setGroupType(jsonArrays.getJSONObject(x).getString("value"));
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Group title")) {
                                        saltWaterfishGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Sub header")) {
                                        allSpeciesData.setSubHeader(jsonArrays.getJSONObject(x).getString("value"));
                                        saltWaterNewData.setSubHeader(jsonArrays.getJSONObject(x).getString("value"));
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Size limit")) {
                                        saltWaterNewData.setSizeLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setSizeLimit(jsonArrays.getJSONObject(x).getString("value"));
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Grouped")) {
                                        allSpeciesData.setGrouped(jsonArrays.getJSONObject(x).getString("value").equals("1"));
                                        saltWaterNewData.setGrouped(jsonArrays.getJSONObject(x).getString("value").equals("1"));
                                    }


                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Group description")) {
                                        allSpeciesData.setDescription(jsonArrays.getJSONObject(x).getString("value"));
                                        saltWaterNewData.setDescription(jsonArrays.getJSONObject(x).getString("value"));
                                        saltWaterfishGroup.setGroupDescription(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupDescription(jsonArrays.getJSONObject(x).getString("value"));

                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Bag limit")) {
                                        saltWaterrules.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        saltWaterNewData.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Rule bag limit")) {//!!!!!!!!!!!!!!!!!!!setRuleBagLimit
                                        saltWaterrules.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        saltWaterNewData.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("About")) {
                                        saltWaterfishFacts.setAbout(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setAbout(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Size")) {
                                        saltWaterfishFacts.setSize(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setSize(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Characteristics")) {
                                        saltWaterfishFacts.setCharacteristics(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setCharacteristics(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Confusing species")) {
                                        saltWaterfishFacts.setConfusingSpecies(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setConfusingSpecies(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Rule legal size")) {
                                        saltWaterrules.setLegalSize(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setLegalSize(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Rule possession")) {
                                        saltWaterrules.setPossession(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setPossession(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").equalsIgnoreCase("Media")) {
                                        saltWaterNewData.setImage(jsonArrays.getJSONObject(x).getString("value"));
                                        saltWaterNewData.setThumbnailImage(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setThumbnailImage(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setImage(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                }

                                ModelManager.getInstance().insertAllSaltWaterNewData(saltWaterNewData);
                                ModelManager.getInstance().insertSaltWaterfishGroup(saltWaterfishGroup);
                                ModelManager.getInstance().insertSaltWaterfishFacts(saltWaterfishFacts);
                                ModelManager.getInstance().insertSaltWaterrules(saltWaterrules);

                                allSpeciesList.add(allSpeciesData);
                                allSpeciesGroupList.add(allSpeciesGroup);
                                allSpeciesfishFactsList.add(allSpeciesfishFacts);
                                allSpeciesrulesList.add(allSpeciesrules);

                            } else if (objects.getString("value").equalsIgnoreCase("FRESHWATER")) {

                                freshWaterfishGroup.setFreshID(data.getId() + "");
                                freshWaterfishGroup.setId(Integer.parseInt(data.getId() + ""));
                                freshWaterfishFacts.setFreshID(data.getId() + "");
                                freshWaterfishFacts.setId(Integer.parseInt(data.getId() + ""));
                                freshWaterrules.setFreshID(data.getId() + "");
                                freshWaterrules.setId(Integer.parseInt(data.getId() + ""));
                                allSpeciesData.setId(data.getId() + "");
                                allSpeciesGroup.setFreshID(data.getId() + "");
                                allSpeciesGroup.setId(Integer.parseInt(data.getId() + ""));
                                allSpeciesfishFacts.setId(Integer.parseInt(data.getId() + ""));
                                allSpeciesfishFacts.setFreshID(data.getId() + "");
                                allSpeciesrules.setFreshID(data.getId() + "");
                                allSpeciesrules.setId(Integer.parseInt(data.getId() + ""));
                                freshWaterNewData.setId(data.getId() + "");
                                /**
                                 * FRESHWATER
                                 */
                                if (objects.getString("value").equalsIgnoreCase("FRESHWATER")) {
                                    freshWaterNewData.setSpeciesType("FRESHWATER");
                                    freshWaterfishGroup.setGroupType("FRESHWATER");
                                    allSpeciesData.setSpeciesType("FRESHWATER");
                                    allSpeciesGroup.setGroupType("FRESHWATER");
                                }

                                for (int x = 0; x < jsonArrays.length(); x++) {
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Fish name")) {
                                        freshWaterNewData.setTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        //freshWaterfishGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));
                                        //freshWaterfishGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        // allSpeciesGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));
                                        // allSpeciesGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Group type")) {
                                        freshWaterfishGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupName(jsonArrays.getJSONObject(x).getString("value"));

                                        freshWaterfishGroup.setGroupType(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupType(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setGroupType(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Group title")) {
                                        freshWaterfishGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupTitle(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Sub header")) {
                                        allSpeciesData.setSubHeader(jsonArrays.getJSONObject(x).getString("value"));
                                        freshWaterNewData.setSubHeader(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Size limit")) {
                                        freshWaterNewData.setSizeLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setSizeLimit(jsonArrays.getJSONObject(x).getString("value"));
                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Grouped")) {
                                        allSpeciesData.setGrouped(jsonArrays.getJSONObject(x).getString("value").equals("1"));
                                        freshWaterNewData.setGrouped(jsonArrays.getJSONObject(x).getString("value").equals("1"));
                                    }


                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Group description")) {
                                        allSpeciesData.setDescription(jsonArrays.getJSONObject(x).getString("value"));
                                        freshWaterNewData.setDescription(jsonArrays.getJSONObject(x).getString("value"));
                                        freshWaterfishGroup.setGroupDescription(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesGroup.setGroupDescription(jsonArrays.getJSONObject(x).getString("value"));

                                    }

                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Bag limit")) {
                                        freshWaterrules.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        freshWaterNewData.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Rule bag limit")) {//!!!!!!!!!1setRuleBagLimite
                                        freshWaterrules.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        freshWaterNewData.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setRuleBagLimit(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("About")) {
                                        freshWaterfishFacts.setAbout(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setAbout(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Size")) {
                                        freshWaterfishFacts.setSize(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setSize(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Characteristics")) {
                                        freshWaterfishFacts.setCharacteristics(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setCharacteristics(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Confusing species")) {
                                        freshWaterfishFacts.setConfusingSpecies(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesfishFacts.setConfusingSpecies(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Rule legal size")) {
                                        freshWaterrules.setLegalSize(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setLegalSize(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").contains("Rule possession")) {
                                        freshWaterrules.setPossession(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesrules.setPossession(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                    if (jsonArrays.getJSONObject(x).getString("key").equalsIgnoreCase("Media")) {
                                        freshWaterNewData.setImage(jsonArrays.getJSONObject(x).getString("value"));
                                        freshWaterNewData.setThumbnailImage(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setThumbnailImage(jsonArrays.getJSONObject(x).getString("value"));
                                        allSpeciesData.setImage(jsonArrays.getJSONObject(x).getString("value"));
                                    }
                                }
                                ModelManager.getInstance().insertAllFreshWaterNewData(freshWaterNewData);
                                ModelManager.getInstance().insertFreshWaterfishGroup(freshWaterfishGroup);
                                ModelManager.getInstance().insertFreshWaterfishFacts(freshWaterfishFacts);
                                ModelManager.getInstance().insertFreshWaterrules(freshWaterrules);
                                allSpeciesList.add(allSpeciesData);
                                allSpeciesGroupList.add(allSpeciesGroup);
                                allSpeciesfishFactsList.add(allSpeciesfishFacts);
                                allSpeciesrulesList.add(allSpeciesrules);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        newSpeciesDataList.add(data);
                    }
                }

                ModelManager.getInstance().insertAllNewSpecies(newSpeciesDataList);
                ModelManager.getInstance().insertAllSpecies(allSpeciesList);
                ModelManager.getInstance().insertAllSpeciesGroup(allSpeciesGroupList);
                ModelManager.getInstance().insertAllSpeciesfishFacts(allSpeciesfishFactsList);
                ModelManager.getInstance().insertAllSpeciesrules(allSpeciesrulesList);
            } catch (Exception e) {
                ////Log.d("loadAllSpeciesData", "ERROR : " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            loadHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    EventBus.getDefault().post(BasicEvent.SPECIES_DOWNLOADED);

                }
            }, 10);

        }

    }
}
