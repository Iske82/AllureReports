package seleniumTests.helpers;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {
	public static boolean isPresent(WebDriver webdriver, By selector) {
		// try to find element by specified selector
		try {
			webdriver.findElement(selector);
		} catch (NoSuchElementException e) {
			// if element not exist return false
			return false;
		}
		return true;
	}

	/**
	 * Metoda ceka da element sa prosledjenim selektorom postane klikabilan
	 * (displayed and enabled)
	 * 
	 * @param driver
	 *            - web driver
	 * @param selector
	 *            - selektor elementa koji cekamo
	 * @param waitInterval
	 *            - vreme koje ce driver da ceka da se element pojavi u DOM
	 *            stablu
	 * @return WebElement selektovani element
	 */
	public static WebElement waitToBeClickable(WebDriver driver, By selector, int waitInterval) {
		WebElement element = (new WebDriverWait(driver, waitInterval))
				.until(ExpectedConditions.elementToBeClickable(selector));
		return element;
	}

	/**
	 * Metoda ceka da se element sa prosledjenim selektorom pojavi u DOM stablu
	 * 
	 * @param driver
	 *            - web driver
	 * @param selector
	 *            - selektor elementa cije prisustvo cekamo
	 * @param waitInterval
	 *            - vreme koje ce driver da ceka da se element pojavi u DOM
	 *            stablu
	 * @return WebElement selektovani element
	 */
	public static WebElement waitForElementPresence(WebDriver driver, By selector, int waitInterval) {
		WebElement element = (new WebDriverWait(driver, waitInterval))
				.until(ExpectedConditions.presenceOfElementLocated(selector));
		return element;
	}

	/**
	 * Metoda ceka da naslov stranice postane jednak prosledjenom stringu
	 * 
	 * @param driver
	 * @param title
	 *            - naslov koji cekamo
	 * @param waitInterval
	 *            - vreme koje ce driver da ceka da se element pojavi u DOM
	 *            stablu
	 */
	public static void waitForTitle(WebDriver driver, String title, int waitInterval) {
		(new WebDriverWait(driver, waitInterval)).until(ExpectedConditions.titleIs(title));
	}

	public static boolean waitForInvisibilityOfElement(WebDriver driver, By selector, int waitInterval) {
		boolean value = (new WebDriverWait(driver, waitInterval))
				.until(ExpectedConditions.invisibilityOfElementLocated(selector));
		return value;

	}
}
