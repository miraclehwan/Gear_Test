package com.example.daehwankim.test_work;

import android.graphics.Color;
import android.renderscript.ScriptGroup;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMeshCollider;
import org.gearvrf.GVRPhongShader;
import org.gearvrf.GVRPicker;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRSphereCollider;
import org.gearvrf.GVRTexture;
import org.gearvrf.IPickEvents;
import org.gearvrf.scene_objects.GVRSphereSceneObject;
import org.gearvrf.scene_objects.GVRTextViewSceneObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

/**
 * Created by daehwan.kim on 2017-05-22.
 */

public class Main extends GVRMain{
    private GVRScene scene = null;
    private PickHandler mPickHandler;
    private GVRPicker mPicker;
    @Override
    public void onInit(GVRContext gvrContext) throws Throwable {

        scene = gvrContext.getMainScene();
        scene.getMainCameraRig().getLeftCamera().setBackgroundColor(Color.BLACK);
        scene.getMainCameraRig().getRightCamera().setBackgroundColor(Color.BLACK);

        GVRSceneObject headTracker = new GVRSceneObject(gvrContext,gvrContext.createQuad(0.1f, 0.1f), gvrContext.loadTexture(new GVRAndroidResource(gvrContext, R.drawable.headtrackingpointer)));
        headTracker.getTransform().setPosition(0.0f, 0.0f, -1.0f);
        headTracker.getRenderData().setDepthTest(false);
        headTracker.getRenderData().setRenderingOrder(10000);
        scene.getMainCameraRig().addChildObject(headTracker);

        GVRSceneObject environment = makeEnvironment(gvrContext);
        scene.addSceneObject(environment);



        GVRSceneObject title = makeTitle(gvrContext);
        scene.addSceneObject(title);




        mPickHandler = new PickHandler();
        scene.getEventReceiver().addListener(mPickHandler);
        mPicker = new GVRPicker(gvrContext, scene);


    }

    public class PickHandler implements IPickEvents{

        public GVRSceneObject PickedObject = null;

        @Override
        public void onPick(GVRPicker picker) {
            GVRPicker.GVRPickedObject picked = picker.getPicked()[0];
            PickedObject = picked.hitObject;
            if (mPickHandler.PickedObject !=null){
                mPickHandler.PickedObject.getRenderData().getMaterial().setDiffuseColor(0,0,1,1);
            }
            if (mPickHandler.PickedObject != null && mPickHandler.PickedObject instanceof GVRTextViewSceneObject){
                ((GVRTextViewSceneObject) mPickHandler.PickedObject).setBackgroundColor(Color.BLUE);
            }
        }

        @Override
        public void onNoPick(GVRPicker picker) {
            if (mPickHandler.PickedObject !=null){
                mPickHandler.PickedObject.getRenderData().getMaterial().setDiffuseColor(1,0,0,1);
            }
            if (mPickHandler.PickedObject != null && mPickHandler.PickedObject instanceof GVRTextViewSceneObject){
                ((GVRTextViewSceneObject) mPickHandler.PickedObject).setBackgroundColor(Color.RED);
            }
            PickedObject = null;
        }

        @Override
        public void onEnter(GVRSceneObject sceneObj, GVRPicker.GVRPickedObject collision) {

        }

        @Override
        public void onExit(GVRSceneObject sceneObj) {

        }

        @Override
        public void onInside(GVRSceneObject sceneObj, GVRPicker.GVRPickedObject collision) {

        }
    }

    private GVRSceneObject makeEnvironment(GVRContext context){
        GVRSphereSceneObject environment = null;
        try {
            Future<GVRTexture> texture = context.getAssetLoader().loadFutureTexture(new GVRAndroidResource(context, new URL("http://images.samsung.com/is/image/samsung/global-mkt-feature-gear-360-gear-360_real360?$Download-Source$")));

            environment = new GVRSphereSceneObject(context, 72, 144, false, texture);
            environment.getTransform().setScale(20.0f, 20.0f, 20.0f);//(배경구의 크기지정)

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return environment;
    }

    private GVRSceneObject makeBalloon(GVRContext context, float x, float y, float z){
        GVRSceneObject sphere = new GVRSphereSceneObject(context, true);
        GVRRenderData renderData = sphere.getRenderData();
        GVRMaterial material = new GVRMaterial(context);
        GVRSphereCollider collider = new GVRSphereCollider(context);

        collider.setRadius(1.0f);
        sphere.attachComponent(collider);
        material.setDiffuseColor(1, 0, 0, 0.5f);
        sphere.setName("balloon");
        renderData.setShaderTemplate(GVRPhongShader.class);
        renderData.setAlphaBlend(true);
        renderData.setMaterial(material);
        renderData.setRenderingOrder(GVRRenderData.GVRRenderingOrder.TRANSPARENT);
        sphere.getTransform().setPosition(x, y, z);
        return sphere;
    }

    public void onTouchEvent(MotionEvent event){
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                if (mPickHandler.PickedObject !=null){
//                    scene.removeSceneObject(mPickHandler.PickedObject);
                }
                if (mPickHandler.PickedObject != null && mPickHandler.PickedObject instanceof GVRTextViewSceneObject){
                    ((GVRTextViewSceneObject) mPickHandler.PickedObject).setBackgroundColor(Color.BLUE);
                }
                break;
            default:
                break;
        }
    }



    private GVRTextViewSceneObject makeTitle(GVRContext context){
        GVRTextViewSceneObject title = new GVRTextViewSceneObject(context, 6, 2, "Private Theater");
        GVRRenderData renderdata = title.getRenderData();
        renderdata.setAlphaBlend(true);
        title.attachComponent(new GVRMeshCollider(context, true));
        title.getTransform().setPosition(0.0f, 0.0f, -3.0f);
        title.setTextColor(Color.YELLOW);
        title.setGravity(Gravity.CENTER);
        title.setBackgroundColor(Color.argb((int) 0.5f, 1, 0, 0));
        return title;
    }



}
