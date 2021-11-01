package com.gareev.polame.game.scene;

import com.gareev.polame.game.object.IObject;

public interface IScene {
    void update(float deltaTime);
    void render();
    IObject getObjectByID(int id);
    int addObjectByID(IObject object);
    int getCameraID();
    void setCameraID(int ID);
}
