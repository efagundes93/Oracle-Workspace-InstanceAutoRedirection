package br.com.bolt.automatingweb;

import br.com.bolt.automatingweb.model.Workspace;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;
import java.util.List;

/**
 * Faced with the impossibility of accessing the Oracle engine API, to redirect instances, there was a need to give several clicks on the Oracle Business Process Workspace tool.
 * I developed this simple java solution to redirect list of suspended instances of a subprocess to the same new activity.
 * Emiliano Thomas Fagundes - 2018 Gituhub @efagundes93 - emiliano.fagundes@hotmail.com
 */

public class InstanceAutoRedirection {

    public final String driverPath = "C:/softwares/Selenium/";

    public final String workspaceURL = "SET HERE THE HOME URL";
    public final String status = "SET HERE THE STATUS OF THE INSTANCES Eg.: Suspended";
    public final String assined_to = "SET HERE  'ASSIGNED TO' FILTER Eg.: Anyone, at any Role";

    public final String processo = "SET HERE THE SUBPROCESS NAME";
    public final String newActivity = "SET HERE THE NEW ACTIVITY NAME";

    public final String httpProxy ="SET HERE THE HTTP PROXY URL OR IP";
    public final String sslProxy ="SET HERE THE SSL PROXY URL OR IP";
    public final String proxyUser = "SET HERE YOUR PROXY USERNAME";
    public final String proxyPassword = "SET HERE YOUT PROXY PASSWORD";

    public static final String workspaceUser = "SET HERE YOUR WORKSPACE USERNAME";
    public static final String workspacePassword = "SET HERE YOUR WORKSPACE PASSWORD";


    public static WebDriver driver;
    private Workspace workspace = new Workspace();

    public void setUp() {
        workspace.setUrl(workspaceURL);
        System.out.println("launching chrome browser");
        System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(sslProxy);
        proxy.setSocksUsername(proxyUser);
        proxy.setSocksPassword(proxyPassword);
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.PROXY, proxy);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--incognito");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        driver.navigate().to(workspace.getUrl());
    }

    /**
     *
     * The algorithm consists of:
     * 1 - Log in to Oracle Business Workspace
     * 2 - Perform the search of the list of instances that should be redirected
     * 3 - Having the list of instances, will select one by one and forward them to the activity set
     * @param username
     * @param Password
     */
    public void autoRedirectionMethod(String username, String Password) {

        String id_input_usuario = "j_username::content";
        String id_input_senha = "j_password::content";
        String id_btn_login = "loginButton";
        String id_link_avancado = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:search";
        String id_input_processos = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:processesChoice::content";
        String id_select_assigned = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:assignedTo::content";
        String id_select_status = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:status::content";
        String id_btn_okSearch = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:searchButton";
        String id_alterflow = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id10pc17:0:j_id_id11pc17";
        String id_select_newActivity = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:GAFComp:changesTable:0:j_id_id19pc19::content";
        String id_btn_resume = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:grabResumeButton";
        String id_btn_close = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:conComp:confirmDialog::ok";
        String id_cb_select_all = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:processesChoice::saId";
        String xpath_lbl_assigned = "id(\"j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:assignedTo\")/td[@class=\"x12 x4z\"]/label[1]";
        String id_dropdown = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:processesChoice::drop";
        String name_process_option = "j_id_id6:j_id_id2pc2:j_id_id14pc2:j_id_id30pc2:j_id_id4pc7:j_id_id34pc7:vpfdc1:vpfdc2:j_id_id29pc17:processesChoice";
        String atributo_html_nome_processo = "_adftrueval";

        WebDriverWait wait = new WebDriverWait(driver, 40000);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_input_usuario))));
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_input_senha))));
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_btn_login))));
        WebElement userName_editbox = driver.findElement(By.id(id_input_usuario));
        WebElement password_editbox = driver.findElement(By.id(id_input_senha));
        WebElement submit_button = driver.findElement(By.id(id_btn_login));
        userName_editbox.sendKeys(username);
        password_editbox.sendKeys(Password);
        submit_button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_link_avancado))));
        WebElement advanced = driver.findElement(By.id(id_link_avancado));
        advanced.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_input_processos))));
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_select_assigned))));
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_btn_okSearch))));
        WebElement cb_select_all = driver.findElement(By.id(id_cb_select_all));

        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_dropdown))));
        WebElement drop = driver.findElement(By.id(id_dropdown));
        drop.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated((By.name(name_process_option))));
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_cb_select_all))));
        cb_select_all.click();

        List<WebElement> occurrences = driver.findElements(By.name(name_process_option));
        for (WebElement e : occurrences) {
            if (e.getAttribute(atributo_html_nome_processo).equals(processo)) {
                e.click();
                continue;
            }

        }
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(xpath_lbl_assigned))));
        WebElement lbl_assigned = driver.findElement(By.xpath(xpath_lbl_assigned));
        lbl_assigned.click();
        Select select_assigned = new Select(driver.findElement(By.id(id_select_assigned)));
        select_assigned.selectByVisibleText(assined_to);
        Select select_status = new Select(driver.findElement(By.id(id_select_status)));
        select_status.selectByVisibleText(status);
        WebElement btn_okSearch = driver.findElement(By.id(id_btn_okSearch));
        btn_okSearch.click();

        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> rows = driver.findElements(By.tagName("nobr"));
        boolean hasobjects = driver.findElements(By.tagName("nobr")).size() > 0;
        System.out.println(rows.size());

        while (hasobjects) {
            for (int y = 0; y < rows.size();) {

                try {
                    WebElement instance_row = rows.get(y);
                    if (instance_row.getText().contains("Instance")) {
                        System.out.println("Instância recuperada:  " + instance_row.getText());
                        instance_row.click();
                    } else {
                        y++;
                        continue;
                    }
                } catch (org.openqa.selenium.StaleElementReferenceException ex) {
                    WebElement instance_row = rows.get(y);
                    if (instance_row.getText().contains("Instance")) {
                        instance_row.click();
                    }
                }

                try {
                    Thread.sleep(1000);
                    wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Actions"))));
                    driver.findElement(By.linkText("Actions")).click();
                    driver.findElement(By.linkText("Actions")).sendKeys(Keys.ENTER);
                    driver.findElement(By.linkText("Actions")).sendKeys(Keys.RETURN);
                } catch (InterruptedException ex) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("Actions"))));
                    driver.findElement(By.linkText("Actions")).click();
                    driver.findElement(By.linkText("Actions")).sendKeys(Keys.ENTER);
                    driver.findElement(By.linkText("Actions")).sendKeys(Keys.RETURN);
                    ex.printStackTrace();
                }

                wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_alterflow))));
                WebElement alter_flow = driver.findElement(By.id(id_alterflow));
                alter_flow.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_select_newActivity))));
                wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_btn_resume))));
                Select select_newActivity = new Select(driver.findElement(By.id(id_select_newActivity)));
                select_newActivity.selectByVisibleText(newActivity);
                WebElement btn_resume = driver.findElement(By.id(id_btn_resume));
                btn_resume.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(id_btn_close))));
                WebElement btnClose = driver.findElement(By.id(id_btn_close));
                btnClose.click();

                try {
                    Thread.sleep(3000);
                    rows.clear();
                    rows = driver.findElements(By.tagName("nobr"));
                    y = 0;
                    } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void closeBrowser() {
        driver.close();
    }

    public static void main(String[] args) {

        InstanceAutoRedirection application = new InstanceAutoRedirection();
        application.setUp();
        application.autoRedirectionMethod(workspaceUser, workspacePassword);
        application.closeBrowser();

    }
}