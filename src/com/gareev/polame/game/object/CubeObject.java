package com.gareev.polame.game.object;

import com.gareev.polame.Utils;
import com.gareev.polame.game.module.IModule;
import com.gareev.polame.game.module.RenderModule;
import com.gareev.polame.game.module.TransformModule;
import com.gareev.polame.graphics.Mesh;
import com.gareev.polame.graphics.ShaderProgram;
import com.gareev.polame.graphics.Texture;
import org.joml.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CubeObject implements IObject{
    public List<IModule> modules = new ArrayList<>();
    public int ID;

    public CubeObject(){
        this.addModuleByID(new TransformModule(
                new Vector3f(0, 0, -3),
                new Vector3f(0, 0, 0),
                1));

        this.addModuleByID(new RenderModule(
                Mesh.loadObjFile("./cube.obj"),
                ShaderProgram.loadShader("cube"),
                Texture.loadTexture("./cube.png")
        ));
    }

    @Override
    public void update(float deltaTime) {
        TransformModule transformModule = (TransformModule) this.getModule(TransformModule.class);

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
