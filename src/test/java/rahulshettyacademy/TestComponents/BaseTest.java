package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {
		
		//properties class
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream("/Users/kawsarrahman/eclipse-workspace/SeleniumFrameworkDesign/src/main/java/rahulshettyacademy/resources/GlobalData.properties");
		pro.load(fis);
		String browserName = pro.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome")) {
		WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		
		}
		else if (browserName.equalsIgnoreCase("firefox")) {
			
			//fixfox
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver","file location");
			 driver = new EdgeDriver();
			
			
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		//read json to string
		
		
		String jsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
		return data;
		
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
		
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";

	}
	
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		
		driver=initializeDriver();
		 landingPage = new LandingPage(driver);
		
		landingPage.goTo();
		return landingPage;
		
	}
	
	
	@AfterMethod(alwaysRun=true)
	public void tearown() {
		
		
		driver.close();
	}
	
	
}
