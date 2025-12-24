package br.com.gerenciamento.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioAceitacaoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAcessarPaginaLogin() {
        driver.get("http://localhost:8080");

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));
        assertEquals("http://localhost:8080/", driver.getCurrentUrl());

        WebElement userField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user")));
        WebElement senhaField = driver.findElement(By.id("senha"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));

        assertTrue(userField.isDisplayed());
        assertTrue(senhaField.isDisplayed());
        assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void testCadastrarUsuario() {
        driver.get("http://localhost:8080");

        WebElement cadastroLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Clique aqui para se cadastrar")));
        cadastroLink.click();

        wait.until(ExpectedConditions.urlContains("/cadastro"));

        long timestamp = System.currentTimeMillis();
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        emailField.sendKeys("teste" + timestamp + "@email.com");

        WebElement userField = driver.findElement(By.id("user"));
        userField.sendKeys("user" + timestamp);

        WebElement senhaField = driver.findElement(By.id("senha"));
        senhaField.sendKeys("senha123");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));
        assertEquals("http://localhost:8080/", driver.getCurrentUrl());
    }

    @Test
    public void testAcessarPaginaCadastro() {
        driver.get("http://localhost:8080");

        WebElement cadastroLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Clique aqui para se cadastrar")));
        cadastroLink.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/cadastro"));
        assertEquals("http://localhost:8080/cadastro", driver.getCurrentUrl());

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        assertTrue(emailField.isDisplayed());
    }
}

