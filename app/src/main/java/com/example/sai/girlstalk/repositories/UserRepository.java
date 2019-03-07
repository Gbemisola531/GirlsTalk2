package com.example.sai.girlstalk.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.example.sai.girlstalk.models.User;
import com.example.sai.girlstalk.utils.FirebaseUtils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class UserRepository {
    private FirebaseUtils firebaseUtils;
    private static UserRepository userRepository;
    private Application application;

    private UserRepository(Application application) {
        firebaseUtils = FirebaseUtils.getInstance();
        this.application = application;
    }

    public static UserRepository getInstance(Application application) {
        if (userRepository == null) userRepository = new UserRepository(application);
        return userRepository;
    }

    public LiveData<Boolean> googleLogin(GoogleSignInAccount account) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseUtils.getAuthInstance().signInWithCredential(credential).addOnCompleteListener(task ->
                result.setValue(task.isSuccessful()));
        return result;
    }

    public LiveData<Boolean> logIn(String email, String password) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        firebaseUtils.getAuthInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task ->
        {
            if (task.isSuccessful()) if (Objects.requireNonNull(firebaseUtils.getAuthInstance().getCurrentUser()).isEmailVerified())
                    result.setValue(true);
            else {
                firebaseUtils.getAuthInstance().signOut();
                result.setValue(false); }
            else {
                result.setValue(false);
                Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return result;
    }

    public LiveData<Boolean> signUp(User newUser) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        firebaseUtils.getAuthInstance().createUserWithEmailAndPassword(newUser.getProfile().getEmail(), newUser.getPassword()).addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
                Objects.requireNonNull(firebaseUtils.getAuthInstance().getCurrentUser()).sendEmailVerification().addOnCompleteListener(emailResult ->
                {
                    if (emailResult.isSuccessful())
                    {
                        firebaseUtils.getDbInstance().collection("Users").add(newUser).addOnCompleteListener(addResult ->
                        {
                            if (addResult.isSuccessful())
                            {
                                firebaseUtils.getAuthInstance().signOut();
                                result.setValue(true);
                            }
                            else {
                                Objects.requireNonNull(firebaseUtils.getAuthInstance().getCurrentUser()).delete();
                                firebaseUtils.getAuthInstance().signOut();
                                result.setValue(false);
                            }
                        });
                    }
                    else {
                        Objects.requireNonNull(firebaseUtils.getAuthInstance().getCurrentUser()).delete();
                        firebaseUtils.getAuthInstance().signOut();
                        result.setValue(false);
                    }
                });
            else result.setValue(false);
        });
        return result;
    }

    public LiveData<Boolean> resetPassword(String email)
    {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        firebaseUtils.getAuthInstance().sendPasswordResetEmail(email).addOnCompleteListener(task ->
        {
            if (task.isSuccessful()) result.setValue(true);
            else {
                Toast.makeText(application, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                result.setValue(false);
            }
        });
        return result;
    }

    public LiveData<User> getUser(String email)
    {
        MutableLiveData<User> result = new MutableLiveData<>();

        firebaseUtils.getDbInstance().collection("Users").whereEqualTo("profile .email",email)
                .get().addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                QuerySnapshot userSnapshot = task.getResult();
                if (userSnapshot != null && !userSnapshot.getDocuments().isEmpty())
                    result.setValue(userSnapshot.getDocuments().get(0).toObject(User.class));
            }
            else result.setValue(null);
        });
        return result;
    }

}
