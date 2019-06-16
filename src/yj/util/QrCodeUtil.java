package yj.util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import sun.misc.BASE64Encoder;
 
public class QrCodeUtil{
    
    public static String createCode(String content,int width, int height) {
        String base64Code="";
        try {
            Qrcode qrcode = new Qrcode();           // 创建Qrcode对象
            // 排错率可选(%)-L(7):M(15):Q(25):H(30)
            qrcode.setQrcodeErrorCorrect('M');
            // 编码模式-Numeric(M-数字)：Binary(B-二进制):KanJi(K-汉字):Alphanumeric(A-英文字母)
            qrcode.setQrcodeEncodeMode('B');
            qrcode.setQrcodeVersion(3);             // 设置版本（可选）
 
            width = width >= 100 ? width : 100;     // 宽度至少100
            height = height >= 100 ? height: 100;   // 高度至少100
            // 创建画布和画图设备
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics2D draw = img.createGraphics();
            draw.setBackground(Color.WHITE);        // 设置背景色
            draw.clearRect(0, 0, width, height);    // 清空原始内容
            draw.setColor(Color.BLACK);             // 设置前景色
 
            int posOff = 2;     // 设置偏移量，避免输出点重叠
            // 设置内容编码
            byte[] codeContent = content.getBytes("utf-8");
            // 生成二维数组,500是内容大小，根据自己的内容大小进行设定
            if (codeContent.length > 0 && codeContent.length < 500) {
                boolean[][] qrcodeOut = qrcode.calQrcode(codeContent);
                // 将内容写入到图片中
                for (int i = 0; i < qrcodeOut.length; i++) {
                    for (int j = 0; j < qrcodeOut.length; j++) {
                        // 如果当前位置有像素点
                        if (qrcodeOut[j][i]){
                           // 写入图片
                           draw.fillRect(j * 16 + posOff, i * 16 + posOff, 16, 16);
                       }
                    }
                }
            }
 
            draw.dispose();                                // 关闭画图设备
            img.flush();                                   // 刷新缓冲区
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);    // 保存图片
            BASE64Encoder encoder = new BASE64Encoder();  
            base64Code=encoder.encode(baos.toByteArray()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Code;
    }
}
