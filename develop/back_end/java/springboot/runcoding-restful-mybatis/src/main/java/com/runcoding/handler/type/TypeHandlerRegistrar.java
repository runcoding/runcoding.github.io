package com.runcoding.handler.type;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.runcoding.handler.type.annotation.ColumnStyle;
import com.runcoding.handler.type.annotation.ColumnType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;

import java.util.Map;
import java.util.Set;

/**
 * @author runcoding
 * @date 2019-03-04
 * @desc:
 */
public class TypeHandlerRegistrar {

    private  static  Map<ColumnStyle, Set<Class<?>>> registrarClass = Maps.newConcurrentMap();


    /***
     * 类型需要注册类
     * @param column
     * @param regClass
     */
    public static void registrarClass(ColumnType column, Class<?> regClass){
        if(column == null){
             return;
        }
        ColumnStyle style = column.style();
        Set<Class<?>> regClassSet = registrarClass.get(style);
        if(regClassSet == null){
            regClassSet = Sets.newHashSet(regClass);
            registrarClass.put(style,regClassSet);
        }else {
            regClassSet.add(regClass);
        }
    }

    /**
     *  sessionFactory.setTypeHandlersPackage("com.runcoding.configurer.data.type");
     *  typeHandlerRegistry.register(BusinessJsonTypeHandler.class);
     *  typeHandlerRegistry.register(AccountPo.class, JsonTypeHandler.class);
     * @param sessionFactory
     * @throws Exception
     */
    public static void typeHandlerRegistry(SqlSessionFactoryBean sessionFactory) throws Exception {
        SqlSessionFactory session = sessionFactory.getObject();
        TypeHandlerRegistry typeHandlerRegistry = session.getConfiguration().getTypeHandlerRegistry();
        registrarClass.forEach((columnType,regClassSet)->{
            Class<? extends JsonTypeHandler> baseTypeHandler = null;
            /**注册JSON*/
            if(columnType.equals(ColumnStyle.JSON)){
                baseTypeHandler = JsonTypeHandler.class;
            }
            for (Class<?> regClass : regClassSet) {
                typeHandlerRegistry.register(regClass, baseTypeHandler);
            }
        });
        /**使用后情况*/
        registrarClass.clear();
    }

}
