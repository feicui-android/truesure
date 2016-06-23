package com.feicuiedu.treasure;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.treasure.commons.LogUtils;

import java.io.IOException;

/**
 * 首页播放视频的Fragment
 */
public class MainMP4Fragment extends Fragment implements TextureView.SurfaceTextureListener {

    private TextureView textureView; // 用来播放视频的view
    private MediaPlayer mediaPlayer;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        textureView = new TextureView(getContext());
        return textureView;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textureView.setSurfaceTextureListener(this);
    }

    @Override public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            Surface mSurface = new Surface(surface);

            AssetFileDescriptor afd = getContext().getAssets().openFd("welcome.mp4");
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setSurface(mSurface);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.setLooping(true);
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            LogUtils.d("媒体播放失败");
        }
    }

    @Override public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
