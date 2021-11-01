package com.gareev.polame.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    public int textureId;
    public Texture(ByteBuffer buffer, int width, int height){
        this.textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
        stbi_image_free(buffer);
    }

    public static Texture loadTexture(String texturePath){
        int width;
        int height;
        ByteBuffer buf;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = stbi_load(texturePath, w, h, channels, 4);
            if (buf == null) {
                System.out.println("Image file [" + texturePath  + "] not loaded: " + stbi_failure_reason());
                System.exit(-1);
            }

            width = w.get();
            height = h.get();
        }

        return new Texture(buf, width, height);
    }

    public static Texture textureByColor(Color color){
        ByteBuffer buf = BufferUtils.createByteBuffer(16*4);
        for(int i = 0;i < 16;i++){
            buf.put((byte) color.getRed());
            buf.put((byte) color.getGreen());
            buf.put((byte) color.getBlue());
            buf.put((byte) color.getAlpha());
        }
        buf.flip();
        return new Texture(buf, 4, 4);
    }
}
