package com.pnambic.joglmodule;

import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.javafx.NewtCanvasJFX;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;

import javafx.scene.canvas.Canvas;

public class JoglModule {

  private Animator animator;

  private GLWindow glWindow;

  /**
   * Create the GLWindow and add it to the JavaFx Group.
   */
  public Canvas prepareCanvas() {
    com.jogamp.newt.Display jfxNewtDisplay =
        NewtFactory.createDisplay(null, false);
    final Screen screen = NewtFactory.createScreen(jfxNewtDisplay, 0);
    final GLCapabilities caps =
        new GLCapabilities(GLProfile.getMaxFixedFunc(true));

    glWindow = GLWindow.create(screen, caps);

    final NewtCanvasJFX result = new NewtCanvasJFX(glWindow);
    return result;
  }

  public void start() {
    animator = new Animator(glWindow);
    animator.start();
  }

  public void stop() throws Exception {
    if (animator != null) {
      animator.stop();
    }
  }

  public void demoDisplay() {
    glWindow.addGLEventListener(new BasicOglListener());
  }

  private static class BasicOglListener implements GLEventListener {

    private float rotateT = 0.0f;

    @Override
    public void init(final GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();
      gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
      gl.glClearDepth(1.0f);
      gl.glEnable(GL.GL_DEPTH_TEST);
      gl.glDepthFunc(GL.GL_LEQUAL);
      gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    }

    @Override
    public void reshape(
        final GLAutoDrawable drawable,
        final int x, final int y, final int width, final int height) {
      GL2 gl = drawable.getGL().getGL2();
      final float aspect = (float) width / (float) height;
      gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
      gl.glLoadIdentity();
      final float fh = 0.5f;
      final float fw = fh * aspect;
      gl.glFrustumf(-fw, fw, -fh, fh, 1.0f, 1000.0f);
      gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
      gl.glLoadIdentity();
    }

    @Override
    public void display(final GLAutoDrawable drawable) {
      final GL2 gl = drawable.getGL().getGL2();
      gl.glClear(GL.GL_COLOR_BUFFER_BIT);
      gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
      gl.glLoadIdentity();
      gl.glTranslatef(0.0f, 0.0f, -5.0f);
      gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
      gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
      gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
      gl.glBegin(GL2.GL_QUADS);
      gl.glColor3f(0.0f, 1.0f, 1.0f);
      gl.glVertex3f(-1.0f, 1.0f, 0.0f);
      gl.glVertex3f( 1.0f, 1.0f, 0.0f);
      gl.glVertex3f( 1.0f,-1.0f, 0.0f);
      gl.glVertex3f(-1.0f,-1.0f, 0.0f);
      gl.glEnd();
      rotateT += 0.2f;
    }

    @Override
    public void dispose(final GLAutoDrawable drawable) {
    }
  }
}
