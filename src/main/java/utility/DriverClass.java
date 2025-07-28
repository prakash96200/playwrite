package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

public class DriverClass {
	public static Locator activeElement;
	static ResultClass objRC = new ResultClass();
	static GenericMethods objGM = new GenericMethods();
	public static String data ="";
	public static ExtentReports reports;
	public static ThreadLocal<Boolean> flag3 = new InheritableThreadLocal<>();
	/*This method allows us to identify the object and perform desired action on objects
	 * Objects are identified by xpath/id/name
	 * Operations performed like click, enter, select and get values
	 */
	//, ExtentTest logger
	public static void objIdentification(Page page, Map<String, List<String>> orDict, ArrayList<String> al_dataSheet, int tcRow, String objName) throws Throwable {
	    // Get locator value from OR dictionary
	    values.set(orDict.get(objName));

	    if (values.get() == null || values.get().isEmpty()) {
//	        logger.log(Status.FAIL, "Object identifier not found: " + objName);
	        return;
	    }

	    // Identify action type (txt, txg, txe, lst, pwd)
	    String actionType = values.get().get(0).substring(values.get().get(0).length() - 3);
	    if (actionType.equals("txt") || actionType.equals("txg") || actionType.equals("txe") || actionType.equals("lst") || actionType.equals("pwd")) {
	        GenericMethods objGM = new GenericMethods();
	        readData.set(objGM.dataValuereading(al_dataSheet.get(0), tcRow, al_dataSheet.indexOf(objName)).trim());
	    }
	      System.out.println("get "+getData());
	    if (getData() != null) {
			try {
			switch (lstStringValue().get(0).substring(0, 1)) {
			case "r":
				try {
					flag3.set(false);
					 if (!lstStringValue().get(0).substring(lstStringValue().get(0).length() - 3).contains("get")) {
	                     Locator element = page.locator("role=" + lstStringValue().get(1));
	                     element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	                     activeElement = element;
					}
						flag3.set(true);
						break;
					}catch(Exception e) {
						objRC.logFail(ResultClass.exTest, "Role Object not found : " + e.getMessage());
					}
				break;
			case "x":
				try {
				flag3.set(false);
				 if (!lstStringValue().get(0).substring(lstStringValue().get(0).length() - 3).contains("get")) {
                     Locator element = page.locator("xpath=" + lstStringValue().get(1));
                     element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                     activeElement = element;
				}
					flag3.set(true);
					break;
				}catch(Exception e) {
//					objRC.logFail(ResultClass.exTest, "Object not found : " + e.getMessage());
				}
			case "i":
			    try {
			    	flag3.set(false);
			        Locator element = page.locator("#" + lstStringValue().get(1)); // ID selector
			        element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			        activeElement = element;
			        flag3.set(true);
			    } catch (Exception e) {
//			        logger.log(Status.FAIL, "Element not found by ID: " + e.getMessage());
			        flag3.set(false);
			    }
			    break;

			case "n":
			    try {
			    	flag3.set(false);
			        Locator element = page.locator("[name='" + lstStringValue().get(1) + "']"); // Name selector
			        element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			        activeElement = element;
			        flag3.set(true);
			    } catch (Exception e) {
//			        logger.log(Status.FAIL, "Element not found by Name: " + e.getMessage());
			        flag3.set(false);
			    }
			    break;

			}
			/*Performs actions like click, enter and selects item from the drop down list
			 * Modified by Dimpal - 03/09/2022
			 */
			switch (lstStringValue().get(0).substring(lstStringValue().get(0).length()-3)) {
			case "clk":
				try {
					activeElement.click();
//					objRC.logPass(appFunctions.Baseclass.getLogger(), "Clicked on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
					break;
				}
			case "jsc":
				try {
					activeElement.click();
//			        logger.log(Status.PASS, "Clicked on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
					break;
				}
			case "skl":
				try {
//			        Screen sikuli = new Screen();
			        String inputFilePath = data;
//			        Pattern closeGrid = new Pattern(inputFilePath);
//			        sikuli.wait(closeGrid, 2); // Wait for 2 seconds to find the image
//			        sikuli.click(closeGrid);
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, "Sikuli action failed: " + e.getMessage());
			    }
			    break;
			    
			case "txt":
				try {
					 activeElement.click();
					    activeElement.fill(getData());
					    activeElement.press("Tab");
//					    logger.log(Status.PASS, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
					    flag3.set(true);
				} catch (Exception e) {
					flag3.set(false);
//				    logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
				break;
			}
			case "txg":
				try {
			        for (char c : getData().toCharArray()) {
			            activeElement.press(String.valueOf(c));
			        }
//			        logger.log(Status.PASS, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
			    break;
				}
			case "pwd":
			    try {
			        activeElement.click();
			        activeElement.fill(getData());
			        activeElement.press("Tab");
//			        logger.log(Status.PASS, "******** entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());			  
			    break;
			    }
			case "txe":
			    try {
			        activeElement.click();
			        activeElement.fill(getData());
			        activeElement.press("Enter");
//			        logger.log(Status.PASS, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());			   
			    break;
			    }
			case "lst":
			    try {
			        activeElement.selectOption(new SelectOption().setValue(getData()));
//			        logger.log(Status.PASS, getData() + " is selected from " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " - Failed to identify the object: " + e.getMessage());
			    }
			    break;

			case "itm":
			    try {
			        @SuppressWarnings("unchecked") // Suppress type-casting warning
			        List<String> options = (List<String>) activeElement.evaluate("el => Array.from(el.options).map(opt => opt.textContent)");
//			        logger.log(Status.INFO, "Dropdown values: " + options);
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified by value: " + e.getMessage());
			    }
			    break;

			case "get":
			    try {
			        if (activeElement != null && activeElement.isVisible()) {
			            String capturedValue = activeElement.textContent().trim();
			            AppConfiguration properties = new AppConfiguration();
			            properties.putPropValues("capturedValue", capturedValue);
//			            logger.log(Status.PASS, "Captured Value: " + capturedValue);
			        } else {
//			            logger.log(Status.FAIL, "Element not found: " + lstStringValue().get(1));
			        }
			    } catch (Exception e) {
//			        logger.log(Status.FAIL, "Error retrieving text: " + e.getMessage());
			    break;
			    }
			case "val":
			    try {
			        if (activeElement != null && activeElement.isVisible()) {
			            String attributeValue = activeElement.getAttribute("value");
			            if (attributeValue != null) {
			                AppConfiguration properties = new AppConfiguration();
			                properties.putPropValues("capturedValue", attributeValue.trim());
//			                logger.log(Status.PASS, "Captured Attribute Value: " + attributeValue.trim());
			            } else {
//			                logger.log(Status.FAIL, "Attribute 'value' not found for element: " + lstStringValue().get(1));
			            }
			        } else {
//			            logger.log(Status.FAIL, "Element not found: " + lstStringValue().get(1));
			        }
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, "Error retrieving attribute value: " + e.getMessage());
			    }
			    break;
			  }
			}
			catch (PlaywrightException e) {
//			    logger.log(Status.FAIL, "Exception is thrown: " + e.getMessage());
			    flag3.set(false);
//			    logger.log(Status.INFO, "Execution not successfully completed");
			} 
			catch (Exception e) { 
//			    logger.log(Status.FAIL, "Exception is thrown: " + e.getMessage());
			    flag3.set(false);
//			    logger.log(Status.INFO, "Execution not successfully completed");
			}

