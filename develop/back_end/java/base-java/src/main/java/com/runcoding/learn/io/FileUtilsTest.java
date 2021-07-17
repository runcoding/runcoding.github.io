package com.runcoding.learn.io;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author: runcoding
 * @Email: runcoding@163.com
 * @Created Time: 2017/9/9 21:19
 * @Description
 **/
public class FileUtilsTest {

    public static void main(String[] args) {
        String path = "public/learn-java/base-java/src/main/java/com/runcoding/learn/algorithm/leet";

        File file = new File(path);

        print(file,path);
    }

    public static void print(File file,String path){
        if(file!=null){
            if(file.isDirectory()){
                String name =  file.getName();
                path = file.getPath();
                path = StringUtils.replace(path,name,name.toLowerCase());
                // path = StringUtils.replace(path,"leet","leet2");
                File[] files=file.listFiles();
                if(files!=null){             //有可能无法列出目录中的文件
                    for(int i=0;i<files.length;i++){
                        print(files[i],path);
                    }
                }
            }else{
                try {
                    String txt =  FileUtils.readFileToString(file,"UTF-8");
                    //String name = StringUtils.removeEnd(file.getName(),".java");
                    // name = StringUtils.capitalize(name);
                    String[] strings = StringUtils.split(path, "//");
                    String str = strings[strings.length-1];
                    path += "/"+file.getName();
                    txt = StringUtils.replace(txt,"leet."+StringUtils.capitalize(str),"leet."+str);
                    FileUtils.write(new File(path),txt,"UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
