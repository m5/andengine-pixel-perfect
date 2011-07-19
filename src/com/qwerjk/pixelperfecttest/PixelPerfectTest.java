package com.qwerjk.pixelperfecttest;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.qwerjk.andengine.entity.sprite.PixelPerfectAnimatedSprite;
import com.qwerjk.andengine.entity.sprite.PixelPerfectSprite;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTextureRegion;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTextureRegionFactory;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTiledTextureRegion;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Toast;


public class PixelPerfectTest extends BaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 320;

    // ===========================================================
    // Fields
    // ===========================================================

    private Camera mCamera;
    private BitmapTextureAtlas triangleTexture;
    private BitmapTextureAtlas diamondTexture;
    private PixelPerfectTiledTextureRegion triangleRegion;
    private PixelPerfectTextureRegion diamond100Region;
    private PixelPerfectTextureRegion diamond32Region;
    
    private BitmapTextureAtlas mFontTexture;
    private Font mFont;
    private PixelPerfectTextureRegion starRegion;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public Engine onLoadEngine() {
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final Engine engine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));

        try {
            if(MultiTouch.isSupported(this)) {
                engine.setTouchController(new MultiTouchController());
            } else {
                Toast.makeText(this, "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
            }
        } catch (final MultiTouchException e) {
            Toast.makeText(this, "Sorry your Android Version does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
        }

        return engine;
    }

    @Override
    public void onLoadResources() {
        PixelPerfectTextureRegionFactory.setAssetBasePath("gfx/");
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        
        this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48, true, Color.BLACK);

        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);

        this.triangleTexture = new BitmapTextureAtlas(2048, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.diamondTexture = new BitmapTextureAtlas(1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.triangleRegion = PixelPerfectTextureRegionFactory.createTiledFromAsset(triangleTexture, this, "spinning-triangle.png", 0, 0, 20, 1);
        this.diamond100Region = PixelPerfectTextureRegionFactory.createFromAsset(diamondTexture, this, "diamond-100.png", 0, 0);
        this.diamond32Region = PixelPerfectTextureRegionFactory.createFromAsset(diamondTexture, this, "diamond-32.png", 102, 0);
        this.starRegion = PixelPerfectTextureRegionFactory.createFromAsset(diamondTexture, this, "star.png", 200, 0);

        this.mEngine.getTextureManager().loadTextures(this.triangleTexture, this.diamondTexture);
    }

    @Override
    public Scene onLoadScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene(1);
        scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

        final PixelPerfectSprite diamond100 = addSprite(scene, 400, 100, diamond100Region);
        final PixelPerfectSprite diamond32 = addSprite(scene, 300,100, diamond32Region);
        final PixelPerfectSprite star = addSprite(scene, 0, 50, starRegion);
        
        final PixelPerfectAnimatedSprite spinningTriangle = addAnimatedSprite(scene, 130, 150, 200, triangleRegion);
        spinningTriangle.setCurrentTileIndex(9);
        final Shape[] sprites = new Shape[]{diamond100, diamond32,spinningTriangle, star};
        
        long t0 = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
            spinningTriangle.collidesWith(star);
        }
        Logger.f("1000 collisions in %d ms",System.currentTimeMillis() - t0);
        
        final ChangeableText collisionText = new ChangeableText(0, 0, this.mFont, "no collisions");
        scene.attachChild(collisionText);
        
        /* The actual collision-checking. */
        scene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void reset() { }

            @Override
            public void onUpdate(final float pSecondsElapsed) {
                for(Shape spriteA : sprites){
                    for(Shape spriteB : sprites){
                        if(spriteA != spriteB && spriteA.collidesWith(spriteB)){
                            collisionText.setText("bam!");
                            return;
                        }
                    }
                }
                collisionText.setText("");
            }
        });

        return scene;
    }
    
    private PixelPerfectAnimatedSprite addAnimatedSprite(final Scene scene, final int x, final int y, final int speed, final PixelPerfectTiledTextureRegion region){
        PixelPerfectAnimatedSprite sprite = new PixelPerfectAnimatedSprite(x,y,region){
            boolean mGrabbed = false;
            
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.mGrabbed = true;
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if(this.mGrabbed) {
                            this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                        }
                        break;
                    case TouchEvent.ACTION_UP:
                        if(this.mGrabbed) {
                            this.mGrabbed = false;
                        }
                        break;
                }
                return true;
            }
        };
        sprite.animate(speed, true);
        scene.attachChild(sprite);
        scene.registerTouchArea(sprite);
        return sprite;
    }
    
    private PixelPerfectSprite addSprite(final Scene scene, final int x, final int y, final PixelPerfectTextureRegion region){
        PixelPerfectSprite sprite = new PixelPerfectSprite(x,y,region){
            boolean mGrabbed = false;
            
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pSceneTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:
                        this.mGrabbed = true;
                        break;
                    case TouchEvent.ACTION_MOVE:
                        if(this.mGrabbed) {
                            this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
                        }
                        break;
                    case TouchEvent.ACTION_UP:
                        if(this.mGrabbed) {
                            this.mGrabbed = false;
                        }
                        break;
                }
                return true;
            }
        };
        scene.attachChild(sprite);
        scene.registerTouchArea(sprite);
        return sprite;
    }

    @Override
    public void onLoadComplete() {

    }
}
