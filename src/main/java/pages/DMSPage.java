package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by
 */
public class DMSPage extends BasePage {

    @FindBy(xpath = "//*[@class='page-header']/*")
    public WebElement title;

    @FindBy(xpath = "//*[contains(text(),'Отправить заявку')][contains(@class,'btn')]")
    public WebElement sendAppBtn;

    public DMSPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public void waitSendAppClickable(){
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains(@class,'btn')]")))).click();
    }
}
