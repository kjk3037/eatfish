package eatfish;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**�����*/
public class Mouse {
	MouseAdapter l;


	/**��������¼�*/
	public  Mouse(){
		this.l = new MouseAdapter() {
			//�������ʱ״̬�л�
			public void mouseEntered(MouseEvent e) {
				if(World.state==World.STOP) {
					World.state=World.RUNNING;
				}
				
			}
			//����Ƴ�ʱ��״̬�л�
			public void mouseExited(MouseEvent e) {
				World.state=World.STOP;
			}
			
			
			
			/** ��갴���¼� */
			public void mousePressed(MouseEvent e) {
				/*World.hero.tx = e.getX()-(World.hero.width/2);
				World.hero.ty = e.getY()-(World.hero.height/2);
				World.hero.spx = (e.getX()-(World.hero.width/2))/World.hero.x;
				World.hero.spy = (e.getY()-(World.hero.height/2))/World.hero.y;*/
			}

			/** ����ͷ��¼� */
			public void mouseReleased(MouseEvent e) {
				//�����������ǵ��Ǹ�һ��λ��
				//�޸�World���״̬Ϊ������
				//>x1  <x2 
				//>y1  <y2
			}
			/** ������¼� */
			public void mouseClicked(MouseEvent e) {
				if(World.state==World.START) {
					World.state=World.RUNNING;
				}
				if(World.state==World.DEAD) {
					World.hero=new Hero();
					World.state=World.RUNNING;
					
				}
				
			}

			/** ����ƶ��¼� */
			public void mouseMoved(MouseEvent e) {
				/*World.hero.x = e.getX()-(World.hero.width/2);
				World.hero.y = e.getY()-(World.hero.height/2);*/
				
			}	
		};

	}
}
