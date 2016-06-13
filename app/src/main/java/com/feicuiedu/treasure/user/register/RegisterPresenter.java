package com.feicuiedu.treasure.user.register;

import android.os.AsyncTask;

import com.feicuiedu.treasure.mvpbase.MvpBasePresenter;
import com.feicuiedu.treasure.user.login.LoginView;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class RegisterPresenter extends MvpBasePresenter<LoginView>{


    public RegisterPresenter(LoginView mvpBaseView) {
        super(mvpBaseView);
    }

    public void register() {
        new RegisterTask().execute();
    }

    private final class RegisterTask extends AsyncTask<String, String, String> {
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