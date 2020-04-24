package com.test4wro.testing;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class AutomationPractice {

    private WebDriver driver;

    // DANE TESTOWE
    private String email = "test@ll1.com";
    private String password = "hasloLL";

    Logger logger = Logger.getLogger("TestyAutomatyczne");

    @Before
    public void setUp(){
        // Uruchamiamy nowy egzemplarz przegladarki Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lazarluk\\chromedriver.exe");
        driver = new ChromeDriver();

        //Dla tych co korzystaja z Firefoxa. Nalezy zakomentowac dwie linijki wyzej
        //System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
        //driver = new FirefoxDriver();

        // Maksymalizujemy okno
        driver.manage().window().maximize();

        // Ustawienie czasu niejawnego oczekiwania na 10 sekund
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Przechodzimy na wybrana strone
        driver.get("http://automationpractice.com/index.php");
    }

    @Test
    public void testSignUp(){

        // Lokalizujemy i tworzymy referencje do obiektu typu WebElement dla przycisku Sign In w prawym górnym rogu strony
        WebElement signInButton = driver.findElement(By.linkText("Sign in"));
        // Klikamy w ten przycisk
        signInButton.click();

        // Lokalizujemy pole do wpisania email do rejestracji pola
        WebElement emailAddressInput = driver.findElement(By.xpath("//input[@id='email_create']"));
        // Wysyłamy "klawisze" -> Słowo do tego pola
        emailAddressInput.sendKeys(email);

        // Lokalizujemy pole do przycisku Submit do tworzenia nowego konta
        WebElement createAnAccountButton = driver.findElement(By.xpath("//button[@id='SubmitCreate']"));
        // Klikamy w to pole
        createAnAccountButton.click();

        // Lokalizujemy radio button przy uzyciu css selectora
        WebElement titleRadioButton = driver.findElement(
                By.cssSelector("#account-creation_form .radio-inline:nth-child(3) [type]"));
        // Klikamy w radio button
        titleRadioButton.click();

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id='customer_firstname']"));
        firstNameInput.sendKeys("Tester");

        WebElement lastNameInput = driver.findElement(By.xpath("//input[@id='customer_lastname']"));
        lastNameInput.sendKeys("Automator");

        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='passwd']"));
        passwordInput.sendKeys(password);

        // Lokalizujemy Dropdown
        Select dayOfBirth = new Select(driver.findElement(By.xpath("//select[@id='days']")));
        // Wybieramy wartość po value
        dayOfBirth.selectByValue("1");

        // Lokalizujemy Dropdown
        Select monthOfBirth = new Select(driver.findElement(By.xpath("//select[@id='months']")));
        // Wybieramy wartość po widocznym tekście
        monthOfBirth.selectByVisibleText("April ");

        Select yearOfBirth = new Select(driver.findElement(By.xpath("//select[@id='years']")));
        // Wybieramy wartość po indeksie -> Numer kolejnej wartości
        yearOfBirth.selectByIndex(19);

        WebElement newsletterCheck  = driver.findElement(By.xpath("//input[@id='newsletter']"));
        newsletterCheck.click();

        WebElement firstNameAddrInput = driver.findElement(By.xpath("//input[@id='firstname']"));
        firstNameAddrInput.sendKeys("Tester");

        WebElement lastNameAddrInput = driver.findElement(By.xpath("//input[@id='lastname']"));
        lastNameAddrInput.sendKeys("Automator");

        WebElement companyAddrInput = driver.findElement(By.xpath("//input[@id='company']"));
        companyAddrInput.sendKeys("Company");

        WebElement address1Input = driver.findElement(By.xpath("//input[@id='address1']"));
        address1Input.sendKeys("Company Address");

        WebElement cityAddrInput = driver.findElement(By.xpath("//input[@id='city']"));
        cityAddrInput.sendKeys("Wrocław");

        Select stateSelect = new Select(driver.findElement(By.xpath("/html//select[@id='id_state']")));
        stateSelect.selectByIndex(3);

        WebElement postCodeAddrInput = driver.findElement(By.xpath("//input[@id='postcode']"));
        postCodeAddrInput.sendKeys("00000");

        Select countrySelect = new Select(driver.findElement(By.xpath("/html//select[@id='id_country']")));
        countrySelect.selectByValue("21");

        WebElement phoneAddr = driver.findElement(By.xpath("//input[@id='phone']"));
        phoneAddr.sendKeys("712123123");

        WebElement mobilePhoneAddr = driver.findElement(By.xpath("//input[@id='phone_mobile']"));
        mobilePhoneAddr.sendKeys("712123123");

        WebElement submitRegisterButton = driver.findElement(By.xpath("//button[@id='submitAccount']"));
        submitRegisterButton.click();

        // Sprawdzamy czy tytuł strony jest zgodny z naszymi przewidywaniami
        assertEquals("My account - My Store", driver.getTitle());
    }

    @Test
    public void testSignIn(){
        WebElement signInBtn = driver.findElement(By.linkText("Sign in"));
        signInBtn.click();

        WebElement emailAddressInput = driver.findElement(By.xpath("//input[@id='email']"));
        emailAddressInput.sendKeys(email);

        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='passwd']"));
        passwordInput.sendKeys(password);

        WebElement signInButton = driver.findElement(By.xpath("//button[@id='SubmitLogin']"));
        signInButton.click();

        assertEquals("My account - My Store", driver.getTitle());
    }

    @Test
    public void testAddItemToBasket(){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement hoverOnElement =
                driver.findElement(By.xpath("//div[@id='block_top_menu']/ul//a[@title='Women']"));

        // Action builder -> SELENIUM API
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverOnElement)
                .build()
                .perform();

        // Oczekiwanie JAWNE
        // Warunek do spełnienia
        WebElement clickOnElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='block_top_menu']/ul/li[1]/ul/li[1]/ul//a[@title='T-shirts']")));
        actions.click(clickOnElement)
                .build()
                .perform();

        WebElement hoverOnPicture = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html//div[@id='center_column']/ul//div[@class='product-image-container']" +
                        "/a[@title='Faded Short Sleeve T-shirts']/img[@alt='Faded Short Sleeve T-shirts']")));

        actions.moveToElement(hoverOnPicture)
                .build()
                .perform();

        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html//div[@id='center_column']/ul//a[@title='Add to cart']/span[.='Add to cart']")));

        addToCart.click();

        // Przełączały się na okienko modalne
        driver.switchTo().activeElement();

        WebElement proceedToCheckout = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[title='Proceed to checkout'] span")));
        proceedToCheckout.click();

        WebElement totalPayment = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("/html//span[@id='total_price']")));

        // Cena powinna się zgadzać ;)
        assertEquals("$18.51", totalPayment.getText());

        logger.info(totalPayment.getText());
    }


    @Test
    public void testMakeAPurchase(){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Ułatwmy sobie trochę ;)
        this.testSignIn();
        this.testAddItemToBasket();
        // Działa jak marzenie!

        WebElement proceedToCheckout = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='center_column']//a[@title='Proceed to checkout']/span")));
        proceedToCheckout.click();

        WebElement fillComment = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='ordermsg']/textarea[@name='message']")));
        fillComment.sendKeys("Tester Automat Zamawia");

        proceedToCheckout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p/button[@type='submit']")));
        proceedToCheckout.click();

        WebElement checkAgreement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='cgv']")));
        checkAgreement.click();

        proceedToCheckout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p/button[@type='submit']")));
        proceedToCheckout.click();

        WebElement payByBankWire = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[title='Pay by bank wire']")));
        payByBankWire.click();

        WebElement confirmOrder = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p/button[@type='submit']")));
        confirmOrder.click();

        // Robimy zrzut ekranu ;)
        try {
            File scrFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("target/main_page.png"));
        }catch (Exception ex){
            logger.warning("It wasn't able to take a screenshot.");
        }

    }


    @After
    public void tearDown() throws Exception{
//        driver.quit();
    }
}
