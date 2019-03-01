package com.example.sai.girlstalk.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.sai.girlstalk.models.User;
import com.example.sai.girlstalk.repositories.UserRepository;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application)
    {
        super(application);
        userRepository = UserRepository.getInstance(getApplication());
    }

    public LiveData<Boolean> googleLogin(GoogleSignInAccount account) {
        return userRepository.googleLogin(account);
    }

    public LiveData<Boolean> logIn(String email, String password) {
        return userRepository.logIn(email, password);
    }

    public LiveData<Boolean> signUp(User newUser) {
        return userRepository.signUp(newUser);
    }

    public LiveData<Boolean> resetPassword(String email) {
        return userRepository.resetPassword(email);
    }
}
