package com.feicuiedu.treasure.user.login;

import android.os.AsyncTask;

import com.feicuiedu.treasure.mvpbase.MvpBasePresenter;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView>{


    public LoginPresenter(LoginView mvpBaseView) {
        super(mvpBaseView);
    }

    public void login() {
        new LoginTask().execute();
    }

    private final class LoginTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getMvpBaseView().showProgress();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                getMvpBaseView().hideProgress();
                getMvpBaseView().showMessage(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getMvpBaseView().hideProgress();
            getMvpBaseView().navigateToHome();
        }
    }
}