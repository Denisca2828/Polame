package com.gareev.polame.game.module;

import com.gareev.polame.Main;
import com.gareev.polame.game.object.IObject;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CameraModule implements IModule{
    public float fov;
    public float width;
    public float height;
    public float near;
    public float far;

    public Matrix4f projectionMatrix;

    public int ID;
    public int parentID;

    public CameraModule(float fov, float width, float height, float near, float far){
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.near = near;
        this.far = far;
        this.projectionMatrix = new Matrix4f();
    }

    public Matrix4f getProjectionMatrix() {
        float aspectRatio = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRatio, near, far);
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        TransformModule transform = (TransformModule) Main.gameState.currentScene.getObjectByID(parentID).getModule(TransformModule.class);
        Vector3f cameraPos = transform.position;
        Vector3f rotation = transform.rotation;
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                  .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    public Matrix4f getModelViewMatrix(TransformModule transform, Matrix4f viewMatrix) {
        Vector3f rotation = transform.rotation;
        Matrix4f modelViewMatrix = new Matrix4f();
        modelViewMatrix.identity().translate(transform.position).
                rotateX((float)Math.toRadians(-rotation.x)).
                rotateY((float)Math.toRadians(-rotation.y)).
                rotateZ((float)Math.toRadians(-rotation.z)).
                scale(transform.scale);
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }

    @Override
    public void update(float deltaTime) {
        getProjectionMatrix();
    }

    @Override
    public void render() {

    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void setParentID(int ID) {
        this.parentID = ID;
    }
}
