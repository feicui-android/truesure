package com.feicuiedu.treasure.user.login;

import android.os.AsyncTask;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

/**
 * Created by Administrator on 2016/6/12 0012.
 */


// MvpView

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{


    public void login() {
        new LoginTask().execute();
    }

    private final class LoginTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getView().showProgress();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                getView().hideProgress();
                getView().showMessage(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getView().hideProgress();
            getView().navigateToHome();
        }
    }
}