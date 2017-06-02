package com.menganime.application;

import android.app.Application;
import android.os.Environment;

import java.io.File;


/**
 * 文件名：MyApplication
 * 描    述：初始化数据
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        deleteAllFiles(new File(Environment.getExternalStorageDirectory()+"/MengAnime/WatchCartoon/"));
    }
    private void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
