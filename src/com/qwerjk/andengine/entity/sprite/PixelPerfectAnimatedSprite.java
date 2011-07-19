package com.qwerjk.andengine.entity.sprite;

import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;

import com.qwerjk.andengine.collision.PixelPerfectBitMask;
import com.qwerjk.andengine.entity.shape.IPixelPerfectShape;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTiledTextureRegion;

public class PixelPerfectAnimatedSprite extends AnimatedSprite implements IPixelPerfectShape {
    PixelPerfectTiledTextureRegion ppTextureRegion;

    private boolean collissionEnabled = true;

    public PixelPerfectAnimatedSprite(float pX, float pY, PixelPerfectTiledTextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion);
        ppTextureRegion = pTextureRegion;
    }
    
    @Override
    public boolean collidesWith(IShape other){
        if (!collissionEnabled)
            return false;
        if(other instanceof IPixelPerfectShape){
            return super.collidesWith(other)
                && pixelPerfectCollidesWith((IPixelPerfectShape) other);
        }else{
            return super.collidesWith(other);
        }
    }

    public boolean baseCollidesWith(IShape other){
        return collissionEnabled && super.collidesWith(other);
    }
    
    public boolean pixelPerfectCollidesWith(IPixelPerfectShape other){
        return collissionEnabled && getMask().collidesWith( other.getMask()
                                     , (int)(other.getX() - this.getX())
                                     , (int)(other.getY() - this.getY()));
    }
    
    @Override
    public PixelPerfectBitMask getMask(){
        return ppTextureRegion.getMask(this.getCurrentTileIndex());
    }

    public boolean isCollissionEnabled() {
        return collissionEnabled;
    }

    public void setCollissionEnabled(boolean collissionEnabled) {
        this.collissionEnabled = collissionEnabled;
    }
}
