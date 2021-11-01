package com.gareev.polame.game;

import com.gareev.polame.game.scene.IScene;
import com.gareev.polame.game.scene.TestScene;

public class GameState {
    public IScene currentScene;

    public GameState(){
        this.currentScene = new TestScene();
    }

    public void update(float deltaTime){
        this.currentScene.update(deltaTime);
    }

    public void render(){
        this.currentScene.render();
    }
}
