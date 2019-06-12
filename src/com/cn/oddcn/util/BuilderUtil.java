package com.cn.oddcn.util;

import com.cn.oddcn.entity.StructEntity;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class BuilderUtil {

    public static String generateBuilderPatternCode(List<StructEntity> structEntityList) {
        StringBuilder strBuilder = new StringBuilder();

        for (StructEntity structEntity : structEntityList) {
            String structName = structEntity.structName;
            String lowerStructName = toLowerCase(structEntity.structName);

            strBuilder.append("\n\n// ");
            strBuilder.append(structName);
            strBuilder.append(" builder pattern code\n");

            strBuilder.append("type ");
            strBuilder.append(structName);
            strBuilder.append("Builder struct {\n\t");
            strBuilder.append(lowerStructName);
            strBuilder.append(" *");
            strBuilder.append(structName);
            strBuilder.append("\n}\n\n");

            strBuilder.append("func New");
            strBuilder.append(structName);
            strBuilder.append("Builder() *");
            strBuilder.append(structName);
            strBuilder.append("Builder {\n\t");
            strBuilder.append(lowerStructName);
            strBuilder.append(" := &");
            strBuilder.append(structName);
            strBuilder.append("{}\n\tb := &");
            strBuilder.append(structName);
            strBuilder.append("Builder{");
            strBuilder.append(lowerStructName);
            strBuilder.append(": ");
            strBuilder.append(lowerStructName);
            strBuilder.append("}\n\treturn b\n}\n\n");

            for (Map.Entry<String, String> entry : structEntity.structKeyValue.entrySet()) {
                String keyName = entry.getKey();
                String lowerKeyName = toLowerCase(keyName);
                String typeName = entry.getValue();
                strBuilder.append("func (b *");
                strBuilder.append(structName);
                strBuilder.append("Builder) ");
                strBuilder.append(keyName);
                strBuilder.append("(");
                strBuilder.append(lowerKeyName);
                strBuilder.append(" ");
                strBuilder.append(typeName);
                strBuilder.append(") *");
                strBuilder.append(structName);
                strBuilder.append("Builder {\n\tb.");
                strBuilder.append(lowerStructName);
                strBuilder.append(".");
                strBuilder.append(keyName);
                strBuilder.append(" = ");
                strBuilder.append(lowerKeyName);
                strBuilder.append("\n\treturn b\n}\n\n");
            }

            strBuilder.append("func (b *");
            strBuilder.append(structName);
            strBuilder.append("Builder) Build() (*");
            strBuilder.append(structName);
            strBuilder.append(", error) {\n\treturn b.");
            strBuilder.append(lowerStructName);
            strBuilder.append(", nil\n}\n\n");

        }
        return strBuilder.toString();
    }

    public static String generateMapCode(List<StructEntity> structEntityList) {
        StringBuilder strBuilder = new StringBuilder();

        for (StructEntity structEntity : structEntityList) {

            String lowerStructName = toLowerCase(structEntity.structName);

            strBuilder.append("\n\t ");
            strBuilder.append("map[string]interface{}{");
            strBuilder.append("\n\t ");
            for (Map.Entry<String, String> entry : structEntity.structKeyValue.entrySet()) {
                String keyName = entry.getKey();

                strBuilder.append("\""+HumpToUnderline(keyName)+"\"" + " :  ,");
                strBuilder.append("\n\t");
            }
            strBuilder.append("}");


        }
        return strBuilder.toString();
    }

    public static String HumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=1;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }


    private static String toLowerCase(String str) {
        char[] cs = str.toCharArray();
        if (Character.isUpperCase(cs[0])) {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }
}
