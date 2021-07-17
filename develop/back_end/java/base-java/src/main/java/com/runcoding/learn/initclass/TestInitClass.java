package com.runcoding.learn.initclass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: runcoding
 * @Email: runcoding@163.com
 * @Created Time: 2017/9/5 10:18
 * @Description
 **/
public class TestInitClass {


    /**
     * 由于初始化只会进行一次，运行时请将注解去掉，依次运行查看结果。
     */
    public static void main(String[] args) throws Exception{
        /**主动引用初始化一: new对象、读取或设置类的静态变量、调用类的静态方法。  */
           new InitClass();                //第一次出现new时，初始化一次
           InitClass.runCoding = "";       //第一次出现给静态变量赋值时，初始化一次
           String a = InitClass.runCoding; //第一次出现给静态变量赋值时，初始化一次
           InitClass.methodA();            //第一次出现调用静态方法时，初始化一次

        /**主动引用初始化二：通过反射实例化对象、读取或设置类的静态变量、调用类的静态方法。  */
           Class cls = InitClass.class;
           cls.newInstance();     //第一次通过反射newInstance时，初始化一次

           Field f = cls.getDeclaredField("runCoding");
           Object o = f.get(null);//RunningCoding
           f.set(null, "Coding"); //重新变更值

           Method mdA = cls.getDeclaredMethod("methodA");
           mdA.invoke(null, null);

           Class[] cArg = new Class[1];
           cArg[0] = Integer.class;

           Method mdB = cls.getDeclaredMethod("methodB",cArg);
           mdB.invoke(null, 1);

        /**主动引用初始化三：实例化子类，引起父类初始化。*/
           new SubInitClass();

    }

}
