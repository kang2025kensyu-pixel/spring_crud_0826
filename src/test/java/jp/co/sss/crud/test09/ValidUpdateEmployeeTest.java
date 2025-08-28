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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("09_入力チェック機能_社員変更")
public class ValidUpdateEmployeeTest {

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
	public void 異常系_社員変更操作_パスワード_空文字入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報入力
		// パスワードは空文字に指定
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		birthdayElement.sendKeys("1986/10/12");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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

		// エラー文字列定義
		String errMsgNullOfEmpPass = "パスワードを入力してください。";
		String errMsgDigitsOverOfEmpPass = "パスワードは16文字以内で入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgNullOfEmpPass), "エラーメッセージが違います：" + errElement.getText());
		assertTrue(errElement.getText().contains(errMsgDigitsOverOfEmpPass), "エラーメッセージが違います：" + errElement.getText());
	}

	@Test
	@Order(2)
	public void 異常系_社員変更操作_パスワード_桁数オーバー入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報入力
		// パスワードは文字数超過で指定
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("12345678901234567");
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		birthdayElement.sendKeys("1986/10/12");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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

		// エラー文字列定義
		String errMsgDigitsOverOfEmpPass = "パスワードは16文字以内で入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgDigitsOverOfEmpPass), "エラーメッセージが違います：" + errElement.getText());
	}

	@Test
	@Order(3)
	public void 異常系_社員変更操作_社員名_空文字入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");
		// 社員名は空文字で指定
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		birthdayElement.sendKeys("1986/10/12");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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

		// エラー文字列定義
		String errMsgNullOfEmpName = "社員名を入力してください。";
		String errMsgDigitsOverOfEmpName = "社員名は30文字以内で入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgNullOfEmpName), "エラーメッセージが違います：" + errElement.getText());
		assertTrue(errElement.getText().contains(errMsgDigitsOverOfEmpName), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(4)
	public void 異常系_社員変更操作_社員名_桁数オーバー入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");
		// 社員名は桁数超過で指定
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほま");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		birthdayElement.sendKeys("1986/10/12");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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

		// エラー文字列定義
		String errMsgDigitsOverOfEmpName = "社員名は30文字以内で入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgDigitsOverOfEmpName), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(5)
	public void 異常系_社員変更操作_住所_空文字入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		// 住所は空文字で指定
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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

		// エラー文字列定義
		String errMsgNullOfAddress = "住所を入力してください。";
		String errMsgDigitsOverOfAddress = "住所は60文字以内で入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgNullOfAddress), "エラーメッセージが違います：" + errElement.getText());
		assertTrue(errElement.getText().contains(errMsgDigitsOverOfAddress), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(6)
	public void 異常系_社員変更操作_住所_桁数オーバー入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		// 住所は桁数超過で指定
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほあいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほま");
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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
		// エラー文字列定義
		String errMsgDigitsOverOfAddress = "住所は60文字以内で入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgDigitsOverOfAddress), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(7)
	public void 異常系_社員変更操作_生年月日_空文字入力メッセージ出力() {

		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報を入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");
		// 生年月日は空文字で指定
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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
		// エラー文字列定義
		String errMsgNullOfBirthday = "生年月日を入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgNullOfBirthday), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(8)
	public void 異常系_社員変更操作_生年月日_非対応日付形式入力メッセージ出力() {
		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報を入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");
		// 存在しない日付を指定
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		birthdayElement.sendKeys("1986-10-02");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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

		// エラー文字列定義
		String errMsgNotExistOfBirthday = "正しい日付を入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgNotExistOfBirthday), "エラーメッセージが違います：" + errElement.getText());

	}

	@Test
	@Order(9)
	public void 異常系_社員変更操作_生年月日_非存在日付入力メッセージ出力() {
		// スクリーンショットのリスト
		ArrayList<File> tempFileList = new ArrayList<>();
		// スクショ保存パス
		String screenshotPath = "screenshots\\09_ValidUpdateEmployeeTest\\";

		doLogin();

		// 変更ボタンが操作可能になるまでの待機時間
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		WebElement buttonUpdate = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']")));

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		buttonUpdate.submit();

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// 社員情報を入力
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");
		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();
		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");
		// 存在しない日付を指定
		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		birthdayElement.sendKeys("1986/10/32");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();
		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		// スクリーンショット
		tempFileList.add(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE));

		// ボタン押下し送信
		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

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

		// エラー文字列定義
		String errMsgNotExistOfBirthday = "正しい日付を入力してください。";

		// 検証のため要素を取得
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertTrue(errElement.getText().contains(errMsgNotExistOfBirthday), "エラーメッセージが違います：" + errElement.getText());

	}

}
