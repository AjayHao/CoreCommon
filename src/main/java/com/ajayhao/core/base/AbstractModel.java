package com.ajayhao.core.base;


import com.ajayhao.core.util.CoreObjectUtils;
import com.ajayhao.core.util.CoreReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务公开接口参数与返回值的超类<br/>
 * <p>
 * Created by wu.charles on 2014/12/10.
 */
public abstract class AbstractModel implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractModel.class);

    private static final long serialVersionUID = 4569257787074246273L;

    /**
     * 调用方服务器IP<br/>
     */
    private String clientIp = null;

    /**
     * 调用方服务器Host Name<br/>
     */
    private String clientHostName = null;

    /**
     * 接收方服务器Ip<br/>
     */
    private String serverIp = null;

    /**
     * 接收方服务器Host Name<br/>
     */
    private String serverHostName = null;


    /**
     * 扩展内容，用于存储非结构化附加信息<br/>
     */
    private HashMap<String, String> extFields = new HashMap<String, String>(4, 1F);

    public AbstractModel() {
        ; // nothing.
    }

    /**
     * 添加一个附件的扩展信息<br/>
     *
     * @param key
     * @param value
     * @return 如果key已经存在，则返回原来的value值；
     */
    public String addExtField(String key, String value) {
        return extFields.put(key, value);
    }

    /**
     * 删除一个附加的扩展信息<br/>
     *
     * @param key
     * @return 如果key已经存在，则返回原来的value值；
     */
    public String removeExtField(String key) {
        return extFields.remove(key);
    }

    /**
     * 通过key获取扩展信息<br/>
     *
     * @param key
     * @return
     */
    public String getExtField(String key) {
        return extFields.get(key);
    }

    /**
     * 将给定的内容复制到当前的附加信息中<br/>
     *
     * @param extFields
     */
    public void copy(Map<String, String> extFields) {
        if (extFields == null) {
            return;
        }

        this.extFields.putAll(extFields);
    }

    /**
     * 获取扩展信息的一个不可变快照<br/>
     *
     * @return
     */
    public Map<String, String> extFields() {
        return Collections.unmodifiableMap(extFields);
    }

    /**
     * 将扩展属性转换为json字符串<br/>
     *
     * @return
     */
    public String jsonExtFields() {
        return CoreObjectUtils.object2Json(extFields);
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientHostName() {
        return clientHostName;
    }

    public void setClientHostName(String clientHostName) {
        this.clientHostName = clientHostName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerHostName() {
        return serverHostName;
    }

    public void setServerHostName(String serverHostName) {
        this.serverHostName = serverHostName;
    }

    public HashMap<String, String> getExtFields() {
        return extFields;
    }

    public void setExtFields(HashMap<String, String> extFields) {
        this.extFields = extFields;
    }

    @Override
    public String toString() {
        return "AbstractModel{" +
                "clientIp='" + clientIp + '\'' +
                ", clientHostName='" + clientHostName + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", serverHostName='" + serverHostName + '\'' +
                ", extFields=" + extFields +
                '}';
    }

    /**
     * 辅助方法<br/>
     *
     * @param $this
     * @return
     */
    public static String toString(AbstractModel $this) {
        try {
            return new CoreReflectionToStringBuilder($this).toString();
        } catch (Exception e) {
            LOG.warn("打印对象异常", e);
        }

        return StringUtils.EMPTY;
    }
}
