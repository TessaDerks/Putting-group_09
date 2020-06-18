package maths;


import entities.Camera;
import main.Main;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.lwjglx.input.Mouse;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;
import terrain.Terrain;

import static maths.Maths.createViewMatrix;
// This class can be used for selecting a certain spot in the rendered world, currently unused.
public class MousePicker {

    private Vector3f currentRay;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

    /**
     *
     * @param cam
     * @param projection
     */
    public MousePicker(Camera cam, Matrix4f projection) {
        camera = cam;
        projectionMatrix = projection;
        viewMatrix = createViewMatrix(camera);
    }

    /**
     *
     * @return
     */
    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
        viewMatrix = Maths.createViewMatrix(camera);
        currentRay = calculateMouseRay();
    }

    /**
     *
     * @return
     */
    private @NotNull Vector3f calculateMouseRay() {
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();
        Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector3f worldRay = toWorldCoords(eyeCoords);
        return worldRay;
    }

    /**
     *
     * @param eyeCoords
     * @return
     */
    private @NotNull Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
        Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalise();
        return mouseRay;
    }

    /**
     *
     * @param clipCoords
     * @return
     */
    private @NotNull Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
        Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    /**
     *
     * @param mouseX
     * @param mouseY
     * @return
     */
    @Contract("_, _ -> new")
    private @NotNull Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
        float x = (2.0f * mouseX) / Main.getWIDTH() - 1f;
        float y = (2.0f * mouseY) / Main.getHEIGHT() - 1f;
        return new Vector2f(x, y);
    }

}