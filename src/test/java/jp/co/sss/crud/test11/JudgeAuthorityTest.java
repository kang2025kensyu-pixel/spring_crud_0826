package jp.co.sss.crud.test11;

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
@DisplayName("11_権限判定")
public class JudgeAuthorityTest {

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
	public void 正常系_社員一覧表示_一般権限() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<File>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\11_JudgeAuthorityTest\\";

		doLogin();

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

		// 検証のため要素を取得
		WebElement empId = webDriver.findElement(By.cssSelector("table tr:nth-of-type(3) td:nth-of-type(1)"));
		WebElement empName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(3) td:nth-of-type(2)"));
		WebElement departmentName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(3) td:nth-of-type(3)"));

		// 検証
		assertEquals("2", empId.getText());
		assertEquals("田中二郎", empName.getText());
		assertEquals("経理部", departmentName.getText());

	}

	@Test
	@Order(2)
	public void 正常系_権限判定操作_一般権限での登録画面アクセス() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<File>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\11_JudgeAuthorityTest\\";

		doLogin();

		// 一覧画面に遷移するまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.urlToBe("http://localhost:7779/spring_crud/list"));

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

		assertEquals("http://localhost:7779/spring_crud/", webDriver.getCurrentUrl());

	}

	@Test
	@Order(3)
	public void 正常系_社員変更操作_変更完了_一般権限() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<File>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\11_JudgeAuthorityTest\\";

		/*****一般権限ログイン******/
		doLogin();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		/*****社員一覧から入力画面へ*****/

		WebElement updateEmpNameElement = webDriver.findElement(By.cssSelector(".user_info a span"));

		// ボタン押下し送信
		updateEmpNameElement.click();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 検証のため要素を取得
		WebElement articleInputTitle = webDriver.findElement(By.cssSelector("article h3"));
		WebElement inputedEmpNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		WebElement inputedGenderElement = webDriver
				.findElement(By.cssSelector(".update input[name='gender'][value='1']"));
		WebElement inputedAddressElement = webDriver.findElement(By.cssSelector(".update input[name='address']"));
		WebElement inputedBirthdayElement = webDriver.findElement(By.cssSelector(".update input[name='birthday']"));
		WebElement inputedDeptIdElement = webDriver
				.findElement(By.cssSelector(".update select[name='deptId'] option[value='1']"));

		//検証
		assertEquals("社員変更入力画面", articleInputTitle.getText());
		assertTrue(inputedEmpNameElement.getAttribute("value").contains("太郎"));//入力がされているか検証
		assertTrue(inputedGenderElement.isSelected());
		assertEquals("東京都", inputedAddressElement.getAttribute("value"));
		assertEquals("1986/10/12", inputedBirthdayElement.getAttribute("value"));
		assertTrue(inputedDeptIdElement.isSelected());

		/*****社員入力から確認画面へ*****/

		// 社員情報を入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.sendKeys("1111");

		inputedEmpNameElement.clear();
		inputedEmpNameElement.sendKeys("佐藤太郎");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 検証のため要素を取得
		WebElement articleCheckTitle = webDriver.findElement(By.cssSelector("article h3"));
		WebElement checkEmpNameElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(2) .input"));

		// 検証
		assertEquals("社員変更確認画面", articleCheckTitle.getText());
		assertEquals("佐藤太郎", checkEmpNameElement.getText());

		/*****社員確認から完了画面へ*****/

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector(".update .input input[value='変更']")).submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 検証のため要素を取得
		WebElement articleCompleteTitle = webDriver.findElement(By.cssSelector("article h3"));

		//検証
		assertEquals("社員変更完了画面", articleCompleteTitle.getText());

		/*****社員完了から一覧画面へ*****/

		// ボタン押下し送信
		WebElement backToEmpListElement = webDriver.findElement(By.linkText("一覧表示に戻る"));
		backToEmpListElement.click();

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

		// 検証のため要素を取得
		WebElement empId = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(1)"));
		WebElement empName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(2)"));
		WebElement headerEmpName = webDriver.findElement(By.linkText("佐藤太郎"));

		// 検証
		assertEquals("1", empId.getText());
		assertEquals("佐藤太郎", empName.getText());
		assertEquals("佐藤太郎", headerEmpName.getText());

	}

}
