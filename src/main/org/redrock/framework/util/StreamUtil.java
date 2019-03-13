package org.redrock.framework.util;

import java.io.*;

/*
* 流工具
* */
public class StreamUtil {
    public static void writeStream(OutputStream out, String text){
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
        try {
            bufferedWriter.write(text);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static String readStream(InputStream in){
        String res = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            res = sb.toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return res;
    }
}
