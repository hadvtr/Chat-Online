import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.*;
import java.text.*;

public class Client implements ActionListener{
	
	JTextField text; //obter o texto que o usuário digitou
    static JPanel a1; //funciona como um conteiner que organizar a interface gráfica
    static JFrame f = new JFrame(); //definindo a janela principal
    static DataOutputStream dout;
    static Box vertical = Box.createVerticalBox();
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client() {
		
		f.setLayout(null);
		
		//Adicionando primeiro painel (cabecalho da aplicação)
		JPanel pl = new JPanel();
		pl.setBackground(new Color(200, 162, 200));
		pl.setBounds(0, 0, 450, 70);
		pl.setLayout(null);
		f.add(pl);
		
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
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		
		JLabel profile = new JLabel(i6);
		profile.setBounds(40, 10, 50, 50);
		pl.add(profile);
		
		//Adicionando nome do usuario ao painel
		JLabel name = new JLabel("Hadassa");
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
		a1 = new JPanel();
		a1.setBounds(5, 75, 440, 520);
		f.add(a1);
		
		//Adicionando barra de texto
		text = new JTextField();
		text.setBounds(5, 600, 310, 35);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(text);
		
		//Adicionando botao de enviar
		JButton send = new JButton("enviar");
		send.setBounds(320, 600, 123, 35);
		send.setBackground(new Color(200, 162, 200));
		send.setForeground(Color.WHITE);
		send.addActionListener(this);
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f.add(send);
		
		
		//Configurações de frame
		f.setSize(450, 640);
		f.setUndecorated(true);
		f.setVisible(true);
		f.setLocation(200, 50);
		f.getContentPane().setBackground(Color.WHITE);
		
	}
	
	

	
	/*Recebe a ação do usuário, atualiza a interface e envia o texto digitado.*/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			
			String out = text.getText();
			JPanel p2 = formatLabel(out); //criando um painel com base no texto recebido
			
			//layout do painel
			a1.setLayout(new BorderLayout());
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2, BorderLayout.LINE_END);
	        vertical.add(right);
	        vertical.add(Box.createVerticalStrut(15));	
	        
	        //adiciona o painel de texto ao painel body
	        a1.add(vertical, BorderLayout.PAGE_START);

	        //texto que é armazenado em out é enviado para o seu destino através do dout
	        dout.writeUTF(out);

	        //limpando o campo do texto
	        text.setText("");

	        //atualizando a interface gráfica
	        f.repaint();
	        f.invalidate();
	        f.validate();   	
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	//Formata o painel de texto (estilização, bordas, tamanhos, cores, etc...)
	public static JPanel formatLabel(String out) {
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 16));
		output.setBackground(new Color(230, 200, 250));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 15, 15, 50));
		
		panel.add(output);
		
		//Adiciona o momento do envio da mensagem
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		return panel;
	}

	/**A main está sendo utilizada para instanciar o Servidor, que é uma aplicação/servico
	 * que deve está disponivel a todo momento para realizar a comunicação entre os seus clientes
	 * */
	public static void main(String [] args) {
		
		new Client();
		
		try {
			Socket s = new Socket ("127.0.0.1",6001);
			//lendo dados enviados pelo servidor
			DataInputStream din = new DataInputStream(s.getInputStream());
			//enviar dados ao servidor 
			dout = new DataOutputStream(s.getOutputStream());
			//cliente recebendo mensagens do servidor 
			while(true) {
				a1.setLayout(new BorderLayout());
				//ler a mensagem em UTF
				String msg = din.readUTF();
				//transforma a mensagem em JPanel
				JPanel panel = formatLabel(msg);
				//layout 
				JPanel left = new JPanel(new BorderLayout());
				left.add(panel, BorderLayout.LINE_START);
				vertical.add(left);
				
				vertical.add(Box.createVerticalStrut(15));
				a1.add(vertical, BorderLayout.PAGE_START);
				
				f.validate();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
