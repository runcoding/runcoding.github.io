package com.runcoding.learn.singleton;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2019-02-25 21:26
 * @description Copyright (C), 2017-2019,
 * https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F.md
 **/
public enum SingletonTest {


        INSTANCE;

        private String objName;


        public String getObjName() {
            return objName;
        }


        public void setObjName(String objName) {
            this.objName = objName;
        }


        public static void main(String[] args) {

            // 单例测试
            SingletonTest firstSingleton = SingletonTest.INSTANCE;
            firstSingleton.setObjName("firstName");
            System.out.println(firstSingleton.getObjName());
            SingletonTest secondSingleton = SingletonTest.INSTANCE;
            secondSingleton.setObjName("secondName");
            System.out.println(firstSingleton.getObjName());
            System.out.println(secondSingleton.getObjName());

            // 反射获取实例测试
            try {
                SingletonTest[] enumConstants = SingletonTest.class.getEnumConstants();
                for (SingletonTest enumConstant : enumConstants) {
                    System.out.println(enumConstant.getObjName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
