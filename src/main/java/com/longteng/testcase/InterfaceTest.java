package com.longteng.testcase;

import com.longteng.framework.asserts.AssertUtil;
import com.longteng.framework.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/19 0019.
 */
public class InterfaceTest extends TestCase {
    //方法内的入参是什么样的类型 取决于ExcelDataProvider返回的什么类型
    @Test(dataProvider = "data")
    public void runInterfaceTest(Map<String, String> stringStringMap) {
        //方法内的入参是什么样的类型，取决于ExcelDataProvider 返回的类型
        String caseName = stringStringMap.get("用例名称");
        String url = stringStringMap.get("接口地址(名称)");
        String methodName = stringStringMap.get("方法");
        String param = stringStringMap.get("入参");
        String assertType = stringStringMap.get("断言类型");
        String expected = stringStringMap.get("预期结果");

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("url", url);
        paramMap.put("methodName", methodName);
        paramMap.put("paramBody", param);

        Map<String, String> resultMap = HttpClientUtil.request(paramMap, null);

        String actual = "{\"code\":200,\"msg\":\"绑定成功\",\"success\":true}";
        actual = clearStr(resultMap.get("returnBody"));
        expected = clearStr(expected);
        if (null == assertType || assertType.equalsIgnoreCase("全部比对")) {
            AssertUtil.assertEquals(actual, expected, caseName);
        } else if (assertType.equalsIgnoreCase("包含")) {
            AssertUtil.assertContains(actual, expected, caseName);
        }
    }
    private String clearStr(String actual){
        if(!StringUtils.isBlank(actual)){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(actual);
            actual = m.replaceAll("");
        }
        return actual;
    }
}

