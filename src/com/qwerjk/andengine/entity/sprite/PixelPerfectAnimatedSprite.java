package com.qwerjk.andengine.entity.sprite;

import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;

import com.qwerjk.andengine.collision.PixelPerfectBitMask;
import com.qwerjk.andengine.entity.shape.IPixelPerfectShape;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTiledTextureRegion;

public class PixelPerfectAnimatedSprite extends AnimatedSprite implements IPixelPerfectShape {
    PixelPerfectTiledTextureRegion ppTextureRegion;
    public PixelPerfectAnimatedSprite(float pX, float pY, PixelPerfectTiledTextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion);
        ppTextureRegion = pTextureRegion;
    }
    
    @Override
    public boolean collidesWith(IShape other){
        if(other instanceof IPixelPerfectShape){
            return super.collidesWith(other)
                && pixelPerfectCollidesWith((IPixelPerfectShape) other);
        }else{
            return super.collidesWith(other);
        }
    }
    
    public boolean pixelPerfectCollidesWith(IPixelPerfectShape other){
        return getMask().collidesWith( other.getMask() 
                                     , (int)(other.getX() - this.getX())
                                     , (int)(other.getY() - this.getY()));
    }
    
    @Override
    public PixelPerfectBitMask getMask(){
        return ppTextureRegion.getMask(this.getCurrentTileIndex());
    }
}
