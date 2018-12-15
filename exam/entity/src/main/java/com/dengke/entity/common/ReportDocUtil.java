package com.dengke.entity.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.http.HttpRequest;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReportDocUtil {

    public static HWPFDocument generate() {
        File templateFile = new File("c:/files/2.doc");

        try {
            FileInputStream in = new FileInputStream(templateFile);
//            FileOutputStream out = new FileOutputStream(in.);
            HWPFDocument hwpfDocument = new HWPFDocument(in);
            // 替换读取到的 word 模板内容的指定字段
            Map<String, String> params = new HashMap<>();
//            params.put("$PZJG$", pzjg);
//            params.put("$GDNR$", gdnr);
//            params.put("$JSYY$", jsyy);
//            params.put("$CHYF$", chyf);
//            params.put("$XZGF$", xzgf);
            Range range = hwpfDocument.getRange();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            return hwpfDocument;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Template xml2XmlDoc( String templetFilePath) throws IOException{

        // 将模板文件路径拆分为文件夹路径和文件名称
        String tempLetDir = templetFilePath.substring(0, templetFilePath.lastIndexOf("/"));
        // 注意：templetFilePath.lastIndexOf("/")中，有的文件分隔符为：\ 要注意文件路径的分隔符
        String templetName = templetFilePath.substring(templetFilePath.lastIndexOf("/") + 1);
        // 将目标文件保存路径拆分为文件夹路径和文件名称
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");

        // 加载模板数据（从文件路径中获取文件，其他方式，可百度查找）
        configuration.setDirectoryForTemplateLoading(new File(tempLetDir));
        // 获取模板实例
        return configuration.getTemplate(templetName);

    }

}



