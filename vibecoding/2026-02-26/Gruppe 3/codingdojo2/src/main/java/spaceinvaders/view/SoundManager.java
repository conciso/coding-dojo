package spaceinvaders.view;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;

public class SoundManager {
    private boolean enabled;

    public SoundManager() {
        this.enabled = true;
    }

    public void playShoot() {
        playTone(800, 50);
    }

    public void playExplosion() {
        playTone(200, 100);
    }

    public void playPowerUp() {
        playTone(1200, 80);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() { return enabled; }

    private void playTone(int frequency, int durationMs) {
        if (!enabled) return;
        try {
            float sampleRate = 8000f;
            int samples = (int) (sampleRate * durationMs / 1000);
            byte[] buf = new byte[samples];
            for (int i = 0; i < samples; i++) {
                double angle = 2.0 * Math.PI * i * frequency / sampleRate;
                buf[i] = (byte) (Math.sin(angle) * 80);
            }
            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            Clip clip = AudioSystem.getClip();
            clip.open(af, buf, 0, buf.length);
            clip.start();
        } catch (Exception e) {
            // Audio not available
        }
    }
}
