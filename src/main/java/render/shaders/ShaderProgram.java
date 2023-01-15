package render.shaders;

import render.opengl.Texture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20C.glGetUniformLocation;

public class ShaderProgram implements AutoCloseable {
    private static final String SHADER_PATH = "shaders/";

    private final int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram(String vertexShader, String fragmentShader) throws Exception {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Could not create Shader");
        }

        createVertexShader(vertexShader);
        createFragmentShader(fragmentShader);
        link();
    }

    public ShaderProgram() {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Could not create Shader");
        }
    }

    public void createVertexShader(String shaderName) throws Exception {
        vertexShaderId = createShader(readResource(shaderName), GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderName) throws Exception {
        fragmentShaderId = createShader(readResource(shaderName), GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderCode, int shaderType) throws Exception {
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

    public void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
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

    public int getProgramId() {
        return programId;
    }

    @Override
    public void close() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }

    private String readResource(String name) throws IOException {
        Path filePath = Paths.get(SHADER_PATH + name);
        return new String(Files.readAllBytes(filePath));
    }
}
