package com.zte.shopping.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class MakeCheckCode {
    private final char [] codeshow={'0','1','2','3','4','5','6','7','8','9'};
    public String getCode(int width, int height, OutputStream os) throws IOException {
        if(width<=0){
            width=60;
        }
        if(height<=0){
            height=20;
        }
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //获取图形
        Graphics g = image.getGraphics();
        //生成伪随机
        Random random = new Random();
        //设定背景色
        g.setColor(getRandColor(200,255));
        g.fillRect(0,0,width,height);
        g.setColor(Color.black);
        g.drawRect(0,0,width-1,height-1);
        //随机产生150条干扰线
        g.setColor(getRandColor(150,200));
        for(int i = 0 ;i<150;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int ex = random.nextInt(10);
            int ey = random.nextInt(10);
            g.drawLine(x,y,x+ex,y+ey);
        }
        StringBuilder checkCode= new StringBuilder();
        for(int i= 0;i<4;i++){
            checkCode.append(codeshow[(int) (codeshow.length * Math.random())]);
        }//设定验证码字符颜色
        g.setColor(new Color(20+random.nextInt(120),20+random.nextInt(120),20+random.nextInt(120)));
        g.setFont(new Font("Atlantic Inline",Font.PLAIN,20));
        //第一个字符的位置
        String str = checkCode.substring(0,1);
        g.drawString(str,8,15);
        str = checkCode.substring(1,2);
        g.drawString(str,20,17);
        str = checkCode.substring(2,3);
        g.drawString(str,34,15);
        str = checkCode.substring(3,4);
        g.drawString(str,50,15);
        Random rand = new Random();
        for(int i = 0;i<20;i++){
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            g.drawOval(x,y,1,1);
        }
        //释放图形
        g.dispose();
        ImageIO.write(image,"JPEG",os);
        return checkCode.toString();
    }
    Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255){
            fc=255;
        }
        if(bc>225){
            bc=255;
        }
        int r = fc+random.nextInt(bc-fc);
        int g = fc+random.nextInt(bc-fc);
        int b = fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
}
