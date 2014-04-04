package org.qooxdoo.demo.mobileshowcase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.ui.Touchable;
import org.oneandone.qxwebdriver.ui.mobile.Selectable;
import org.oneandone.qxwebdriver.ui.mobile.core.WidgetImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasTouchScreen;

public class List extends Mobileshowcase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Mobileshowcase.setUpBeforeClass();
		String title = "List";
		selectItem(title);
		verifyTitle(title);
	}
	
	@Test
	public void selectItem() {
		Selectable list = (Selectable) driver.findWidget(By.xpath("//div[contains(@class, 'master-detail-detail')]/descendant::ul[contains(@class, 'list')]"));
		list.selectItem("Item #3");
		
		WebElement selected = driver.findElement(By.xpath("//div[text() = 'You selected Item #3']"));
		Assert.assertTrue(selected.isDisplayed());
		Touchable ok = (Touchable) driver.findWidget(By.xpath("//div[text() = 'You selected Item #3']/ancestor::div[contains(@class, 'popup-content')]/descendant::div[contains(@class, 'dialog-button')]"));
		ok.tap();
		try {
			Assert.assertFalse(selected.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
	}
	
	@Test
	public void removeItem() throws InterruptedException {
		if (!(driver.getWebDriver() instanceof HasTouchScreen)) {
			return;
		}
		WebElement item = driver.findElement(By.xpath("//div[text() = 'Item #6']"));
		WidgetImpl.track(driver.getWebDriver(), item, 700, 0, 10);
		Thread.sleep(1000);
		try {
			Assert.assertFalse(item.isDisplayed());
		}
		catch(StaleElementReferenceException e) {
			// Element is no longer in the DOM
		}
	}
}
