package com.runcoding.aviator.function;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;

import java.util.Map;

/**
 * @author runcoding
 * @desc 如果你的参数个数不确定，可以继承 AbstractVariadicFunction 类，只要实现其中的 variadicCall 方法即可，
 * 比如我们实现一个找到第一个参数不为 null 的函数：
 */
public class GetFirstNonNullFunction extends AbstractVariadicFunction {

    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        if (args == null) {
            return new AviatorString(null);
        }
        for (AviatorObject arg : args) {
            if (arg.getValue(env) != null) {
                return arg;
            }
        }
        return new AviatorString(null);
    }


    @Override
    public String getName() {
        return "getFirstNonNull";
    }

}