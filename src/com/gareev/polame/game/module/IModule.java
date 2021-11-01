package com.gareev.polame.game.module;

public interface IModule {
    void update(float deltaTime);
    void render();
    void setID(int ID);
    void setParentID(int ID);
}
