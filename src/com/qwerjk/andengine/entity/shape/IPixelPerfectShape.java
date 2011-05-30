package com.qwerjk.andengine.entity.shape;

import com.qwerjk.andengine.collision.PixelPerfectBitMask;

public interface IPixelPerfectShape {
    public PixelPerfectBitMask getMask();
    public float getX();
    public float getY();
}
