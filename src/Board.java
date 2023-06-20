import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Board extends JPanel implements ActionListener {
    int B_HEIGHT = 400;
    int B_WIDTH = 400;
    //the snake size
    int DOTS = 3;
    int MAX_DOTS = 1600; //40*40
    int DOT_SIZE = 10;  //pixel size
    //co ordinate of the snake



    int x[] = new int[MAX_DOTS];
    int y[] = new int[MAX_DOTS];
    int apple_x;
    int apple_y;
    Image body,head,apple;
    Timer time;
    int DELAY = 200;
    boolean left = true;
    boolean right = false;
    boolean up  = false;
    boolean down = false;
    boolean inGame = true;
    //constructer
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        loadImage();

    }
    //initialize the function of game

    public void initGame(){
        DOTS = 3;
        //POSITION OF THE SNAKE
        x[0]=250;
        y[0]=250;
        for(int i  = 1;i < DOTS;i++){
            x[i]=x[0]+DOT_SIZE*i;
            y[i]=y[0];
        }
        //calling apple position after snake created
        locateApple();
        time = new Timer(DELAY,this);
        time.start();
    }
    //load the image from resources
    public void loadImage(){
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }
    //to draw snake and apple position

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        toDrawing(g);
    }
    //position of the images
    public void toDrawing(Graphics g){
       if(inGame){
           g.drawImage(apple,apple_x,apple_y,this);
           //for head and body of the snake
           for(int i = 0;i < DOTS;i++){
               if(i == 0){
                   g.drawImage(head,x[0],y[0],this);
               }else{
                   g.drawImage(body,x[i],y[i],this);
               }
           }
       }else{
          // checkCollision(g);
           gameOver(g);
           time.stop();
       }

    }
    // to locate apple in diff positions
    public void locateApple(){
        apple_x = ((int)(Math.random()*39))*DOT_SIZE;
        apple_y = ((int)(Math.random()*39))*DOT_SIZE;
    }
    //to check collision of body or border
    public void checkCollision(){
        //to body
        for(int i= 1;i< DOTS;i++){
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        //to border
        if(x[0]< 0 || y[0]<0){
            inGame = false;
        }
        if(x[0] >= B_WIDTH || y[0] >=B_HEIGHT){
            inGame = false;
        }
    }
    //to display game over
    public void gameOver(Graphics g){
        String msg = "Game Over";
        int score = (DOTS - 3) * 100;
        String scoremsg = "Score : "+Integer.toString(score);
        Font small= new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics = getFontMetrics(small);

        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2,B_HEIGHT/4);
        g.drawString(scoremsg,(B_WIDTH-fontMetrics.stringWidth(scoremsg))/2,3*(B_HEIGHT/4));

    }
    //@override()
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }
    //to make snake move
    public void move(){
        for(int i = DOTS-1;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0]-=DOT_SIZE;
        }
        if(right){
            x[0]+=DOT_SIZE;
        }
        if(up){
            y[0]-=DOT_SIZE;
        }
        if(down){
            y[0]+=DOT_SIZE;
        }
    }
    // to make snske eat apple
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            DOTS++;
            locateApple();
        }
    }
    //implement cntroll of the snake
    private class TAdapter extends KeyAdapter{
      //  @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down= false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down= false;
            }
            if(key == KeyEvent.VK_UP && !down){
                left = false;
                up = true;
                right= false;
            }
         if(key == KeyEvent.VK_DOWN && !up) {
             left = false;
             right = false;
             down = true;
         }
        }
    }
}
