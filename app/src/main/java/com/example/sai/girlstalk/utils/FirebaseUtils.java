package com.example.sai.girlstalk.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

public class FirebaseUtils
{
    private static FirebaseUtils firebaseUtils;
    private FirebaseAuth auth;
    private PhoneAuthProvider phoneAuthProvider;

    private FirebaseUtils(){}

    public static FirebaseUtils getInstance()
    {
        if (firebaseUtils == null) firebaseUtils = new FirebaseUtils();
        return firebaseUtils;
    }

    public FirebaseAuth getAuthInstance()
    {
        if (auth == null) auth = FirebaseAuth.getInstance();
        return auth;
    }

    public PhoneAuthProvider getPhoneAuthInstance()
    {
        if (phoneAuthProvider == null) phoneAuthProvider = PhoneAuthProvider.getInstance();
        return phoneAuthProvider;
    }


}
