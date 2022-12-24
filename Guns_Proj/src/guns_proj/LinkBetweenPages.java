package guns_proj;

import Textures.AnimListener;
import Textures.TextureReader;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

public class LinkBetweenPages extends AnimListener implements MouseMotionListener, MouseListener{


    GL gl;
    static String pages = "home";

    String textureNames[] = {".png", "Monster2.png", "instructions.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    @Override
    public void init(GLAutoDrawable gld) {

        gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        switch (pages) {
            case "home":
                DrawBackground(0);
                break;
            case "play":
                DrawBackground(1);
                break;
            case "instrctions":
                DrawBackground(2);
                break;

        }

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawBackground(int index) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        //gl.glColor3f(0, 0.5f, 0.5f);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    /*
     * KeyListener
     */
    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    public static void main(String[] args) {
        new GunGame();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(e.getX() + "  " + e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (pages) {
            case "home":
                if (e.getX() > 260 && e.getX() < 436 && e.getY() > 204 && e.getY() < 242) {
                    pages = "play";

                } else if (e.getX() > 260 && e.getX() < 436 && e.getY() > 340 && e.getY() < 375) {
                    pages = "instrctions";

                } else if (e.getX() > 260 && e.getX() < 436 && e.getY() > 390 && e.getY() < 423) {
                    System.exit(0);
                }
                break;
                
                
                

            case "play":
                if (e.getX() > 260 && e.getX() < 436 && e.getY() > 390 && e.getY() < 425) {//back from play
                    pages = "home";
                }
                break;
                
                
            case "instrctions":
                if (e.getX() > 260 && e.getX() < 436 && e.getY() > 444 && e.getY() < 485) {//back from play
                    pages = "home";
                }
                break;

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
