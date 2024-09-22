import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Server  implements ActionListener{
	
	JTextField text; //obter o texto que o usuário digitou
    static JPanel a1; //funciona como um conteiner que organizar a interface gráfica
    static JFrame f = new JFrame(); //definindo a janela principal
    static DataOutputStream dout;
    static Box vertical = Box.createVerticalBox();
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Server() {
		
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
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
		Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		
		JLabel profile = new JLabel(i6);
		profile.setBounds(40, 10, 50, 50);
		pl.add(profile);
		
		
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
	        
	        a1.add(vertical, BorderLayout.PAGE_START);

	        //texto que é armazenado em out é enviado para o seu destino através do dout
	        dout.writeUTF(out);

	        //limpando o campo do texto
	        text.setText("");

	        //atualizando a interface gráfica
	        f.repaint();
	        f.invalidate();
	        f.validate();   	
		} catch (Exception err) {
			err.printStackTrace();
		}
		
	}
	
	/*Recebe a ação do usuário, atualiza a interface e envia o texto digitado.*/
	
	
	public static JPanel formatLabel(String out) {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		//configurando o layout para o boxlayout na vertical
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//criação de um JLabel para exibir o texto
		JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
		//configurações do design
		output.setFont(new Font("Tahoma", Font.PLAIN,16));
		output.setBackground(new Color(230, 200, 250));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15,15,15,50));
		
		//adicionando o JLabel ao JPanel
		panel.add(output);
		
		//criação de um calendario para obter a hora atual
		Calendar cal = Calendar.getInstance();
		//criação de um SimpleDateFormat para formatar a hora
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		//exibindo a hora atual
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		//adição do JLabel com a hora do JPanel
		panel.add(time);
		
		return panel;
//		return null;
	}

	/**A main está sendo utilizada para instanciar o Servidor, que é uma aplicação/servico
	 * que deve está disponivel a todo momento para realizar a comunicação entre os seus clientes
	 * */
	public static void main(String [] args) {
		
		new Server();
		
		try {
			//responsavel por aceitar conexões de clientes
			ServerSocket skt = new ServerSocket(6001);
			//cada vez que um cliente se conecta, um socket é criado para a comunicação
			while(true) {
				Socket s = skt.accept();
				DataInputStream din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				
				while(true) {
					//servidor ler a mensagem em UTF e transforma em JPanel
					String msg = din.readUTF();
					JPanel panel = formatLabel(msg);
					//cria um novo panel/painel, coloca do lado esquerdo, adiciona o painel ao container
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);
					//atualiza a interface
					f.validate();
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	


}


