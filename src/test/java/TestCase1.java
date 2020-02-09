import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;


public class TestCase1 {
    private WebDriver driver;
    private String baseUrl = "http://live.demoguru99.com";
    public int scc = 0;


    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "c:\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);
        WebElement mobileLink = driver.findElement(By.xpath("//a[text()=\"Mobile\"]"));
        mobileLink.click();
    }


    @Test
//    @Ignore
    public void testPageTitle() {
        Assert.assertEquals("Title should be equal", "Mobile", driver.getTitle());
        Select sortBySelect = new Select(driver.findElement(By.xpath("//div[@class='sort-by']//select")));
        sortBySelect.selectByVisibleText("Name");
        List<WebElement> productsElements = driver.findElements(By.xpath("//div[@class='product-info']/h2/a"));
        List<String> productNames = new ArrayList<>();
        productsElements.forEach(element -> productNames.add(element.getText()));
        Assert.assertTrue("Product names should be sorted", isSorted(productNames));
    }

    @Test
//    @Ignore
    public void testVerifyDetailsPrice() {
        WebElement productPriceElement = driver.findElement(By.xpath("//li[@class='item last']//div[@class='product-info']//h2//a[text()='Sony Xperia']//parent::h2//parent::div//div[@class='price-box']/span/span"));
        String productPrice = productPriceElement.getText();
        driver.findElement(By.xpath("//li[@class='item last']//a")).click();
        String detailsPrice = driver.findElement(By.xpath("//div[@class='price-info']//span[@class='price']")).getText();
        Assert.assertEquals("Prices should be the same", productPrice, detailsPrice);
    }

//    @Ignore
    @Test
    public void testVerifyErrors() {
        driver.findElement(By.xpath("//li[@class='item last']//div[@class='product-info']//h2//a[text()='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']/button")).click();
        driver.findElement(By.xpath("//input[@title='Qty']")).sendKeys("1000");
        driver.findElement(By.xpath("//span[text()='Update']")).click();
        String errorMessage = driver.findElement(By.xpath("//p[@class='item-msg error']")).getText();
        Assert.assertTrue("Error message: ", errorMessage.contains("The maximum quantity allowed for purchase is 500."));
        driver.findElement(By.xpath(".//*[@id='empty_cart_button']")).click();
        String qty = driver.findElement(By.xpath("//div[@class='header-minicart']//span[@class='count']")).getText();
        String noItemsMsg = driver.findElement(By.xpath("//div[@class='cart-empty']")).getText();
        Assert.assertEquals("You have no items in your shopping cart.\n" +
                "Click here to continue shopping.",noItemsMsg);
    }

    @Test
//    @Ignore
    public void compareTwoProducts(){
        for (int i = 0; i < 2; i++) {
            driver.findElements(By.xpath("//a[text()='Add to Compare']")).get(i).click();
        }

        driver.findElement(By.xpath("//span[text()='Compare']")).click();
        driver.switchTo().window(getPopupHandel("Products Comparison List - Magento Commerce"));
        driver.findElement(By.xpath("//h1[text()='Compare Products']"));
        System.out.println(driver.getTitle());
        driver.close();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private boolean isSorted(List<String> productsNames) {
        List<String> beforeSorted = new ArrayList<>(productsNames);
        Collections.sort(productsNames);
        return beforeSorted.equals(productsNames);
    }

    private String getPopupHandel(String popupTitle) {
        String subWindowHandler = null;
        Set<String> handles = driver.getWindowHandles(); // get all window handles
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
            if (popupTitle.equals(driver.switchTo().window(subWindowHandler).getTitle())) {
                return subWindowHandler;
            }
        }
        return null;
    }
}