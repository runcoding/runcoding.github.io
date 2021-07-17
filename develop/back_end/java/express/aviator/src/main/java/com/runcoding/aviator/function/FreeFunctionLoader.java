package com.runcoding.aviator.function;

import com.googlecode.aviator.FunctionLoader;
import com.googlecode.aviator.runtime.type.AviatorFunction;
import lombok.extern.slf4j.Slf4j;

/**
 * @author runcoding
 * @date 2019-08-20
 * @desc: 自定方法加载器
 */
@Slf4j
public class FreeFunctionLoader implements FunctionLoader {

    @Override
    public AviatorFunction onFunctionNotFound(String name) {
        log.info("自定方法加载器name={}",name);
        return new AddFunction();
    }

}
