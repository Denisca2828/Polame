package com.gareev.polame;

import com.gareev.polame.game.GameState;
import com.gareev.polame.graphics.Window;

public class Main {
    public static float WIDTH = 1280;
    public static float HEIGHT = 720;
    public static GameState gameState;
    public static float deltaTime;
    public static Window window;

    public Main(){
        window = new Window();
        gameState = new GameState();

        while(!window.shouldClose()){
            long startDrawing = System.currentTimeMillis();
            window.clear();

            gameState.update(deltaTime);

            gameState.render();

            window.swapBuffers();
            window.pollEvents();

            long endDrawing = System.currentTimeMillis();
            deltaTime = (float) (endDrawing - startDrawing) / 1000;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
