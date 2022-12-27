



import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Gedo
 */
public class Soundd {
Clip clip;
URL soundURL[]=new URL[30];

public Soundd(){
    soundURL[0]=getClass().getResource("/sound/Backk.wav");
    soundURL[1]=getClass().getResource("/sound/Bullet.wav");
    soundURL[2]=getClass().getResource("/sound/Backk2.wav");
    soundURL[3]=getClass().getResource("/sound/Backk3.wav");
    soundURL[4]=getClass().getResource("/sound/Backk4.wav");
    soundURL[5]=getClass().getResource("/sound/cuttree.wav");
    soundURL[6]=getClass().getResource("/sound/gameover.wav");
    soundURL[7]=getClass().getResource("/sound/xxx.wav");
    soundURL[8]=getClass().getResource("/sound/win.wav");
}
public void StFile(int i){
  
              try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(ais);

            
        } catch (Exception e) {
            System.out.println(e);

        }

  }
  
  public void play (){
  
      clip.start();
  }
  
  public void loop (){
  
      clip.loop(clip.LOOP_CONTINUOUSLY);
  }
  
  public void stop (){
  
      clip.stop();
  }

}
