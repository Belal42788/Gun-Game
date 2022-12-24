

import Textures.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class Guns implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
  //Screen Dimensions  and  picture Dimensions
  int Min_Screen_X = 0;
  int Max_Screen_X = 700;
  int Min_Screen_Y = 0;
  int Max_Screen_Y = 700;
  int Max_X = 50;
  int Max_Y = 50;

  int Delay =1;
  //Moving Objects
  int[][] Bullets1 = new int[Max_X][Max_Y];
  int[][] Bullets2 = new int[Max_X][Max_Y];
  int[][] Enemies = new int[Max_X][Max_Y];
  int soldier1X = 2;
  int soldier1Y = 20;

  int soldier2X=2;
  int soldier2Y=40;
  //Lives
  int hearts1 =5;
  int hearts2 =5;

  boolean Multi=false;

  int slodier1index = 0;
  int slodier2index = 0;
  int monsterindex = 0;

  String textureNames[] = {"Background game\\background.png",
  "MAN\\Man1_right.png","MAN\\Man2_right.png","MAN\\Man3_right.png","MAN\\Man4_right.png","B1.png",
  "RedMan\\RedMan1_right-1.png","RedMan\\RedMan2_right-1.png","RedMan\\RedMan3_right-1.png","RedMan\\RedMan4_right-1.png","Bullet.png",
  "Running Monster\\0_Golem_Running_001-1.png","Running Monster\\0_Golem_Running_002-1.png","Running Monster\\0_Golem_Running_003-1.png",
  "Running Monster\\0_Golem_Running_004-1.png","Running Monster\\0_Golem_Running_005-1.png","Running Monster\\0_Golem_Running_006-1.png"
  };
  TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
  int textures[] = new int[textureNames.length];

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();