			finally {
			    if (flag3.get() == false) {
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " - Failed to identify the object");
			        flag3.set(false);
			    } else {
			        flag3.set(true);
			    }
			}
		} 
	}
	
	
	public static void objIdentification(Page page, Map<String, List<String>> orDict, ArrayList<String> al_dataSheet, int tcRow, String objName,ExtentTest logger) throws Throwable {
	    // Get locator value from OR dictionary
	    values.set(orDict.get(objName));

	    if (values.get() == null || values.get().isEmpty()) {
	        logger.log(Status.FAIL, "Object identifier not found: " + objName);
	        return;
	    }

	    // Identify action type (txt, txg, txe, lst, pwd)
	    String actionType = values.get().get(0).substring(values.get().get(0).length() - 3);
	    if (actionType.equals("txt") || actionType.equals("txg") || actionType.equals("txe") || actionType.equals("lst") || actionType.equals("pwd")) {
	        GenericMethods objGM = new GenericMethods();
	        readData.set(objGM.dataValuereading(al_dataSheet.get(0), tcRow, al_dataSheet.indexOf(objName)).trim());
	    }
	      System.out.println("get "+getData());
	    if (getData() != null) {
			try {
			switch (lstStringValue().get(0).substring(0, 1)) {
			case "x":
				try {
				flag3.set(false);
				Thread.sleep(100);
				 if (!lstStringValue().get(0).substring(lstStringValue().get(0).length() - 3).contains("get")) {
                     Locator element = page.locator(lstStringValue().get(1));
                     Thread.sleep(100);
                     element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                     activeElement = element;
				}
					flag3.set(true);
//					objRC.logPass(ResultClass.exTest, actionType);
					break;
				}catch(Exception e) {
					System.out.println("failed element "+activeElement);
					objRC.logFail(ResultClass.exTest, "Object not found : " + e.getStackTrace(),page);
				}
				break;
			case "i":
			    try {
			    	flag3.set(false);
			        Locator element = page.locator("#" + lstStringValue().get(1)); // ID selector
			        element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			        activeElement = element;
			        flag3.set(true);
			    } catch (Exception e) {
			        logger.log(Status.FAIL, "Element not found by ID: " + e.getMessage());
			        flag3.set(false);
			    }
			    break;

			case "n":
			    try {
			    	flag3.set(false);
			        Locator element = page.locator("[name='" + lstStringValue().get(1) + "']"); // Name selector
			        element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			        activeElement = element;
			        flag3.set(true);
			    } catch (Exception e) {
			        logger.log(Status.FAIL, "Element not found by Name: " + e.getMessage());
			        flag3.set(false);
			    }
			    break;

			}
			/*Performs actions like click, enter and selects item from the drop down list
			 * Modified by Dimpal - 03/09/2022
			 */
			switch (lstStringValue().get(0).substring(lstStringValue().get(0).length()-3)) {
			case "clk":
				try {
					activeElement.click();
					objRC.logPass(ResultClass.exTest, "Clicked on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
			        objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
					break;
				}
			case "jsc":
				try {
					activeElement.click();
					System.out.println("report ***** "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        objRC.logPass(ResultClass.exTest, "Clicked on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
			        objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage(),page);
					break;
				}
			case "skl":
				try {
//			        Screen sikuli = new Screen();
			        String inputFilePath = data;
//			        Pattern closeGrid = new Pattern(inputFilePath);
//			        sikuli.wait(closeGrid, 2); // Wait for 2 seconds to find the image
//			        sikuli.click(closeGrid);
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, "Sikuli action failed: " + e.getMessage());
			    }
			    break;
			    
			case "txt":
				try {
					 activeElement.click();
					    activeElement.fill(getData());
					    activeElement.press("Tab");
					    objRC.logPass(ResultClass.exTest, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
					    flag3.set(true);
					    break;
				} catch (Exception e) {
					flag3.set(false);
				    objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
				break;
			}
			case "txg":
				try {
			        for (char c : getData().toCharArray()) {
			            activeElement.press(String.valueOf(c));
			        }
//			        logger.log(Status.PASS, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			        break;
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
			    break;
				}
			case "pwd":
			    try {
			        activeElement.click();
			        activeElement.fill(getData());
			        activeElement.press("Tab");
			        objRC.logPass(ResultClass.exTest, "******** entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			        break;
			    } catch (Exception e) {
			        flag3.set(false);
			        objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage(),page);			  
			    break;
			    }
			case "txe":
			    try {
			        activeElement.click();
			        activeElement.fill(getData());
			        activeElement.press("Enter");
//			        logger.log(Status.PASS, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());			   
			    break;
			    }
			case "lst":
			    try {
			        activeElement.selectOption(new SelectOption().setValue(getData()));
//			        logger.log(Status.PASS, getData() + " is selected from " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " - Failed to identify the object: " + e.getMessage());
			    }
			    break;

			case "itm":
			    try {
			        @SuppressWarnings("unchecked") // Suppress type-casting warning
			        List<String> options = (List<String>) activeElement.evaluate("el => Array.from(el.options).map(opt => opt.textContent)");
//			        logger.log(Status.INFO, "Dropdown values: " + options);
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified by value: " + e.getMessage());
			    }
			    break;

			case "get":
			    try {
			        if (activeElement != null && activeElement.isVisible()) {
			            String capturedValue = activeElement.textContent().trim();
			            AppConfiguration properties = new AppConfiguration();
			            properties.putPropValues("capturedValue", capturedValue);
//			            logger.log(Status.PASS, "Captured Value: " + capturedValue);
			        } else {
//			            logger.log(Status.FAIL, "Element not found: " + lstStringValue().get(1));
			        }
			    } catch (Exception e) {
//			        logger.log(Status.FAIL, "Error retrieving text: " + e.getMessage());
			    break;
			    }
			case "val":
			    try {
			        if (activeElement != null && activeElement.isVisible()) {
			            String attributeValue = activeElement.getAttribute("value");
			            if (attributeValue != null) {
			                AppConfiguration properties = new AppConfiguration();
			                properties.putPropValues("capturedValue", attributeValue.trim());
//			                logger.log(Status.PASS, "Captured Attribute Value: " + attributeValue.trim());
			            } else {
//			                logger.log(Status.FAIL, "Attribute 'value' not found for element: " + lstStringValue().get(1));
			            }
			        } else {
//			            logger.log(Status.FAIL, "Element not found: " + lstStringValue().get(1));
			        }
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, "Error retrieving attribute value: " + e.getMessage());
			    }
			    break;
			  }
			}
			catch (PlaywrightException e) {
//			    logger.log(Status.FAIL, "Exception is thrown: " + e.getMessage());
			    flag3.set(false);
//			    logger.log(Status.INFO, "Execution not successfully completed");
			} 
			catch (Exception e) { 
//			    logger.log(Status.FAIL, "Exception is thrown: " + e.getMessage());
			    flag3.set(false);
//			    logger.log(Status.INFO, "Execution not successfully completed");
			}

			finally {
			    if (flag3.get() == false) {
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " - Failed to identify the object");
			        flag3.set(false);
			    } else {
			        flag3.set(true);
			    }
			}
		} 
	}
	
	
	
	public static void objIdentification(Page page,FrameLocator frame, Map<String, List<String>> orDict, ArrayList<String> al_dataSheet, int tcRow, String objName,ExtentTest logger) throws Throwable {
	    // Get locator value from OR dictionary
	    values.set(orDict.get(objName));

	    if (values.get() == null || values.get().isEmpty()) {
	        logger.log(Status.FAIL, "Object identifier not found: " + objName);
	        return;
	    }

	    // Identify action type (txt, txg, txe, lst, pwd)
	    String actionType = values.get().get(0).substring(values.get().get(0).length() - 3);
	    if (actionType.equals("txt") || actionType.equals("txg") || actionType.equals("txe") || actionType.equals("lst") || actionType.equals("pwd")) {
	        GenericMethods objGM = new GenericMethods();
	        readData.set(objGM.dataValuereading(al_dataSheet.get(0), tcRow, al_dataSheet.indexOf(objName)).trim());
	    }
	      System.out.println("get "+getData());
	    if (getData() != null) {
			try {
			switch (lstStringValue().get(0).substring(0, 1)) {
			case "x":
				try {
				flag3.set(false);
				Thread.sleep(100);
				 if (!lstStringValue().get(0).substring(lstStringValue().get(0).length() - 3).contains("get")) {
                     Locator element = frame.locator(lstStringValue().get(1));
                     Thread.sleep(100);
                     element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                     activeElement = element;
				}
					flag3.set(true);
//					objRC.logPass(ResultClass.exTest, actionType);
					break;
				}catch(Exception e) {
					System.out.println("failed element "+activeElement);
					objRC.logFail(ResultClass.exTest, "Object not found : " + e.getStackTrace(),page);
				}
				break;
			case "i":
			    try {
			    	flag3.set(false);
			        Locator element = frame.locator("#" + lstStringValue().get(1)); // ID selector
			        element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			        activeElement = element;
			        flag3.set(true);
			    } catch (Exception e) {
			        logger.log(Status.FAIL, "Element not found by ID: " + e.getMessage());
			        flag3.set(false);
			    }
			    break;

			case "n":
			    try {
			    	flag3.set(false);
			        Locator element = frame.locator("[name='" + lstStringValue().get(1) + "']"); // Name selector
			        element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
			        activeElement = element;
			        flag3.set(true);
			    } catch (Exception e) {
			        logger.log(Status.FAIL, "Element not found by Name: " + e.getMessage());
			        flag3.set(false);
			    }
			    break;

			}
			/*Performs actions like click, enter and selects item from the drop down list
			 * Modified by Dimpal - 03/09/2022
			 */
			switch (lstStringValue().get(0).substring(lstStringValue().get(0).length()-3)) {
			case "clk":
				try {
					activeElement.click();
					objRC.logPass(ResultClass.exTest, "Clicked on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
			        objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
					break;
				}
			case "jsc":
				try {
					activeElement.click();
					System.out.println("report ***** "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        objRC.logPass(ResultClass.exTest, "Clicked on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
			        objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage(),page);
					break;
				}
			case "skl":
				try {
//			        Screen sikuli = new Screen();
			        String inputFilePath = data;
//			        Pattern closeGrid = new Pattern(inputFilePath);
//			        sikuli.wait(closeGrid, 2); // Wait for 2 seconds to find the image
//			        sikuli.click(closeGrid);
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, "Sikuli action failed: " + e.getMessage());
			    }
			    break;
			    
			case "txt":
				try {
					 activeElement.click();
					    activeElement.fill(getData());
					    Thread.sleep(10);
					    activeElement.press("Tab");
					    objRC.logPass(ResultClass.exTest, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
					    flag3.set(true);
					    break;
				} catch (Exception e) {
					flag3.set(false);
				    objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
				break;
			}
			case "txg":
				try {
			        for (char c : getData().toCharArray()) {
			            activeElement.press(String.valueOf(c));
			        }
//			        logger.log(Status.PASS, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			        break;
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());
			    break;
				}
			case "pwd":
			    try {
			        activeElement.click();
			        activeElement.fill(getData());
			        activeElement.press("Tab");
			        objRC.logPass(ResultClass.exTest, "******** entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			        break;
			    } catch (Exception e) {
			        flag3.set(false);
			        objRC.logFail(ResultClass.exTest, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage(),page);			  
			    break;
			    }
			case "txe":
			    try {
			        activeElement.click();
			        activeElement.fill(getData());
			        activeElement.press("Enter");
//			        logger.log(Status.PASS, getData() + " entered on " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified: " + e.getMessage());			   
			    break;
			    }
			case "lst":
			    try {
			        activeElement.selectOption(new SelectOption().setValue(getData()));
//			        logger.log(Status.PASS, getData() + " is selected from " + lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4));
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " - Failed to identify the object: " + e.getMessage());
			    }
			    break;

			case "itm":
			    try {
			        @SuppressWarnings("unchecked") // Suppress type-casting warning
			        List<String> options = (List<String>) activeElement.evaluate("el => Array.from(el.options).map(opt => opt.textContent)");
//			        logger.log(Status.INFO, "Dropdown values: " + options);
			        flag3.set(true);
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " is not identified by value: " + e.getMessage());
			    }
			    break;

			case "get":
			    try {
			        if (activeElement != null && activeElement.isVisible()) {
			            String capturedValue = activeElement.textContent().trim();
			            AppConfiguration properties = new AppConfiguration();
			            properties.putPropValues("capturedValue", capturedValue);
//			            logger.log(Status.PASS, "Captured Value: " + capturedValue);
			        } else {
//			            logger.log(Status.FAIL, "Element not found: " + lstStringValue().get(1));
			        }
			    } catch (Exception e) {
//			        logger.log(Status.FAIL, "Error retrieving text: " + e.getMessage());
			    break;
			    }
			case "val":
			    try {
			        if (activeElement != null && activeElement.isVisible()) {
			            String attributeValue = activeElement.getAttribute("value");
			            if (attributeValue != null) {
			                AppConfiguration properties = new AppConfiguration();
			                properties.putPropValues("capturedValue", attributeValue.trim());
//			                logger.log(Status.PASS, "Captured Attribute Value: " + attributeValue.trim());
			            } else {
//			                logger.log(Status.FAIL, "Attribute 'value' not found for element: " + lstStringValue().get(1));
			            }
			        } else {
//			            logger.log(Status.FAIL, "Element not found: " + lstStringValue().get(1));
			        }
			    } catch (Exception e) {
			        flag3.set(false);
//			        logger.log(Status.FAIL, "Error retrieving attribute value: " + e.getMessage());
			    }
			    break;
			case "sel":
				try {
					 if (activeElement != null ) {
						 activeElement.selectOption(getData());
					 }
				}
				catch(Exception e) {
					
				}
			  }
			}
			catch (PlaywrightException e) {
//			    logger.log(Status.FAIL, "Exception is thrown: " + e.getMessage());
			    flag3.set(false);
//			    logger.log(Status.INFO, "Execution not successfully completed");
			} 
			catch (Exception e) { 
//			    logger.log(Status.FAIL, "Exception is thrown: " + e.getMessage());
			    flag3.set(false);
//			    logger.log(Status.INFO, "Execution not successfully completed");
			}

			finally {
			    if (flag3.get() == false) {
//			        logger.log(Status.FAIL, lstStringValue().get(0).substring(2, lstStringValue().get(0).length() - 4) + " - Failed to identify the object");
			        flag3.set(false);
			    } else {
			        flag3.set(true);
			    }
			}
		} 
	}
	
	
	
	
	
	public static void locateElement(Page page, List<String> values, ExtentTest logger) {
		
	}
	
	public static ThreadLocal<String> readData = new InheritableThreadLocal<>();

	public static String getData() {
	    return readData.get();
	}

	public static ThreadLocal<Page> pageThreadLocal = new InheritableThreadLocal<>();

	public static Page getPage() {
	    return pageThreadLocal.get();
	}

	public static ThreadLocal<List<String>> values = new InheritableThreadLocal<>();

	public static List<String> lstStringValue() {
	    return values.get();
	}

	public static Boolean getFlag3() {
	    return flag3.get();
	}


	public static void objIdentification(Page page, FrameLocator iframe, Map<String, List<String>> dictObj_or,
			ArrayList<String> al_createuser, String firstName, String objName, ExtentTest exTest) {
		// TODO Auto-generated method stub
		
	}
}
