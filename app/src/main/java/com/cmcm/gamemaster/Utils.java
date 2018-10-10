package com.cmcm.gamemaster;

import android.content.res.AssetManager;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

class Utils {
    static List<File> extractSampleImages(MainActivity mainActivity) {
        try {
            AssetManager assets = mainActivity.getApplicationContext().getAssets();
            String[] samples = assets.list("sample");
            List<File> sampleList = new ArrayList<>(samples.length);
            for (String sample : samples) {
                InputStream sampleIns = assets.open("sample/" + sample);
                File sampleFile = buildImageFile(mainActivity, sample);
                sampleList.add(sampleFile);
                copyInputStreamToFile(sampleIns, sampleFile);
            }
            return sampleList;
        } catch (Exception e) {
            e.printStackTrace();
            mainActivity.finish();
        }
        return null;
    }

    private static final String CACHE_IMAGE_DIR = "glide370imgs";
    private static final String IMAGE_FILE_SUFFIX = ".png";

    private static File buildImageFile(MainActivity mainActivity, String appPackageName) {
        return new File(createImageDir(mainActivity, CACHE_IMAGE_DIR), appPackageName + IMAGE_FILE_SUFFIX);
    }

    private static File createImageDir(MainActivity mainActivity, String dirName) {
        File externalFilesDir = mainActivity.getApplicationContext().getExternalFilesDir(dirName);
        if (!externalFilesDir.exists()) {
            externalFilesDir.mkdir();
        }
        return externalFilesDir;
    }

    private static final int SYSTEM_ROOT_STATE_DISABLE = 0;

    private static void copyInputStreamToFile(InputStream inputStream, File file) throws Exception {
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannel = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileChannel = fileOutputStream.getChannel();
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                fileChannel.write(ByteBuffer.wrap(bArr, SYSTEM_ROOT_STATE_DISABLE, read));
            }
        } finally {
            closeSilently(inputStream);
            closeSilently(fileChannel);
            closeSilently(fileOutputStream);
        }
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
        }
    }
}
