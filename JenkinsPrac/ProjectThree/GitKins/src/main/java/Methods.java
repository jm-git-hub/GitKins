import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class Methods extends Item {

    //This method clicks on the desired choice from the women's list
    //It takes the element name as a String, the WebDriver object name, and the name of the Actions object name
    public void clickElementFromWomenList(String womenListItem, WebDriver driver, Actions hoverOver) throws InterruptedException {
        WebElement womenButton = driver.findElement(By.xpath("//a[@title='Women']"));
        hoverOver.moveToElement(womenButton).perform();
        Thread.sleep(2000);
        List<WebElement> womenList = driver.findElements(By.xpath("//ul[@class='submenu-container clearfix first-in-line-xs']/li/ul/li"));
        for (WebElement webElement : womenList)
            if (webElement.getText().equalsIgnoreCase(womenListItem)) {
                webElement.click();
                break;
            }
    }



    public void scrollingToElement(WebElement item, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", item);

    }

    public int lowestPriceIndex(List<Item> wholeProduct) {
        double lowestPrice = Double.MAX_VALUE;
        double price;
        int indexOfItemWithLowestPrice = 0;
        for (int i = 0; i < wholeProduct.size(); i++) {
            price = wholeProduct.get(i).getProductPrice();
            if (price <= lowestPrice) {
                lowestPrice = price;
                indexOfItemWithLowestPrice = i;
            }
        }
        return indexOfItemWithLowestPrice;
    }

    public int highestPriceIndex(List<Item> wholeProduct) {
        double highestPrice = Double.MIN_VALUE;
        double price;
        int indexOfItemWithLowestPrice = 0;
        for (int i = 0; i < wholeProduct.size(); i++) {
            price = wholeProduct.get(i).getProductPrice();
            if (price >= highestPrice) {
                highestPrice = price;
                indexOfItemWithLowestPrice = i;
            }
        }
        return indexOfItemWithLowestPrice;
    }

    public void printItemBlock(Item itemIndex) {
        System.out.println("--------------------------");
        System.out.println("Product name: " + itemIndex.getProductName());
        System.out.println("Product price: " + itemIndex.getProductPrice());
        System.out.println("Product discount rate: " + itemIndex.getProductDiscount() + "%");
        System.out.println("Product price before discount: " + itemIndex.getProductOldPrice());
        System.out.println("--------------------------");
    }

    /*
    This Method organizes a List of WebElements fields and stores them in a an ArrayList of type Item
     */
    public void storeItemBlockInList(List<WebElement> priceList, ArrayList<Item> wholeProduct) {
        for (WebElement webElement : priceList) {
            String product = webElement.getText();

            double productPrice;
            double productOldPrice;
            double productDiscount;

            if (!product.isEmpty()) {
                String productName_firstPart = product.substring(0, product.indexOf("\n"));
                String product_secondPart = product.substring(product.indexOf("$"));

                int lengthOfTheSecondPartOfTheString = product_secondPart.length();

                if (product_secondPart.startsWith("$") && lengthOfTheSecondPartOfTheString <= 6) {
                    productPrice = Double.parseDouble(product_secondPart.substring(1));
                    productOldPrice = 0;
                    productDiscount = 0;
                } else if (product_secondPart.startsWith("$") && lengthOfTheSecondPartOfTheString > 6) {
                    productPrice = Double.parseDouble(product_secondPart.substring(1, product_secondPart.indexOf(" ")));
                    productOldPrice = Double.parseDouble(product_secondPart.substring(8, product_secondPart.indexOf("-")));
                    productDiscount = Integer.parseInt(product_secondPart.substring(product_secondPart.indexOf("-") + 1, product_secondPart.length() - 1));
                } else {
                    continue;
                }
                wholeProduct.add(singleItem(productName_firstPart, productPrice, productOldPrice, productDiscount));
            }
        }
    }

}
