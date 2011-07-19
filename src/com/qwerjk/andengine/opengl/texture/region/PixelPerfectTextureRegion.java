package com.qwerjk.andengine.opengl.texture.region;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

import com.qwerjk.andengine.collision.PixelPerfectBitMask;

import android.graphics.Bitmap;

public class PixelPerfectTextureRegion  extends TextureRegion{
    protected PixelPerfectBitMask bitMask;

    public PixelPerfectBitMask getMask(){
        return bitMask;
    }
    
    public PixelPerfectTextureRegion(Texture pTexture, IBitmapTextureAtlasSource textureSource,
                                     int pTexturePositionX, int pTexturePositionY, 
                                     int pWidth, int pHeight
                                     ) {
        super(pTexture, pTexturePositionX, pTexturePositionY, pWidth, pHeight);
        Bitmap bmp = textureSource. onLoadBitmap(Bitmap.Config.ARGB_4444);
        this.bitMask = new PixelPerfectBitMask(bmp, pWidth, pHeight);
    }
}
