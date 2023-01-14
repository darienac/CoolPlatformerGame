package render.shaders;

import org.joml.Matrix4f;
import render.Mesh;
import render.opengl.GlRenderObjectGroup;
import render.opengl.Texture;

import static org.lwjgl.opengl.GL33.*;

public class SpritesShader extends ShaderProgram {
    private int uViewProjectionMatrix;
    private int uTexture;
    private int aVertex;
    private int aTexCoord;
    private int aModelMatrix;
    private int aTexCoordMatrix;
    private final float[] uViewProjectionMatrixBuffer = new float[16];

    public SpritesShader() throws Exception {
        super();
    }

    public SpritesShader(String vertexShader, String fragmentShader) throws Exception {
        super(vertexShader, fragmentShader);
    }

    public void link() throws Exception {
        super.link();

        aVertex = glGetAttribLocation(getProgramId(), "aVertex");
        glEnableVertexAttribArray(aVertex);
        aTexCoord = glGetAttribLocation(getProgramId(), "aTexCoord");
        glEnableVertexAttribArray(aTexCoord);

        aTexCoordMatrix = glGetAttribLocation(getProgramId(), "aTexCoordMatrix");
        glEnableVertexAttribArray(aTexCoordMatrix);
        glVertexAttribDivisor(aTexCoordMatrix, 1);
        glEnableVertexAttribArray(aTexCoordMatrix + 1);
        glVertexAttribDivisor(aTexCoordMatrix + 1, 1);
        glEnableVertexAttribArray(aTexCoordMatrix + 2);
        glVertexAttribDivisor(aTexCoordMatrix + 2, 1);

        aModelMatrix = glGetAttribLocation(getProgramId(), "aModelMatrix");
        glEnableVertexAttribArray(aModelMatrix);
        glVertexAttribDivisor(aModelMatrix, 1);
        glEnableVertexAttribArray(aModelMatrix + 1);
        glVertexAttribDivisor(aModelMatrix + 1, 1);
        glEnableVertexAttribArray(aModelMatrix + 2);
        glVertexAttribDivisor(aModelMatrix + 2, 1);
        glEnableVertexAttribArray(aModelMatrix + 3);
        glVertexAttribDivisor(aModelMatrix + 3, 1);

        uViewProjectionMatrix = glGetUniformLocation(getProgramId(), "uViewProjectionMatrix");
    }

    public void bindMesh(Mesh mesh) {
        mesh.getVertices().bind();
        glVertexAttribPointer(aVertex, 3, GL_FLOAT, false, 0, 0);
        mesh.getTexCoords().bind();
        glVertexAttribPointer(aTexCoord, 2, GL_FLOAT, false, 0, 0);
    }

    public void bindRenderObjects(GlRenderObjectGroup renderObjects) {
        int vec4size = 16;
        int vec2size = 8;
        renderObjects.getModelMatrices().bind();
        glVertexAttribPointer(aModelMatrix, 4, GL_FLOAT, false, 4 * vec4size, 0);
        glVertexAttribPointer(aModelMatrix + 1, 4, GL_FLOAT, false, 4 * vec4size, vec4size);
        glVertexAttribPointer(aModelMatrix + 2, 4, GL_FLOAT, false, 4 * vec4size, vec4size * 2);
        glVertexAttribPointer(aModelMatrix + 3, 4, GL_FLOAT, false, 4 * vec4size, vec4size * 3);
        renderObjects.getTexCoordMatrices().bind();
        glVertexAttribPointer(aTexCoordMatrix, 2, GL_FLOAT, false, 3 * vec2size, 0);
        glVertexAttribPointer(aTexCoordMatrix + 1, 2, GL_FLOAT, false, 3 * vec2size, vec2size);
        glVertexAttribPointer(aTexCoordMatrix + 2, 2, GL_FLOAT, false, 3 * vec2size, vec2size * 2);
    }

    public void bindMatrices(Matrix4f viewProject) {
        glUniformMatrix4fv(uViewProjectionMatrix, false, viewProject.get(uViewProjectionMatrixBuffer));
    }

    public void bindTexture(Texture texture) {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureId());
    }
}
