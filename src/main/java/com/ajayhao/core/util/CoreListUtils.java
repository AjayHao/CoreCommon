/**
 * Qiangungun.com Inc.
 *
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.ajayhao.core.util;

import com.qiangungun.core.functor.Transformer;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * List相关的辅助函数<br/>
 *
 * Created by wu.charles on 2015/3/26.
 */
public abstract class CoreListUtils {
    private CoreListUtils() {
        ;
    }

    /**
     * 将输入实例转换成输出实例类型的列表<br/>
     *
     * @param list
     * @param transformer
     * @param <Input>
     * @param <Output>
     * @return
     */
    public static <Input, Output> List<Output> transform(
            Collection<Input> list, Transformer<Input, Output> transformer) {
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<Output> result = new ArrayList<>(list.size());
        for(Input item : list) {
            result.add(transformer.transform(item));
        }

        return result;
    }
}
