
public class Stopwatch {

    private long startTime;
    private long elapsedTime;
    private boolean running;

    public void start() {
        if (!running) {
            startTime = System.nanoTime();
            running = true;
        }
    }

    public void pause() {
        if (running) {
            elapsedTime += System.nanoTime() - startTime;
            running = false;
        }
    }

    public void reset() {
        startTime = 0;
        elapsedTime = 0;
        running = false;
    }

    public long getElapsedTime() {
        if (running) {
            return elapsedTime + (System.nanoTime() - startTime);
        }

        return elapsedTime;
    }

    public boolean isRunning() {
        return running;
    }
}
