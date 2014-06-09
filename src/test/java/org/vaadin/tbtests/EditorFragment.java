
package org.vaadin.tbtests;

import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page "fragment" for the BookEditor class
 */
public class EditorFragment {
    
    @Root
    WebElement bookEditor;
    
    @FindBy(xpath = "//*[text() = 'Save']")
    WebElement saveButton;
    
    // No parameter to @FindBy -> finds element with id "bookTitle",
    // derived from the java field name
    @FindBy
    WebElement bookTitle;
    
    public void typeNameOfTheBook(String name) {
        bookTitle.sendKeys(name);
    }
    
    public void save() {
        saveButton.click();
    }



}
