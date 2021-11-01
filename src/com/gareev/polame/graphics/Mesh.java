package com.gareev.polame.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

import com.gareev.polame.Utils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private int vaoId;
    private List<Integer> vboList;
    private final int vertexCount;

    public Mesh(float[] positions, float[] normals, float[] textCoords, int[] indices) {
        int vboId;
        vboList = new ArrayList<>();
        vertexCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        vboList.add(vboId);
        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
        verticesBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vboId = glGenBuffers();
        vboList.add(vboId);
        FloatBuffer textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
        textCoordsBuffer.put(textCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vboId = glGenBuffers();
        vboList.add(vboId);
        FloatBuffer normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
        normalsBuffer.put(normals).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vboId = glGenBuffers();
        vboList.add(vboId);
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        memFree(verticesBuffer);
        memFree(textCoordsBuffer);
        memFree(normalsBuffer);
        memFree(indicesBuffer);

        glBindVertexArray(0);
    }

    public static Mesh loadObjFile(String fileName){
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textCoords = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Float> vertices1 = new ArrayList<>();
        List<Float> textCoords1 = new ArrayList<>();
        List<Float> normals1 = new ArrayList<>();
        List<Integer> indices1 = new ArrayList<>();

        String[] objCodeA = Utils.readFile(fileName).split("\n");
        for(String line : objCodeA){
            String[] lineA = line.split(" ");
            if(lineA[0].equals("v")){
                float x = Float.parseFloat(lineA[1]);
                float y = Float.parseFloat(lineA[2]);
                float z = Float.parseFloat(lineA[3]);
                vertices.add(new Vector3f(x, y, z));
            }
            if(lineA[0].equals("vt")){
                float x = Float.parseFloat(lineA[1]);
                float y = Float.parseFloat(lineA[2]);
                textCoords.add(new Vector2f(x, y));
            }
            if(lineA[0].equals("vn")){
                float x = Float.parseFloat(lineA[1]);
                float y = Float.parseFloat(lineA[2]);
                float z = Float.parseFloat(lineA[3]);
                normals.add(new Vector3f(x, y, z));
            }
            if(lineA[0].equals("f")){
                String[] arg1 = lineA[1].split("/");
                String[] arg2 = lineA[2].split("/");
                String[] arg3 = lineA[3].split("/");
                int vi1 = Integer.parseInt(arg1[0]) - 1;
                int vi2 = Integer.parseInt(arg2[0]) - 1;
                int vi3 = Integer.parseInt(arg3[0]) - 1;
                int tci1 = Integer.parseInt(arg1[1]) - 1;
                int tci2 = Integer.parseInt(arg2[1]) - 1;
                int tci3 = Integer.parseInt(arg3[1]) - 1;
                int ni1 = Integer.parseInt(arg1[2]) - 1;
                int ni2 = Integer.parseInt(arg2[2]) - 1;
                int ni3 = Integer.parseInt(arg3[2]) - 1;
                Vector3f v1 = vertices.get(vi1);
                Vector3f v2 = vertices.get(vi2);
                Vector3f v3 = vertices.get(vi3);
                indices1.add(vertices1.size() / 3);vertices1.add(v1.x);vertices1.add(v1.y);vertices1.add(v1.z);
                indices1.add(vertices1.size() / 3);vertices1.add(v2.x);vertices1.add(v2.y);vertices1.add(v2.z);
                indices1.add(vertices1.size() / 3);vertices1.add(v3.x);vertices1.add(v3.y);vertices1.add(v3.z);
                Vector2f tc1 = textCoords.get(tci1);
                Vector2f tc2 = textCoords.get(tci2);
                Vector2f tc3 = textCoords.get(tci3);
                textCoords1.add(tc1.x);textCoords1.add(1-tc1.y);
                textCoords1.add(tc2.x);textCoords1.add(1-tc2.y);
                textCoords1.add(tc3.x);textCoords1.add(1-tc3.y);
                Vector3f n1 = normals.get(ni1);
                Vector3f n2 = normals.get(ni2);
                Vector3f n3 = normals.get(ni3);
                normals1.add(n1.x);normals1.add(n1.y);normals1.add(n1.z);
                normals1.add(n2.x);normals1.add(n2.y);normals1.add(n2.z);
                normals1.add(n3.x);normals1.add(n3.y);normals1.add(n3.z);
            }
        }

        float[] vertices2 = new float[vertices1.size()];
        float[] textCoords2 = new float[textCoords1.size()];
        float[] normals2 = new float[normals1.size()];
        int[] indices2 = new int[indices1.size()];

        for(int i = 0;i < vertices1.size();i++) vertices2[i] = vertices1.get(i);
        for(int i = 0;i < textCoords1.size();i++) textCoords2[i] = textCoords1.get(i);
        for(int i = 0;i < normals1.size();i++) normals2[i] = normals1.get(i);
        for(int i = 0;i < indices1.size();i++) indices2[i] = indices1.get(i);

        return new Mesh(vertices2, normals2, textCoords2, indices2);
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for(int i : vboList) glDeleteBuffers(i);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}