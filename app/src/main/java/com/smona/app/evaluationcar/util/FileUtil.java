package com.smona.app.evaluationcar.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    
    public static boolean delete(File file) {
        return file.delete();
    }

    public static boolean mkdirs(File file) {
        return file.mkdirs();
    }

    public static boolean createNewFile(File file) throws IOException {
        return file.createNewFile();
    }
}
