package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import kit.edu.mensameet.server.Application;
import kit.edu.mensameet.server.model.Line;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.MensaData;

import java.io.IOException;
import java.time.*;
import java.time.temporal.IsoFields;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Component
public class MensaDataController {

	private MensaData mensaData;
	
	public MensaData getMensaData() throws IOException {
		if (mensaData == null) {
			updateMensaData() ;
		}
		
		return mensaData;
	}

	public MensaData updateMensaData() throws IOException {
		// LocalDate for the current week of year for getting the current mealplan
		LocalDate localDate = LocalDate.now();
		int weekNumber = localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

		System.out.println(weekNumber);
		// get the current mealplan
		Document doc = Jsoup.connect(
				"https://www.sw-ka.de/de/essen/?view=ok&STYLE=popup_plain&c=adenauerring&p=1&kw=" + (weekNumber)).get();

		String pageText = doc.body().text();

		// Creating a Array of Strings, each Array includes the whole menu of one line
		String[] LineText = new String[14];

		int i = 0;

		while (i < LineText.length) {
			// The cases [koeriwerk] and the [pizzawerk] are special cases were you can't
			// just split the string at the "[", you have to add "\\" before "]"
			if (i == 7 || i > 9) {
				// For the last line you have to cut off the meals following after today
				if (i == 12) {
					// System.out.println(MealLine.values()[i].toString());
					String[] split = pageText.split("\\" + MealLine.values()[i].toString(), 2);
					LineText[i] = split[0];
					// MO-DO
					if (split[1].contains("Linie 1")) {
						LineText[i + 1] = split[1].split("Linie 1", 2)[0];
						// FR
					} else {
						LineText[i + 1] = split[1].split("Legende", 2)[0];
					}
					// System.out.println(LineText[i]);
					// System.out.println(LineText[i + 1]);
					break;
				} else {
					String[] split = pageText.split("\\" + MealLine.values()[i].toString(), 2);
					LineText[i] = split[0];
					// System.out.println(LineText[i]);
					pageText = split[1];
					i++;
				}
			}
			// for the other meal lines you can split the string normally
			else {
				// Split the whole pageText at each MealLine
				String[] split = pageText.split(MealLine.values()[i].toString(), 2);

				LineText[i] = split[0];
				// System.out.println(LineText[i]);
				// System.out.println(MealLine.values()[i].toString());
				pageText = split[1];
				i++;
			}
		}
		// LineTextConverter(LineText);
		return mensaData = Converter(LineText);

	}

	/**
	 * This method converts an array of meal lines into single meals
	 * 
	 * @param LineText is an Array of Strings, each String contains one line
	 */
	private MensaData Converter(String[] LineText) {
		// create all 13 lines
		Line[] lines = new Line[13];
		// iterate through all lines
		for (int j = 1; j <= lines.length; j++) {
			lines[j - 1] = new Line();
			// Check if a line doesn't offer meals(some lines are closed during the exam
			// phase)
			if (!LineText[j].contains("€")) {
				String[] meals = new String[] { LineText[j] };
				lines[j - 1].setMeals(meals);
				lines[j - 1].setMealLine(MealLine.values()[j - 1]);
				// System.out.println(lines[j - 1].getMeals()[0]);
				// System.out.println(lines[j - 1].getMealLine().toString());

			} else {
				// Split the line String at each "€" and we get each individual meal
				String[] meals = LineText[j].split(" € ");
				// For the last line we have to delete the last "meal" of the array, because it
				// does not contain any food, just some text leftovers
				if (j == 13) {
					String[] temp = new String[meals.length - 1];
					for (int i = 0; i < meals.length - 1; i++) {

						temp[i] = meals[i];
					}
					meals = temp;
				}
				// because of splitting the String at each "€", we have to add it again after
				// each meal with a given price
				for (int k = 0; k < meals.length; k++) {
					if (!meals[k].endsWith(" "))
						meals[k] = meals[k] + "€";
				}

				lines[j - 1].setMeals(meals);
				lines[j - 1].setMealLine(MealLine.values()[j - 1]);
				// System.out.println(meals.length);
				// System.out.println(lines[j - 1].getMealLine().toString());

			}
		}
//for testing purposes
//		for (int i = 0; i < lines.length; i++) {
//			System.out.println(lines[i].getMealLine().toString() + ": ");
//			for (int j = 0; j < lines[i].getMeals().length; j++) {
//				System.out.println(lines[i].getMeals()[j]);
//			}
//		}
		// create new mensaData with an Array of the created lines
		return mensaData = new MensaData(lines);
	}
}