package com.gareev.polame.game.scene;

import com.gareev.polame.game.object.CameraObject;
import com.gareev.polame.game.object.IObject;
import com.gareev.polame.game.object.CubeObject;

import java.util.ArrayList;
import java.util.List;

public class TestScene implements IScene{
    public List<IObject> objects = new ArrayList<>();
    public int cameraID;

    public TestScene(){
        CameraObject camera = new CameraObject(90, 0.01f, 1000f);
        this.cameraID = this.addObjectByID(camera);
        this.addObjectByID(new CubeObject());
    }

    @Override
    public void update(float deltaTime) {
        for(IObject obj : objects) obj.update(deltaTime);
    }

    @Override
    public void render() {
        for(IObject obj : objects) obj.render();
    }

    @Override
    public IObject getObjectByID(int ID) {
        return objects.get(ID);
    }

    @Override
    public int addObjectByID(IObject object) {
        objects.add(object);
        int ID = objects.size() - 1;
        object.setID(ID);
        return ID;
    }

    @Override
    public int getCameraID() {
        return cameraID;
    }

    @Override
    public void setCameraID(int ID) {
        this.cameraID = ID;
    }
}
