#version 330

uniform mat4 uViewProjectionMatrix;

in vec4 aVertex;
in vec2 aTexCoord;

in mat3x2 aTexCoordMatrix;
in mat4 aModelMatrix;

out vec4 vVertex;
out vec2 vTexCoord;

void main() {
    vVertex = aVertex;
    vTexCoord = aTexCoordMatrix * vec3(aTexCoord, 1.0);

    gl_Position = uViewProjectionMatrix * aModelMatrix * aVertex;
}