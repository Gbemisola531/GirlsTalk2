package com.example.sai.girlstalk.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.sai.girlstalk.repositories.UserRepository;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    public UserViewModel() {
        userRepository = UserRepository.getInstance();
    }

    public LiveData<Boolean> googleLogin(GoogleSignInAccount account) {
        return userRepository.googleLogin(account);
    }

    public LiveData<String> signIn(String email, String password) {
        return userRepository.signIn(email, password);
    }
}
