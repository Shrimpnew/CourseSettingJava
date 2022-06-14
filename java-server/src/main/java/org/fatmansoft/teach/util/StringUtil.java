package org.fatmansoft.teach.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    /**
    * @Description: 下划线命名转驼峰命名
    * @Date: 2022-04-04 21:49
    * @Param underLine:
    * @return: java.lang.String
    **/
    public static String underLineToCamelCase(String underLine){
        String[] strings = underLine.split("_");
        String camelCase = "";
        for(String s:strings){
            camelCase = camelCase + s.substring(0,1).toUpperCase() + s.substring(1);
        }
        return camelCase;
    }

    /**
    * @Description: 驼峰命名转下划线命名
    * @Date: 2022-04-04 21:49
    * @Param camelCase:
    * @return: java.lang.String
    **/
    public static String CamelCaseToUnderLine(String camelCase){
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(camelCase);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String toUpperHead(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }

    public static String toLowerHead(String str){
        return str.substring(0,1).toLowerCase()+str.substring(1);
    }
}
