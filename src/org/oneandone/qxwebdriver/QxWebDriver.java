package org.oneandone.qxwebdriver;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.oneandone.qxwebdriver.widget.ComboBox;
import org.oneandone.qxwebdriver.widget.QxWidget;
import org.oneandone.qxwebdriver.widget.ScrollArea;
import org.oneandone.qxwebdriver.widget.SelectBox;
import org.oneandone.qxwebdriver.widget.Menu;
import org.oneandone.qxwebdriver.widget.Widget;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QxWebDriver implements WebDriver {

	public QxWebDriver(WebDriver webdriver) {
		driver = webdriver;
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	static public final String GET_CLASS_HIERARCHY_BY_ELEMENT = "var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);" +
			"var hierarchy = [];" +
			"var clazz = widget.constructor;" +
			"while (clazz && clazz.classname) {" +
			"  hierarchy.push(clazz.classname);" +
			"  clazz = clazz.superclass;" +
			"}" +
			"return hierarchy;";
	
	static public final String GET_INTERFACES_BY_ELEMENT = "var widget = qx.ui.core.Widget.getWidgetByElement(arguments[0]);" +
			"var iFaces = [];" +
			"var clazz = widget.constructor;" +
			"qx.Class.getInterfaces(clazz).forEach(function(item, i) {" +
			"  iFaces.push(/\\[Interface (.*?)\\]/.exec(item.toString())[1]);" +
			"});" +
			"return iFaces;";
	
	static public final String IS_QX_APPLICATION_READY = "return (qx && qx.core && qx.core.Init && !!qx.core.Init.getApplication())";
	
	public ExpectedCondition<Boolean> qxAppIsReady() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				Object result = jsExecutor.executeScript(IS_QX_APPLICATION_READY);
				Boolean isReady = (Boolean) result;
				return isReady;
			}

			@Override
			public String toString() {
				return "qooxdoo application is ready.";
			}
		};
	}
	
	public WebDriver driver;
	private JavascriptExecutor jsExecutor;
	
	public List<String> getWidgetInterfaces(WebElement element) {
		String script = JavaScript.INSTANCE.getValue("getInterfaces");
		return (List<String>) jsExecutor.executeScript(script, element);
	}
	
	public List<String> getWidgetInheritance(WebElement element) {
		String script = JavaScript.INSTANCE.getValue("getInheritance");
		return (List<String>) jsExecutor.executeScript(script, element);
	}
	
	public Widget findWidget(By by) {
		WebElement element = findElement(by);
		return getWidgetForElement(element);
	}
	
	public Widget getWidgetForElement(WebElement element) {
		//List<String> interfaces = getWidgetInterfaces(element);
		List<String> classes = getWidgetInheritance(element);
		
		Iterator<String> iter = classes.iterator();
		
		while(iter.hasNext()) {
			String className = iter.next();
			if (className.equals("qx.ui.form.SelectBox")) {
				return new SelectBox(element, driver);
			}
			
			if (className.equals("qx.ui.form.ComboBox")) {
				return new ComboBox(element, driver);
			}
			
			if (className.equals("qx.ui.menu.Menu")) {
				return new Menu(element, driver);
			}
			
			if (className.equals("qx.ui.core.scroll.AbstractScrollArea")) {
				return new ScrollArea(element, driver);
			}
		}
		
		return new QxWidget(element, driver);
	}

	@Override
	public void close() {
		driver.close();
	}

	@Override
	public WebElement findElement(By arg0) {
		return driver.findElement(arg0);
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return driver.findElements(arg0);
	}

	@Override
	public void get(String arg0) {
		driver.get(arg0);
		new WebDriverWait(driver, 10, 250).until(qxAppIsReady());
	}

	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	@Override
	public String getPageSource() {
		return driver.getPageSource();
	}

	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	@Override
	public Options manage() {
		return driver.manage();
	}

	@Override
	public Navigation navigate() {
		return driver.navigate();
	}

	@Override
	public void quit() {
		driver.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

}
