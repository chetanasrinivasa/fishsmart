package com.mobiddiction.fishsmart.Species;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by swiftly on 11/05/17.
 */

public class SpeciesDownloader {
    // TODO: Ability to download the detail of a species after saving a species
    private static final SpeciesDownloader instance = new SpeciesDownloader();
    private static Realm realm = Realm.getDefaultInstance();
    ArrayList<SpeciesModel> tempFreshWaterSpecies = new ArrayList<>();  //safe
    Context context;
    private SpeciesDownloader(){}

    public static SpeciesDownloader getInstance() {
        return instance;
    }

    // Ability to download a detail view

    public static boolean isFishSaved(String id) {
        Log.v("L", "Is Fish saved for id " + id);
        SpeciesModel fish = realm.where(SpeciesModel.class).equalTo("id", id).findFirst();
//        Log.v("L", "Fish..." + fish.getTitle() + " isSaved: " + fish.isSaved);
        if (fish != null && fish.isSaved != null) {
            return fish.isSaved;
        } else {
            return false;
        }
    }

    public static void saveFish(String id) {
        updateFishSavedState(id, true);
    }

    public static void unsaveFish(String id) {
        updateFishSavedState(id, false);
    }

    private static void updateFishSavedState(String id, Boolean newState) {
        realm.beginTransaction();
        SpeciesModel fish = realm.where(SpeciesModel.class).equalTo("id", id).findFirst();
        fish.isSaved = newState;
        realm.commitTransaction();
        EventBus.getDefault().post(fish);
    }


    public static boolean isFishInDB(String id) {
        SpeciesModel existingObj = realm.where(SpeciesModel.class).equalTo("id", id).findFirst();

        return (existingObj != null);
    }

    public static SpeciesModel getSpecies(String id) {
        return realm.where(SpeciesModel.class).equalTo("id", id).findFirst();
    }

    public static void storeFishHandler(SpeciesModel species, Context caller) {

        if (isFishInDB(species.getId())) {
            realm.beginTransaction();

            // obtain the results of a query
            final RealmResults<SpeciesModel> results = realm.where(SpeciesModel.class).findAll();
            SpeciesModel delObj = results.where().equalTo("id", species.getId()).findFirst();
            delObj.deleteFromRealm();

            realm.commitTransaction();
        }
        else {
            realm.beginTransaction();

            SpeciesModel savedSpecies = realm.createObject(SpeciesModel.class); // Create managed objects directly

            savedSpecies.setId(species.getId());
            savedSpecies.setSpeciesType(species.getSpeciesType());
            savedSpecies.setTitle(species.getTitle());
            savedSpecies.setDescription(species.getDescription());
            savedSpecies.setBagLimitForCardView(species.getBagLimitForCardView());
            savedSpecies.setSizeLimit(species.getSizeLimit());
            savedSpecies.setImage(species.getImage());
            savedSpecies.setThumbnail(species.getThumbnail());
            savedSpecies.setGroupName(species.getGroupName());
            savedSpecies.setGrouped(species.getGrouped());

            // Download more information
            realm.commitTransaction();

            // How do we make it not run async...


            callToAsync(savedSpecies.getId(), caller);
        }
    }

    public static void addFishIntoDB(SpeciesModel species, Context caller) {
        // Either create a new version or
        SpeciesModel savedSpecies;
        realm.beginTransaction();

        savedSpecies = getSpecies(species.getId());
        if (savedSpecies == null) {
            savedSpecies = realm.createObject(SpeciesModel.class); // Create managed objects directly
            savedSpecies.setId(species.getId());
            savedSpecies.setSpeciesType(species.getSpeciesType());
            savedSpecies.setTitle(species.getTitle());
            savedSpecies.setDescription(species.getDescription());
            savedSpecies.setBagLimitForCardView(species.getBagLimitForCardView());
            savedSpecies.setSizeLimit(species.getSizeLimit());
            savedSpecies.setImage(species.getImage());
            savedSpecies.setThumbnail(species.getThumbnail());
            savedSpecies.setGroupName(species.getGroupName());
            savedSpecies.setGrouped(species.getGrouped());
        }

        realm.commitTransaction();
    }

    private static void callToAsync(String fishId, Context caller) {
        ArrayList<SpeciesDetailModel> speciesdetailList = new ArrayList<>();

        new SpeciesDetailAsync(caller, speciesdetailList).execute("/api/species?Id=" + fishId);
    }

