/**
 * Qiangungun.com Inc.
 *
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ajayhao.core.util;

import com.qiangungun.core.functor.CoreFunctorUtils;
import com.qiangungun.core.functor.Transformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map相关的辅助功能<br/>
 *
 * Created by wu.charles on 2015/3/24.
 */
public abstract class CoreMapUtils {
    private CoreMapUtils() {
        ;
    }

    /**
     * 将List转换为Map<br/>
     *
     * @param list
     * @param transformer
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K,V> mapping(List<V> list, Transformer<V, K> transformer) {
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<K,V> map = new HashMap<>(list.size(), 1F);
        for(V v:list) {
            map.put(transformer.transform(v), v);
        }

        return map;
    }

    /**
     * 对map进行转换<br/>
     *
     * @param map
     * @param keyTrans
     * @param valueTrans
     * @param <FK>
     * @param <FV>
     * @param <TK>
     * @param <TV>
     * @return
     */
    public static <FK, FV, TK, TV> Map<TK, TV> transform(Map<FK, FV> map
            , Transformer<FK, TK> keyTrans, Transformer<FV, TV> valueTrans) {
        if(MapUtils.isEmpty(map)) {
            return Collections.emptyMap();
        }

        Map<TK,TV> result = new HashMap<>(map.size(), 1F);
        for(Map.Entry<FK, FV> entry : map.entrySet()) {
            result.put(keyTrans.transform(entry.getKey())
                    , valueTrans.transform(entry.getValue()));
        }

        return result;
    }

    /**
     * 只对map的value进行转换<br/>
     *
     * @param map
     * @param valueTrans
     * @param <FK>
     * @param <FV>
     * @param <TV>
     * @return
     */
    public static <FK, FV, TV> Map<FK, TV> transformValue(Map<FK, FV> map
            , Transformer<FV, TV> valueTrans) {
        return transform(map, CoreFunctorUtils.<FK>nopTransform(), valueTrans);
    }

    /**
     * 只对map的key进行转换<br/>
     *
     * @param map
     * @param keyTrans
     * @param <FK>
     * @param <FV>
     * @param <TK>
     * @return
     */
    public static <FK, FV, TK> Map<TK, FV> transformKey(Map<FK, FV> map
            , Transformer<FK, TK> keyTrans) {
        return transform(map, keyTrans, CoreFunctorUtils.<FV>nopTransform());
    }
}
