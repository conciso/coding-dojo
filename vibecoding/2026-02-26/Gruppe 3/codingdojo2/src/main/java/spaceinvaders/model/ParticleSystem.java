package spaceinvaders.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ParticleSystem {
    private final List<Particle> particles;
    private final Random random;

    public ParticleSystem() {
        this.particles = new ArrayList<>();
        this.random = new Random();
    }

    public ParticleSystem(long seed) {
        this();
        this.random.setSeed(seed);
    }

    public void spawnExplosion(double x, double y, int count, int r, int g, int b) {
        for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double speed = 50 + random.nextDouble() * 150;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            double life = 0.3 + random.nextDouble() * 0.7;
            particles.add(new Particle(x, y, vx, vy, life, r, g, b));
        }
    }

    public void update(double deltaTime) {
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update(deltaTime);
            if (p.isDead()) {
                it.remove();
            }
        }
    }

    public List<Particle> getParticles() { return particles; }

    public void clear() { particles.clear(); }
}
