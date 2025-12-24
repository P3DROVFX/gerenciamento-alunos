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

public class AlunoAceitacaoTest {

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
    public void testAcessarPaginaCadastro() {
        driver.get("http://localhost:8080");

        WebElement cadastroLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Clique aqui para se cadastrar")));
        cadastroLink.click();

        wait.until(ExpectedConditions.urlContains("/cadastro"));
        assertTrue(driver.getCurrentUrl().contains("/cadastro"));

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        WebElement userField = driver.findElement(By.id("user"));
        WebElement senhaField = driver.findElement(By.id("senha"));

        assertTrue(emailField.isDisplayed());
        assertTrue(userField.isDisplayed());
        assertTrue(senhaField.isDisplayed());
    }

    @Test
    public void testCadastrarUsuario() {
        driver.get("http://localhost:8080");

        WebElement cadastroLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Clique aqui para se cadastrar")));
        cadastroLink.click();

        wait.until(ExpectedConditions.urlContains("/cadastro"));

        long timestamp = System.currentTimeMillis();
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        emailField.sendKeys("aluno" + timestamp + "@teste.com");

        WebElement userField = driver.findElement(By.id("user"));
        userField.sendKeys("aluno" + timestamp);

        WebElement senhaField = driver.findElement(By.id("senha"));
        senhaField.sendKeys("senha123");

        assertEquals("aluno" + timestamp + "@teste.com", emailField.getAttribute("value"));
        assertEquals("aluno" + timestamp, userField.getAttribute("value"));
        assertEquals("senha123", senhaField.getAttribute("value"));

        WebElement cadastrarButton = driver.findElement(By.cssSelector("button[type='submit']"));
        cadastrarButton.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));
        assertEquals("http://localhost:8080/", driver.getCurrentUrl());
    }
}

