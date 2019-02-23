package com.jeesite.modules.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("all")
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * list转map(1对1)
     */
    public static <K, V> Map<K, V> list2Map(List<V> list, String keyField) {
        Map<K, V> map = new HashMap<K, V>();
        if (list != null && !list.isEmpty()) {
            try {
                for (V s : list) {
                    K val = (K) PropertyUtils.getProperty(s, keyField);
                    if (val != null) {
                        map.put(val, s);
                    }
                }
            } catch (Exception e) {
                logger.error(String.format("keyField [%s] not found !", keyField));
                throw new IllegalArgumentException(String.format("keyField [%s] not found !", keyField));
            }
        }
        return map;
    }

    /**
     * list转map(1对多)
     */
    public static <K, V> Map<K, List<V>> list2Map2(List<V> list, String keyField) {
        Map<K, List<V>> map = new HashMap<>();
        if (list != null && !list.isEmpty()) {
            try {
                for (V s : list) {
                    K val = (K) PropertyUtils.getProperty(s, keyField);
                    if (map.containsKey(val)) {
                        map.get(val).add(s);
                    } else {
                        List<V> listv = new ArrayList<>();
                        listv.add(s);
                        map.put(val, listv);
                    }
                }
            } catch (Exception e) {
                logger.error(String.format("keyField [%s] not found !", keyField));
                throw new IllegalArgumentException(String.format("keyField [%s] not found !", keyField));
            }
        }
        return map;
    }

    /**
     * 获取某一字段
     */
    public static <K, V> List<K> getField(List<V> list, String field) {
        List<K> res = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            try {
                for (V object : list) {
                    Object value = PropertyUtils.getProperty(object, field);
                    if (value != null) {
                        res.add((K) value);
                    }
                }
            } catch (Exception e) {
                logger.error(String.format("field [%s] not found !", field));
                throw new IllegalArgumentException(String.format("field [%s] not found !", field));
            }
        }
        return res;
    }

    public static <K, V> List<K> getFieldSet(List<V> list, String field) {
        List<K> field2 = getField(list, field);
        return new ArrayList<K>(new HashSet<>(field2));
    }

    /**
     * 列表转列表
     *
     * @param destClass
     *            目标对象类
     */
    public static <D, S> List<D> tran(List<S> list, Class<D> destClass) {
        List<D> ret = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (S s : list) {
                try {
                    ret.add(destClass.getConstructor(s.getClass()).newInstance(s));
                } catch (Exception e) {
                    logger.error("tran exception", e);
                }
            }
        }
        return ret;
    }

    public static <D> List<D> tranJSONArray(JSONArray arr, Class<D> destClass) {
        List<D> ret = new ArrayList<>();
        Iterator<Object> iterator = arr.iterator();
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            try {
                ret.add(destClass.getConstructor(JSONObject.class).newInstance(next));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("tranJSONArray exception", e);
            }
        }
        return ret;
    }

}

