package jp.co.sss.crud.test05;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("05_部署ID検索機能")
public class SearchDeptIdTest {

	private WebDriver webDriver;

	/**
	 * テストメソッドを実行する前に実行されるメソッド
	 */
	@BeforeEach
	public void createDriver() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		webDriver = new ChromeDriver(ops);
		// ページの読み込み完了までの最大待機時間の設定
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
	}

	@AfterEach
	public void quitDriver() {
		webDriver.close();
	}

	private void doLogin() {

		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		WebElement loginIdElement = webDriver.findElement(By.name("empId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("2");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

	}

	@Test
	@Order(1)
	public void 正常系_部署検索操作_選択_経理部() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<File>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\05_SearchDeptIdTest\\";

		doLogin();

		// 要素が操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement deptSelectElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("deptId")));

		// 部署を選択
		Select select = new Select(deptSelectElement);
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 部署検索を押下し送信
		webDriver.findElement(By.cssSelector("aside div:nth-of-type(4) input[type='submit']")).submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// スクリーンショット出力
		int count = 0;
		try {
			for (File file : tempFileList) {
				count++;
				Files.move(file, new File(screenshotPath + new Object() {
				}.getClass().getEnclosingMethod().getName() + "_" + count + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empNameOfTanaka = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(2)"));
		WebElement empNameOfWatanabe = webDriver
				.findElement(By.cssSelector("table tr:nth-of-type(3) td:nth-of-type(2)"));

		// 検証
		assertEquals("田中二郎", empNameOfTanaka.getText());
		assertEquals("渡辺花子", empNameOfWatanabe.getText());

	}

	@Test
	@Order(2)
	public void 正常系_部署検索操作_選択_総務部() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<File>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\05_SearchDeptIdTest\\";

		doLogin();

		// 要素が操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement deptSelectElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("deptId")));

		// 部署を選択
		Select select = new Select(deptSelectElement);
		select.selectByValue("3");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		webDriver.findElement(By.cssSelector("aside div:nth-of-type(4) input[type='submit']")).submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// スクリーンショット出力
		int count = 0;
		try {
			for (File file : tempFileList) {
				count++;
				Files.move(file, new File(screenshotPath + new Object() {
				}.getClass().getEnclosingMethod().getName() + "_" + count + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement msgOfNoneEmployee = webDriver.findElement(By.className("back_to_top_message"));

		// 検証
		assertEquals("該当する社員は存在しません。", msgOfNoneEmployee.getText());

	}

}