    public static SpeciesDetailModel getSpeciesDetails(String fishId) {
        final RealmResults<SpeciesDetailModel> results = realm.where(SpeciesDetailModel.class).findAll();
        SpeciesDetailModel existingObject = results.where().equalTo("id", fishId).findFirst();

        return existingObject;
    }

    public static void saveSpeciesDetails(Context context, ArrayList<SpeciesDetailModel> speciesdetaillist) {
        ArrayList<SpeciesDetailModel> SpeciesDetailList = new ArrayList<>();

        for(SpeciesDetailModel speciesDetail : speciesdetaillist) {
            String id = speciesDetail.id;

            realm.beginTransaction();

            final RealmResults<SpeciesDetailModel> results = realm.where(SpeciesDetailModel.class).findAll();
            SpeciesDetailModel existingObject = results.where().equalTo("id", id).findFirst();

            SpeciesDetailModel aSpecies = realm.createObject(SpeciesDetailModel.class); // Create managed objects directly
            if (existingObject != null) {
                aSpecies = existingObject;
            }

            aSpecies.id = id;
            aSpecies.setTitle(speciesDetail.getTitle());
            aSpecies.setDescription(speciesDetail.getDescription());
            aSpecies.setGrouped(speciesDetail.getGrouped());
            aSpecies.setSpeciesType(speciesDetail.getSpeciesType());
            aSpecies.setSizeLimit(speciesDetail.getSizeLimit());
            aSpecies.setThumbnailImage(speciesDetail.getThumbnailImage());

            aSpecies.setFishGroupName(speciesDetail.getFishGroupName());
            aSpecies.setFishGroupTitle(speciesDetail.getFishGroupTitle());
            aSpecies.setFishGroupDesc(speciesDetail.getFishGroupDesc());

            aSpecies.setAbout(speciesDetail.getAbout());
            aSpecies.setSize(speciesDetail.getSize());
            aSpecies.setConfusingSpecies(speciesDetail.getConfusingSpecies());
            aSpecies.setCharacteristics(speciesDetail.getCharacteristics());

            aSpecies.setLegalSize(speciesDetail.getLegalSize());
            aSpecies.setBagLimit(speciesDetail.getBagLimit());
            aSpecies.setPossession(speciesDetail.getPossession());

            realm.commitTransaction();
            FutureTarget<File> future = Glide.with(context)
                    .load(speciesDetail.getThumbnailImage())
                    .downloadOnly(500, 500);
            SpeciesDetailList.add(aSpecies);
        }

    }

    public static ArrayList<SpeciesModel> getAllFreshWaterSpecies() {
        return getAllSpeciesForType("FRESHWATER");
    }

    public static ArrayList<SpeciesModel> getAllSaltWaterSpecies() {
        return getAllSpeciesForType("SALTWATER");
    }

    public static ArrayList<SpeciesModel> getAllSpecies() {
        ArrayList<SpeciesModel> speciesList = new ArrayList<>();
        realm.beginTransaction();

        // obtain the results of a query
        final RealmResults<SpeciesModel> results = realm.where(SpeciesModel.class).findAll();
        realm.commitTransaction();
        speciesList.addAll(results);
        return speciesList;
    }

    private static ArrayList<SpeciesModel> getAllSpeciesForType(String type) {
        ArrayList<SpeciesModel> matchedSpecies = new ArrayList<>();
        realm.beginTransaction();
        final RealmResults<SpeciesModel> results = realm.where(SpeciesModel.class).findAll();
        Log.v("L", "All in DB "+ results.size());
        matchedSpecies.addAll(realm.copyFromRealm(results.where().equalTo("speciesType", type).findAll()));
        realm.commitTransaction();
        return matchedSpecies;
    }

    public static ArrayList<SpeciesModel> getSavedSpecies() {

        ArrayList<SpeciesModel> speciesListSaved = new ArrayList<>();
        realm.beginTransaction();

        // obtain the results of a query
        final RealmResults<SpeciesModel> results = realm.where(SpeciesModel.class).findAll();
        speciesListSaved.addAll(realm.copyFromRealm(results.where().equalTo("isSaved", true).findAll()));
        realm.commitTransaction();
        return speciesListSaved;
    }

    public void downloadFreshWaterSpecies(Context ctx) {
        this.context = ctx;
        new FreshWaterAsync(SpeciesDownloader.this, this.tempFreshWaterSpecies).execute("/api/speciesData");
    }

    public void hasDownloadedFreshWater() {
        for (SpeciesModel aSpecies: this.tempFreshWaterSpecies) {
            addFishIntoDB(aSpecies, context);
        }
    }
}

