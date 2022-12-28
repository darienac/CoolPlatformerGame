#version 330

uniform sampler2D uTexture;

in vec4 vVertex;
in vec2 vTexCoord;

layout(location = 0) out vec4 fragColor;

void main() {
    fragColor = texture(uTexture, vTexCoord);
}