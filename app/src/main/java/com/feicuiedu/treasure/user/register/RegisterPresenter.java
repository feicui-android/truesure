package com.feicuiedu.treasure.user.register;

import android.os.AsyncTask;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    public void register() {
        new RegisterTask().execute();
    }

    private final class RegisterTask extends AsyncTask<String, String, String> {
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