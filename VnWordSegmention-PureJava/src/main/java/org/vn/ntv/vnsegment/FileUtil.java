/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.vnsegment;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.Writer;
import java.util.zip.GZIPOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import java.util.List;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author namtv
 */
public class FileUtil {

    /**
     * trim No Character
     *
     * @param text
     * @return
     */
    public static String trimNoCharacter(String text) {
        int len = text.length();
        int st = 0;
        char[] val = text.toCharArray();    /* avoid getfield opcode */

        while ((st < len) && (!Character.isLetterOrDigit(val[st]))) {
            st++;
        }
        while ((st < len) && (!Character.isLetterOrDigit(val[len - 1]))) {
            len--;
        }
        return ((st >= 0) || (len < val.length)) ? text.substring(st, len) : text;
    }

    public static String readFile(String filename) {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static String readTextFile(String fileName) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        return content;
    }

    public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
    }

    public static String readTextFromFile(String fileName) {

        try {
            StringBuilder sb = new StringBuilder();
            Reader reader = new InputStreamReader(
                new FileInputStream(fileName), "UTF-8");
            BufferedReader fin = new BufferedReader(reader);

            String s;
            while ((s = fin.readLine()) != null) {
                sb.append(s);
            }

            //Remember to call close. 
            //calling close on a BufferedReader/BufferedWriter 
            // will automatically call close on its underlying stream 
            fin.close();
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void writeTextFromFile(String fileName, String content) {

        try {
            Writer writer = new OutputStreamWriter(
                new FileOutputStream(fileName), "UTF-8");
            try (BufferedWriter fout = new BufferedWriter(writer)) {
                fout.write(content);

                //Remember to call close.
                //calling close on a BufferedReader/BufferedWriter
                // will automatically call close on its underlying stream 
            }

        } catch (IOException e) {
        }
    }

    public static List<String> readAllLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines.addAll(Files.readAllLines(Paths.get(fileName), Charset.forName("UTF-8")));
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }

    public static boolean compressContent2File(String fileName, String content) throws IOException {
        if (content == null || content.length() == 0) {
            return false;
        }

        BufferedWriter writer = null;

        try {
            File file = new File(fileName + ".gzip");
            GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(file));

            writer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"));

            writer.append(content);
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void gzipFile(String source_filepath, String destinaton_zip_filepath) {

        byte[] buffer = new byte[1024];

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(destinaton_zip_filepath);

            GZIPOutputStream gzipOuputStream = new GZIPOutputStream(fileOutputStream);

            FileInputStream fileInput = new FileInputStream(source_filepath);

            int bytes_read;

            while ((bytes_read = fileInput.read(buffer)) > 0) {
                gzipOuputStream.write(buffer, 0, bytes_read);
            }

            fileInput.close();

            gzipOuputStream.finish();
            gzipOuputStream.close();

            System.out.println("The file was compressed successfully!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static long appendContent2File(String fileName, String content) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(content);
            return file.length();
        } catch (IOException e) {
            // File writing/opening failed at some stage.
        } finally {
            try {
                if (bw != null) {
                    bw.close(); // Will close fw too
                } else if (fw != null) {
                    fw.close();
                } else {
                    // Oh boy did it fail hard! :3
                }
            } catch (IOException e) {
                // Closing the file writers failed for some obscure reason
            }
        }
        return 0;
    }

    public static byte[] readFileAsByte(String file) throws IOException {
        return readFileAsByte(new File(file));
    }

    public static byte[] readFileAsByte(File file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength) {
                throw new IOException("File size >= 2 GB");
            }
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }

    /**
     *
     * @param dirPath
     * @param ext
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<String> listFile(String dirPath, String ext) throws FileNotFoundException, IOException {
        List<String> result = new ArrayList<>();

        File dir = new File(dirPath);
        if (!dir.exists()) {
            return result;
        }
        for (File file : dir.listFiles()) {
            String path = file.getPath();
            if (ext.isEmpty()) {
                if (path.endsWith(ext)) {
                    result.add(path);
                }
            } else {
                result.add(path);
            }
        }

        return result;
    }

    /**
     *
     * @param path
     * @return
     */
    public static Properties loadConfiguration(String path) {
        Properties properties = new Properties();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path), Charset.forName("UTF-8"));

            for (String line : lines) {

                if (line.startsWith("#")) {
                    continue;
                }
                int pos = line.indexOf('=');
                if (pos > 0) {
                    String key = line.substring(0, pos).trim();
                    String value = line.substring(pos + 1).trim();
                    properties.setProperty(key, value);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    /**
     * Write String to file as Text File.
     *
     * @param content Content need to write.
     * @param fileName File name need to write.
     * @return TRUE If read successful, FALSE otherwise.
     */
    public static boolean writeFile(String content, String fileName) {
        boolean bResult = false;
        try {
            File file = new File(fileName);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file.getAbsoluteFile()), "UTF-8"));
            bw.write(content);
            bw.close();
            bResult = true;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return bResult;
    }

    public static boolean appendContent(String content, String fileName) {
        boolean bResult = false;
        try {
            File file = new File(fileName);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(fileName, true), "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(content);
            fbw.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return bResult;
    }

    /**
     *
     * @param input
     * @param length
     * @param buffsize
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String getContentFromInput(InputStream input, int length, final int buffsize, String encoding) throws IOException {
        DataInputStream in = new DataInputStream(input);
        ByteArrayOutputStream result = new ByteArrayOutputStream(length);
        byte[] buff = new byte[buffsize];
        int nRead = 0;
        while ((nRead = in.read(buff, 0, buffsize)) != -1) {
            result.write(buff, 0, nRead);
        }
        return new String(result.toByteArray(), 0, length, encoding);
    }

    /**
     * Make view of size in measure
     *
     * @param val
     * @return
     */
    public static String getSizeInB(long val) {
        if (val < 1024) {
            return val + "B";
        }
        val = val / 1024;
        if (val < 1024) {
            return val + "KB";
        }
        val = val / 1024;
        if (val < 1024) {
            return val + "MB";
        }
        val = val / 1024;
        if (val < 1024) {
            return val + "GB";
        }
        val = val / 1024;
        return val + "TB";
    }

    /**
     *
     * @param path
     * @param processor
     * @throws Exception
     */
    public static void scanline(String path, IDataProcessor<String> processor) throws Exception {
        scanLine(path, processor, "UTF-8");
    }

    /**
     *
     * @param processor
     * @return
     */
    public static void scanLine(String path, IDataProcessor<String> processor, String charset) throws Exception {
//        Scanner scanner = new Scanner(file);
//        while (scanner.hasNext()) {
//            processor.processQueue(scanner.nextLine());
//        }
//        scanner.close();

        Reader reader = new InputStreamReader(
            new FileInputStream(path), charset);
        BufferedReader fin = new BufferedReader(reader);

        String s;
        while ((s = fin.readLine()) != null) {
            processor.processQueue(s);
        }
        fin.close();
        reader.close();
    }

    /**
     *
     * @param classToAdd
     * @param fileName
     * @return
     */
    public static List<String> readResource(Class classToAdd, String fileName) throws IOException {
        List<String> result = new ArrayList<>();
        InputStream in = classToAdd.getClassLoader().getResourceAsStream(fileName);
        if (in == null) {
            return result;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in), 4096);

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            result.add(line.trim());
        }

        return result;
    }

    /**
     *
     * @param classToAdd
     * @param fileName
     * @return
     */
    public static String readTextResource(Class classToAdd, String fileName) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = classToAdd.getClassLoader().getResourceAsStream(fileName);
        if (in == null) {
            return result.toString();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in), 4096);

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            result.append(line.trim());
            result.append(System.lineSeparator());
        }

        return result.toString();
    }

    public static void deleteFolder(File f) {
        if (f.exists() && f.isDirectory()) {
            for (File item : f.listFiles()) {
                deleteFolder(item);
            }
        }
        if (!f.delete()) {
            f.deleteOnExit();
        }
    }

}
