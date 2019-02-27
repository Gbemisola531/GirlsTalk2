package com.example.sai.girlstalk.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.sai.girlstalk.utils.FirebaseUtils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class UserRepository {
    private FirebaseUtils firebaseUtils;
    private static UserRepository userRepository;

    private UserRepository() {
        firebaseUtils = FirebaseUtils.getInstance();
    }

    public static UserRepository getInstance() {
        if (userRepository == null) userRepository = new UserRepository();
        return userRepository;
    }

    public LiveData<Boolean> googleLogin(GoogleSignInAccount account) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseUtils.getAuthInstance().signInWithCredential(credential).addOnCompleteListener(task ->
                result.setValue(task.isSuccessful()));
        return result;
    }

    public LiveData<String> signIn(String email, String password) {
        MutableLiveData<String> result = new MutableLiveData<>();
        firebaseUtils.getAuthInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
        {
            if (task.isSuccessful()) result.setValue(" ");
            else result.setValue(Objects.requireNonNull(task.getException()).getMessage());
        });
        return result;
    }
}
