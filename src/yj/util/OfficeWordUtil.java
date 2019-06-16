package yj.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.google.zxing.WriterException;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import sun.misc.BASE64Encoder;

//officeπ§æﬂ
public class OfficeWordUtil {
    
    public static void main(String[] args) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, WriterException {
        new OfficeWordUtil().createWord();
    }
    
    public void createWord() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, WriterException{  
        Map<String,Object> dataMap=new HashMap<String,Object>();
        dataMap.put("receiver","’≈ Â Â");
        dataMap.put("writeDate",new Date().toGMTString());
        dataMap.put("image",QrCodeUtil.createCode("http://www.baidu.com",480,550));
        Configuration configuration = new Configuration();  
        configuration.setEncoding(Locale.getDefault(), "utf-8");
        configuration.setDirectoryForTemplateLoading(new File("D:\\testFtl")); 
        Template t =  configuration.getTemplate("testFtl.ftl","utf-8"); 
        File outFile = new File("D://testFtl//outFilessa.doc");  
        Writer out = null;  
        try {  
            FileOutputStream fos = new FileOutputStream(outFile);
            OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");
            out = new BufferedWriter(oWriter);
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace();  
        }  
           
        try {  
            t.process(dataMap, out);  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }

    
    private String getImageStr() {  
        String imgFile = "D:/testFtl/pic.PNG";  
        InputStream in = null;  
        byte[] data = null;  
        try {  
            in = new FileInputStream(imgFile);  
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);  
    }
}
