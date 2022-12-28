package render;

import render.camera.ICamera;
import render.opengl.GlFramebuffer;
import render.opengl.GlRenderObjectGroup;

import java.util.List;

public class RenderScene {
    private ICamera camera;
    private List<GlRenderObjectGroup> renderGroups;
    private GlFramebuffer renderTarget;

    public RenderScene(ICamera camera, List<GlRenderObjectGroup> renderGroups, GlFramebuffer renderTarget) {
        this.camera = camera;
        this.renderGroups = renderGroups;
        this.renderTarget = renderTarget;
    }

    public ICamera getCamera() {
        return camera;
    }

    public void setCamera(ICamera camera) {
        this.camera = camera;
    }

    public List<GlRenderObjectGroup> getRenderGroups() {
        return renderGroups;
    }

    public void setRenderGroups(List<GlRenderObjectGroup> renderGroups) {
        this.renderGroups = renderGroups;
    }

    public GlFramebuffer getRenderTarget() {
        return renderTarget;
    }

    public void setRenderTarget(GlFramebuffer renderTarget) {
        this.renderTarget = renderTarget;
    }
}
