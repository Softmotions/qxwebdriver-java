package org.oneandone.qxwebdriver.examples.widgetbrowser;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oneandone.qxwebdriver.By;
import org.oneandone.qxwebdriver.ui.Selectable;
import org.oneandone.qxwebdriver.ui.Widget;

public class TestList extends Common {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Common.setUpBeforeClass();
		selectTab("List");
	}
	
	@Test
	public void list() {
		By listLocator = By.qxh("*/qx.ui.form.List"); 
		Widget list = tabPage.findWidget(listLocator);

		// For the regular, non-virtual list we can directly interact with the
		// item since it's already rendered. The tree pane will automatically
		// scroll the item into view when it's focused
		String label1 = "Hohl, Hartlieb";
		Widget item = list.findWidget(By.qxh("*/[@label=" + label1 + "]"));
		item.click();
		
		java.util.List<Widget> selection = list.getWidgetListFromProperty("selection");
		assertEquals(1, selection.size());
		Widget selected = selection.get(0);
		String selectedLabel = (String) selected.getPropertyValue("label");
		assertEquals(label1, selectedLabel);
		
		// Alternatively, we can use the Selectable interface
		String label2 = "Prill, Nico";
		Selectable sList = (Selectable) list;
		sList.selectItem(label2);
		
		selection = list.getWidgetListFromProperty("selection");
		assertEquals(1, selection.size());
		selected = selection.get(0);
		selectedLabel = (String) selected.getPropertyValue("label");
		assertEquals(label2, selectedLabel);
	}
	
	@Test
	public void virtualList() {
		// For the virtual list, we must use the Selectable interface. It will
		// scroll the tree until it finds an item with a matching label, then
		// click it.
		String label1 = "Pauls, Gernfried";
		By virtualListLocator = By.qxh("*/qx.ui.list.List");
		Selectable vlist = (Selectable) tabPage.findWidget(virtualListLocator);
		vlist.selectItem(label1);
		
		java.util.List<String> selection = (java.util.List<String>) vlist.getPropertyValue("selection");
		assertEquals(1, selection.size());
		String selected = selection.get(0);
		//the Virtual List's model items don't override toString, so selected is
		//something like qx.data.model.firstname"lastname[2314-0] :( 
		//assertEquals(label1, selected);
		
		By groupedVirtualListLocator = By.qxh("widgetbrowser.pages.List/qx.ui.container.Composite/child[5]");
		Selectable gvlist = (Selectable) tabPage.findWidget(groupedVirtualListLocator);
		gvlist.selectItem("Fritz, Katrein");
		
		selection = (java.util.List<String>) vlist.getPropertyValue("selection");
		assertEquals(1, selection.size());
		selected = selection.get(0);
	}

}
