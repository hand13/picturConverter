package com.hand13.bitmapturn;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.testng.annotations.Test;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hd110 on 2017/8/15.
 */
@Test
public class Tests {
    private int [][] data;
    private int [][] aData;
    private Raster raster;
    private int kd;
    private int cd;
    private int bas;
    public Tests(){
        try {
            imgTest();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        setBas(10);
    }
    public Tests(int bas){
        try {
            imgTest();
        }
        catch (IOException IOE){
            IOE.printStackTrace();
        }
        setBas(bas);
    }
    public void setBas(int bas){
        this.bas=bas;
        aData=new int[kd/bas][cd/bas];
    }
    @Test
    public void imgTest() throws IOException {
        ImageReader reader = ImageIO.getImageReadersByFormatName("JPG").next();
        ImageInputStream in = ImageIO.createImageInputStream(new File("first.jpg"));
        reader.setInput(in,true);
        BufferedImage wholeImage = reader.read(0);
        int width = wholeImage.getWidth();
        int height = wholeImage.getHeight();
        raster = wholeImage.getData();
        kd = width;
        cd = height;
    }
    public void execute()throws InterruptedException{
        ExecutorService service = Executors.newFixedThreadPool(100);
        for(int i=0;i<100;i++){
            Thread thread = new Thread(new Rs(i));
            service.execute(thread);
            thread.join();
        }
        service.shutdown();
    }
    public static int getP(int [] colors){
        int sum = 0;
        for(int i:colors){
            sum += i;
        }
        return sum/colors.length;
    }
    class Rs implements Runnable{
        private int index;
        public Rs(int index){
            this.index = index;
        }
        @Override
        public void run() {
            int [] buffer = new int[3];
            for(int i = index;i<kd;i=i+100){
                for(int j=0;j<cd;j++){
                    data[i][j] = getP(raster.getPixel(i,j,buffer));
                }
            }
        }
    }
    public void aMake(){
        int size = bas*bas*3;
        int [] buffer = new int[size];
        int width = aData.length;
        int height = aData[0].length;
        for(int i = 0;i<width;i++){
            for(int j=0;j<height;j++){
                aData[i][j] = getP(raster.getPixels(i*bas,j*bas,bas,bas,buffer));
            }
        }
        System.out.println(width);
        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++) {
                System.out.print(getChar(aData[i][j])+" ");
            }
            System.out.println();
        }
    }
    public char getChar(int i){
        if(i<32)
            return '%';
        else if(i<64)
            return '&';
        else if(i<160)
            return '#';
        else if(i<192)
            return 'w';
        else if(i<224)
            return '~';
        else
            return ' ';
    }
}
