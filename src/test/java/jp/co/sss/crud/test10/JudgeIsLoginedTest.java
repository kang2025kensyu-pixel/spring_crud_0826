package jp.co.sss.crud.test10;

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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("10_ログイン判定")
public class JudgeIsLoginedTest {

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
		loginIdElement.sendKeys("1");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("1111");

		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

	}

	@Test
	@Order(1)
	public void 正常系_ログイン判定操作_ログアウト後の登録画面アクセス() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\10_JudgeIsLoginedTest\\";

		doLogin();

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement logoutWebElement = wait.until(ExpectedConditions.elementToBeClickable(
				By.linkText("ログアウト")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// リンクを押下
		logoutWebElement.click();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 指定したURLにアクセス
		String path = "http://localhost:7779/spring_crud/regist/input";
		webDriver.get(path);

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

		// 検証
		assertEquals("http://localhost:7779/spring_crud/", webDriver.getCurrentUrl());

	}

	@Test
	@Order(2)
	public void 正常系_ログイン判定操作_ブラウザ終了後の登録画面アクセス() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\10_JudgeIsLoginedTest\\";

		doLogin();

		// ブラウザを閉じる代わりにページをクリアする
		webDriver.manage().deleteAllCookies();
		webDriver.navigate().refresh();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 指定したURLにアクセス
		String path = "http://localhost:7779/spring_crud/regist/input";
		webDriver.get(path);

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

		// 検証
		assertEquals("http://localhost:7779/spring_crud/", webDriver.getCurrentUrl());

	}

}
