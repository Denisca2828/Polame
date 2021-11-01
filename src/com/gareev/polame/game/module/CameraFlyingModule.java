package com.gareev.polame.game.module;

import com.gareev.polame.Main;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.lwjgl.system.windows.MOUSEINPUT;

import static org.lwjgl.glfw.GLFW.*;

public class CameraFlyingModule implements IModule{
    public int ID;
    public int parentID;
    public float speed = 1.5f;
    public float rotationSpeed = 5;

    public CameraFlyingModule(){
        Main.window.setCursorVisible(false);
        Main.window.setMousePos(Main.WIDTH / 2, Main.HEIGHT / 2);
    }

    @Override
    public void update(float deltaTime) {
        double centerW = Main.WIDTH / 2;
        double centerH = Main.HEIGHT / 2;

        TransformModule transformModule = (TransformModule) Main.gameState.currentScene.getObjectByID(this.parentID).getModule(TransformModule.class);

        Vector3f moving = new Vector3f(0, 0, 0);

        if(Main.window.isKeyState(GLFW_KEY_W, GLFW_PRESS)){
            moving = moving.add(transformModule.forward.mul(speed * deltaTime));
        }
        if(Main.window.isKeyState(GLFW_KEY_S, GLFW_PRESS)){
            moving = moving.add(transformModule.back.mul(speed * deltaTime));
        }
        if(Main.window.isKeyState(GLFW_KEY_A, GLFW_PRESS)){
            moving = moving.add(transformModule.left.mul(speed * deltaTime));
        }
        if(Main.window.isKeyState(GLFW_KEY_D, GLFW_PRESS)){
            moving = moving.add(transformModule.right.mul(speed * deltaTime));
        }

        transformModule.position = transformModule.position.add(moving);

        Vector2d curPos = Main.window.getMousePos();
        Vector2d dPos = new Vector2d(curPos.x - centerW, curPos.y - centerH);

        transformModule.rotation.y += dPos.x * rotationSpeed * deltaTime;
        transformModule.rotation.x += dPos.y * rotationSpeed * deltaTime;

        Main.window.setMousePos(centerW, centerH);
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
