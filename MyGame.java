package game;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;





@SuppressWarnings("serial")
public class MyGame extends JFrame implements ActionListener
{
		JLabel heading,clockLabel;
		Font font=new Font("",Font.BOLD,40);
		
		JPanel mainPanel;
		JButton []btns=new JButton[9];
		
		int wps[][]= {  {0,1,2},{3,4,5},{6,7,8},  //HORIZONTAL
						{0,3,6},{1,4,7},{2,5,8},  //VERTICAL
						{0,4,8},{2,4,6}           //DIAGONAL
		};
		int winner=2;
		
		int gamechances[]={2,2,2,2,2,2,2,2,2};  //2 neither player 1 play nor player 0 play
		int activePlayer=0;
		
		boolean gameOver=false;

		MyGame()
		{
			//System.out.println("Creating a game");
			
			setTitle("Tic Tac Toe Game..");
			setSize(750,750);
			
			ImageIcon icon=new ImageIcon("src/img/O_X1.png");
			
			setIconImage(icon.getImage());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			CreateGui();
			setVisible(true);
			
			
		}
		
		public void CreateGui() 
		{
			this.getContentPane().setBackground(Color.decode("#2196f3"));
			this.setLayout(new BorderLayout());
			
			heading=new JLabel("Tic Tac Toe");
			heading.setFont(font);
			heading.setHorizontalAlignment(SwingConstants.CENTER);
			
		//	heading.setIcon(new ImageIcon("src/img/O_X1.png"));
			
			heading.setHorizontalTextPosition(SwingConstants.CENTER);
			heading.setVerticalTextPosition(SwingConstants.BOTTOM);
			
			heading.setForeground(Color.white);
			
			this.add(heading,BorderLayout.NORTH);
			//create object of clock
			
			clockLabel=new JLabel("Clock");
			clockLabel.setFont(font);
			
			clockLabel.setForeground(Color.white);
			clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(clockLabel,BorderLayout.SOUTH);
			
			Thread t=new Thread()
		    {
				public void run() 
				{
					
					try {
						while(true)
						{
							@SuppressWarnings("deprecation")
							String datetime=new Date().toLocaleString();
							
							clockLabel.setText(datetime);
							
							Thread.sleep(1000);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}
		    };
			t.start();
			
			mainPanel=new JPanel();
			mainPanel.setLayout(new GridLayout(3,3));
			for(int i=1;i<=9;i++)
			{
				//JButton btn=new JButton(i+"");
				JButton btn=new JButton();
				
				btn.setBackground(Color.green);//Color.decode("#90caf9");
				btn.setFont(font);
				mainPanel.add(btn);
				btns[i-1]=btn;
				btn.addActionListener(this);
				
				btn.setName(String.valueOf(i-1));
			}
			this.add(mainPanel,BorderLayout.CENTER);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			
			JButton currentButton =(JButton)e.getSource();
			
			String nameStr=currentButton.getName();
			
			//System.out.println(nameStr);
			
			int name=Integer.parseInt(nameStr.trim());
			
			if(gameOver)
			{
				JOptionPane.showMessageDialog(this,"Game is Already Over");
				return;
			}
			
			
			if(gamechances[name]==2)
			{
				if(activePlayer==0)
				{
					currentButton.setIcon(new ImageIcon("src/img/o1.png"));   //active player 0====="""O"""
					gamechances[name]=activePlayer;
					
					activePlayer=1;
					
				}
				else
				{
					currentButton.setIcon(new ImageIcon("src/img/x1.png"));
					gamechances[name]=activePlayer;
					
					activePlayer=0;
				}
				
				//FIND THE WINNER
				
				for(int []temp:wps)
				{
					if((gamechances[temp[0]]==gamechances[temp[1]])&&(gamechances[temp[1]]==gamechances[temp[2]])&&(gamechances[temp[2]]!=2))
					{
						winner=gamechances[temp[0]];
						//IF WE HAVE GOT WINNER SO GAME IS OVER
						gameOver=true;
						
						
						JOptionPane.showMessageDialog(null,"Player "+winner+" Has Won the Game");
						
						int i=JOptionPane.showConfirmDialog(this,"Do you want to play more");
						if(i==0)
						{
							this.setVisible(false);
							new MyGame();
						}
						else if(i==1)
						{
							System.exit(1);
						}
						
						System.out.println(i);
						break;
					}
				}
				//DRAW LOGIC ---------------------
				int c=0;
				for(int x:gamechances)
				{
					if(x==2)
					{
						c++;
						break;
					}
					
				}
				if(c==0 && gameOver==false)
				{
					// game is Draw
					JOptionPane.showMessageDialog(null,"It is Draw");
					int i=JOptionPane.showConfirmDialog(this, "Do yo want to play more?");
					
					if(i==0)
					{
						this.setVisible(false);
						new MyGame();
					}
					else if(i==1)
					{
						System.exit(1);
					}
					else
					{
						
					}
					gameOver=true;
				}
				else
				{
					
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Position is already Occupied");
			}
			
			
			
		}


	

}
