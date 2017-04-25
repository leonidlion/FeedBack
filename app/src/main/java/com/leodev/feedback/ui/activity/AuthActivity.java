package com.leodev.feedback.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.leodev.feedback.R;
import com.leodev.feedback.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_auth;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @BindView(R.id.et_input_email)
    EditText mInputEmailEdit;
    @BindView(R.id.et_input_password)
    EditText mInputPassEdit;
    @BindView(R.id.btn_login)
    Button mLoginBtn;

    @OnClick(R.id.btn_login)
    public void onClick(){
        signIn();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.auth_message));
        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn(){
        if (!isValidEmail() || !isValidPassword()){
            return;
        }

        if (!Utils.isNetworkConnected(this) ||
                !Utils.isPlayServiceUpdated(this)){
            return;
        }

        mLoginBtn.setEnabled(false);

        showProgressDialog();

        hideSoftKeyboard();

        String email = mInputEmailEdit.getText().toString();
        String password = mInputPassEdit.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();

                        if (task.isSuccessful()){
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    R.string.wrong_auth,
                                    Toast.LENGTH_SHORT).show();
                            mLoginBtn.setEnabled(true);
                        }
                    }
                });
    }

    private void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager immm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            immm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showProgressDialog(){
        if (mProgressDialog != null && !mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    private void hideProgressDialog(){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    private boolean isValidPassword(){
        if (TextUtils.isEmpty(mInputPassEdit.getText().toString())){
            mInputPassEdit.setError(getString(R.string.required));
            return false;
        } else if (mInputPassEdit.getText().length() < 6){
            mInputPassEdit.setError(getString(R.string.min_sumb));
            return false;
        }
        mInputPassEdit.setError(null);
        return true;
    }

    private boolean isValidEmail() {
        if(TextUtils.isEmpty(mInputEmailEdit.getText().toString())) {
            mInputEmailEdit.setError(getString(R.string.required));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mInputEmailEdit.getText().toString()).matches()) {
            mInputEmailEdit.setError(getString(R.string.wrong_email));
            return false;
        }
        mInputEmailEdit.setError(null);
        return true;
    }
}