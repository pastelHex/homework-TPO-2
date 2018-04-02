package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.window;
import zad1.Service;

public class Controller {

	private Service model;
	private window view;
	private ActionListener buttonListener;

	public Controller() {
		try {
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

	}

	protected void set_weather(String country, String city) {
		if (city != null && city.length() > 1) {
			String weatherString = model.getWeatherPreety(country, city);
			view.txtWeatherInfo.setText(weatherString);
			view.txtWeatherInfo.repaint();
		}
	}

	protected void set_currency(String country, String curr) {
		if (curr != null) {
			model.setCurrency(curr);
			view.label_kurs.setText(
					view.label_kurs.getText().substring(0, view.label_kurs.getText().length() - 3) + curr);
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
}
