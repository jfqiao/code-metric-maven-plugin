package com.alibaba.codemetric.maven.bootstrap;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by fangqiao.jfq on 2017/7/27.
 */
public class Util {
    private static final String ANALYSIS_PATH = "D:\\lines.csv";

    private static PrintWriter pw;

    static {
        File file = new File(ANALYSIS_PATH);
        if (!file.exists())
            try {
                file.createNewFile();
                pw = new PrintWriter(new FileOutputStream(file, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }



    public static int getLines(String dir) {
        File rootFile = new File(dir);
        int count = 0;
        if (rootFile.exists()) {
            File[] files = rootFile.listFiles();
            LinkedList<File> list = new LinkedList<>();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        list.add(files[i]);
                    } else
                        count += countFileLines(files[i]);
                }
            }
            File tmpFile;
            while (!list.isEmpty()) {
                tmpFile = list.removeFirst();
                files = tmpFile.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isDirectory()) {
                            list.add(files[i]);
                        } else
                            count += countFileLines(files[i]);
                    }
                }
            }
        }
        return count;
    }

    public static int countFileLines(File file) {
        if (file.getName().endsWith(".java")) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                int count = 0;
                while (br.readLine() != null) {
                    count++;
                }
                return count;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (br != null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        }
        return 0;
    }

    public static void csvWrite(String projectName, int lines) {
        if (pw != null)
            pw.println(projectName + "," + lines);
    }
}
