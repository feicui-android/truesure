package com.feicuiedu.treasure.user;

import android.os.AsyncTask;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void login() {
        new LoginTask().execute();
    }

    private final class LoginTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loginView.showProgress();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                loginView.hideProgress();
                loginView.showMessage(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loginView.hideProgress();
            loginView.navigateToHome();
        }
    }
}