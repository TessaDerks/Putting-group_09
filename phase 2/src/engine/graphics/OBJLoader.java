package engine.graphics;

import engine.graphics.models.RawModel;
import org.jetbrains.annotations.NotNull;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
// Object Loader, loads an .obj file
// credits: https://www.dropbox.com/sh/x1fyet1otejxk3z/AAAoCqArl4cIx0THdRk2poW3a?dl=0
public class OBJLoader {
    /**
     *
     * @param fileName
     * @param loader
     * @return
     */
    public static RawModel loadObjModel(String fileName, Loader loader){
        FileReader reader = null;
        try{
            reader =  new FileReader(new File("res/" + fileName + ".obj"));
        }
        catch(FileNotFoundException e){
            System.out.println("couldn't load file");
            e.printStackTrace();
        }
        BufferedReader bf = new BufferedReader(reader);
        String line;
        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();
        float [] verticesArray = null;
        float [] normalsArray = null;
        float [] textureArray = null;
        int [] indicesArray = null;
        try{
            while(true){
                line = bf.readLine();
                String[] currentLine = line.split(" ");
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                }
                else if(line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                }
                else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                }
                else if(line.startsWith("f ")){
                    textureArray = new float[vertices.size()*2];
                    normalsArray = new float[vertices.size()*3];
                    break;
                }
            }

            while(line != null){
                if(!line.startsWith("f ")){
                    line = bf.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = bf.readLine();
            }
            bf.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for(Vector3f vertex: vertices){
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for(int i = 0; i< indices.size(); i++){
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
    }

    /**
     *
     * @param vertexData
     * @param indices
     * @param textures
     * @param normals
     * @param textureArray
     * @param normalsArray
     */
    private static void processVertex(String @NotNull [] vertexData, @NotNull List<Integer> indices, @NotNull List<Vector2f> textures, @NotNull List<Vector3f> normals, float @NotNull [] textureArray, float @NotNull [] normalsArray){
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentText = textures.get(Integer.parseInt(vertexData[1])-1);
        textureArray[currentVertexPointer*2] = currentText.x;
        textureArray[currentVertexPointer*2 +1] = 1 - currentText.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*3] = currentNorm.x;
        normalsArray[currentVertexPointer*3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer*3 + 2] = currentNorm.z;
    }

}
