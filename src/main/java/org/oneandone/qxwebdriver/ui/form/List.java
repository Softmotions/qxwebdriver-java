/* ************************************************************************

   qxwebdriver-java

   http://github.com/qooxdoo/qxwebdriver-java

   Copyright:
     2012-2013 1&1 Internet AG, Germany, http://www.1und1.de

   License:
     LGPL: http://www.gnu.org/licenses/lgpl.html
     EPL: http://www.eclipse.org/org/documents/epl-v10.php
     See the license.txt file in the project's top-level directory for details.

   Authors:
     * Daniel Wagner (danielwagner)

************************************************************************ */

package org.oneandone.qxwebdriver.ui.form;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.oneandone.qxwebdriver.ui.Scrollable;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;
import org.oneandone.qxwebdriver.ui.core.scroll.AbstractScrollArea;
import org.openqa.selenium.WebElement;

/**
 * Represents a <a href="http://demo.qooxdoo.org/current/apiviewer/#qx.ui.form.List">List</a>
 * widget
 */
public class List extends AbstractScrollArea implements Selectable, Scrollable {

	public List(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}

	public Widget getSelectableItem(Integer index) {
		Object result = jsRunner.runScript("getItemFromSelectables", contentElement, index);
		WebElement element = (WebElement) result;
		return driver.getWidgetForElement(element);
	}

	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}

	public Widget getSelectableItem(String regex) {
		Object result = jsRunner.runScript("getItemFromSelectables", contentElement, regex);
		WebElement element = (WebElement) result;
		return driver.getWidgetForElement(element);
	}

	public void selectItem(String regex) {
		getSelectableItem(regex).click();
	}
}
