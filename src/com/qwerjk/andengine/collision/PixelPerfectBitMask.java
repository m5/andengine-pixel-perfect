package com.qwerjk.andengine.collision;

import android.graphics.Bitmap;
import android.util.Log;

public class PixelPerfectBitMask {
    private static final int INT_WIDTH = 32;
    
    private int[][]mask;
    private int width;
    private int height;
    private int columns;
    
    public PixelPerfectBitMask(Bitmap bmp, int width, int height){
        this(bmp, 0, 0, width, height);
    }
    
    public PixelPerfectBitMask(Bitmap bmp, int xOffset, int yOffset, int width, int height){
        this.height = height;
        this.width = width;
        this.columns = (width+(INT_WIDTH-1))/INT_WIDTH;
        int stride = columns*INT_WIDTH;
        
        this.mask = new int[height][columns];
        
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                this.mask[y][x/INT_WIDTH] |= ((bmp.getPixel(x+xOffset, y+yOffset) == 0 ? 0 : 1) << (INT_WIDTH - (x % INT_WIDTH)-1));
            }
        }
    }

    public boolean collidesWith(PixelPerfectBitMask oMask, int xOffset, int yOffset){
        PixelPerfectBitMask ppBitMaskA, ppBitMaskB;
        if(xOffset >= 0){
            ppBitMaskA = this;
            ppBitMaskB = oMask;
        }else{
            xOffset = -xOffset;
            yOffset = -yOffset;
            ppBitMaskA = oMask;
            ppBitMaskB = this;
        }
        
        int[][] maskB = ppBitMaskB.getMask();
        int heightB = ppBitMaskB.getHeight();
        int widthB = ppBitMaskB.getWidth();
        int colsB = (widthB + (INT_WIDTH-1)) / INT_WIDTH;
        
        int[][] maskA = ppBitMaskA.getMask();
        int widthA = ppBitMaskA.getWidth();
        int heightA = ppBitMaskA.getHeight();
        int colsA = (widthA + (INT_WIDTH-1)) / INT_WIDTH;

        int xColumnOffset = xOffset / INT_WIDTH;
        int xBitOffset = xOffset % INT_WIDTH;
        int xLBitOffset = (INT_WIDTH - xBitOffset) % INT_WIDTH;
       
        int yMin = Math.max(0, yOffset);
        int yMax = Math.min(heightA, heightB + yOffset);

        int xColMin = Math.max(0, xColumnOffset);
        int xColMax = Math.min(colsA, colsB + xColumnOffset);

        for(int rowA=yMin; rowA < yMax; rowA++){
            for(int colA = xColMin; colA < xColMax; colA++){
                
                final int colB = colA - xColumnOffset;
                final int rowB = rowA - yOffset;

                final int composite  = colB > 0 ? maskB[rowB][colB-1] << xLBitOffset 
                                                | maskB[rowB][colB]  >>> xBitOffset
                                                : maskB[rowB][colB]  >>> xBitOffset;
                
                if((composite & maskA[rowA][colA]) != 0){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void printMask(){
        for(int row=0; row<height; row++){
            String rowString = "";
            for(int col=0; col<columns; col++){
                rowString += String.format("%32s", Integer.toBinaryString(mask[row][col]));
            }
            rowString = rowString.replaceAll("0", ".");
            rowString = rowString.replaceAll("1", "#");
            Log.v("mask", rowString);
        }

    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public int[][] getMask(){
        return mask;
    }
}
