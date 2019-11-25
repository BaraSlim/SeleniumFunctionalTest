import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Maltsev A.A. on 25.11
 */

public class InsuranceTest {
    WebDriver driver;
    String baseURL;

    @Before     //метод выполнения перед каждым тестом
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");  //Установка системных переменных пути к драйверу
        baseURL = "https://www.rgs.ru/";    //инициировать адрес целевого сайта
        driver = new ChromeDriver();        //инициировать объект драйвера
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);       //неявное ожидание 30 сек. загрузка страницы
        driver.manage().window().maximize();        //развернуть на полный экран
        driver.get(baseURL);    //переход на указанный адрес
    }

    @Test
    public void testInsurance() {
        driver.findElement(By.xpath("//a[contains (text(), 'Меню') and contains (@class, 'hidden-xs')]")).click();          //провалиться в меню
        driver.findElement(By.xpath("//div[contains(@class, 'rgs-main-menu-category')]//a[contains(text(),'ДМС')]")).click();       //выбрать пункт ДМС из меню
        WebElement sendTicketBtn = driver.findElement(By.xpath("//*[contains(text(),'Отправить заявку')][contains (@class, 'btn')]"));
        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);  //явное ожидание появления кнопки
        wait.until(ExpectedConditions.visibilityOf(sendTicketBtn)).click();     //оджидать появления кнопки отправки заявки
        WebElement ticketsForm = driver.findElement(By.xpath("//*[contains(text(),'Заявка на добровольное медицинское страхование')]"));
        wait.until(ExpectedConditions.visibilityOf(ticketsForm));   //ожидает появления формы заполнения заявки
        Assert.assertEquals("Значение title не соответствует ожидаемому", "Заявка на добровольное медицинское страхование", ticketsForm.getText());

        fillField(By.name("LastName"), "Pupkin");       //
        fillField(By.name("FirstName"), "Petro");       //Использование метода для заполнения поля формы
        fillField(By.name("MiddleName"), "Pupkin");     //

        new Select(driver.findElement(By.xpath("//*[contains (@name, 'Region')]"))).selectByVisibleText("Чукотский АО");     //выбор элемента из выпадающего списка
        fillField(By.name("Comment"), "Hello! This is autotest");    //заполнить поле комментарий
        fillField(By.name("Email"), "wrongAdress");       //заполнить поле почты неверным значением
        driver.findElement(By.xpath("//input[contains (@class, 'checkbox')]")).click();     //чек бокс подтверждения обработки данных
        driver.findElement(By.id("button-m")).click();         //нажать кнопку Отправить

        Assert.assertEquals("Ошибка при заполнении формы не", "Введите адрес электронной почты",
                driver.findElement(By.xpath("//*[text()='Эл. почта']/..//span[@class = 'validation-error-text']")).getText());  //проверка текстовки ошибки
        Assert.assertEquals("Значение имени введено правильно", "Petro",
                driver.findElement(By.name("FirstName")).getAttribute("value"));  //получаем значение value от найденного теста
    }

    public void fillField(By xPath, String value) {     //метод для поиска по name и заполнения поля по value
        driver.findElement(xPath).clear();
        driver.findElement(xPath).sendKeys(value);
    }

    @After      //метод выполнения после каждого теста
    public void afterTest() {
        driver.quit();
    }
}
