package org.vaadin.tbtests;

import java.net.URL;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.vaadin.BookService;
import org.vaadin.entities.Book;
import org.vaadin.presentation.BookUI;

/**
 *
 */
@RunWith(Arquillian.class)
public class ArquilliantExampleTest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        final PomEquippedResolveStage runtime = Maven.resolver().
                loadPomFromFile("pom.xml").importRuntimeDependencies();

        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(BookUI.class.getPackage())
                .addClasses(BookService.class, Book.class)
                .addAsResource(new ClassLoaderAsset("META-INF/persistence.xml"),
                        "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create(
                                "beans.xml"))
                .addAsLibraries(runtime.resolve().withTransitivity().asFile());
        return war;
    }

    @Inject
    BookService service;

    @Test
    public void testServiceOnly() {
        Book book = new Book();
        book.setBookTitle("Böö");
        service.saveOrPersist(book);
        Assert.assertEquals(1, service.findByTitle("Böö").size());
    }

    @Drone
    WebDriver driver;

    @ArquillianResource
    private URL deploymentUrl;

    @FindBy(xpath = "//*[text() = '+']")
    WebElement addNewButton;

    @FindBy(xpath = "//*[text() = 'Save']")
    WebElement saveButton;

    @FindBy(xpath = "//input[@type = 'text']")
    WebElement titleInput;

    @FindBy(className = "v-table")
    WebElement table;

    @Test
    public void testUIAndService() {
        String nameOfNewBook = "Book of Vaadin";

        // No books with given name should exist in the back end
        Assert.assertEquals(0, service.findByTitle(nameOfNewBook).size());

        // Use TestBench enhanced WebDriver to add book with browser
        driver.get(deploymentUrl.toString());
        addNewButton.click();
        titleInput.sendKeys(nameOfNewBook);
        saveButton.click();

        // Note, that no ajax savvy haxies niided. Even with implicit timeouts, 
        // this would fail without TestBench
        Assert.assertTrue("Not in book list!", table.getText().contains(
                nameOfNewBook));

        // Now the cool part, ensure from the real service, that entity was
        // added
        Assert.assertEquals(1, service.findByTitle(nameOfNewBook).size());

    }

}
