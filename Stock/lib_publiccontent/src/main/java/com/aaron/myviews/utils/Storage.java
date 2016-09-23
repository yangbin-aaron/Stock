package com.aaron.myviews.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class Storage {

    private static final String FOLDER_DOC = "cain_doc";
    private static final String DEFAULT_FILE = "default";

    private static Storage sInstance;

    private File mCacheFile;
    private File mDocFile;

    private boolean mCacheable;
    
    private  static Context mContext;

    private Storage(Context context) {
        mContext = context;
        mCacheable = true;
        mCacheFile = context.getFilesDir();
        mDocFile = createDocumentFile();
    }

    public static Storage getInstance(Context context) {
        mContext = context;
        if (sInstance == null) {
            sInstance = new Storage(context);
        }
        return sInstance;
    }


    private File createDocumentFile() {
        File file;
        if (!isExternalStorageWritable()) return null;

        file = new File(android.os.Environment.getExternalStorageDirectory(), FOLDER_DOC);
        if (file != null) {
            if (!file.mkdirs()) {
                Log.d("TEST", "Create folder failed");
            }
        }
        return file;
    }

    private boolean isExternalStorageWritable() {
        String state = android.os.Environment.getExternalStorageState();
        if (state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public void writeToDoc(String content) {
        if (mDocFile == null) return;

        File file = new File(mDocFile.getPath() + "/" + DEFAULT_FILE);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToCache(String key, Object object) {
        if (object != null) {
            String obj = new Gson().toJson(object);
            writeToCache(key, obj);
        }
    }

    public Object readFromCache(String key, Class clazz) {
        String obj = readFromCache(key);
        if (obj != null) {
            return new Gson().fromJson(obj, clazz);
        }
        return null;
    }

    public Object readFromCache(String key, Type type) {
        String obj = readFromCache(key);
        if (obj != null) {
            return new Gson().fromJson(obj, type);
        }
        return null;
    }

    public String readFromAssets(String fileName) {
        AssetManager assetManager = mContext.getAssets();
        byte[] buffer = null;
        try {
            InputStream inputStream = assetManager.open(fileName);
            int size = inputStream.available();
            buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                return new String(buffer);
            }
            return null;
        }
    }

    private void writeToCache(String key, String content) {
        File file = new File(mCacheFile.getPath() + "/" + key);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromCache(String key) {
        File file = new File(mCacheFile.getPath() + "/" + key);
        StringBuilder result = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                result.append(text);
            }

            bufferedReader.close();

            return result.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
