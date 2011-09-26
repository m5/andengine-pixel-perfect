Pixel Perfect Collision Detection
=================================

Sloppy collisions are boring. This library extends AndEngine's `Sprite` and `AnimatedSprite` with pixel-perfect collision detection. 

Note: **this will only work for collisions between unrotated, unscaled sprites**.


Installation
============

* Save [the jar file](https://github.com/m5/andengine-pixel-perfect/raw/master/pixel-perfect-collision.jar) to your project's `libs` directory.
* If you are using eclipse, right-click on the jar, and click on `build path > Add To Build Path`
* Done!

Note: If you are using a version of AndEngine built before July 2011 (ie. you're using TextureRegions instead of BitmapTextureAliases), use [this jar](https://github.com/m5/andengine-pixel-perfect/raw/texture-regions/pixel-perfect-collision.jar) instead.



Usage
============

`PixelPerfectSprite`s work just like regular sprites, but are initialized with `PixelPerfectTextureRegion`s, which are created by the `PixelPerfectTextureRegionFactory`.

Your code should look something like this:
 
```java
public class Game extends BaseGameActivity{
    private BitmapTextureAtlas texture;
    private PixelPerfectTextureRegion diamondRegion;
    private PixelPerfectTiledTextureRegion triangleRegion;
    ...

    public void onLoadResources(){
        PixelPerfectTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/"); // if you're using non-pp bitmaps
        ...
      
        texture = new BitmapTextureAtlas(256, 64, TextureOptions.NEAREST_PREMULTIPLYALPHA);
        diamondRegion = PixelPerfectTextureRegionFactory.createFromAsset(texture, this, "diamond.png", 0,0);
        triangleRegion = PixelPerfectTextureRegionFactory.createTiledFromAsset(texture, this, "triangle.png", 65,0);

        getEngine().getTextureManager().loadTextures(texture, ...);
    }
     
    public void onLoadScene(){
        ...
        PixelPerfectSprite diamond = new PixelPerfectSprite(0,0,ppRegion);
        scene.getTopLayer().addEntity(diamond); 

        PixelPerfectSprite spinningTriangle = new PixelPerfectSprite(100,100,ppRegion);
        spinningTriangle.animate(20, true);
        scene.getTopLayer().addEntity(spinningTriangle); 

        spinningTriangle.collidesWith(diamond);
    }
} 
```

For a more complete example, check out [the demo](https://github.com/m5/andengine-pixel-perfect/blob/master/src/com/qwerjk/pixelperfecttest/PixelPerfectTest.java)


Technical Notes
============

The alpha channel of each sprite's bitmap is packed into a 1-bit-per-pixel `int[][]` mask. If the bounding boxes of the sprites intersect, logical shifts and &'s are used to test the intersection for a pixel-level collision.

The running time of the collision algorithm is proportional to the area of the intersection of the bounding boxes of the sprites being tested. 1000 collision checks against a 100x100 pixel intersection ran in 200ms on my HTC Incredible. At that rate, it shouldn't be any sort of bottleneck in your game. However, my benchmark was crude and my game required only a fraction of that speed, so YMMV.


Contributors
===========

 * [Micah Fivecoate](http://qwerjk.com/) - Creator and Maintainer
 * [alximik00 (Denis Polulyakh)](https://github.com/alximik00) - updated library to new version of AndEngine


Contact
============

If you have any questions or find any bugs, let me know.

micah.fivecoate@gmail.com
