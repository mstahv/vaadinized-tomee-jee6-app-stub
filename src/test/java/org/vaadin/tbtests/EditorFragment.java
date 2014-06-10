
package org.vaadin.tbtests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page "fragment" for the BookEditor class
 */
public class EditorFragment {
    
    @FindBy(xpath = "//*[text() = 'Save']")
    private WebElement saveButton;
    
    // No parameter to @FindBy -> finds element with id "bookTitle",
    // derived from the java field name
    @FindBy
    private WebElement bookTitle;
    
    public void typeNameOfTheBook(String name) {
        bookTitle.sendKeys(name);
    }
    
    public void save() {
        saveButton.click();
    }

}
