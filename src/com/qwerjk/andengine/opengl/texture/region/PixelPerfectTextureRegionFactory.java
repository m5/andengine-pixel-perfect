package com.qwerjk.andengine.opengl.texture.region;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;


import android.content.Context;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

public class PixelPerfectTextureRegionFactory{
    private static String sAssetBasePath = "";

    public static void setAssetBasePath(final String pAssetBasePath) {
        if(pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            sAssetBasePath = pAssetBasePath;
        } else {
            throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
        }
    }
    public static PixelPerfectTiledTextureRegion createTiledFromAsset(final BitmapTextureAtlas pTexture, final Context pContext, final String pAssetPath, final int pTexturePositionX, final int pTexturePositionY, final int pTileColumns, final int pTileRows) {
        final IBitmapTextureAtlasSource textureSource = new AssetBitmapTextureAtlasSource(pContext, sAssetBasePath + pAssetPath);
        return createTiledFromSource(pTexture, textureSource, pTexturePositionX, pTexturePositionY, pTileColumns, pTileRows);
    }
    public static PixelPerfectTextureRegion createFromAsset(final BitmapTextureAtlas pTexture, final Context pContext, final String pAssetPath, final int pTexturePositionX, final int pTexturePositionY) {
        final IBitmapTextureAtlasSource textureSource = new AssetBitmapTextureAtlasSource(pContext, sAssetBasePath + pAssetPath);
        return createFromSource(pTexture, textureSource, pTexturePositionX, pTexturePositionY);
    }
    
    public static PixelPerfectTextureRegion createFromSource(final BitmapTextureAtlas pTexture, final IBitmapTextureAtlasSource pTextureSource, final int pTexturePositionX, final int pTexturePositionY) {
        final PixelPerfectTextureRegion textureRegion = new PixelPerfectTextureRegion(pTexture, pTextureSource, pTexturePositionX, pTexturePositionY, pTextureSource.getWidth(), pTextureSource.getHeight());
        pTexture.addTextureAtlasSource(pTextureSource, textureRegion.getTexturePositionX(), textureRegion.getTexturePositionY());
        return textureRegion;
    }

    public static PixelPerfectTiledTextureRegion createTiledFromSource(final BitmapTextureAtlas pTexture, final IBitmapTextureAtlasSource pTextureSource, final int pTexturePositionX, final int pTexturePositionY, final int pTileColumns, final int pTileRows) {
        final PixelPerfectTiledTextureRegion tiledTextureRegion = new PixelPerfectTiledTextureRegion(pTexture, pTextureSource, pTexturePositionX, pTexturePositionY, pTextureSource.getWidth(), pTextureSource.getHeight(), pTileColumns, pTileRows);
        pTexture.addTextureAtlasSource(pTextureSource, tiledTextureRegion.getTexturePositionX(), tiledTextureRegion.getTexturePositionY());
        return tiledTextureRegion;
    }
}
