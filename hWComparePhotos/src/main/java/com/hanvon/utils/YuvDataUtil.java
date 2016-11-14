package com.hanvon.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;



public class YuvDataUtil {
	 //it works becuase in YCbCr_420_SP and YCbCr_422_SP, the Y channel is planar and appears first
    public static void rotateYuvData(byte[] rotatedData, byte[] data, int width, int height,int nCase)
    {
    	if( nCase == 0)
    	{
    		rotateYUV240SP_Clockwise(data,rotatedData,width,height);
    	}else
    	{
    		rotateYUV240SP_AntiClockwise(data,rotatedData,width,height);
    	}  	
      
    }
    
    public static void rotateYUV240SP_Clockwise(byte[] src,byte[] des,int width,int height)  
	 {          
	        int wh = width * height;  
	        //Ðý×ªY   
	        int k = 0;  
	        for(int i=0;i<width;i++) {  
	            for(int j=0;j<height;j++)   
	            {  
	                  des[k] = src[width*(height-j-1) + i];              
	                  k++;  
	            }  
	        }  
	          
	        for(int i=0;i<width;i+=2) {  
	            for(int j=0;j<height/2;j++)   
	            {     
	                  des[k] = src[wh+ width*(height/2-j-1) + i];      
	                  des[k+1]=src[wh + width*(height/2-j-1) + i+1];  
	                  k+=2;  
	            }  
	        }            
	          
	 }
    
    public static void rotateYUV240SP_AntiClockwise(byte[] src,byte[] des,int width,int height)  
	 {  	         
	        int wh = width * height;  
	        //Ðý×ªY   
	        int k = 0;  
	        for(int i=0;i<width;i++) {  
	            for(int j=0;j<height;j++)   
	            {  
	                  des[k] = src[width*j + width-i-1];              
	                  k++;  
	            }  
	        }  
	          
	        for(int i=0;i<width;i+=2) {  
	            for(int j=0;j<height/2;j++)   
	            {     
	                  des[k+1] = src[wh+ width*j + width-i-1];      
	                  des[k]=src[wh + width*j + width-(i+1)-1];  
	                  k+=2;  
	            }  
	        } 
	          
	}
   

}
