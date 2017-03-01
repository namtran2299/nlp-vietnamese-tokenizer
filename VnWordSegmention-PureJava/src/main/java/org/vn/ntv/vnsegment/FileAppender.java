/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author namtv19
 */
public class FileAppender implements Closeable {

    BufferedWriter bw = null;
    FileWriter fw = null;

    /**
     *
     * @param fileName
     * @throws IOException
     */
    public FileAppender(String fileName) throws IOException {
        this(fileName, 8192);
    }

    /**
     *
     * @param fileName
     * @param bufferSize
     * @throws IOException
     */
    public FileAppender(String fileName, int bufferSize) throws IOException {
        File file = new File(fileName);

        if (!file.exists()) {
            file.createNewFile();
        }
        fw = new FileWriter(file, true);
        bw = new BufferedWriter(fw, bufferSize);
    }

    @Override
    public void close() throws IOException {
        if (bw != null) {
            bw.close(); // Will close fw too
        } else if (fw != null) {
            fw.close();
        } else {
            // Oh boy did it fail hard! :3
        }
    }

    public void writeString(String c) throws IOException {
        if (bw != null) {
            bw.write(c);
        }
    }

    public void flush() throws IOException {
        bw.flush();
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
}
