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
            Qrcode qrcode = new Qrcode();           // ����Qrcode����
            // �Ŵ��ʿ�ѡ(%)-L(7):M(15):Q(25):H(30)
            qrcode.setQrcodeErrorCorrect('M');
            // ����ģʽ-Numeric(M-����)��Binary(B-������):KanJi(K-����):Alphanumeric(A-Ӣ����ĸ)
            qrcode.setQrcodeEncodeMode('B');
            qrcode.setQrcodeVersion(3);             // ���ð汾����ѡ��
 
            width = width >= 100 ? width : 100;     // �������100
            height = height >= 100 ? height: 100;   // �߶�����100
            // ���������ͻ�ͼ�豸
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics2D draw = img.createGraphics();
            draw.setBackground(Color.WHITE);        // ���ñ���ɫ
            draw.clearRect(0, 0, width, height);    // ���ԭʼ����
            draw.setColor(Color.BLACK);             // ����ǰ��ɫ
 
            int posOff = 2;     // ����ƫ����������������ص�
            // �������ݱ���
            byte[] codeContent = content.getBytes("utf-8");
            // ���ɶ�ά����,500�����ݴ�С�������Լ������ݴ�С�����趨
            if (codeContent.length > 0 && codeContent.length < 500) {
                boolean[][] qrcodeOut = qrcode.calQrcode(codeContent);
                // ������д�뵽ͼƬ��
                for (int i = 0; i < qrcodeOut.length; i++) {
                    for (int j = 0; j < qrcodeOut.length; j++) {
                        // �����ǰλ�������ص�
                        if (qrcodeOut[j][i]){
                           // д��ͼƬ
                           draw.fillRect(j * 16 + posOff, i * 16 + posOff, 16, 16);
                       }
                    }
                }
            }
 
            draw.dispose();                                // �رջ�ͼ�豸
            img.flush();                                   // ˢ�»�����
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);    // ����ͼƬ
            BASE64Encoder encoder = new BASE64Encoder();  
            base64Code=encoder.encode(baos.toByteArray()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64Code;
    }
}
