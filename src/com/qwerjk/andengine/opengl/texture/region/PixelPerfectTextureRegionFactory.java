package com.qwerjk.andengine.opengl.texture.region;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.source.AssetTextureSource;
import org.anddev.andengine.opengl.texture.source.ITextureSource;


import android.content.Context;

public class PixelPerfectTextureRegionFactory{
    private static String sAssetBasePath = "";

    public static void setAssetBasePath(final String pAssetBasePath) {
        if(pAssetBasePath.endsWith("/") || pAssetBasePath.length() == 0) {
            sAssetBasePath = pAssetBasePath;
        } else {
            throw new IllegalStateException("pAssetBasePath must end with '/' or be lenght zero.");
        }
    }
    public static PixelPerfectTiledTextureRegion createTiledFromAsset(final Texture pTexture, final Context pContext, final String pAssetPath, final int pTexturePositionX, final int pTexturePositionY, final int pTileColumns, final int pTileRows) {
        final ITextureSource textureSource = new AssetTextureSource(pContext, sAssetBasePath + pAssetPath);
        return createTiledFromSource(pTexture, textureSource, pTexturePositionX, pTexturePositionY, pTileColumns, pTileRows);
    }
    public static PixelPerfectTextureRegion createFromAsset(final Texture pTexture, final Context pContext, final String pAssetPath, final int pTexturePositionX, final int pTexturePositionY) {
        final ITextureSource textureSource = new AssetTextureSource(pContext, sAssetBasePath + pAssetPath);
        return createFromSource(pTexture, textureSource, pTexturePositionX, pTexturePositionY);
    }
    
    public static PixelPerfectTextureRegion createFromSource(final Texture pTexture, final ITextureSource pTextureSource, final int pTexturePositionX, final int pTexturePositionY) {
        final PixelPerfectTextureRegion textureRegion = new PixelPerfectTextureRegion(pTexture, pTextureSource, pTexturePositionX, pTexturePositionY, pTextureSource.getWidth(), pTextureSource.getHeight());
        pTexture.addTextureSource(pTextureSource, textureRegion.getTexturePositionX(), textureRegion.getTexturePositionY());
        return textureRegion;
    }

    public static PixelPerfectTiledTextureRegion createTiledFromSource(final Texture pTexture, final ITextureSource pTextureSource, final int pTexturePositionX, final int pTexturePositionY, final int pTileColumns, final int pTileRows) {
        final PixelPerfectTiledTextureRegion tiledTextureRegion = new PixelPerfectTiledTextureRegion(pTexture, pTextureSource, pTexturePositionX, pTexturePositionY, pTextureSource.getWidth(), pTextureSource.getHeight(), pTileColumns, pTileRows);
        pTexture.addTextureSource(pTextureSource, tiledTextureRegion.getTexturePositionX(), tiledTextureRegion.getTexturePositionY());
        return tiledTextureRegion;
    }
}
