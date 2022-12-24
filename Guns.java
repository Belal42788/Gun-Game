

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Guns implements GLEventListener, KeyListener {
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
  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrtho(Min_Screen_X, Max_Screen_X, Min_Screen_Y, Max_Screen_Y, -1.0, 1.0);
    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
  }
  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);

    //ٍSoldier1
    TO_Draw_Soldier1(gl);

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
      TO_Draw_Soldier2(gl);

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
  //Soldier
  void TO_Draw_Soldier1(GL gl) {
    gl.glPointSize(20.0f);
    gl.glColor3f(1.0f, 0.0f, 1.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(X(soldier1X), Y(soldier1Y));
    gl.glEnd();
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
  void TO_Draw_Soldier2(GL gl) {
    gl.glPointSize(20.0f);
    gl.glColor3f(1.0f, 0.0f, 1.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(X(soldier2X), Y(soldier2Y));
    gl.glEnd();
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
    gl.glPointSize(10.0f);
    gl.glColor3f(0.0f, 0.0f, 1.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(X(x), Y(y));
    gl.glEnd();
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
    gl.glPointSize(10.0f);
    gl.glColor3f(0.0f, 0.0f, 1.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(X(x), Y(y));
    gl.glEnd();
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
  void TO_Draw_One_Enemy(GL gl, int x, int y) {
    gl.glPointSize(30.0f);
    gl.glColor3f(0.0f, 1.0f, 0.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(X(x), Y(y));
    gl.glEnd();
  }
  public void TO_Draw_Enemies(GL gl){
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1) {
          TO_Draw_One_Enemy(gl, i, j);
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
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      TO_Move_Soldier1(KeyEvent.VK_DOWN);
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      TO_Move_Soldier1(KeyEvent.VK_RIGHT);
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      TO_Move_Soldier1(KeyEvent.VK_LEFT);
    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      TO_Fire1();
    }


    if (e.getKeyCode() == KeyEvent.VK_W) {
      TO_Move_Soldier2(KeyEvent.VK_W);
    }else if (e.getKeyCode() == KeyEvent.VK_S) {
      TO_Move_Soldier2(KeyEvent.VK_S);
    }else if (e.getKeyCode() == KeyEvent.VK_D) {
      TO_Move_Soldier2(KeyEvent.VK_D);
    }else if (e.getKeyCode() == KeyEvent.VK_A) {
      TO_Move_Soldier2(KeyEvent.VK_A);
    }else if (e.getKeyCode() == KeyEvent.VK_X) {
      TO_Fire2();
    }
  }
  @Override
  public void keyReleased(KeyEvent e) {}
}