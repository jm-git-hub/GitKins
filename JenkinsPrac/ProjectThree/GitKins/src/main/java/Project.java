import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Project extends Methods {
    WebDriver driver;
    Actions hoverOver;

    @BeforeClass
    public void TestManager() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
    }

    @BeforeMethod
    public void Webpage() {
        driver.navigate().to("http://automationpractice.com/index.php");
        hoverOver = new Actions(driver);
    }

    @Test(priority = 0)
    public void TestCaseOne() {
        String actual = driver.findElement(By.xpath("//span[.='(empty)']")).getText();
        String expected = "(empty)";
        Assert.assertEquals(actual, expected, "\"Cart (Empty)\" text is not displayed");

        List<WebElement> itemsList = driver.findElements(By.xpath("//div[@class='product-container']"));

        ArrayList<Item> itemBlockList = new ArrayList<>();

        storeItemBlockInList(itemsList, itemBlockList);

        int indexOfItemWithLowestPrice = lowestPriceIndex(itemBlockList);

        Item cheapestItem = itemBlockList.get(indexOfItemWithLowestPrice);

        System.out.println("Lowest Price Item: ");
        printItemBlock(cheapestItem);

        Assert.assertEquals(cheapestItem.getProductPrice(), 16.4, "Discount has not been applied correctly");
    }

    @Test(priority = 1)
    public void TestCaseTwo() throws InterruptedException {
        List<WebElement> moreButtons = driver.findElements(By.xpath("//span[.='More']"));
        List<WebElement> itemsList = driver.findElements(By.xpath("//div[@class='product-container']"));

        ArrayList<Item> itemBlockList = new ArrayList<>();

        storeItemBlockInList(itemsList, itemBlockList);

        int indexOfItemWithHighestPrice = highestPriceIndex(itemBlockList);

        Item highestPriceItem = itemBlockList.get(indexOfItemWithHighestPrice);

        System.out.println("Highest Price Item: ");
        printItemBlock(highestPriceItem);

        Actions hoverOver = new Actions(driver);
        WebElement theExpensiveElementLocator = itemsList.get(indexOfItemWithHighestPrice);

        scrollingToElement(theExpensiveElementLocator, driver);
        hoverOver.moveToElement(theExpensiveElementLocator).perform();
        for (WebElement more : moreButtons) {
            if (more.isDisplayed()) {
                hoverOver.moveToElement(more).click().perform();
            }
        }

        Thread.sleep(2000);
        WebElement priceValidation = driver.findElement(By.id("our_price_display"));

        Assert.assertEquals(highestPriceItem.getProductPrice(), Double.parseDouble(priceValidation.getText().substring(1)), (highestPriceItem.getProductName()) + " price validation failed");
        driver.navigate().back();
    }

