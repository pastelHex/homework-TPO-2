/**
 *
 *  @author Zesławska Anna S14211
 *
 */

package zad1;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import JSON.JSONDocument;
import JSON.JSONParser;
import JSON.JSONSet;

//api.openweathermap.org
//7fd31841a82606e42539c44919e3795f
//http://data.fixer.io/api/latest?access_key=001ad8d974f6c65d02c3d1b456c6164f
//http://data.fixer.io/api/latest?access_key=API_KEY&base=USD
//http://api.openweathermap.org/data/2.5/weather?q=city,country=country&mode=json&appid=key
//http://api.openweathermap.org/data/2.5/weather?q=Warsaw,Poland&mode=json&appid=7fd31841a82606e42539c44919e3795f

public class Service {

	String country;
	String currency;
	String city = "Warsaw";
	String weatherAPIKEY = "7fd31841a82606e42539c44919e3795f";
	String fixerRates = "http://data.fixer.io/api/latest?access_key=001ad8d974f6c65d02c3d1b456c6164f";
	String NBPSiteA = "http://www.nbp.pl/kursy/kursya.html";
	String NBPSiteB = "http://www.nbp.pl/kursy/kursyb.html";

	Map<String, String> currencyMapping_eng;// country name - currency code
	Map<String, Double> currencyMapping_pol;// currency code - value in plns

	public Service(String string) {
		this.country = string;
		this.loadCountries();
		this.loadRates();
	}

	public Service() {
		this.loadCountries();
		this.loadRates();
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCurrencyCodeForCountry(String country) {
		return currencyMapping_eng.get(country);
	}

	public void setCurrency(String curr) {
		this.currency = curr;
	}

	private void loadCountries() {
		Locale[] locales = Locale.getAvailableLocales();
		currencyMapping_eng = new HashMap<>();
		Locale pol = new Locale("PL");
		for (Locale l : locales) {
			if (null == l.getCountry() || l.getCountry().isEmpty())
				continue;
			Currency c = Currency.getInstance(l);
			// System.out.println(l.getDisplayCountry(Locale.UK) + " " +
			// c.getCurrencyCode());

			currencyMapping_eng.put(l.getDisplayCountry(Locale.UK), c.getCurrencyCode());
		}
	}

	private String getWeatherURL() {
		return "http://api.openweathermap.org/data/2.5/weather?q=" + this.city + "," + this.country
				+ "&mode=json&appid=" + this.weatherAPIKEY;
	}

	private String getSite(String url) {
		HttpURLConnection connection = null;
		String inputLine = "";
		try {
			URL myURL = new URL(url);
			connection = (HttpURLConnection) myURL.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer content = new StringBuffer();

			while ((inputLine = br.readLine()) != null) {
				content.append(inputLine);
			}
			br.close();
			connection.disconnect();
			inputLine = content.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputLine;
	}

	public String getWeather(String city) {
		String json = getSite("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + this.country
				+ "&mode=json&appid=" + this.weatherAPIKEY);
		return json;
	}

	public String getWeatherPreety(String country, String city) {
		this.country = country;
		String json = getWeather(city);
		System.out.println(json);
		JSONDocument doc = new JSONDocument("document");
		// JSONParser.get_json_node_from_string(json, doc);
		JSONParser.generate_document(json, doc);
		// System.out.println(JSONParser.get_node(doc, "document/weather/main@0"));
		String response = JSONParser.preety_print(doc, " ");
		//System.out.println(response);
		return response;
	}

	public Double getRateFor(String waluta) {
		this.setCurrency(waluta);
		String rates = getSite(fixerRates);

		JSONDocument doc = new JSONDocument("document");
		JSONParser.generate_document(rates, doc);
		//System.out.println(waluta + "::" + JSONParser.get_node(doc, "document/rates/" + waluta));
		String walutaValue = JSONParser.get_node(doc, "document/rates/" + waluta).toString();
		//System.out.println(waluta + "::" + JSONParser.get_node(doc, "document/rates/" + currencyMapping_eng.get(country)));
		String countryWalutaValue = JSONParser.get_node(doc, "document/rates/" + currencyMapping_eng.get(country)).toString();
		return Double.parseDouble(countryWalutaValue)/Double.parseDouble(walutaValue);
	}

	public Double getNBPRate() {
		String walutaDanegoKraju = getCurrencyCodeForCountry(this.country);
		return currencyMapping_pol.get(walutaDanegoKraju);
	}

	private NodeList parseRates(String site) {
		String NBPsite = getSite(site);
		NBPsite = NBPsite.replaceFirst("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">", "");
		NBPsite = NBPsite.replaceFirst("<head>.*</head>", "");
		NBPsite = NBPsite.replaceAll("&", "");
		NBPsite = NBPsite.substring(1);
		// System.out.println(NBPsite);
		DocumentBuilder db;
		Document document;
		NodeList list = null;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(NBPsite));
			document = db.parse(is);
			list = document.getElementsByTagName("tr");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	private void loadRates() {
		currencyMapping_pol = new HashMap<>();
		NodeList list = parseRates(NBPSiteA);
		for (int i = 4; i < list.getLength() - 1; i++) {
			NodeList y = list.item(i).getChildNodes();
			int multi = Integer.parseInt(y.item(3).getTextContent().replaceFirst("\\s+\\w+", ""));
			String key = y.item(3).getTextContent().replaceFirst("\\d+\\s+", "");
			Double value = Double.parseDouble(y.item(5).getTextContent().replace(',', '.')) / multi;
			currencyMapping_pol.put(key, value);
			// System.out.println(multi+"::"+key+"::"+value);
		}
		list = parseRates(NBPSiteB);
		for (int i = 4; i < list.getLength() - 7; i++) {
			NodeList y = list.item(i).getChildNodes();
			int multi = Integer.parseInt(y.item(3).getTextContent().replaceFirst("\\s+\\w+", ""));
			String key = y.item(3).getTextContent().replaceFirst("\\d+\\s+", "");
			Double value = Double.parseDouble(y.item(5).getTextContent().replace(',', '.')) / multi;
			currencyMapping_pol.put(key, value);
			// System.out.println(multi+"::"+key+"::"+value);
		}
		currencyMapping_pol.put("PLN", 1.0);

	}

}
