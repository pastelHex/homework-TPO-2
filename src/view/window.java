package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JTable;

public class window {

	private JFrame frame;
	public JTextField txtCountry;
	public JTextField txtCity;
	public JTextField txtCurrency;
	private JPanel panel_weather;
	private JPanel panel_currency;
	public JPanel panel_wiki;
	public JButton btnShow;
	public JTextArea txtWeatherInfo;
	public JLabel labelPLNCurrency;
	public JLabel labelCurrency;
	public JLabel label_kursPLN;
	public JLabel label_kurs;
	public JLabel label_City;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private Component verticalStrut;
	private Component verticalStrut_1;
	private JSplitPane splitPane;
	private JPanel panelWeather;
	public JTable table;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { window window = new window();
	 * window.frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); }
	 * } }); }
	 */

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 770, 478);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel_location = new JPanel();
		panel_location.setLayout(new BoxLayout(panel_location, BoxLayout.X_AXIS));

		horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_location.add(horizontalStrut_3);

		JLabel label = new JLabel("Country ");
		panel_location.add(label);

		txtCountry = new JTextField();
		panel_location.add(txtCountry);
		txtCountry.setColumns(1);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		panel_weather = new JPanel();
		tabbedPane.addTab("weather", null, panel_weather, null);
		panel_weather.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Weather info");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_weather.add(lblNewLabel, BorderLayout.NORTH);

		txtWeatherInfo = new JTextArea();
		panelWeather = new JPanel();
		JScrollPane scrollPane = new JScrollPane(txtWeatherInfo);
		//panel_weather.add(scrollPane, BorderLayout.CENTER);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollPane,panelWeather);
		splitPane.setResizeWeight(0.5);
		panelWeather.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(new DefaultTableModel(new Object[]{"Key", "Value"}, 0));
		panelWeather.add(table, BorderLayout.CENTER);
		panel_weather.add(splitPane, BorderLayout.CENTER);
		
		
		//panel_weather.add(panelWeather, BorderLayout.WEST);
		
		panel_currency = new JPanel();
		tabbedPane.addTab("currency", null, panel_currency, null);
		panel_currency.setLayout(new BoxLayout(panel_currency, BoxLayout.Y_AXIS));

		verticalStrut = Box.createVerticalStrut(20);
		panel_currency.add(verticalStrut);

		label_kurs = new JLabel("Kurs PLN wobec waluty " + "XXX");
		label_kurs.setHorizontalAlignment(SwingConstants.LEFT);
		label_kurs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_currency.add(label_kurs);

		labelPLNCurrency = new JLabel("0");
		labelPLNCurrency.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_currency.add(labelPLNCurrency);

		verticalStrut_1 = Box.createVerticalStrut(20);
		panel_currency.add(verticalStrut_1);

		label_kursPLN = new JLabel("Kurs waluty kraju wobec podanej waluty");
		label_kursPLN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_currency.add(label_kursPLN);

		labelCurrency = new JLabel("0");
		labelCurrency.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_currency.add(labelCurrency);

		panel_wiki = new JPanel();
		tabbedPane.addTab("wiki", null, panel_wiki, null);
		panel_wiki.setLayout(new BorderLayout(0, 0));

		label_City = new JLabel("New label");
		label_City.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_wiki.add(label_City, BorderLayout.NORTH);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panel_location, BorderLayout.NORTH);

		horizontalStrut = Box.createHorizontalStrut(20);
		panel_location.add(horizontalStrut);

		JLabel label_city = new JLabel("City ");
		panel_location.add(label_city);

		txtCity = new JTextField();
		panel_location.add(txtCity);
		txtCity.setColumns(10);

		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_location.add(horizontalStrut_1);

		JLabel lblNewLabel_1 = new JLabel("Currency ");
		panel_location.add(lblNewLabel_1);

		txtCurrency = new JTextField();
		panel_location.add(txtCurrency);
		txtCurrency.setColumns(10);

		btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_location.add(horizontalStrut_2);
		panel_location.add(btnShow);
		frame.getContentPane().add(tabbedPane);
	}
}
