package com.gareev.polame.game.module;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public class TransformModule implements IModule{
    public Vector3f position;
    public Vector3f rotation;
    public float scale = 1;
    public int ID = 0;
    public int parentID = 0;
    public Vector3f forward;
    public Vector3f back;
    public Vector3f up;
    public Vector3f down;
    public Vector3f left;
    public Vector3f right;

    public TransformModule(Vector3f position, Vector3f rotation, float scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public void update(float deltaTime) {
        Matrix3f temp = new Matrix3f();
        temp.identity().rotateX((float) Math.toRadians(this.rotation.x))
                .rotateY((float) Math.toRadians(-this.rotation.y))
                .rotateZ((float) Math.toRadians(-this.rotation.z));
        forward = new Vector3f(0, 0, -1).mul(temp);
        back    = new Vector3f(0, 0, 1).mul(temp);
        up      = new Vector3f(0, 1, 0).mul(temp);
        down    = new Vector3f(0, -1, 0).mul(temp);
        left    = new Vector3f(-1, 0, 0).mul(temp);
        right   = new Vector3f(1, 0, 0).mul(temp);
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
