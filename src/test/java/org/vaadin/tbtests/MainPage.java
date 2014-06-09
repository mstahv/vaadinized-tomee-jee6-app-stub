package org.vaadin.tbtests;

import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the main page functionalities.
 */
@Location("") // Vaadin UI is mapped to "/*"
public class MainPage {

    @FindBy(xpath = "//*[text() = '+']")
    private WebElement addNewButton;

    // FindByJQuery is Graphene annotation, similar to std FindBy, but your
    // front end teen nerd might be more productive with it than with e.g. xpath
    // or std css selectors. With this setup, available for Vaadin TestBench 
    // users as well
    @FindByJQuery("div.v-table")
    private WebElement table;

    @FindBy
    private EditorFragment bookEditor;

    public EditorFragment clickNewBookButton() {
        addNewButton.click();
        return bookEditor;
    }

    public void assertBookInListing(String nameOfNewBook) {
        Assert.assertTrue("Not in book list!", table.getText().contains(
                nameOfNewBook));
    }

    public void assertBookNotInListing(String nameOfNewBook) {
        Assert.assertFalse("Book in list!", table.getText().contains(
                nameOfNewBook));
    }

}
