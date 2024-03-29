
import Textures.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Guns implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

  //Sound
  boolean sound = true;

  //Screen Dimensions  and  picture Dimensions
  int Max_Screen_X = 700;
  int Max_Screen_Y = 700;
  int Max_X = 50;
  int Max_Y = 50;


  //Score
  int m, ScoreCount, ScoreCont2;
  String score = "0" , score2="0";

  //names

  int Salah, Eid, Abdo, Gedo, Bilal;


  TextRenderer ren = new TextRenderer(new Font("Rockwell Extra Bold", Font.BOLD, 25));

//Users Names
  static String name , name1 ;




  static int Time, AnimatorTime;


  int Delay =1;
  //Moving Objects
  int[][] Bullets1 = new int[Max_X][Max_Y];
  int[][] Bullets2 = new int[Max_X][Max_Y];
  int[][] Enemies = new int[Max_X][Max_Y];

  //Soldiers
  int soldier1X = 2;
  int soldier1Y = 20;
  int soldier2X=2;
  int soldier2Y=40;


  //Lives
  static int hearts1 =5;
  static int hearts2 =5;

  //Pages
  static String pages = "home";
  static String pagesBack = "home";

  //Textures indexes
  int slodier1index = 0;
  int slodier2index = 0;
  int monsterindex = 0;
  Soundd sou =new Soundd();
  Soundd so =new Soundd();

  String textureNames[] = {
          //Pages
          "HOME\\Monster(Home).png","Page 2(Single vs MULTI)\\Monster(Single vs Multi).png","INSTRUCTIONS\\Monster(instructions).png","OPTIONS\\OPTIONS.png ",// 3
          "CONTACT_US\\CONTACT_US.png","LEVELS\\Monster(levels).png",  //  4

          //Background game
          "Background game\\background.png",  // 5

          //Bullet2
          "Balloon1.png",//6

          //Soldier 1
          "MAN\\Man1_right.png","MAN\\Man2_right.png","MAN\\Man3_right.png","MAN\\Man4_right.png",//  10

          //Soldier2
          "RedMan\\RedMan1_right-1.png","RedMan\\RedMan2_right-1.png","RedMan\\RedMan3_right-1.png","RedMan\\RedMan4_right-1.png",// 14

          //Enemies
          "Running Monster\\0_Golem_Running_001-1.png","Running Monster\\0_Golem_Running_002-1.png","Running Monster\\0_Golem_Running_003-1.png",  //  18
          "Running Monster\\0_Golem_Running_004-1.png","Running Monster\\0_Golem_Running_005-1.png","Running Monster\\0_Golem_Running_006-1.png"  //22
          ,"Running Monster\\0_Golem_Running_007-1.png","Running Monster\\0_Golem_Running_007-1.png",//23

          //pause
          "PAUSE.png",//24

          //Bullet1
          "B1.png",//25

          //names
          "names\\belal.png","names\\eid.png","names\\ezzar.png","names\\gedo.png","names\\salah.png"//30
  };
  TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
  int textures[] = new int[textureNames.length];

  @Override
  public void init(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();
    gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    gl.glEnable(GL.GL_TEXTURE_2D);
    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    gl.glGenTextures(textureNames.length, textures, 0);

    for (int i = 0; i < textureNames.length; i++) {
      try {
        texture[i] = TextureReader.readTexture("MONSTER_GAME\\"+ textureNames[i], true);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
        new GLU().gluBuild2DMipmaps(
                GL.GL_TEXTURE_2D,
                GL.GL_RGBA,
                texture[i].getWidth(), texture[i].getHeight(),
                GL.GL_RGBA,
                GL.GL_UNSIGNED_BYTE,
                texture[i].getPixels()
        );
      } catch (IOException e) {
        System.out.println(e);
        e.printStackTrace();
      }
    }

  if(sound) playmusic(0);
    else stopmusic();
  }
  @Override
  public void display(GLAutoDrawable glAutoDrawable) {
    GL gl = glAutoDrawable.getGL();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    gl.glLoadIdentity();

    //Animationindex
    slodier1index =8+slodier1index % 4;
    slodier2index = 12 + slodier2index % 4;
    monsterindex++;
    monsterindex = 16 + monsterindex % 8;

    //PAGES
    if (pages == "home") {
      DrawBackground(0, gl);

    } else if (pages == "play") {
      DrawBackground(1, gl);

    } else if (pages == "instrctions") {
      DrawBackground(2, gl);

    } else if (pages == "OPTIONS") {
      DrawBackground(3, gl);

    } else if (pages == "CONTACT_US") {
      TO_Draw_Credits(gl);
    } else if (pages == "SINGLE") {
      DrawBackground(5, gl);

    } else if (pages == "EASY SINGLE") {
      pagesBack = "EASY SINGLE";
      AnimatorTime++;
      if (AnimatorTime > 10) {
        AnimatorTime = 0 ;
        Time++;
      }
      //Background
      DrawBackground(6, gl);
      m=16;


      //ٍSoldier1
      TO_Draw_Soldier1(gl, slodier1index);

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
      Handle_Soldier_Collision1(151);
      //time
      TO_Draw_Time();
      drawlifes();
      drawscore();

    } else if (pages == "MEDIUM SINGLE") {
      pagesBack = "MEDIUM SINGLE";
      AnimatorTime++;
      if (AnimatorTime > 10) {
        AnimatorTime = 0 ;
        Time++;
      }
      //Background
      DrawBackground(6, gl);

      //ٍSoldier1
      TO_Draw_Soldier1(gl, slodier1index);
      m=8;

      //Bullet1
      TO_Draw_Bullets1(gl);
      TO_Move_Bullets1();
      TO_Disappear_Bullets1();

      //Enemies
      TO_Delay_Enemies();
      TO_Draw_Enemies(gl);
      TO_Move_Enemies_Medium();
      TO_Disappear_Enemies_Medium();

      //Handling Collisions1
      Handle_Bullet_Collision1();
      Handle_Soldier_Collision1(211);
      //time
      TO_Draw_Time();
      drawlifes();
      drawscore();

      } else if (pages == "HARD SINGLE") {
      pagesBack = "HARD SINGLE";
      AnimatorTime++;
      if (AnimatorTime > 10) {
        AnimatorTime = 0 ;
        Time++;
      }
      //Background
        DrawBackground(6, gl);
        m=6;

      //ٍSoldier1
      TO_Draw_Soldier1(gl, slodier1index);

      //Bullet1
      TO_Draw_Bullets1(gl);
      TO_Move_Bullets1();
      TO_Disappear_Bullets1();


      //Enemies
      TO_Delay_Enemies();
      TO_Draw_Enemies(gl);
      TO_Move_Enemies_Hard();
      TO_Disappear_Enemies_Hard();

      //Handling Collisions1
      Handle_Bullet_Collision1();
      Handle_Soldier_Collision1(271);

      //time
      TO_Draw_Time();
      drawlifes();
      drawscore();
      } else if (pages == "DOUBLE") {
        DrawBackground(5, gl);

      } else if (pages == "EASY DOUBLE") {
      pagesBack = "EASY DOUBLE";
      AnimatorTime++;
      if (AnimatorTime > 10) {
        AnimatorTime = 0 ;
        Time++;
      }
        //Background
      m=16;
      DrawBackground(6, gl);
      //ٍSoldier1
      TO_Draw_Soldier1(gl, slodier1index);


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
      Handle_Soldier_Collision1(151);

      //ٍSoldier2
      TO_Draw_Soldier2(gl, slodier2index);

      //Bullet2
      TO_Draw_Bullets2(gl);
      TO_Move_Bullets2();
      TO_Disappear_Bullets2();

      //Handling Collisions2
      Handle_Bullet_Collision2();
      Handle_Soldier_Collision2(151);
      //time
      TO_Draw_Time();
      drawlifes();
      drawlifes2();
      drawscore();
      drawscore2();
      } else if (pages == "MEDIUM DOUBLE") {
      pagesBack = "MEDIUM DOUBLE";
      AnimatorTime++;
      if (AnimatorTime > 10) {
        AnimatorTime = 0 ;
        Time++;
      }
      //Background
      DrawBackground(6, gl);
      //ٍSoldier1
      TO_Draw_Soldier1(gl, slodier1index);
      m=8;


      //Bullet1
      TO_Draw_Bullets1(gl);
      TO_Move_Bullets1();
      TO_Disappear_Bullets1();

      //Enemies
      TO_Delay_Enemies();
      TO_Draw_Enemies(gl);
      TO_Move_Enemies_Medium();
      TO_Disappear_Enemies_Medium();

      //Handling Collisions1
      Handle_Bullet_Collision1();
      Handle_Soldier_Collision1(211);

      //ٍSoldier2
      TO_Draw_Soldier2(gl, slodier2index);

      //Bullet2
      TO_Draw_Bullets2(gl);
      TO_Move_Bullets2();
      TO_Disappear_Bullets2();

      //Handling Collisions2
      Handle_Bullet_Collision2();
      Handle_Soldier_Collision2(211);
      //time
      TO_Draw_Time();
      drawlifes();
      drawlifes2();
      drawscore2();
      drawscore();
        } else if (pages == "HARD DOUBLE") {
      pagesBack = "HARD DOUBLE";
      m=6;
      AnimatorTime++;
      if (AnimatorTime > 10) {
        AnimatorTime = 0 ;
        Time++;
      }

    //Background
    DrawBackground(6, gl);
    //ٍSoldier1
    TO_Draw_Soldier1(gl, slodier1index);


    //Bullet1
    TO_Draw_Bullets1(gl);
    TO_Move_Bullets1();
    TO_Disappear_Bullets1();

    //Enemies
    TO_Delay_Enemies();
    TO_Draw_Enemies(gl);
    TO_Move_Enemies_Medium();
    TO_Disappear_Enemies_Medium();

    //Handling Collisions1
    Handle_Bullet_Collision1();
    Handle_Soldier_Collision1(271);

    //ٍSoldier2
    TO_Draw_Soldier2(gl, slodier2index);

    //Bullet2
    TO_Draw_Bullets2(gl);
    TO_Move_Bullets2();
    TO_Disappear_Bullets2();



    //Handling Collisions2
    Handle_Bullet_Collision2();
    Handle_Soldier_Collision2(271);

    //time
    TO_Draw_Time();
    drawlifes();
    drawlifes2();
    drawscore2();
    drawscore();

     } else if(pages == "PAUSE"){
      DrawBackground(24, gl);
    }
  }
  ////////////////////////////////////////////////////////////////////////
  //Background
  public void DrawBackground(int index,GL gl) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

    gl.glPushMatrix();
    gl.glBegin(GL.GL_QUADS);
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
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);
    gl.glPushMatrix();
    gl.glTranslated(soldier1X * 13 / (Max_Screen_X / 2.0) -0.95 , soldier1Y * 10/ (Max_Screen_Y / 2.0) -0.75 , 0);
    gl.glScaled(0.1, 0.1 , 1);
    gl.glBegin(GL.GL_QUADS);

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
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);
    gl.glPushMatrix();
    gl.glTranslated(soldier2X * 13 / (Max_Screen_X / 2.0) -0.95 , soldier2Y * 10/ (Max_Screen_Y / 2.0) -0.75 , 0);
    gl.glScaled(0.1, 0.1 , 1);
    gl.glBegin(GL.GL_QUADS);

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
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[25]);
    gl.glPushMatrix();
    gl.glTranslated(X(x)  / (Max_Screen_X / 2.0) -0.84 , Y(y) / 1.4 / (Max_Screen_Y / 2.0) -0.77 , 0);
    gl.glScaled(0.02 , 0.02 , 1);
    gl.glBegin(GL.GL_QUADS);

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
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[7]);
    gl.glPushMatrix();
    gl.glTranslated(X(x)  / (Max_Screen_X / 2.0) -0.84 , Y(y) / 1.4 / (Max_Screen_Y / 2.0) -0.77 , 0);
    gl.glScaled(0.02 , 0.02 , 1);
    gl.glBegin(GL.GL_QUADS);
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
    if (Delay++ %m==0) {
      TO_Generate_Enemies();
    }
    if(Delay ==1000)
      Delay =1;
  }

  void TO_Generate_Enemies() {
    int x = 49;
    int y = (int) (Math.random() * (Max_Y-2));
    Enemies[x][y] = 1;
  }

  void TO_Draw_One_Enemy(GL gl, int x, int y, int index) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);
    gl.glPushMatrix();
    gl.glTranslated(x * 13 / (Max_Screen_X / 2.0) -0.9 , y * 9/ (Max_Screen_Y / 2.0) -0.7 , 0);

    gl.glScaled(0.15, 0.15 , 1);
    gl.glBegin(GL.GL_QUADS);

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
  public void TO_Move_Enemies_Medium(){
    for (int i = 2; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1) {
          Enemies[i][j] = 0;
          Enemies[i - 2][j] = 1;
        }
      }
    }
  }

  public void TO_Disappear_Enemies_Medium(){
    for (int j = 0; j < Max_Y; j++) {
      if (Enemies[0][j] == 1) {
        Enemies[0][j] = 0;
      }
      if (Enemies[1][j] == 1) {
          Enemies[1][j] = 0;
      }
    }
  }


    public void TO_Move_Enemies_Hard(){
      for (int i = 3; i < Max_X; i++) {
        for (int j = 0; j < Max_Y; j++) {
          if (Enemies[i][j] == 1) {
            Enemies[i][j] = 0;
            Enemies[i - 3][j] = 1;
          }
        }
      }
    }

    public void TO_Disappear_Enemies_Hard() {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[0][j] == 1) {
          Enemies[0][j] = 0;}
        if (Enemies[1][j] == 1) {
          Enemies[1][j] = 0;}
        if (Enemies[2][j] == 1) {
          Enemies[2][j] = 0;
      }
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////
  //collision1
  void Handle_Bullet_Collision1() {
    for (int i = 0; i < Max_X -1; i++) {
      for (int j = 3; j < Max_Y -3; j++) {
        if ((Bullets1[i][j] == 1 )&&((Enemies[i][j] == 1)||(Enemies[i+1][j] == 1)||(Enemies[i+1][j-1] == 1)||
            (Enemies[i+1][j+1] == 1) || (Enemies[i+1][j-2] == 1)||(Enemies[i+1][j+2] == 1)|| (Enemies[i+1][j-3] == 1)||(Enemies[i+1][j+3] == 1) ||
            (Enemies[i][j-1] == 1)||(Enemies[i][j+1] == 1) ||(Enemies[i][j-2] == 1)||(Enemies[i][j+2] == 1)|| (Enemies[i][j-3] == 1)||(Enemies[i][j+3] == 1))) {
          Bullets1[i][j] = 0;
          Enemies[i][j] = 0;
          Enemies[i][j-1] = 0;
          Enemies[i][j-2] = 0;
          Enemies[i][j-3] = 0;
          Enemies[i][j+1] = 0;
          Enemies[i][j+2] = 0;
          Enemies[i][j+3] = 0;
          Enemies[i+1][j] = 0;
          Enemies[i+1][j-1] = 0;
          Enemies[i+1][j-2] = 0;
          Enemies[i+1][j-3] = 0;
          Enemies[i+1][j+1] = 0;
          Enemies[i+1][j+2] = 0;
          Enemies[i+1][j+3] = 0;
          ScoreCount++;
          score= ScoreCount +"";
          break;
        }
      }
    }
  }

  void Handle_Soldier_Collision1(int z) {
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1 && (i == soldier1X ||i == soldier1X -1||i == soldier1X -2||i == soldier1X +1||i == soldier1X +2) && (j == soldier1Y ||j == soldier1Y -1||j == soldier1Y -2||j == soldier1Y -3||j == soldier1Y -4||j == soldier1Y -5||j == soldier1Y +1||j == soldier1Y +2||j == soldier1Y +3||j == soldier1Y +4||j == soldier1Y +5)) {
          if (hearts1-1 == 0) {

            if (sound== false)stopmusic();
            if(sound)playSE(6);
            stopmusic();

            Bullets1 = new int[Max_X][Max_Y];
            Bullets2 = new int[Max_X][Max_Y];
            Enemies = new int[Max_X][Max_Y];

            //Soldiers
            soldier1X = 2;
            soldier1Y = 20;
            soldier2X=2;
            soldier2Y=40;
            hearts1=5;
            hearts2=5;

            Time =0;

            score2= ScoreCont2 +"";
            if (sound== false)stopmusic();
            if(sound)playSE(6);
            stopmusic();

            JOptionPane.showMessageDialog(null, "              GameOver \n"+ "Name: "+name+ "   \n"+"Your Score is :"+ ScoreCount +"", "GameOver For "  , JOptionPane.WARNING_MESSAGE);
            ScoreCount =0;
            score= ScoreCount +"";
            ScoreCont2 =0;
            pages="home";
            Time =0;
          } else{
            Enemies[i][j] = 0;
            System.out.println(--hearts1);
          }
        }
        if (Time == z) {
          Bullets1 = new int[Max_X][Max_Y];
          Bullets2 = new int[Max_X][Max_Y];
          Enemies = new int[Max_X][Max_Y];

          //Soldiers
          soldier1X = 2;
          soldier1Y = 20;
          soldier2X=2;
          soldier2Y=40;
          hearts1=5;
          hearts2=5;

          Time =0;

          score2= ScoreCont2 +"";
          if(sound)playSE(8);
          JOptionPane.showMessageDialog(null, "             win \n"+ "Name: "+name+ "   \n"+"Your Score is :"+ ScoreCount +"", "GameOver For "  , JOptionPane.WARNING_MESSAGE);
          ScoreCount =0;
          score= ScoreCount +"";
          ScoreCont2 =0;
          pages="home";
          Time =0;

        }
      }

    }
  }/////////////////////////////////////////////////////////////////////////////////////
  //collision2
  void Handle_Bullet_Collision2() {
    for (int i = 0; i < Max_X -1; i++) {
      for (int j = 3; j < Max_Y -3; j++) {
        if ((Bullets2[i][j] == 1 )&&((Enemies[i][j] == 1)||(Enemies[i+1][j] == 1)||(Enemies[i+1][j-1] == 1)||
                (Enemies[i+1][j+1] == 1) || (Enemies[i+1][j-2] == 1)||(Enemies[i+1][j+2] == 1)|| (Enemies[i+1][j-3] == 1)||(Enemies[i+1][j+3] == 1) ||
                (Enemies[i][j-1] == 1)||(Enemies[i][j+1] == 1) ||(Enemies[i][j-2] == 1)||(Enemies[i][j+2] == 1)|| (Enemies[i][j-3] == 1)||(Enemies[i][j+3] == 1))) {
          Bullets2[i][j] = 0;
          Enemies[i][j] = 0;
          Enemies[i][j-1] = 0;
          Enemies[i][j-2] = 0;
          Enemies[i][j-3] = 0;
          Enemies[i][j+1] = 0;
          Enemies[i][j+2] = 0;
          Enemies[i][j+3] = 0;
          Enemies[i+1][j] = 0;
          Enemies[i+1][j-1] = 0;
          Enemies[i+1][j-2] = 0;
          Enemies[i+1][j-3] = 0;
          Enemies[i+1][j+1] = 0;
          Enemies[i+1][j+2] = 0;
          Enemies[i+1][j+3] = 0;
          ScoreCont2++;
          score2= ScoreCont2 +"";

          break;
        }
      }
    }
  }

  void Handle_Soldier_Collision2(int z) {
    for (int i = 0; i < Max_X; i++) {
      for (int j = 0; j < Max_Y; j++) {
        if (Enemies[i][j] == 1 && (i == soldier2X ||i == soldier2X -1||i == soldier2X -2||i == soldier2X +1||i == soldier2X +2) && (j == soldier2Y ||j == soldier2Y -1||j == soldier2Y -2||j == soldier2Y -3||j == soldier2Y -4||j == soldier2Y -5||j == soldier2Y +1||j == soldier2Y +2)) {
          if (hearts2-1 == 0) {
            if (sound==false)stopmusic();
            if(sound)playSE(6);
            stopmusic();

            Bullets1 = new int[Max_X][Max_Y];
            Bullets2 = new int[Max_X][Max_Y];
            Enemies = new int[Max_X][Max_Y];

            //Soldiers
            soldier1X = 2;
            soldier1Y = 20;
            soldier2X=2;
            soldier2Y=40;
            hearts1=5;
            hearts2=5;

            Time =0;
            if (sound== false)stopmusic();
            if(sound)playSE(6);
            stopmusic();
            JOptionPane.showMessageDialog(null, "     GameOver For player 2    \n"+"Name2: "+name1+ "   \n"+"YOur Score is :"+ ScoreCont2 +"" , "GameOver For player 2", JOptionPane.WARNING_MESSAGE);
            pages="home";
            Time =0;
            ScoreCont2 =0;

            score2= ScoreCont2 +"";
          } else{
            Enemies[i][j] = 0;
            System.out.println(--hearts2);
          }
        }
        if (Time == z) {


          Bullets1 = new int[Max_X][Max_Y];
          Bullets2 = new int[Max_X][Max_Y];
          Enemies = new int[Max_X][Max_Y];

          //Soldiers
          soldier1X = 2;
          soldier1Y = 20;
          soldier2X=2;
          soldier2Y=40;
          hearts1=5;
          hearts2=5;

          Time =0;

          score2= ScoreCont2 +"";
          if(sound)playSE(8);
          JOptionPane.showMessageDialog(null, "             win \n"+ "Name: "+name+ "   \n"+"YOur Score is :"+ ScoreCount +"", "Win"  , JOptionPane.WARNING_MESSAGE);
          ScoreCount =0;
          score= ScoreCount +"";
          ScoreCont2 =0;
          pages="home";
          Time =0;

        }
      }

    }


  }

  /////////////////////////////////////////////////////////////////////////////////////
  int X(int x) { return x * Max_Screen_X / Max_X; }
  int Y(int y) { return y * Max_Screen_Y / Max_Y; }
  /////////////////////////////////////////////////////////////////////////////////////
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
      if(sound)
        playSE(1);
      else
        stoppmusic();
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
      if(sound)  playSE(1);
      else stoppmusic();

    }
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
      if(pages == "EASY SINGLE"||pages == "MEDIUM SINGLE"||pages == "HARD SINGLE"||pages == "EASY DOUBLE"||pages == "MEDIUM DOUBLE"||pages == "HARD DOUBLE"){
        pages = "PAUSE";
      } else if(pages == "PAUSE"){
        if( pagesBack == "EASY SINGLE"){
          pages = "EASY SINGLE";
        } else if( pagesBack == "MEDIUM SINGLE"){
          pages = "MEDIUM SINGLE";
        }else if( pagesBack == "HARD SINGLE"){
          pages = "HARD SINGLE";
        }else if( pagesBack == "EASY DOUBLE"){
          pages = "EASY DOUBLE";
        } else if( pagesBack == "MEDIUM DOUBLE"){
          pages = "MEDIUM DOUBLE";
        }else if( pagesBack == "HARD DOUBLE"){
          pages = "HARD DOUBLE";
        }
      }
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    switch (pages) {
      case "home":
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 204 && e.getY() < 242) {
          pages = "play";
          if (sound) playSE(5);
        } else if (e.getX() > 260 && e.getX() < 436 && e.getY() > 340 && e.getY() < 375) {
          pages = "instrctions";
          if (sound) playSE(5);
        } else if (e.getX() > 260 && e.getX() < 436 && e.getY() > 275 && e.getY() < 317) {
          pages = "OPTIONS";
          if (sound) playSE(5);
        } else if (e.getX() > 260 && e.getX() < 436 && e.getY() > 400 && e.getY() < 423) {
          if (sound) playSE(5);
          System.exit(0);
        }
        break;

      case "play":
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 390 && e.getY() < 425) {//back from play
          pages = "home";
          if (sound) playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 206 && e.getY() < 246) {
          pages = "SINGLE";
          name =JOptionPane.showInputDialog(null,"Enter Name");
          if (sound)  playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 300 && e.getY() < 342) {
          pages = "DOUBLE";
          name =JOptionPane.showInputDialog(null,"user Name 1");
          name1 =JOptionPane.showInputDialog(null,"User Name 2");
          if (sound) playSE(5);
        }
        break;

      case "instrctions":
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 444 && e.getY() < 485) {//back from play
          pages = "home";
          if (sound) playSE(5);
        }
        break;

      case "OPTIONS":
        if (e.getX() > 277 && e.getX() < 421 && e.getY() > 350 && e.getY() < 382) {
          pages = "CONTACT_US";
          if (sound) playSE(5);
        }
        if (e.getX() > 276 && e.getX() < 420 && e.getY() > 446 && e.getY() < 484) {
          pages = "home";
          if (sound) playSE(5);
        }
        if (e.getX() > 299 && e.getX() < 335 && e.getY() > 284 && e.getY() < 316) {
          if (sound == false) {
            playmusic(0);
            sound = true;
          }
        }
        if (e.getX() > 364&& e.getX() < 393 && e.getY() > 286 && e.getY() < 316) {
          if (sound == true) {
            stopmusic();
            stoppmusic();
            sound = false;
          }
        }
        break;

      case "SINGLE":
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 403 && e.getY() < 441) {
          pages = "play";
          if (sound) playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 204 && e.getY() < 243) {
          if (sound) playmusic(2);
          pages = "EASY SINGLE";
          if (sound) playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 272 && e.getY() < 311) {
          if (sound) playmusic(2);
          pages = "MEDIUM SINGLE";
          if (sound) playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 330 && e.getY() < 375) {
          if (sound) playmusic(2);
          pages = "HARD SINGLE";
          if (sound) playSE(5);
        }
        break;
      case "DOUBLE":
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 403 && e.getY() < 441) {
          pages = "play";
          if (sound) playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 204 && e.getY() < 243) {
          if (sound) playmusic(2);
          pages = "EASY DOUBLE";
          if (sound) playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 272 && e.getY() < 311) {
          if (sound) playmusic(2);
          pages = "MEDIUM DOUBLE";
          if (sound) playSE(5);
        }
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 330 && e.getY() < 375) {
          if (sound) playmusic(2);
          pages = "HARD DOUBLE";
          if (sound) playSE(5);
        }
        break;
      case "CONTACT_US":
        if (e.getX() > 260 && e.getX() < 436 && e.getY() > 446 && e.getY() < 484) {
          pages = "OPTIONS";
          if (sound) playSE(5);
        }
        break;
    }
  }
  //score
  public void drawscore2() {
    ren.beginRendering(700, 700);
    ren.setColor(Color.RED);
    ren.draw("" + score2 , 285 , 600 );
    ren.setColor(Color.WHITE);
    ren.endRendering();
  }
  public void drawscore() {
    ren.beginRendering(700, 700);
    ren.setColor(Color.BLUE);
    ren.draw("" + score , 285 , 638 );
    ren.setColor(Color.WHITE);
    ren.endRendering();
  }

  public void playmusic(int i){

    sou.StFile(i);
    sou.play();
    sou.loop();
  }
  public void stopmusic(){
    sou.stop();
  }
  public void playSE(int i){
    so.StFile(i);
    so.play();
  }
  public void stoppmusic(){
    so.stop();
  }
  public void TO_Draw_Time() {
    ren.beginRendering(700, 700);
    ren.setColor(Color.BLUE);
    ren.draw ("" + Time, 431, 635);
    ren.setColor(Color.WHITE);
    ren.endRendering();
  }
  public void drawlifes() {
    ren.beginRendering(700, 700);
    ren.setColor(Color.BLUE);
    ren.draw("" + hearts1, 360, 635);
    ren.setColor(Color.WHITE);
    ren.endRendering();
  }

  public void drawlifes2() {
    ren.beginRendering(700, 700);
    ren.setColor(Color.RED);
    ren.draw("" + hearts2, 360, 600);
    ren.setColor(Color.WHITE);
    ren.endRendering();
  }

  public void TO_Draw_Credits(GL gl) {

    DrawBackground(4, gl);
   Drawnames(gl, 320, Salah +285, textureNames.length-1, 2.5f);
    Drawnames(gl, 320, Bilal +285, 26, 2.5f);
    Drawnames(gl, 320, Eid +285, 27, 2.5f);
    Drawnames(gl, 320, Abdo +285, 28, 2.5f);
    Drawnames(gl, 320, Gedo +285, 29, 2.5f);

    Gedo += 5;
    Abdo += 5;
    Eid += 5;
    Bilal += 5;
    Salah += 5;

    if (Salah == 200) {
      Salah = -5;
    }
   if (Bilal == 200) {
      Bilal = -5;
   }
  if (Eid == 200) {
     Eid = -5;
   }
    if (Abdo == 200) {
     Abdo = -5;
   }
    if (Gedo == 200) {
      Gedo = -5;
    }
  }
// names
  public void Drawnames(GL gl, int x, int y, int index, float scale) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

    gl.glPushMatrix();

    gl.glTranslated(x / (Max_Screen_X / 2.0) - 0.9, y / (Max_Screen_Y / 2.0) - 0.9, 0);

    gl.glScaled(0.3 * scale, 0.3 * scale, 1);

    gl.glBegin(GL.GL_QUADS);
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

  @Override
  public void mousePressed(MouseEvent e) { }
  @Override
  public void mouseReleased(MouseEvent e) { }
  @Override
  public void mouseEntered(MouseEvent e) { }
  @Override
  public void mouseExited(MouseEvent e) { }
  @Override
  public void mouseDragged(MouseEvent e) { }
  @Override
  public void mouseMoved(MouseEvent e) { }
  @Override
  public void keyReleased(KeyEvent e) { }
  @Override
  public void reshape(GLAutoDrawable glAutoDrawable, int i, int j, int k, int l) {}
  @Override
  public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean c) {}
  @Override
  public void keyTyped(KeyEvent e) {}
}