//    @Test(priority = 2)
//    public void TestCaseThree() throws InterruptedException {
//        //First part
//
//        List<WebElement> listOfPrices = driver.findElements(By.xpath("//span[@class='price product-price']"));
//        double firstItemPrice = Double.parseDouble(listOfPrices.get(1).getText().substring(1));
//
//        clickElementFromWomenList("Summer Dresses", driver, hoverOver);
//        Thread.sleep(2000);
//
//        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//a[@class='button ajax_add_to_cart_button btn btn-default']"));
//
//        WebElement firstItem = driver.findElement(By.xpath("//div[@class='product-image-container']"));
//        WebElement firstItemAddToCartButton = addToCartButtons.get(0);
//        WebElement successfullyAdded = driver.findElement(By.xpath("//h2[contains(.,'Product successfully added to your shopping')]"));
//
//        scrollingToElement(firstItem, driver);
//        hoverOver.moveToElement(firstItem).perform();
//        hoverOver.moveToElement(firstItemAddToCartButton).click().perform();
//
//        Thread.sleep(2000);
//        Assert.assertTrue(successfullyAdded.getText().contains("Product successfully added to your shopping cart"), "The product was not added successfully");
//        Assert.assertEquals(firstItemPrice, 16.51, "Pop-up window price does not match default window price");
//
//        String actual = driver.findElement(By.xpath("//span[contains(.,'There is 1 item in your cart')]")).getText();
//        String expected = "There is 1 item in your cart.";
//        Assert.assertEquals(actual, expected, "Invalid message");
//
//        WebElement continueButton = driver.findElement(By.xpath("//span[@title='Continue shopping']"));
//        continueButton.click();
//
//        actual = driver.findElement(By.xpath("//a[@title='View my shopping cart']")).getText();
//        expected = "Cart 1 Product";
//        Assert.assertEquals(actual, expected, "cart validation failed");
//
//        //second part
//
//        WebElement secondItemAddToCartButton = addToCartButtons.get(1);
//        secondItemAddToCartButton.click();
//
//        Thread.sleep(2000);
//        actual = driver.findElement(By.xpath("//div[@class='layer_cart_cart col-xs-12 col-md-6']/h2")).getText();
//        expected = "There are 2 items in your cart.";
//        Assert.assertEquals(actual, expected, "cart validation failed");
//
//        actual = driver.findElement(By.xpath("//span[@class='ajax_block_cart_total']")).getText().substring(1);
//        Assert.assertEquals(Double.parseDouble(actual), 61.48, "cart total validation failed");
//
//        WebElement checkout = driver.findElement(By.xpath("//a[@title='Proceed to checkout']"));
//        checkout.click();
//
//        WebElement secondElementDelete = driver.findElement(By.id("6_31_0_0"));
//        secondElementDelete.click();
//
//        WebElement increaseQuantityOfFirstItem = driver.findElement(By.id("cart_quantity_up_5_19_0_0"));
//        increaseQuantityOfFirstItem.click();
//        Thread.sleep(1000);
//        increaseQuantityOfFirstItem.click();
//        Thread.sleep(1000);
//
//        String productPrice = driver.findElement(By.id("total_product")).getText().substring(1);
//        Assert.assertEquals(Double.parseDouble(productPrice), 86.94, "product price was not updated");
//
//        String totalPrice = driver.findElement(By.id("total_price")).getText().substring(1);
//        Assert.assertEquals(Double.parseDouble(totalPrice), (Double.parseDouble(productPrice) + 2), "the total price was not updated");
//    }
//
//    @Test(priority = 3)
//    public void TestCaseFour() throws InterruptedException {
//        clickElementFromWomenList("Summer Dresses", driver, hoverOver);
//        Thread.sleep(2000);
//
//        String actual = driver.findElement(By.xpath("//div[@class='product-count']")).getText();
//        String expected = "Showing 1 - 3 of 3 items";
//        Assert.assertEquals(actual, expected, "invalidate message \"Showing 1 - 3 of 3 items\"");
//
//        WebElement slider = driver.findElement(By.xpath("//a[@class='ui-slider-handle ui-state-default ui-corner-all']"));
//
//        WebElement range = driver.findElement(By.xpath("//span[@id='layered_price_range']"));
//        double actualRange = Double.parseDouble(range.getText().substring(1,range.getText().indexOf(" ")));
//        double expectedRange = 28.00;
//
//        int i = 0;
//        scrollingToElement(slider,driver);
//        while (actualRange != expectedRange) {
//            hoverOver.dragAndDropBy(slider, i, 0).perform();
//            actualRange = Double.parseDouble(range.getText().substring(1,range.getText().indexOf(" ")));
//            if (actualRange == 28) {
//                break;
//            }
//            i+=30;////?????
//        }
//        Assert.assertEquals(actualRange, expectedRange, "slider is not working properly");
//    }

    public static void ScreenShot(WebDriver driver) throws IOException {
        File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenShot, new File("Desktop/new.png"));
    }
}
