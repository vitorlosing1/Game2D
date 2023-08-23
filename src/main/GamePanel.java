package main;

import entity.Player;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // CONFIGURAÇÕES DA TELA
    final int originalTileSize = 16;  // 16x16 (escala dos "blocos")
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 escala
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;  // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    //FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);

    // seta posição padrão do jogador
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    //roda o metodo run
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // METODO SLEEP PARA RODAR O GAME
//    public void run() {
//
//        double drawInterval = 1000000000/FPS; // 0.01666 segundos
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//
//        while (gameThread != null) {
//
//            // update: atualiza informações como as coordenadas do personagem
//            update();
//            // draw: movimenta a tela de acordo com a atualização de informações
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000; // reduz de nano segundos para
//
//                if(remainingTime< 0) {
//                    remainingTime = 0;
//                }
//
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    // METODO DELTA ( MAIS PRECISO )
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                //System.out.println("FPS:" + drawCount); // mostra o FPS
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // fica registrado atualizações feitas ao fazer uma ação
        public void update() {

        player.update();

        }
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D)g;

            player.draw(g2);

            g2.dispose();

        }

}

