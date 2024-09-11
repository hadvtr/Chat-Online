import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;

import javax.swing.*;

public class Server extends JFrame implements ActionListener{
	JTextField text; //obter o texto que o usuário digitou
    JPanel a1; //funciona como um conteiner que organizar a interface gráfica
    static JFrame f = new JFrame(); //definindo a janela principal
    static DataOutputStream dout;
    static Box vertical = Box.createVerticalBox();
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Server() {
		
		setLayout(null);
		
		//Adicionando primeiro painel (cabecalho da aplicação)
		JPanel pl = new JPanel();
		pl.setBackground(new Color(7, 94, 84));
		pl.setBounds(0, 0, 450, 70);
		pl.setLayout(null);
		add(pl);
		
		//Primeira imagem
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		
		JLabel back = new JLabel(i3);
		back.setBounds(5, 20, 25, 25);
		pl.add(back);
		
		//Evento de click para fechar a aplicação ao clicar no icone de voltar (seta)
		back.addMouseListener(new MouseAdapter () {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		}) ;
		
		
		//Segunda Imagem
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		
		JLabel profile = new JLabel(i6);
		profile.setBounds(40, 10, 50, 50);
		pl.add(profile);
		
		
		//Terceira Imagem
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
				
		JLabel video = new JLabel(i9);
		video.setBounds(300, 20, 30, 30);
		pl.add(video);
				
		//Quarta Imagem
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
				
		JLabel phone = new JLabel(i12);
		phone.setBounds(360, 20, 35, 30);
		pl.add(phone);
		
		
		//Quinta Imagem
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
						
		JLabel morevert = new JLabel(i15);
		morevert.setBounds(420, 20, 10, 25);
		pl.add(morevert);
		
		//Adicionando nome do usuario ao painel
		JLabel name = new JLabel("Dassu");
		name.setBounds(110, 15, 100, 18);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN SERIF", Font.BOLD, 18));
		pl.add(name);
		
		
		//Adicionando status ao painel
		JLabel status = new JLabel("Active Now");
		status.setBounds(110, 35, 100, 18);
		status.setForeground(Color.WHITE);
		status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
		pl.add(status);
		
		//Adicionando segundo painel (corpo da aplicação)
		JPanel al = new JPanel();
		al.setBounds(5, 75, 440, 520);
		add(al);
		
		//Adicionando barra de texto
		JTextField text = new JTextField();
		text.setBounds(5, 600, 310, 35);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		add(text);
		
		//Adicionando botao de enviar
		JButton send = new JButton("enviar");
		send.setBounds(320, 600, 123, 35);
		send.setBackground(new Color(7, 94, 84));
		send.setForeground(Color.WHITE);
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		add(send);
		
		
		//Configurações de frame
		setSize(450, 640);
		setUndecorated(true);
		setVisible(true);
		setLocation(200, 50);
		getContentPane().setBackground(Color.WHITE);
		
	}
	
	/*Recebe a ação do usuário, atualiza a interface e envia o texto digitado.*/
	public void actionPerfomed(ActionEvent action) {
		try {
			String out = text.getText();
			
			JPanel p2 = formatLabel(out); //criando um painel com base no texto recebido
			
			//layout do painel
			a1.setLayout(new BorderLayout());
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2, BorderLayout.LINE_END);
	        vertical.add(right);
	        vertical.add(Box.createVerticalStrut(15));
	        
	        a1.add(vertical, BorderLayout.PAGE_START);

	        //texto que é armazenado em out é enviado para o seu destino através do dout
	        dout.writeUTF(out);

	        //limpando o campo do texto
	        text.setText("");

	        //atualizando a interface gráfica
	        f.repaint();
	        f.invalidate();
	        f.validate();   	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private JPanel formatLabel(String out) {
		// TODO Auto-generated method stub
		return null;
	}

	/**A main está sendo utilizada para instanciar o Servidor, que é uma aplicação/servico
	 * que deve está disponivel a todo momento para realizar a comunicação entre os seus clientes
	 * */
	public static void main(String [] args) {
		
		new Server();
		
	}

}

