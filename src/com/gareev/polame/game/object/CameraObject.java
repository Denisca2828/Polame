package com.gareev.polame.game.object;

import com.gareev.polame.Main;
import com.gareev.polame.game.module.CameraFlyingModule;
import com.gareev.polame.game.module.CameraModule;
import com.gareev.polame.game.module.IModule;
import com.gareev.polame.game.module.TransformModule;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CameraObject implements IObject{
    public List<IModule> modules = new ArrayList<>();
    public int ID = 0;

    public CameraObject(float fov, float near, float far){
        this.addModuleByID(new TransformModule(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1));
        this.addModuleByID(new CameraModule(fov, Main.WIDTH, Main.HEIGHT, near, far));
        this.addModuleByID(new CameraFlyingModule());
    }

    @Override
    public void update(float deltaTime) {
        for(IModule module : modules) module.update(deltaTime);
    }

    @Override
    public void render() {
        for(IModule module : modules) module.render();
    }

    @Override
    public IModule getModuleByID(int id) {
        return modules.get(id);
    }

    @Override
    public int addModuleByID(IModule module) {
        modules.add(module);
        int ID = modules.size() - 1;
        module.setID(ID);
        module.setParentID(this.ID);
        return ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
        for(IModule module : modules) module.setParentID(ID);
    }

    @Override
    public IModule getModule(Class<?> cls) {
        for(int i = 0;i < modules.size();i++){
            if(cls.isInstance(modules.get(i))) return modules.get(i);
        }
        return null;
    }
}
