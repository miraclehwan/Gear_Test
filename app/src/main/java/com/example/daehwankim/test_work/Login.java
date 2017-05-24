package com.example.daehwankim.test_work;

import android.graphics.Color;
import android.transition.Scene;
import android.view.Gravity;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMeshCollider;
import org.gearvrf.GVRPicker;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.IPickEvents;
import org.gearvrf.scene_objects.GVRTextViewSceneObject;

/**
 * Created by daehwan.kim on 2017-05-23.
 */

public class Login extends GVRScene {

    private PickHandler mPickHandler;
    private GVRPicker mPicker;

    /**
     * Constructs a scene with a camera rig holding left & right cameras in it.
     *
     * @param gvrContext {@link GVRContext} the app is using.
     */
    public Login(GVRContext gvrContext) {
        super(gvrContext);
        GVRScene login_scene = gvrContext.getMainScene();
        GVRSceneObject headTracker = new GVRSceneObject(gvrContext,gvrContext.createQuad(0.1f, 0.1f), gvrContext.loadTexture(new GVRAndroidResource(gvrContext, R.drawable.headtrackingpointer)));
        headTracker.getTransform().setPosition(0.0f, 0.0f, -1.0f);
        headTracker.getRenderData().setDepthTest(false);
        headTracker.getRenderData().setRenderingOrder(10000);
        getMainCameraRig().addChildObject(headTracker);

        GVRSceneObject title = makeTitle(gvrContext, "Private Theater", 4.0f, 2.0f, Color.RED, Color.YELLOW, 0.0f, 0.0f, -3.0f);
        addSceneObject(title);

        GVRSceneObject login_B = makeTitle(gvrContext, "Login", 1.9f, 1.0f, Color.RED, Color.YELLOW, -1.0f, -2.0f, -3.0f);
        addSceneObject(login_B);

        GVRSceneObject join_B = makeTitle(gvrContext, "Join", 1.9f, 1.0f, Color.RED, Color.YELLOW, 1.0f, -2.0f, -3.0f);
        addSceneObject(join_B);


    }

    public static class PickHandler implements IPickEvents {

        public GVRSceneObject PickedObject = null;

        @Override
        public void onPick(GVRPicker picker) {
        }

        @Override
        public void onNoPick(GVRPicker picker) {
        }

        @Override
        public void onEnter(GVRSceneObject sceneObj, GVRPicker.GVRPickedObject collision) {
//            sceneObj.getRenderData().setRenderMask(0);
            sceneObj.getRenderData().getMaterial().setDiffuseColor(0,0,1,1);
        }

        @Override
        public void onExit(GVRSceneObject sceneObj) {
//            sceneObj.getRenderData().setRenderMask(GVRRenderData.GVRRenderMaskBit.Left | GVRRenderData.GVRRenderMaskBit.Right);
            sceneObj.getRenderData().getMaterial().setDiffuseColor(1,0,0,1);
        }

        @Override
        public void onInside(GVRSceneObject sceneObj, GVRPicker.GVRPickedObject collision) {

        }
    }


    private GVRTextViewSceneObject makeTitle(GVRContext context, String text, float objectsizeX, float objectsizeY , int backgroundColor, int TextColor, float positionX, float positionY, float positionZ){
        GVRTextViewSceneObject title = new GVRTextViewSceneObject(context, objectsizeX, objectsizeY, text);
        GVRRenderData renderdata = title.getRenderData();
        renderdata.setAlphaBlend(true);
        title.attachComponent(new GVRMeshCollider(context, true));
        title.getTransform().setPosition(positionX, positionY, positionZ);
        title.setTextColor(TextColor);
        title.setGravity(Gravity.CENTER);
        title.setBackgroundColor(backgroundColor);
        title.setName(text);
        return title;
    }

}
