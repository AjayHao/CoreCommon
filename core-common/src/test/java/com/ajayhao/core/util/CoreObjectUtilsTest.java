package com.ajayhao.core.util;

import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.util.CoreObjectUtils;
import com.ajayhao.core.util.testcase.Apkind;
import com.ajayhao.core.util.testcase.MHTStepCode;
import com.ajayhao.core.util.testcase.User;
import com.ajayhao.core.util.testcase.UserModel;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by ajay.hao on 2016/11/22.
 */
public class CoreObjectUtilsTest {
    @BeforeClass
    public void beforeClass() {
        System.out.println("this is before class");
    }

    @Test
    public void test_object2json_normalObj() {
        User u = new User();
        u.setAge(10);
        u.setUserId("100");
        u.setUserName("normlObj");
        String actual = CoreObjectUtils.object2Json(u);
        String expected = "{\"userId\":\"100\",\"userName\":\"normlObj\",\"age\":10}";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_object2Json_subAbstractModel() {
        UserModel u = new UserModel();
        u.setAge(100);
        u.setUserId("1000");
        u.setUserName("abstractModelSubClass");
        String actual = CoreObjectUtils.object2Json(u);
        String expected = "{\"clientIp\":null,\"clientHostName\":null,\"serverIp\":null,\"serverHostName\":null,\"extFields\":{},\"userId\":\"1000\",\"userName\":\"abstractModelSubClass\",\"age\":100}";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_object2Json_subAbstractEnum() {
        String actual = CoreObjectUtils.object2Json(MHTStepCode.AIP);
        String expected = "{\"name\":\"0009\",\"desc\":\"定投\"}";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_object2Json_subAbstractCodedEnum() {
        String actual = CoreObjectUtils.object2Json(Apkind.CLOSE_ACCO_APP);
        String expected = "{\"name\":\"CLOSE_ACCO_APP\",\"desc\":\"销户申请\",\"code\":\"002\"}";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_json2Object_StringNum() {
        String jsonStr = "1000";
        String u = CoreObjectUtils.json2Object(jsonStr, String.class);
        Assert.assertEquals(u, "1000");
    }

    @Test
    public void test_json2Object_String() {
        String jsonStr = "ajay";
        String u = CoreObjectUtils.json2Object(jsonStr, String.class);
        Assert.assertNull(u);
    }


    @Test
    public void test_json2Object_normalObj() {
        String jsonStr = "{\"userId\":\"100\",\"userName\":\"normlObj\",\"age\":10}";
        User u = CoreObjectUtils.json2Object(jsonStr, User.class);
        Assert.assertEquals(u.getUserId(), "100");
        Assert.assertEquals(u.getUserName(), "normlObj");
        Assert.assertEquals(u.getAge(), 10);
    }

    @Test
    public void test_json2Object_subAbstractModel() {
        String jsonStr = "{\"extFields\":{\"f1\":\"v1\",\"f2\":\"v2\"},\"userId\":\"aaa\",\"userName\":\"abstractModelSubClass\",\"age\":100}";
        UserModel u = CoreObjectUtils.json2Object(jsonStr, UserModel.class);
        Assert.assertEquals(u.getAge(), 100);
        Assert.assertEquals(u.getUserId(), "aaa");
        Assert.assertEquals(u.extFields().get("f1"), "v1");
    }

    @Test
    public void test_json2Object_subAbstractEnum() {
        String jsonStr = "{\"name\":\"0003\",\"desc\":\"快赎\"}";
        MHTStepCode a = CoreObjectUtils.json2Object(jsonStr, MHTStepCode.class);
        Assert.assertEquals(a.name(), "0003");
        Assert.assertEquals(a.desc(), "快赎");
    }

    @Test
    public void test_json2Object_subAbstractCodedEnum() {
        String jsonStr = "{\"name\":\"PURCHAS_APP\",\"desc\":\"申购申请\",\"code\":\"022\"}";
        Apkind a = CoreObjectUtils.json2Object(jsonStr, Apkind.class);
        Assert.assertEquals(a.code(), "022");
        Assert.assertEquals(a.desc(), "申购申请");
        Assert.assertEquals(a.name(), "PURCHAS_APP");
    }

    @Test
    public void test_object2jsonExt_normalObj() {
        User u = new User();
        u.setAge(10);
        u.setUserId("100");
        u.setUserName("normlObj");
        String actual = CoreObjectUtils.object2JsonExt(u);
        String expected = "{\"userId\":\"100\",\"userName\":\"normlObj\",\"age\":10}";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_object2JsonExt_subAbstractModel() {
        UserModel u = new UserModel();
        u.setAge(100);
        u.setUserId("1000");
        u.setUserName("abstractModelSubClass");
        String actual = CoreObjectUtils.object2JsonExt(u);
        String expected = "{\"clientIp\":null,\"clientHostName\":null,\"serverIp\":null,\"serverHostName\":null,\"extFields\":{},\"userId\":\"1000\",\"userName\":\"abstractModelSubClass\",\"age\":100}";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_object2JsonExt_subAbstractEnum() {
        String actual = CoreObjectUtils.object2JsonExt(MHTStepCode.AIP);
        String expected = "\"0009\"";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_object2JsonExt_subAbstractCodedEnum() {
        String actual = CoreObjectUtils.object2JsonExt(Apkind.CLOSE_ACCO_APP);
        String expected = "\"002\"";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void test_json2ObjectExt_normalObj() {
        String jsonStr = "{\"userId\":\"100\",\"userName\":\"normlObj\",\"age\":10}";
        User u = CoreObjectUtils.json2ObjectExt(jsonStr, User.class);
        Assert.assertEquals(u.getUserId(), "100");
        Assert.assertEquals(u.getUserName(), "normlObj");
        Assert.assertEquals(u.getAge(), 10);
    }

    @Test
    public void test_json2ObjectExt_subAbstractModel() {
        String jsonStr = "{\"extFields\":{\"f1\":\"v1\",\"f2\":\"v2\"},\"userId\":\"aaa\",\"userName\":\"abstractModelSubClass\",\"age\":100}";
        UserModel u = CoreObjectUtils.json2ObjectExt(jsonStr, UserModel.class);
        Assert.assertEquals(u.getAge(), 100);
        Assert.assertEquals(u.getUserId(), "aaa");
        Assert.assertEquals(u.extFields().get("f1"), "v1");
    }

    @Test
    public void test_json2ObjectExt_subAbstractEnum() {
        String jsonStr = "\"0003\"";
        MHTStepCode a = CoreObjectUtils.json2Object(jsonStr, MHTStepCode.class);
        Assert.assertEquals(a.name(), "0003");
        Assert.assertEquals(a.desc(), "快赎");
    }

    @Test
    public void test_json2ObjectExt_subAbstractCodedEnum() {

        String jsonStr = "\"022\"";
        Apkind a = CoreObjectUtils.json2ObjectExt(jsonStr, Apkind.class);
        Assert.assertEquals(a.code(), "022");
        Assert.assertEquals(a.desc(), "申购申请");
        Assert.assertEquals(a.name(), "PURCHAS_APP");

        BizCode b = CoreObjectUtils.json2ObjectExt(jsonStr, BizCode.class);
        Assert.assertEquals(b.code(), "022");
        Assert.assertEquals(b.desc(), "不支持的操作");
        Assert.assertEquals(b.name(), "UnsupportedOperation");
    }

    @Test
    public void test_json2Object_mixDeserialze() {
        String enum_jsonStr = "{\"name\":\"0003\",\"desc\":\"快赎\"}";
        MHTStepCode enum_a = CoreObjectUtils.json2Object(enum_jsonStr, MHTStepCode.class);
        Assert.assertEquals(enum_a.name(), "0003");
        Assert.assertEquals(enum_a.desc(), "快赎");

        MHTStepCode enum_aa = CoreObjectUtils.json2ObjectExt(enum_jsonStr, MHTStepCode.class);
        Assert.assertEquals(enum_aa.name(), "0003");
        Assert.assertEquals(enum_aa.desc(), "快赎");

        String enum_valStr = "\"0003\"";
        MHTStepCode enum_v = CoreObjectUtils.json2Object(enum_valStr, MHTStepCode.class);
        Assert.assertEquals(enum_v.name(), "0003");
        Assert.assertEquals(enum_v.desc(), "快赎");

        MHTStepCode enum_vv = CoreObjectUtils.json2ObjectExt(enum_valStr, MHTStepCode.class);
        Assert.assertEquals(enum_vv.name(), "0003");
        Assert.assertEquals(enum_vv.desc(), "快赎");

        String jsonStr = "{\"name\":\"PURCHAS_APP\",\"desc\":\"申购申请\",\"code\":\"022\"}";
        Apkind a = CoreObjectUtils.json2Object(jsonStr, Apkind.class);
        Assert.assertEquals(a.code(), "022");
        Assert.assertEquals(a.desc(), "申购申请");
        Assert.assertEquals(a.name(), "PURCHAS_APP");

        Apkind aa = CoreObjectUtils.json2ObjectExt(jsonStr, Apkind.class);
        Assert.assertEquals(aa.code(), "022");
        Assert.assertEquals(aa.desc(), "申购申请");
        Assert.assertEquals(aa.name(), "PURCHAS_APP");

        String valStr = "\"022\"";
        Apkind a2 = CoreObjectUtils.json2Object(valStr, Apkind.class);
        Assert.assertEquals(a2.code(), "022");
        Assert.assertEquals(a2.desc(), "申购申请");
        Assert.assertEquals(a2.name(), "PURCHAS_APP");

        Apkind aa2 = CoreObjectUtils.json2ObjectExt(valStr, Apkind.class);
        Assert.assertEquals(aa2.code(), "022");
        Assert.assertEquals(aa2.desc(), "申购申请");
        Assert.assertEquals(aa2.name(), "PURCHAS_APP");

    }

    @AfterClass
    public void afterClass() {
        System.out.println("this is after class");
    }
}
