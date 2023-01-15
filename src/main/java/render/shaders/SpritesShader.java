package render.shaders;

import org.joml.Matrix4f;
import render.Mesh;
import render.opengl.GlRenderObjectGroup;
import render.opengl.Texture;

import static org.lwjgl.opengl.GL33.*;

public class SpritesShader extends ShaderProgram {
    private int uViewProjectionMatrix;
    private int uTexture;
    private GlAttribute aVertex;
    private GlAttribute aTexCoord;
    private GlAttribute aModelMatrix;
    private GlAttribute aTexCoordMatrix;

    private GlAttribute aOpacity;

    private final float[] uViewProjectionMatrixBuffer = new float[16];

    public SpritesShader() throws Exception {
        super();
    }

    public SpritesShader(String vertexShader, String fragmentShader) throws Exception {
        super(vertexShader, fragmentShader);
    }

    public void link() throws Exception {
        super.link();

        aVertex = new GlAttribute(getProgramId(), "aVertex", false);
        aTexCoord = new GlAttribute(getProgramId(), "aTexCoord", false);

        aTexCoordMatrix = new GlAttribute(getProgramId(), "aTexCoordMatrix", true);
        aModelMatrix = new GlAttribute(getProgramId(), "aModelMatrix", true);
        aOpacity = new GlAttribute(getProgramId(), "aOpacity", true);

        uViewProjectionMatrix = glGetUniformLocation(getProgramId(), "uViewProjectionMatrix");
    }

    public void bindMesh(Mesh mesh) {
        aVertex.loadVector3f(mesh.getVertices());
        aTexCoord.loadVector2f(mesh.getTexCoords());
    }

    public void bindRenderObjects(GlRenderObjectGroup renderObjects) {
        aModelMatrix.loadMatrix4f(renderObjects.getModelMatrices());
        aTexCoordMatrix.loadMatrix3x2f(renderObjects.getTexCoordMatrices());

        aOpacity.loadFloat(renderObjects.getOpacities());
    }

    public void bindMatrices(Matrix4f viewProject) {
        glUniformMatrix4fv(uViewProjectionMatrix, false, viewProject.get(uViewProjectionMatrixBuffer));
    }

    public void bindTexture(Texture texture) {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureId());
    }
}
