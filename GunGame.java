

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;

public class GunGame extends JFrame {
  Animator animator= new FPSAnimator(10);
  GLCanvas glcanvas= new GLCanvas();;
  Guns listener = new Guns();

  public static void main(String[] args) {
    new GunGame().animator.start();
  }
  public GunGame() {
    super("Gun Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    glcanvas.addGLEventListener(listener);
    animator.add(glcanvas);
    glcanvas.addKeyListener(listener);
    add(glcanvas, BorderLayout.CENTER);
    setSize(700, 700);
    setLocationRelativeTo(this);
    setVisible(true);
    setFocusable(true);
    glcanvas.requestFocus();
  }
}