//    gl.glMatrixMode(GL.GL_PROJECTION);
//    gl.glLoadIdentity();
//    gl.glOrtho(Min_Screen_X, Max_Screen_X, Min_Screen_Y, Max_Screen_Y, -1.0, 1.0);
//    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

    gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    gl.glGenTextures(textureNames.length, textures, 0);

    for (int i = 0; i < textureNames.length; i++) {
      try {
        texture[i] = TextureReader.readTexture("MONSTER_GAME\\"+ textureNames[i], true);
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
  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glLoadIdentity();

    //Animationindex
    slodier1index = 1 + slodier1index % 4;
    slodier2index = 6 + slodier2index % 4;

    monsterindex++;
    monsterindex = 11 + monsterindex % 6;
    //Background
    DrawBackground(0,gl);

    //ٍSoldier1
    TO_Draw_Soldier1(gl,slodier1index);

    //Bullet1
    TO_Draw_Bullets1(gl);
    TO_Move_Bullets1();
    TO_Disappear_Bullets1();

    //Enemies
    TO_Delay_Enemies();
    TO_Draw_Enemies(gl);
    TO_Move_Enemies();
    TO_Disappear_Enemies();

    //Handling Collisions1
    Handle_Bullet_Collision1();
    Handle_Soldier_Collision1();

    if(Multi){
      //ٍSoldier2
      TO_Draw_Soldier2(gl,slodier2index);

      //Bullet2
      TO_Draw_Bullets2(gl);
      TO_Move_Bullets2();
      TO_Disappear_Bullets2();

      //Handling Collisions2
      Handle_Bullet_Collision2();
      Handle_Soldier_Collision2();
    }
  }
  ////////////////////////////////////////////////////////////////////////
  //Background
  public void DrawBackground(int index,GL gl) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

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

  ////////////////////////////////////////////////////////////////////////
  //Soldier
  void TO_Draw_Soldier1(GL gl,int index) {
//    gl.glPointSize(20.0f);
//    gl.glColor3f(1.0f, 0.0f, 1.0f);
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(X(soldier1X), Y(soldier1Y));
//    gl.glEnd();

    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

    gl.glPushMatrix();

    gl.glTranslated(soldier1X * 13 / (Max_Screen_X / 2.0) -0.95 , soldier1Y * 13/ (Max_Screen_Y / 2.0) -0.95 , 0);

    gl.glScaled(0.1, 0.1 , 1);
    //System.out.println(x +" " + y);
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

  public void TO_Move_Soldier1(int Key) {
    if (Key == KeyEvent.VK_UP&&(soldier1Y < Max_Y -1)) {
      soldier1Y++;
    } else if (Key == KeyEvent.VK_DOWN&&(soldier1Y > 1) ){
      soldier1Y--;
    } else if (Key == KeyEvent.VK_RIGHT&&(soldier1X < Max_X -1)) {
      soldier1X++;
    } else if (Key == KeyEvent.VK_LEFT&&(soldier1X > 1)) {
      soldier1X--;
    }
  }

  ////////////////////////////////////////////////////////////////////////
  //Soldier
  void TO_Draw_Soldier2(GL gl,int index) {
//    gl.glPointSize(20.0f);
//    gl.glColor3f(1.0f, 0.0f, 1.0f);
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(X(soldier2X), Y(soldier2Y));
//    gl.glEnd();

    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

    gl.glPushMatrix();

    gl.glTranslated(soldier1X * 13 / (Max_Screen_X / 2.0) -0.95 , soldier1Y * 13/ (Max_Screen_Y / 2.0) -0.8 , 0);

    gl.glScaled(0.1, 0.1 , 1);
    //System.out.println(x +" " + y);
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
  public void TO_Move_Soldier2(int Key) {
    if (Key == KeyEvent.VK_W&&(soldier2Y < Max_Y -1)) {
      soldier2Y++;
    } else if (Key == KeyEvent.VK_S&&(soldier2Y > 1) ){
      soldier2Y--;
    } else if (Key == KeyEvent.VK_D&&(soldier2X < Max_X -1)) {
      soldier2X++;
    } else if (Key == KeyEvent.VK_A&&(soldier2X > 1)) {
      soldier2X--;
    }
  }
  ///////////////////////////////////////////////////////////////////////
  //Bullets1
  void TO_Draw_One_Bullet1(GL gl, int x, int y) {
//    gl.glPointSize(10.0f);
//    gl.glColor3f(0.0f, 0.0f, 1.0f);
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(X(x), Y(y));
//    gl.glEnd();

    gl.glEnable(GL.GL_BLEND);

    // index هنا كل مرة بيعرض صورة مختلفة للطلقة او الشكل اللي انا محدده على حسب ال
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]);	// Turn Blending On

    gl.glPushMatrix();

    gl.glTranslated(X(x)  / (Max_Screen_X / 2.0) -0.85 , Y(y) / (Max_Screen_Y / 2.0) -1.02 , 0);
    gl.glScaled(0.01 , 0.01 , 1);

    //لو عازز تطبع المكان اللي واقف فيه الشكل بتاعك او الرصاصة
    //System.out.println(PositionX +" " + y);

    //QUADSابدأ ارسملي مربع او
    gl.glBegin(GL.GL_QUADS);
    // Front Face
    //هنا بربط الرصاصة بالمربع اللي انا حاطط فيه العسكري
    //كل جانب او side في الرصاصة ب side في المربع
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
  public void TO_Draw_Bullets1(GL gl){
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Bullets1[i][j] == 1) {
          TO_Draw_One_Bullet1(gl, i, j);
        }
      }
    }
  }
  public void TO_Move_Bullets1(){
    for (int i = Max_X - 2; i >= 0; i--) {
      for (int j = 0; j < Max_Y; j++) {
        if (Bullets1[i][j] == 1) {
          Bullets1[i][j] = 0;
          Bullets1[i + 1][j] = 1;
        }
      }
    }
  }
  public void TO_Disappear_Bullets1(){
    for (int j = 0; j < Max_Y; j++) {
      if (Bullets1[Max_X - 1][j] == 1) {
        Bullets1[Max_X - 1][j] = 0;
      }
    }
  }
  public void TO_Fire1() {
    Bullets1[soldier1X][soldier1Y] = 1;
  }
  ///////////////////////////////////////////////////////////////////////
  //Bullets2
  void TO_Draw_One_Bullet2(GL gl, int x, int y) {
//    gl.glPointSize(10.0f);
//    gl.glColor3f(0.0f, 0.0f, 1.0f);
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(X(x), Y(y));
//    gl.glEnd();
    gl.glEnable(GL.GL_BLEND);

    // index هنا كل مرة بيعرض صورة مختلفة للطلقة او الشكل اللي انا محدده على حسب ال
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[10]);	// Turn Blending On

    gl.glPushMatrix();

    gl.glTranslated(X(x)  / (Max_Screen_X / 2.0) -0.85 , Y(y) / (Max_Screen_Y / 2.0) -1.02 , 0);
    gl.glScaled(0.01 , 0.01 , 1);

    //لو عازز تطبع المكان اللي واقف فيه الشكل بتاعك او الرصاصة
    //System.out.println(PositionX +" " + y);

    //QUADSابدأ ارسملي مربع او
    gl.glBegin(GL.GL_QUADS);
    // Front Face
    //هنا بربط الرصاصة بالمربع اللي انا حاطط فيه العسكري
    //كل جانب او side في الرصاصة ب side في المربع
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
  public void TO_Draw_Bullets2(GL gl){
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Bullets2[i][j] == 1) {
          TO_Draw_One_Bullet2(gl, i, j);
        }
      }
    }
  }
  public void TO_Move_Bullets2(){
    for (int i = Max_X - 2; i >= 0; i--) {
      for (int j = 0; j < Max_Y; j++) {
        if (Bullets2[i][j] == 1) {
          Bullets2[i][j] = 0;
          Bullets2[i + 1][j] = 1;
        }
      }
    }
  }
  public void TO_Disappear_Bullets2(){
    for (int j = 0; j < Max_Y; j++) {
      if (Bullets2[Max_X - 1][j] == 1) {
        Bullets2[Max_X - 1][j] = 0;
      }
    }
  }
  public void TO_Fire2() {
    Bullets2[soldier2X][soldier2Y] = 1;
  }
  /////////////////////////////////////////////////////////////////////////////////////
  //Enemies
  public void TO_Delay_Enemies(){
    if (Delay++ %16==0) {
      TO_Generate_Enemies();
    }
    if(Delay ==1000) Delay =1;
  }
  void TO_Generate_Enemies() {
    int x = 49;
    int y = (int) (Math.random() * (Max_Y-1));
    Enemies[x][y] = 1;
  }
  void TO_Draw_One_Enemy(GL gl, int x, int y, int index) {
//    gl.glPointSize(30.0f);
//    gl.glColor3f(0.0f, 1.0f, 0.0f);
//    gl.glBegin(GL.GL_POINTS);
//    gl.glVertex2i(X(x), Y(y));
//    gl.glEnd();

    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

    gl.glPushMatrix();

    gl.glTranslated(x * 13 / (Max_Screen_X / 2.0) -0.9 , y * 13/ (Max_Screen_Y / 2.0) -0.9 , 0);
    gl.glScaled(0.15, 0.15 , 1);

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
  public void TO_Draw_Enemies(GL gl){
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1) {
          TO_Draw_One_Enemy(gl, i, j,monsterindex);
        }
      }
    }
  }
  public void TO_Move_Enemies(){
    for (int i = 1; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1) {
          Enemies[i][j] = 0;
          Enemies[i - 1][j] = 1;
        }
      }
    }
  }
  public void TO_Disappear_Enemies(){
    for (int j = 0; j < Max_Y; j++) {
      if (Enemies[0][j] == 1) {
        Enemies[0][j] = 0;
      }
    }
  }
  /////////////////////////////////////////////////////////////////////////////////////
  //collision1
  void Handle_Bullet_Collision1() {
    for (int i = 3; i < Max_X -3; i++) {
      for (int j = 3; j < Max_Y -3; j++) {
        if ((Bullets1[i][j] == 1 )&& ((Enemies[i+1][j] == 1)||(Enemies[i+1][j-1] == 1)||(Enemies[i+1][j+1] == 1)|| (Enemies[i+1][j-2] == 1)||(Enemies[i+1][j+2] == 1)|| (Enemies[i+1][j-3] == 1)||(Enemies[i+1][j+3] == 1))) {
          Bullets1[i][j] = 0;
          Enemies[i+1][j] = 0;
          Enemies[i+1][j-1] = 0;
          Enemies[i+1][j-2] = 0;
          Enemies[i+1][j+1] = 0;
          Enemies[i+1][j+2] = 0;
          break;
        }
      }
    }
  }

  void Handle_Soldier_Collision1() {
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1 && (i == soldier1X ||i == soldier1X -1||i == soldier1X -2||i == soldier1X +1||i == soldier1X +2) && (j == soldier1Y ||j == soldier1Y -1||j == soldier1Y -2||j == soldier1Y +1||j == soldier1Y +2)) {
          if (hearts1 == 0) {
            System.out.println("GameOver For player 1");
            JOptionPane.showMessageDialog(null, "GameOver For player 1.", "GameOver For player 1", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
          } else{
            Enemies[i][j] = 0;
            System.out.println(--hearts1);
          }
        }
      }
    }
  }/////////////////////////////////////////////////////////////////////////////////////
  //collision2
  void Handle_Bullet_Collision2() {
    for (int i = 3; i < Max_X -3; i++) {
      for (int j = 3; j < Max_Y -3; j++) {
        if ((Bullets2[i][j] == 1 )&& ((Enemies[i+1][j] == 1)||(Enemies[i+1][j-1] == 1)||(Enemies[i+1][j+1] == 1)|| (Enemies[i+1][j-2] == 1)||(Enemies[i+1][j+2] == 1)|| (Enemies[i+1][j-3] == 1)||(Enemies[i+1][j+3] == 1))) {
          Bullets2[i][j] = 0;
          Enemies[i+1][j] = 0;
          Enemies[i+1][j-1] = 0;
          Enemies[i+1][j-2] = 0;
          Enemies[i+1][j+1] = 0;
          Enemies[i+1][j+2] = 0;
          break;
        }
      }
    }
  }

  void Handle_Soldier_Collision2() {
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1 && (i == soldier2X ||i == soldier2X -1||i == soldier2X -2||i == soldier2X +1||i == soldier2X +2) && (j == soldier2Y ||j == soldier2Y -1||j == soldier2Y -2||j == soldier2Y +1||j == soldier2Y +2)) {
          if (hearts2 == 0) {
            System.out.println("GameOver For player 2");
            JOptionPane.showMessageDialog(null, "GameOver For player 2.", "GameOver For player 2", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
          } else{
            Enemies[i][j] = 0;
            System.out.println(--hearts2);
          }
        }
      }
    }
  }
  /////////////////////////////////////////////////////////////////////////////////////
  int X(int x) { return x * Max_Screen_X / Max_X; }
  int Y(int y) { return y * Max_Screen_Y / Max_Y; }
  /////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int i, int j, int k, int l) {}
  @Override
  public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean c) {}
  @Override
  public void keyTyped(KeyEvent e) {}
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      TO_Move_Soldier1(KeyEvent.VK_UP);
      slodier1index++;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      TO_Move_Soldier1(KeyEvent.VK_DOWN);
      slodier1index++;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      TO_Move_Soldier1(KeyEvent.VK_RIGHT);
      slodier1index++;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      TO_Move_Soldier1(KeyEvent.VK_LEFT);
      slodier1index++;
    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      TO_Fire1();
    }


    if (e.getKeyCode() == KeyEvent.VK_W) {
      TO_Move_Soldier2(KeyEvent.VK_W);
      slodier2index++;
    }else if (e.getKeyCode() == KeyEvent.VK_S) {
      TO_Move_Soldier2(KeyEvent.VK_S);
      slodier2index++;
    }else if (e.getKeyCode() == KeyEvent.VK_D) {
      TO_Move_Soldier2(KeyEvent.VK_D);
      slodier2index++;
    }else if (e.getKeyCode() == KeyEvent.VK_A) {
      TO_Move_Soldier2(KeyEvent.VK_A);
      slodier2index++;
    }else if (e.getKeyCode() == KeyEvent.VK_X) {
      TO_Fire2();
    }
  }
  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void mouseClicked(MouseEvent e) {

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

  @Override
  public void mouseDragged(MouseEvent e) {

  }

  @Override
  public void mouseMoved(MouseEvent e) {

  }
}