import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class infobot extends JFrame implements ActionListener {
	private JTextArea textArea = new JTextArea();
	private JTextField textField = new JTextField();                                  
	private JButton button = new JButton();
	private JLabel label = new JLabel();
	public infobot(){                                                                          
		JFrame frame = new JFrame();                                     
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);                      
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(1500,800);
		frame.getContentPane().setBackground(new Color(0, 0, 128));
		frame.setTitle("InfoBot");
		frame.add(textField);
		frame.add(textArea);
		frame.add(button);
		textArea.setSize(1500,310);
		textArea.setLocation(1, 1);
		textArea.setBackground(Color.white);
		textField.setSize(1500,60);
		textField.setLocation(1,400);
		label.setText("SEND");
		button.add(label);
		button.setSize(80,40);
		button.setLocation(710,360);
		button.addActionListener(this); 
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {                           
			String text = textField.getText().toLowerCase();
			textArea.append("You: " + text + "\n");
			textField.setText("");
			if(greeting(text)) {                         
				response("Hello. What can I help you with today?");
			}
			else if(text.contains("how are you")) {
				response("I am feeling great! Thanks for asking.");
			}
			else if(text.contains("What is your name")) {
				response("I am infobot. Who are you?");
			}
			else if(text.contains("thank you") || text.contains("thanks")) {
				response("No problem.");
			}
			else if(text.contains("bye")) {
				response("Goodbye. Have a nice day!");
			}
			else if (text.contains("who is") || text.contains("what is")) {

				String content = "";
				if (text.contains("who is")) {
					content = text.substring(7);
				}
				else {
					content = text.substring(8);
				}
				Document doc;
				try {
					doc = Jsoup.connect("http://en.wikipedia.org/wiki/" + content.replace(" ", "_")).get();
					Elements paragraphs = doc.select("p");
					String data = paragraphs.text();
					String caption = data.substring(0, 205) + "\n ... for more info visit http://en.wikipedia.org/wiki/" + content.replace(" ", "_");
					response(caption);
				} catch (IOException e1) {
					response("I'm not sure I understand. Is there anything else I can help you with?");
				}
			}
			else {
				response("I'm not sure I understand. Is there anything else I can help you with?");
			}
		}
	}
	public void response(String str) {                          
		textArea.append("InfoBot: " + str + "\n");         
	}
	public boolean greeting(String s) {
		String[] greetings = new String[] {"hi", "hello", "hey", "howdy", "yo", "what's up"};
		boolean found = false;
		for (String greet : greetings) {
			if (s.contains(greet)) {
				found = true;
			}
		}
		return found;
	}

}

