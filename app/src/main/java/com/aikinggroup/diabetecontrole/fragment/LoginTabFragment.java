package com.aikinggroup.diabetecontrole.fragment;

import static com.aikinggroup.diabetecontrole.Constants.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.aikinggroup.diabetecontrole.Constants;
import com.aikinggroup.diabetecontrole.DiabeteControleApplication;
import com.aikinggroup.diabetecontrole.R;
import com.aikinggroup.diabetecontrole.activity.HelloActivity;
import com.aikinggroup.diabetecontrole.activity.MainActivity;
import com.aikinggroup.diabetecontrole.db.entity.UserEntity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;

public class LoginTabFragment extends Fragment {

    /*private View pass;
    private View username;
    private View forgetPass;
    private View login;*/
    private View forgetPass;

    @BindView(R.id.patientLoginButton)
     Button login;
    @BindView(R.id.username)
    TextView username;

    @BindView(R.id.telephone)
    TextView telephone;

    @BindView(R.id.password)
    TextView pass;


    @Override
    public ViewGroup onCreateView(LayoutInflater inflater, ViewGroup contenair, Bundle savedInstanceState){
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,contenair,false);

        username = root.findViewById(R.id.username);
        pass = root.findViewById(R.id.password);
        forgetPass = root.findViewById(R.id.passwordForgotten);
        login =root.findViewById(R.id.patientLoginButton);

        username.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();





        login.setOnClickListener(new View.OnClickListener() {
            String email = username.getText().toString();
            String password = pass.getText().toString();
            @Override
            public void onClick(View view) {
                Credentials emailPasswordCredentials = Credentials.emailPassword("delianeb1@gmail.com", "Delwende@91");
               app.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        Log.v("AUTH", "Successfully authenticated using an email and password.");
                        User user =app.currentUser();
                        //DiabeteControleApplication application = (DiabeteControleApplication) getActivity().getApplication();
                       // application.getDBHandlerLogin();
                        //link(user,"delianeb1@gmail.com", "Delwende@91");
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Log.e("AUTH", it.getError().toString());
                    }
                });
            }
        });
        return root;
    }

    private void link(User user, String username, String password){
        user.linkCredentialsAsync(
                Credentials.emailPassword(username, password), result -> {
        if (result.isSuccess()) {
            Log.v("EXAMPLE", "Successfully linked existing user " +
                    "identity with email/password user: " + result.get());
        } else {
            Log.e("EXAMPLE", "Failed to link user identities with: " +
                    result.getError());
        }
    });
    }


}
