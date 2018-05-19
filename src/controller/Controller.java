package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import view.window;
import zad1.Service;

public class Controller {

	private Service model;
	private window view;
	private ActionListener buttonListener;
	private WebView webView;
	JFXPanel jfxPanel;

	public Controller() {
		try {
			jfxPanel = null;
			model = new Service();
			view = new window();
			control();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void control() {
		buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String country = view.txtCountry.getText();
				String city = view.txtCity.getText();
				String curr = view.txtCurrency.getText();
				set_currency(country, curr);
				set_weather(country, city);
				set_wiki(city);
			}
		};
		view.btnShow.addActionListener(buttonListener);
	}

	protected void set_wiki(String city) {
		Component[] com = view.panel_wiki.getComponents();
		if (jfxPanel != null && com.length != 0) {
			for (int i = 0; i < com.length; i++) {
				if (com[i].getName() != null && jfxPanel.getName() != null)
					if (com[i].getName().equals(jfxPanel.getName()))
						view.panel_wiki.remove(jfxPanel);
			}

		}
		jfxPanel = new JFXPanel();
		jfxPanel.setName("jfxPanel");
		view.panel_wiki.add(jfxPanel);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(jfxPanel, "https://en.wikipedia.org/wiki/" + city);
			}
		});
		view.label_City.setText(city);
		view.label_City.repaint();
		view.panel_wiki.repaint();
	}

	protected void set_weather(String country, String city) {
		if (city != null && city.length() > 1) {
			String weatherString = model.getWeatherPreety(country, city);
			view.txtWeatherInfo.setText(weatherString);
			view.txtWeatherInfo.repaint();
			setTable();
		}
	}

	protected void set_currency(String country, String curr) {
		if (curr != null) {
			model.setCurrency(curr);
			view.label_kurs.setText(view.label_kurs.getText().substring(0, view.label_kurs.getText().length() - 3)
					+ model.getCurrencyCodeForCountry(country));
			view.label_kurs.repaint();
		}
		if ((curr != null && curr.length() > 2) && (model.getCountry() != null || country != null)) {
			if (country != null) {
				model.setCountry(country);
			}
			view.labelCurrency.setText(model.getRateFor(curr).toString());
			view.labelCurrency.repaint();
		}

		if (model.getCountry() != null || country != null) {
			if (country != null) {
				model.setCountry(country);
			}
			view.labelPLNCurrency.setText(model.getNBPRate().toString());
			view.labelPLNCurrency.repaint();
		}
	}

	private void initFX(JFXPanel x, String url) {
		// fxPanel = new JFXPanel();
		Scene scene = createScene(url);
		x.setScene(scene);

		GridPane.setHgrow(webView, Priority.ALWAYS);
		GridPane.setVgrow(webView, Priority.ALWAYS);
		// return fxPanel;
	}

	private Scene createScene(String url) {
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);
		try {
			webView = new WebView();
			GridPane.setHgrow(webView, Priority.ALWAYS);
			GridPane.setVgrow(webView, Priority.ALWAYS);

			WebEngine webEngine = webView.getEngine();
			webEngine.load(url);
			root.getChildren().add(webView);

			GridPane.setHgrow(webView, Priority.ALWAYS);
			GridPane.setVgrow(webView, Priority.ALWAYS);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return scene;
	}

	private void setTable() {
		String weatherDesc = model.getWeatherAtribute("document/weather/description@0");
		String tempMax = model.getWeatherAtribute("document/main/temp_max");
		String tempMin = model.getWeatherAtribute("document/main/temp_min");
		String pressure = model.getWeatherAtribute("document/main/pressure");
		String visibility = model.getWeatherAtribute("document/visibility");

		DefaultTableModel model = (DefaultTableModel) view.table.getModel();
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		model.addRow(new Object[] { "Weather descryption", weatherDesc });
		model.addRow(new Object[] { "Max temperature", Double.parseDouble(tempMax) - 273.15 });
		model.addRow(new Object[] { "Min temperature", Double.parseDouble(tempMin) - 273.15 });
		model.addRow(new Object[] { "Pressure", pressure });
		model.addRow(new Object[] { "Visibility", visibility });

		view.table.repaint();
	}
}
