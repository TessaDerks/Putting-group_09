package maths;

import entities.Camera;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;

public class Maths {

// Bary Centric function: will return the height of the triangle of the player position

    /**
     * method that performs barrycentric calculation,
     * @param p1 Vector3f, 1st point of a triangle
     * @param p2 Vector3f, 2nd point of a triangle
     * @param p3 Vector3f, 3rd point of a triangle
     * @param pos Vector2f, position
     * @return float, the height of the triangle of the position
     */
    @Contract(pure = true)
    public static float barryCentric(@NotNull Vector3f p1, @NotNull Vector3f p2, @NotNull Vector3f p3, @NotNull Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }
// creation of a transformation Matrix

    /**
     * Method that creates a transformation matrix out of a Vector2f translation.
     * @param translation Vector2f, the vector that contains the transformation which will be turned into a Transformation matrix.
     * @param scale float, value that represents the scaling of the translation.
     * @return Matrix4f, returns the transformation Matrix of the translation.
     */
    public static @NotNull Matrix4f createTransformationMatrix(Vector2f translation, @NotNull Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
        return matrix;
    }

    /**
     * Method that creates a transformation matrix out of a Vector3f translation.
     * @param translation Vector3f, the vector that contains the transformation which will be turned into a Transformation matrix.
     * @param rx float, value that represents the rotation of the x-axis.
     * @param ry float, value that represents the rotation of the y-axis.
     * @param rz float, value that represents the rotation of the z-axis.
     * @param scale float, value that represents the scaling of the translation.
     * @return Matrix4f, returns the transformation Matrix of the translation.
     */
    public static @NotNull Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
        matrix.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
        matrix.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
        matrix.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }
// creation of a view Matrix

    /**
     * Method that creates a view Matrix out of a camera object.
     * @param camera Camera, the camera that will be turned into a view Matrix
     * @return Matrix4f, returns the view Matrix from the Camera object.
     */
    public static @NotNull Matrix4f createViewMatrix(@NotNull Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
                viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }

}

