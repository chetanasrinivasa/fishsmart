package com.mobiddiction.fishsmart.Network;

/**
 * Created by AI on 16/06/2017.
 */

public enum BasicEvent {
    SPECIES_DOWNLOADED,
    SPECIES_ERROR,
    SALT_WATER_ERROR,
    FRESH_LOADED,
    FRESH_ERROR,
    SALT_LOADED,
    SALT_ERROR,
    FRESH_LOADED_OFFLINE,
    SALT_LOADED_OFFLINE,
    ONLINE, OFFLINE,
    GET_ONBOARDING_SUCCESS,
    GET_ONBOARDING_FAILED,
    DO_LOGIN_SUCCESS,
    DO_LOGIN_401,
    DO_LOGIN_FAILED,
    GET_TERM_CONDITION_SUCCESS,
    GET_TERM_CONDITION_FAILED,
    SIGN_UP_SUCCESS,
    SIGN_UP_DUPLICATE,
    SIGN_UP_PASSWORD,
    SIGN_UP_FAILED,
    MAP_LOADED_SUCCESS,
    MAP_LOADED_FAILED,
    GUIDE_LOADED_SUCCESS,
    GUIDE_LOADED_FAILED,
    LICENCE_SUCCESS,
    LICENCE_FAILED,
    ACCEPT_TERMS_SUCCESS,
    ACCEPT_TERMS_FAILED,
    GALLERY_SUCCESS,
    GALLERY_FAILED,
    GET_NOTIFICATION_SUCCESS,
    GET_NOTIFICATION_FAILED,
    DRIVER_SUCCESS,
    NOTIFICATION_ADD_DEVICE,
    NOTIFICATION_UPDATE_DEVICE,
    NOTIFICATION_DEVICE_NOT_FOUND,
    FEATURED_SUCCESS,
    FEATURED_FAILED,
    FEATURED_SPECIES_DOWNLOADED,
    FEATURED_SPECIES_ERROR
}