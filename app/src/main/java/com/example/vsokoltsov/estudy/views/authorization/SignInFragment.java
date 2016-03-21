package com.example.vsokoltsov.estudy.views.authorization;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.estudy.R;
import com.example.vsokoltsov.estudy.interfaces.UserApi;
import com.example.vsokoltsov.estudy.models.authorization.SignInRequest;
import com.example.vsokoltsov.estudy.models.authorization.Token;
import com.example.vsokoltsov.estudy.util.ApiRequester;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by vsokoltsov on 14.03.16.
 */
public class SignInFragment extends Fragment {
    private EditText emailField;
    private EditText passwordField;
    private Button signInButton;
    private View fragmentView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.authorization_fragment, container, false);
        emailField = (EditText) fragmentView.findViewById(R.id.emailField);
        passwordField= (EditText) fragmentView.findViewById(R.id.passwordField);
        Button signInButton = (Button) fragmentView.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInRequest user = new SignInRequest(emailField.getText().toString(),
                        passwordField.getText().toString());
                Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
                UserApi service = retrofit.create(UserApi.class);
                service.signIn(user).enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Log.e("Res", response.body().toString() );
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {

                    }
                });
//                signIn(v);
            }
        });
        return fragmentView;
    }
}