package jp.co.sss.crud.test08;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
@DisplayName("08s_社員削除機能")
public class DeleteEmployeeTest {

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
	public void 正常系_社員削除操作_削除完了_管理者権限() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\08_DeleteEmployeeTest\\";

		doLogin();

		/*****社員一覧から確認画面へ*****/
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(9) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 検証のため要素を取得
		WebElement articleCheckTitle = webDriver.findElement(By.cssSelector("article h3"));
		WebElement checkEmpNameElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(2) .input"));
		WebElement checkGenderElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(3) .input"));
		WebElement checkAddressElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(4) .input"));
		WebElement checkBirthdayElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(5) .input"));
		WebElement checkAuthorityElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(6) .input"));
		WebElement checkDeptNameElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(7) .input"));

		// 検証
		assertEquals("社員削除確認画面", articleCheckTitle.getText());
		assertEquals("渡辺花子", checkEmpNameElement.getText());
		assertEquals("女性", checkGenderElement.getText());
		assertEquals("大阪府", checkAddressElement.getText());
		assertEquals("1988/04/23", checkBirthdayElement.getText());
		assertEquals("管理者", checkAuthorityElement.getText());
		assertEquals("経理部", checkDeptNameElement.getText());

		/*****社員削除確認から完了画面へ*****/
		webDriver.findElement(By.cssSelector(".update .input input[value='削除']")).submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 検証のため要素を取得
		WebElement articleCompleteTitle = webDriver.findElement(By.cssSelector("article h3"));

		// 検証
		assertEquals("社員削除完了画面", articleCompleteTitle.getText());

		/*****社員完了から一覧画面へ*****/

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

		List<WebElement> records = webDriver.findElements(By.cssSelector("table tr"));

		StringBuilder stringBuilder = new StringBuilder();

		for (WebElement element : records) {
			stringBuilder.append(element.getText());
		}
		// 検証
		assertTrue(!stringBuilder.toString().contains("渡辺花子"), "渡辺花子が削除出来ていません：" + stringBuilder.toString());//存在しなければtrue

	}

	@Test
	@Order(2)
	public void 正常系_社員削除操作_確認画面_戻るボタンを押下する() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\08_DeleteEmployeeTest\\";

		doLogin();

		/*****社員一覧から確認画面へ*****/

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(9) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector(".update .input input[value='戻る']")).submit();

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
		WebElement articleCompleteTitle = webDriver.findElement(By.cssSelector("article h3"));

		//検証
		assertEquals("社員一覧画面", articleCompleteTitle.getText());

	}

}
