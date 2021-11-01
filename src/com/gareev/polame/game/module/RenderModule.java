package com.gareev.polame.game.module;

import com.gareev.polame.Main;
import com.gareev.polame.graphics.Mesh;
import com.gareev.polame.graphics.ShaderProgram;
import com.gareev.polame.graphics.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderModule implements IModule{
    public int ID;
    public int parentID;
    public Mesh mesh;
    public ShaderProgram shader;
    public Texture texture;

    public RenderModule(Mesh mesh, ShaderProgram shader, Texture texture){
        this.mesh = mesh;
        this.shader = shader;
        this.texture = texture;

        this.shader.createUniform("texture_sampler");
        this.shader.createUniform("projectionMatrix");
        this.shader.createUniform("modelViewMatrix");
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render() {
        TransformModule transformModule = (TransformModule) Main.gameState.currentScene.getObjectByID(this.parentID).getModule(TransformModule.class);
        CameraModule cameraModule = (CameraModule) Main.gameState.currentScene.getObjectByID(Main.gameState.currentScene.getCameraID()).getModule(CameraModule.class);

        this.shader.bind();

        this.shader.setUniform("texture_sampler", this.texture.textureId);
        this.shader.setUniform("projectionMatrix", cameraModule.getProjectionMatrix());
        this.shader.setUniform("modelViewMatrix", cameraModule.getModelViewMatrix(transformModule, cameraModule.getViewMatrix()));

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, this.texture.textureId);

        glBindVertexArray(this.mesh.getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, this.mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);

        this.shader.unbind();
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
