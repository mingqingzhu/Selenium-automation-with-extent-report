import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class SwagLabs {
    WebDriver driver;
    ExtentSparkReporter spark;
    ExtentReports extent;
    ExtentTest logger;
    @FindBy(how = How.XPATH, using = "//*[@id=\"user-name\"]")
    WebElement username;

    @FindBy(how = How.XPATH, using = "//*[@id=\"password\"]")
    WebElement password;

    @FindBy(how = How.XPATH, using = "//*[@id=\"login-button\"]")
    WebElement login;

    @FindBy(how = How.XPATH, using = "//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")
    WebElement addCart;

    @FindBy(how = How.XPATH, using = "//*[@id=\"shopping_cart_container\"]/a")
    WebElement shoppingCart;

    @FindBy(how = How.XPATH, using = "//*[@id=\"checkout\"]")
    WebElement checkOut;

    @FindBy(how = How.XPATH, using = "//*[@id=\"header_container\"]/div[2]/span")
    WebElement verification; //Checkout: Your Information



    public SwagLabs() {
//        System.setProperty("webdriver.chrome.driver","/Users/zhumingqing/Desktop/work/chromedriver");
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        PageFactory.initElements(driver, this);
    }

    @BeforeTest
    public void startReport() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/firstTest.html");
        extent.attachReporter(spark);
        spark.config().setDocumentTitle("First report title");
        spark.config().setReportName("First report name");
    }

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver","/Users/zhumingqing/Desktop/work/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        PageFactory.initElements(driver, this);
    }

    @Test
    public void shopping() throws Exception{
        driver.get("https://www.saucedemo.com");
        logger = extent.createTest("To check shopping cart function");
        logger.info("Checking website title...");
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
        logger.pass("Getting website successfully!");
        Thread.sleep(3000);
        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        Thread.sleep(2000);
        login.click();
        logger.createNode("login successfully!");
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
//        Alert alert = driver.switchTo().alert();
//        alert.accept();
        Thread.sleep(3000);
        addCart.click();
        Thread.sleep(2000);
        shoppingCart.click();
        Thread.sleep(2000);
        checkOut.click();
        Thread.sleep(3000);

        logger.createNode("Check out working!");
        Assert.assertEquals("CHECKOUT: YOUR INFORMATION", verification.getText());

    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test case failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
        }else if(result.getStatus() == ITestResult.SKIP){
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }else if(result.getStatus() == ITestResult.SUCCESS)
        {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        driver.quit();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
}
