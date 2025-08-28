package jp.co.sss.crud.test09;

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

import com.google.common.io.Files;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("09_入力チェック機能_ログイン")
public class ValidLoginTest {

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

	@Test
	@Order(1)
	public void 異常系_ログイン操作_社員ID_空文字入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidLoginTest\\";

		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 社員ID入力欄をクリア
		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

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
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// エラー文字列定義
		String errMsgInputNUllOfEmpId = "社員IDを入力してください。";

		// 検証
		assertTrue(errElement.getText().contains(errMsgInputNUllOfEmpId), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(2)
	public void 異常系_ログイン操作_社員ID_桁数オーバー入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidLoginTest\\";

		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 社員IDの桁数超過で入力
		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("111111");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

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
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// エラー文字列定義
		String errMsgDigitsOverOfEmpId = "社員IDは99999までの整数値で入力してください。";

		// 検証
		assertTrue(errElement.getText().contains(errMsgDigitsOverOfEmpId), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(3)
	public void 異常系_ログイン操作_社員ID_文字種エラー1入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidLoginTest\\";

		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 社員IDの文字種を規定外に設定
		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("abc");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

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
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// エラー文字列定義
		String errMsgCharTypeOfEmpId = "社員IDは整数値で入力してください。";

		// 検証
		assertTrue(errElement.getText().contains(errMsgCharTypeOfEmpId), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(4)
	public void 異常系_ログイン操作_社員ID_文字種エラー2入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidLoginTest\\";

		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 社員IDの文字種を規定外に指定
		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("12.3");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

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
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// エラー文字列定義
		String errMsgCharTypeOfEmpId = "社員IDは整数値で入力してください。";

		// 検証
		assertTrue(errElement.getText().contains(errMsgCharTypeOfEmpId), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(5)
	public void 異常系_ログイン操作_パスワード_空文字入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidLoginTest\\";

		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("1");

		// パスワードを空文字に指定
		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

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
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// エラー文字列定義
		String errMsgNullOfEmpPass = "パスワードを入力してください。";

		// 検証
		assertTrue(errElement.getText().contains(errMsgNullOfEmpPass), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(6)
	public void 異常系_ログイン操作_ログインエラーメッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidLoginTest\\";

		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 存在しない社員の情報で入力指定
		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("5");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("5");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

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
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// エラー文字列定義
		String errMsgIllegalEmpIdOrEmpPass = "社員ID、またはパスワードが間違っています。";

		// 検証
		assertTrue(errElement.getText().contains(errMsgIllegalEmpIdOrEmpPass), "エラーメッセージが違います：" + errElement.getText());

	}

}
