package eatfish;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.GetImg;
import utils.MusicUtil;



public class World extends JPanel{
	public static int state=0;
	public static int RUNNING=1;
	public static int DEAD=2;
	public static int START=0;
	public static int STOP=3;
	
	static BufferedImage[] imgs=new BufferedImage[3];
	static {
		imgs[0]=GetImg.read("start.png");
		imgs[1]=GetImg.read("dead.png");
		imgs[2]=GetImg.read("stop.png");
		
		
	}
	//Ѫ��
	Heart[] h=new Heart[10];
	// ����һ�����Ķ���
	Mouse mouse = new Mouse();
	// ����һ�����̵Ķ���
	Keyboard key = new Keyboard();
	// ��ȡͼƬ
	Pic p=new Pic();
	//�ٿؽ�ɫ
	static Hero hero=new Hero();
	//�ж�����
	AllFather[] enemy= {};
	//�����
	Random rand=new Random();
	public void stepaction() {
		hero.herostep();
		for(int i=0;i<enemy.length;i++) {
			if(enemy[i].award==0) {
				enemy[i].step();
			}else {
				enemy[i].step2();
			}
			
		}
	}
	int enemyIndex=0;
	public void isLife() {
		if(hero.life==0) {
			state=2;
		}
	}
	public void addenemy(){
		if (enemyIndex++ % 50 == 0) {
			enemy = Arrays.copyOf(enemy, enemy.length + 1);
			//int num = rand.nextInt(20);
			enemy[enemy.length-1]=new Smallfish();
			//System.out.println("������"+1+"��enemy����");
		}
		if (enemyIndex++ % 200 == 0) {
			enemy = Arrays.copyOf(enemy, enemy.length + 1);
			//int num = rand.nextInt(20);
			enemy[enemy.length-1]=new Midfish();
			//System.out.println("������"+1+"��enemy����");
			
		}
		if (enemyIndex++ % 400 == 0) {
			enemy = Arrays.copyOf(enemy, enemy.length + 1);
			//int num = rand.nextInt(20);
			enemy[enemy.length-1]=new Bigfish();
			//System.out.println("������"+1+"��enemy����");
		}

	}
	
	public void eat() {
		for(int i=0;i<enemy.length;i++) {
			if(hero.hit(enemy[i])) {
				if(hero.level>=enemy[i].level) {
					if(enemy[i].state!=AllFather.DEAD) {
						enemy[i].state=AllFather.DEAD;
						
						hero.jy+=enemy[i].level;
						
					}
					
				}
				else {
					if(hero.state!=Hero.God) {
						hero.state=Hero.God;
						hero.life-=1;	
					}
					
				}
				
				if(hero.jy==20) {
					hero.level=2;
					hero.width=160;
					hero.height=100;
					hero.life=5;
					//System.out.println("������");
				}
				if(hero.jy==50 || hero.jy==51) {
					hero.level=3;
					hero.width=300;
					hero.height=150;
					hero.life=10;
					//System.out.println("������");
				}
			}
		}
	}
	int musicIndex=0;
	public void action() {
		for(int i=0;i<h.length;i++) {
			h[i]=new Heart(10+i*50, 10, 50, 50);
		}
		MouseAdapter m=new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case 2:
					System.out.println("������");
					hero=new Hero();
					enemy=new AllFather[0];
					state=RUNNING;
					break;
	
				case 3:
					state=RUNNING;
					break;
				}
			}
		};
		// ���һ����ʱ������
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {// 10������һ��
				if(state==1) {
					eat();
					addenemy();
					stepaction();
					repaint();// �ػ� ����paint()
					isLife();	
				}
				if(musicIndex%18800==0||musicIndex==0){
					MusicUtil.play();
				}
				musicIndex++;
			}
		}, 10, 10);
		this.addMouseListener(m);
		this.addMouseListener(mouse.l);
		this.addMouseMotionListener(mouse.l);
		this.addKeyListener(key.s);
		this.setFocusable(true);
		this.requestFocus();
	}
	int GodIndex=0;
	public void paint(Graphics g) {
		
		p.paintObject(g);
		
		for(int i=0;i<hero.life;i++) {
			
			h[i].paintObject(g);
		}
		
		if(hero.state!=Hero.God) {
			hero.paintObject(g);
		}else {
			GodIndex++;

			if(GodIndex%60<=50) {
				hero.paintObject(g);
			}
			if(GodIndex%300==0){
				hero.state=AllFather.LIFE;
				hero.paintObject(g);
			}
		}
		
		for(int i=0;i<enemy.length;i++) {
			enemy[i].paintObject(g);
		}
		g.setColor(new Color(255,255,255));
		g.drawString("life:"+hero.life, 20, 20);
		if(World.state==World.START) {
			g.drawImage(imgs[0], 0, 0, null);
		}
		if(World.state==World.DEAD) {
			g.drawImage(imgs[1], 0, 0, null);
		}
		if(World.state==World.STOP) {
			g.drawImage(imgs[2], 0, 0, null);
		}
	}
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		// ����һ����Ϸ����
		World world = new World();
		frame.add(world);
		// ���ô���Ϊ�ɼ���
		frame.setVisible(true);
		// ���ô��ڵĴ�С
		frame.setSize(915, 633);
		frame.setTitle("�����С��");
		frame.setLocationRelativeTo(null);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		world.action();
	}
	
}
