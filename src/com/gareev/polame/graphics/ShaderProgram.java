package com.gareev.polame.graphics;

import com.gareev.polame.Utils;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;
    private Map<String,Integer> uniforms;

    public ShaderProgram() {
        uniforms = new HashMap<>();
        programId = glCreateProgram();
        if (programId == 0) {
            System.out.println("Could not create Shader!");
            System.exit(-1);
        }
    }

    public static ShaderProgram loadShader(String shaderName){
        ShaderProgram output = new ShaderProgram();
        output.createVertexShader(Utils.readFile(shaderName + ".v.glsl"));
        output.createFragmentShader(Utils.readFile(shaderName + ".f.glsl"));
        output.link();
        return output;
    }

    public void createUniform(String uniformName) {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            System.out.println("Could not find uniform:" + uniformName);
            System.exit(-1);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void createVertexShader(String shaderCode) {
        try {
            vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFragmentShader(String shaderCode) {
        try {
            fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            System.out.println("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
            System.exit(-1);
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}