package eatfish;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;



public abstract class AllFather {
	public int level;
	int award;//0Ϊ���ң�1Ϊ����
	//����ȼ�
	
	//��������״̬����
	public static final int LIFE=0;//����
	public static final int DEAD=1;//����
	public static final int REMOVE=2;//��ʧ
	//���嵱ǰ�����״̬����
	protected int state=LIFE;//��ʼĬ�ϻ���
		
	int x;
	int y;
	int width;
	int height;
	Random rand = new Random();
	//����
	
	public AllFather(int width, int height) {

		this.award=rand.nextInt(2);
		if(award==0) {
			this.x = 900+this.width;
			this.y = rand.nextInt((600 - this.height));
			this.width = width;
			this.height = height;
		}else if(award==1){
			this.x = -300-this.width;
			this.y = rand.nextInt((600 - this.height));
			this.width = width;
			this.height = height;
		}
	}
	/** ����� Ӣ�ۻ� �ӵ� */
	public AllFather(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public abstract BufferedImage getImages();
	// �ƶ��ķ���
	public abstract void step();
	public abstract void step2();

		/** ��ͼƬ�ķ��� */
		public void paintObject(Graphics g) {
			if(getImages()!=null) {
				g.drawImage(getImages(), x, y, null);
			}
			
		}

		/*
		 * enemy[0].hit(hero)
		 */
		/** ��ײ */
		public boolean hit(AllFather f) {
			// this.����Ϊ�ӵ�,f����Ϊһ�ܵл�
			int x1 = f.x - this.width;// ����
			int x2 = f.x + f.width;// �Ҳ��

			int y1 = f.y - this.height;// �Ϸ���
			int y2 = f.y + f.height;// �·���

			return this.x > x1 && this.x < x2 && this.y > y1 && this.y < y2;
		}
}
