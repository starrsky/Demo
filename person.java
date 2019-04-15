package jisuanqi;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack; //Stack是一个后进先出的堆栈

public class person extends JFrame implements ActionListener {
	
	public static void main(String[] args) {                    //主函数
		new person();
	}
	
	String [] An= {"7","8","9","+","4","5","6","-",
			"1","2","3","*",".","0","=","/","%","sqrt","^²","^³"};  //设置图形面板中按钮字符
	JButton a[]=new JButton[20];                                //设置按钮集合
	JTextField jt;                                              //定义一个文本框
	String input="";                                            //输入的字符串
	public person(){
		JPanel jp1=new JPanel();                                //定义了三个面板，jp1，jp2，jp3
		JPanel jp2=new JPanel();
		JPanel jp3=new JPanel();
		JButton b1=new JButton("清零");                          //清零按钮
		JButton b2=new JButton("退格");                          //退格按钮
		b1.setFont(new Font("草书", Font.PLAIN, 16));            
		b2.setFont(new Font("草书", Font.PLAIN, 16));
		GridLayout g=new GridLayout(5,4,5,5);                    
		jp1.setLayout(new BorderLayout());                       //面板1边布局
		jp2.setLayout(g);                                        //面板2网格布局   //5*4网格,间距5
		jp3.setLayout(new GridLayout(1,2,3,3));                  //面板3网格布局   //1*2网格，间距3         
		
		jt=new JTextField();
		jt.setPreferredSize(new Dimension(250,40));               //文本大小
		jt.setHorizontalAlignment(SwingConstants.LEFT);           //左对齐
		this.add(jp1,BorderLayout.NORTH);                         //北
		jp1.add(jt,BorderLayout.WEST);                            //西
		jp1.add(jp3,BorderLayout.EAST); 						   //东
		
		
		jp3.add(b1);
		jp3.add(b2);
		b1.setBackground(Color.yellow);
		b2.setBackground(Color.yellow);
		b1.addActionListener(this);                               //定义处理事件的方法
		b2.addActionListener(this);
		
	
		for(int i=0;i<20;i++)                                      //添加按钮
		{
			a[i]=new JButton(An[i]);
			a[i].setFont(new Font("隶书",Font.PLAIN,16));          //设置按钮字符字体颜色
			a[i].setForeground(Color.blue);
			if(i==3||i==7||i==11||i==14||i==15){
				a[i].setFont(new Font("隶书",Font.PLAIN,20));
				a[i].setForeground(Color.red);
			}
			jp2.add(a[i]);
			a[i].addActionListener(this);                        //添加按钮点击事件
		}

		
		this.add(jp2,BorderLayout.CENTER);
		this.setLocation(300,300);                               //窗体显示位置  
		this.setResizable(true);                                //不可调整窗体大小
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(400, 350);                                       //设置窗体大小
		setTitle("计算器");                                       //设置窗体名字
		this.pack();                                             // 使计算器中各组件大小合适
		
	}
	public void actionPerformed(ActionEvent e) {
		int t=0;
		String s=e.getActionCommand();                           //获取点击按钮上的字符
		if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")||s.equals("%")||s.equals("sqrt")||s.equals("^²")||s.equals("^³")) {
			input+=" "+s+" ";                                    //如果碰到运算符，就在运算符前后分别加一个空格
		}else if(s.equals("清零")) {
			input="";
		}else if(s.equals("退格")) {
			if((input.charAt(input.length()-1))==' ')             //检测字符串的最后一个字符是否为空格
			{
				input=input.substring(0,input.length()-3);        //如果是则删除末尾3个字符
			}
			else                                                  //否则删除1个字符
			{
				input=input.substring(0,input.length()-1);
			}
		}
		else if(s.equals("=")) {
			input=compute(input);
			jt.setText(input);                                    //输出计算结果
			t=1;
		}
		else
			input += s;
		if(t==0) {
			jt.setText(input);
		}
	}
	private String compute(String str) {
		String array[];
		array=str.split(" ");                                     //当遇到空格的时候拆分数组
		Stack<Double> string1=new Stack<Double>();
		Double a=Double.parseDouble(array[0]);                    //把字符变成double型
		string1.push(a);
		for(int i=1;i<array.length;i++) {
			if(i%2==1) {
				if(array[i].compareTo("+")==0)                    //比较运算符，再相应计算
				{
					double b= Double.parseDouble(array[i+1]);
					string1.push(b);
				}
				if(array[i].compareTo("-")==0)
				{
					double b= Double.parseDouble(array[i+1]);
					string1.push(-b);
				}
				if(array[i].compareTo("*")==0)
				{
					double b= Double.parseDouble(array[i+1]);
					double c=string1.pop();
					c*=b;
					string1.push(c);
				}
				if(array[i].compareTo("/")==0)
				{
					double b= Double.parseDouble(array[i+1]);
					double c=string1.peek();
					string1.pop();
					c/=b;
					string1.push(c);
				}
				if(array[i].compareTo("%")==0)
				{
					double b= Double.parseDouble(array[i+1]);
					double c=string1.peek();
					string1.pop();
					c=c%b;
					string1.push(c);
				}
				if(array[i].compareTo("sqrt")==0)
				{
					double c=string1.peek();
					c=Math.sqrt(c)-c;
					string1.push(c);
				}
				if(array[i].compareTo("^²")==0)
				{
					double c=string1.pop();
					c=c*c;
					string1.push(c);
				}
				if(array[i].compareTo("^³")==0)
				{
					double c=string1.pop();
					c=c*c*c;
					string1.push(c);
				}
			}
		}
		double sum=0;
		while(!string1.isEmpty()) {                                       //排除除数是0的情况  返回无穷大          
			sum+=string1.pop();
		}
		String result=String.valueOf(sum);
		return result;
	}
	
}

