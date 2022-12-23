import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
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
  int[][] Bullets = new int[Max_X][Max_Y];
  int[][] Enemies = new int[Max_X][Max_Y];
  int soldierX = 2;
  int soldierY = 25;

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

    //ٍSoldier
    TO_Draw_Soldier(gl);

    //Bullet
    TO_Draw_Bullets(gl);
    TO_Move_Bullets();
    TO_Disappear_Bullets();

    //Enemies
    TO_Delay_Enemies();
    TO_Draw_Enemies(gl);
    TO_Move_Enemies();
    TO_Disappear_Enemies();


  }
  ////////////////////////////////////////////////////////////////////////
  //Soldier
  void TO_Draw_Soldier(GL gl) {
    gl.glPointSize(20.0f);
    gl.glColor3f(1.0f, 0.0f, 1.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(X(soldierX), Y(soldierY));
    gl.glEnd();
  }
  public void TO_Move_Soldier(int Key) {
    if (Key == KeyEvent.VK_UP&&(soldierY < Max_Y -1)) {
      soldierY++;
    } else if (Key == KeyEvent.VK_DOWN&&(soldierY > 0) ){
      soldierY--;
    } else if (Key == KeyEvent.VK_RIGHT&&(soldierX < Max_X -1)) {
      soldierX++;
    } else if (Key == KeyEvent.VK_LEFT&&(soldierX > 0)) {
      soldierX--;
    }
  }
  ///////////////////////////////////////////////////////////////////////
  //Bullets
  void TO_Draw_One_Bullet(GL gl, int x, int y) {
    gl.glPointSize(10.0f);
    gl.glColor3f(0.0f, 0.0f, 1.0f);
    gl.glBegin(GL.GL_POINTS);
    gl.glVertex2i(X(x), Y(y));
    gl.glEnd();
  }
  public void TO_Draw_Bullets(GL gl){
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Bullets[i][j] == 1) {
          TO_Draw_One_Bullet(gl, i, j);
        }
      }
    }
  }
  public void TO_Move_Bullets(){
    for (int i = Max_X - 2; i >= 0; i--) {
      for (int j = 0; j < Max_Y; j++) {
        if (Bullets[i][j] == 1) {
          Bullets[i][j] = 0;
          Bullets[i + 1][j] = 1;
        }
      }
    }
  }
  public void TO_Disappear_Bullets(){
    for (int j = 0; j < Max_Y; j++) {
      if (Bullets[Max_X - 1][j] == 1) {
        Bullets[Max_X - 1][j] = 0;
      }
    }
  }
  public void TO_Fire() {
    Bullets[soldierX][soldierY] = 1;
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
    TO_Move_Soldier(KeyEvent.VK_UP);
  } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
    TO_Move_Soldier(KeyEvent.VK_DOWN);
  } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
    TO_Move_Soldier(KeyEvent.VK_RIGHT);
  } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
    TO_Move_Soldier(KeyEvent.VK_LEFT);
  } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
    TO_Fire();
  }
  }
  @Override
  public void keyReleased(KeyEvent e) {}
}