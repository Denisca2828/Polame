package com.gareev.polame.game.object;

import com.gareev.polame.game.module.IModule;

public interface IObject {
    void update(float deltaTime);
    void render();
    IModule getModuleByID(int id);
    int addModuleByID(IModule module);
    void setID(int ID);
    IModule getModule(Class<?> cls);
}
