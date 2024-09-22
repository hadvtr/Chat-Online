package servidor.clientes.tela;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import servidor.clientes.cliente.Cliente;
import servidor.clientes.cliente.Leitor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SalaChat implements ActionListener {

    private JTextField text;
    private JTextArea chatArea;
    private static JPanel a1;
    private static JFrame f = new JFrame();
    private static Box vertical = Box.createVerticalBox();
    private static Cliente cliente;
    
    public SalaChat () {
    	
    }

    public SalaChat(String nome) throws IOException {
    	
        cliente = new Cliente(nome);

        f.setLayout(null);

        // Painel do cabeçalho
        JPanel pl = new JPanel();
        pl.setBackground(new Color(200, 162, 200));
        pl.setBounds(0, 0, 450, 70);
        pl.setLayout(null);
        f.add(pl);

        // Adicionando ícones ao cabeçalho
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        pl.add(back);
        
        //fecha a comunicação do cliente com o servidor e encerra a conexão, apagando o objeto da memoria
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
            	try {
					cliente.fecharComunicacao();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        pl.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        pl.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        pl.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        pl.add(morevert);

        // Adicionando nome do usuário ao painel
        JLabel name = new JLabel(nome);
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF", Font.BOLD, 18));
        pl.add(name);

        // Adicionando status ao painel
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        pl.add(status);

         //Painel para exibir mensagens
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 520);
        
        f.add(a1);
		
        // Área de texto para mensagens
        chatArea = new JTextArea();
        chatArea.setEditable(false); // Apenas leitura
        chatArea.setLineWrap(true);  // Ativa a quebra de linha
        chatArea.setWrapStyleWord(true);  // Quebra de linha respeitando as palavras
        chatArea.setFont(new Font("SAN_SERIF", Font.PLAIN, 16)); // Define um tamanho de fonte maior
        chatArea.setMargin(new Insets(10, 10, 10, 10)); // Adiciona margens ao redor do texto

        // Instancia o leitor para receber mensagens do servidor
        new Thread(new Leitor(cliente.getSocket(), chatArea)).start();

        JScrollPane scrollPane = new JScrollPane(chatArea);
        a1.setLayout(new BorderLayout());
        a1.add(scrollPane, BorderLayout.CENTER);  // Adiciona o scrollPane centralizado

        JPanel painelMensagem = formatLabel(chatArea);
        vertical.add(painelMensagem);
        vertical.add(Box.createVerticalStrut(15));
        //a1.add(vertical, BorderLayout.PAGE_START);

        // Campo de texto para envio de mensagens
        text = new JTextField();
        text.setBounds(5, 600, 310, 35);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);
        
 
        // Botão de enviar
        JButton send = new JButton("Enviar");
        send.setBounds(320, 600, 123, 35);
        send.setBackground(new Color(200, 162, 200));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);
        

        // Configurações do frame
        f.setSize(450, 640);
        f.setUndecorated(true);
        f.setVisible(true);
        f.setLocation(200, 50);
        f.getContentPane().setBackground(Color.WHITE);

       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String mensagem = text.getText();
            if (!mensagem.isEmpty()) {
                // Envia a mensagem ao servidor
                cliente.enviarMensagem(mensagem);

                // Limpa o campo de texto
                text.setText("");

                // Atualiza a interface gráfica
                f.repaint();
                f.invalidate();
                f.validate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static JPanel formatLabel(JTextArea chatArea2) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + chatArea2 + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(230, 200, 250));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        JLabel time = new JLabel(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new SalaChat();
    }
}
