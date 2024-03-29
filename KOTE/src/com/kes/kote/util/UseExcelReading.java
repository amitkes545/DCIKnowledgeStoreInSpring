package com.kes.kote.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.kes.kote.domain.UseAccessDomain;
import com.kes.kote.domain.UseMailServerDomain;
import com.kes.kote.domain.UseNotificationDomain;
import com.kes.kote.domain.UseParamDomain;
import com.kes.kote.domain.UseRoleDomain;
import com.kes.kote.domain.UseRoleMapDomain;
import com.kes.kote.domain.UseUserDomain;

public class UseExcelReading {

	public static List<UseParamDomain> getUseParamDetails(HttpSession sess) {

		List<UseParamDomain> result = new ArrayList<UseParamDomain>();

		try {
			SessionUtil util=(SessionUtil) sess.getAttribute("SessionUtil");
			 String operation = util.getConfigLayerbyManualorUpload();
			//String operation = "Upload";
			int spocsRowCount = ExcelSheetUtil.USEPARAMROWCOUNT - 1;
			if (operation.trim().equalsIgnoreCase(ExcelSheetUtil.UPLOAD)) {
				ReadExcelBySheetName excelRead = new ReadExcelBySheetName();
				excelRead.processBySheet(ExcelSheetUtil.USEPARAM,sess);

				ArrayList<HashMap<String, String>> info = util.getSheetDetails();
				if (info != null && info.size() > 0) {
					for (int i = 0; i < info.size(); i++) {
						HashMap<String, String> rowinfo = info.get(i);
						String[] cols = util.getSheetCols();
						UseParamDomain row = new UseParamDomain();
						row.setDescription(rowinfo.get(cols[0]));
						row.setKey(rowinfo.get(cols[1]));
						row.setValue(rowinfo.get(cols[2]));
						row.setType(rowinfo.get(cols[3]));

						result.add(row);
					}
				}

			} else {
				
				String[] paramDesc={"No. of Days before which notification to be sent for Fee reminder","Password Validity Period in Days","No. of Days before which notification to be sent for password change",
						"How many minutes before or after which session can be started","How many minutes before alert message to be sent for starting the session","How many minutes before alert message to be sent for ending the session",
						"How many days before alert message to be sent to submit the assignment by Student","Defines whether the user id should be autogenerated or use the id given by the teaching source",
						"What should be the prefix for user id at the time of auto generation","This decides the next enrollment number to be generated","This parameter contains that needs to prefixed to the auto generated number","This set to 'Yes', if same enrolment number for student doing multiple courses",
						"This defines the number courses that a student can do simoltaneously with one teaching source"};
				
				String[] paramKey={"fee_rem_days","pwd_val_days","pwd_exp_rem_days","session_start_threshold","session_start_alert","session_end_alert","assesment_rem_days","uid_generation","uid_prefix","nxt_enrol_no","enrid_prefix","same_enrolid","no_of_parallel_courses"};
				
				String[] paramType={"Numeric","Numeric","Numeric","Numeric","Numeric","Numeric","Numeric","Text","Text","Numeric","Text","Text","Numeric"};
				
				for (int i = 0; i < spocsRowCount; i++) {
					UseParamDomain row = new UseParamDomain();
					row.setDescription(paramDesc[i]);
					row.setKey(paramKey[i]);
					row.setType(paramType[i]);
					
					result.add(row);
				}
			}
			util.setTotRow(spocsRowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static List<UseMailServerDomain> getUseMailServerDetails(HttpSession sess) {

		List<UseMailServerDomain> result = new ArrayList<UseMailServerDomain>();

		try {
			SessionUtil util=(SessionUtil) sess.getAttribute("SessionUtil");
			 String operation = util.getConfigLayerbyManualorUpload();
			//String operation = "Upload";
			int spocsRowCount = ExcelSheetUtil.MAILSERVERROWCOUNT - 1;
			if (operation.trim().equalsIgnoreCase(ExcelSheetUtil.UPLOAD)) {
				ReadExcelBySheetName excelRead = new ReadExcelBySheetName();
				excelRead.processBySheet(ExcelSheetUtil.MAILSERVER,sess);

				ArrayList<HashMap<String, String>> info = util.getSheetDetails();
				if (info != null && info.size() > 0) {
					for (int i = 0; i < info.size(); i++) {
						HashMap<String, String> rowinfo = info.get(i);
						String[] cols = util.getSheetCols();
						UseMailServerDomain row = new UseMailServerDomain();
						row.setName(rowinfo.get(cols[0]));
						row.setAddress(rowinfo.get(cols[1]));
						row.setHost(rowinfo.get(cols[2]));
						row.setPort(rowinfo.get(cols[3]));

						result.add(row);
					}
				}

			} else {
				for (int i = 0; i < spocsRowCount; i++) {
					UseMailServerDomain row = new UseMailServerDomain();
					result.add(row);
				}
			}
			util.setTotRow(spocsRowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static List<UseUserDomain> getUseUsers(HttpSession sess) {

		List<UseUserDomain> result = new ArrayList<UseUserDomain>();

		try {
			SessionUtil util=(SessionUtil) sess.getAttribute("SessionUtil");
			 String operation = util.getConfigLayerbyManualorUpload();
			//String operation = "Upload";
			int spocsRowCount = ExcelSheetUtil.USERROWCOUNT - 1;
			if (operation.trim().equalsIgnoreCase(ExcelSheetUtil.UPLOAD)) {
				ReadExcelBySheetName excelRead = new ReadExcelBySheetName();
				excelRead.processBySheet(ExcelSheetUtil.USER,sess);

				ArrayList<HashMap<String, String>> info = util.getSheetDetails();
				if (info != null && info.size() > 0) {
					for (int i = 0; i < info.size(); i++) {
						HashMap<String, String> rowinfo = info.get(i);
						String[] cols = util.getSheetCols();
						UseUserDomain row = new UseUserDomain();
						row.setUserId(rowinfo.get(cols[0]));
						row.setFirstName(rowinfo.get(cols[1]));
						row.setLastName(rowinfo.get(cols[2]));
						row.setDesignation(rowinfo.get(cols[3]));
						row.setDepartment(rowinfo.get(cols[4]));
						row.setAddress(rowinfo.get(cols[5]));
						row.setCity(rowinfo.get(cols[6]));
						row.setState(rowinfo.get(cols[7]));
						row.setCountry(rowinfo.get(cols[8]));
						row.setPostal(rowinfo.get(cols[9]));
						row.setDob(rowinfo.get(cols[10]));
						row.setGender(rowinfo.get(cols[11]));
						row.setMobile(rowinfo.get(cols[12]));
						row.setLandline(rowinfo.get(cols[13]));
						row.setExtension(rowinfo.get(cols[14]));
						row.setEmail(rowinfo.get(cols[15]));
						row.setMailServer(rowinfo.get(cols[16]));
						row.setSpace(rowinfo.get(cols[17]));
						row.setUom(rowinfo.get(cols[18]).toUpperCase());
						result.add(row);
					}
				}

			} else {
				for (int i = 0; i < spocsRowCount; i++) {
					UseUserDomain row = new UseUserDomain();
					result.add(row);
				}
			}
			util.setTotRow(spocsRowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static List<UseRoleDomain> getUseRoles(HttpSession sess) {
		List<UseRoleDomain> result = new ArrayList<UseRoleDomain>();

		try {
			SessionUtil util=(SessionUtil) sess.getAttribute("SessionUtil");
			 String operation = util.getConfigLayerbyManualorUpload();
			//String operation = "Upload";
			int spocsRowCount = ExcelSheetUtil.ROLEROWCOUNT - 1;
			if (operation.trim().equalsIgnoreCase(ExcelSheetUtil.UPLOAD)) {
				ReadExcelBySheetName excelRead = new ReadExcelBySheetName();
				excelRead.processBySheet(ExcelSheetUtil.ROLE,sess);

				ArrayList<HashMap<String, String>> info = util.getSheetDetails();
				if (info != null && info.size() > 0) {
					for (int i = 0; i < info.size(); i++) {
						HashMap<String, String> rowinfo = info.get(i);
						String[] cols = util.getSheetCols();
						UseRoleDomain row = new UseRoleDomain();
						row.setRoleId(rowinfo.get(cols[0]));
						row.setDescription(rowinfo.get(cols[1]));
						row.setType(rowinfo.get(cols[2]));
						result.add(row);
					}
				}

			} else {
				for (int i = 0; i < spocsRowCount; i++) {
					UseRoleDomain row = new UseRoleDomain();
					result.add(row);
				}
			}
			util.setTotRow(spocsRowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static List<UseRoleMapDomain> getUseRoleMap(HttpSession sess) {
		List<UseRoleMapDomain> result = new ArrayList<UseRoleMapDomain>();

		try {
			SessionUtil util=(SessionUtil) sess.getAttribute("SessionUtil");
			 String operation = util.getConfigLayerbyManualorUpload();
			//String operation = "Upload";
			int spocsRowCount = ExcelSheetUtil.USERROLEMAPROWCOUNT - 1;
			if (operation.trim().equalsIgnoreCase(ExcelSheetUtil.UPLOAD)) {
				ReadExcelBySheetName excelRead = new ReadExcelBySheetName();
				excelRead.processBySheet(ExcelSheetUtil.USERROLEMAP,sess);

				ArrayList<HashMap<String, String>> info = util.getSheetDetails();
				if (info != null && info.size() > 0) {
					for (int i = 0; i < info.size(); i++) {
						HashMap<String, String> rowinfo = info.get(i);
						String[] cols = util.getSheetCols();
						UseRoleMapDomain row = new UseRoleMapDomain();
						
						row.setUserId(rowinfo.get(cols[0]));
						row.setRoleId(rowinfo.get(cols[1]));
						result.add(row);
					}
				}
			} else {
				for (int i = 0; i < spocsRowCount; i++) {
					UseRoleMapDomain row = new UseRoleMapDomain();
					result.add(row);
				}
			}
			util.setTotRow(spocsRowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static List<UseAccessDomain> getUseAccessRights(HttpSession sess) {
		List<UseAccessDomain> result = new ArrayList<UseAccessDomain>();

		try {
			SessionUtil util=(SessionUtil) sess.getAttribute("SessionUtil");
			 String operation = util.getConfigLayerbyManualorUpload();
			//String operation = "Upload";
			int spocsRowCount = ExcelSheetUtil.ACCESSRIGHTSROWCOUNT - 1;
			if (operation.trim().equalsIgnoreCase(ExcelSheetUtil.UPLOAD)) {
				ReadExcelBySheetName excelRead = new ReadExcelBySheetName();
				excelRead.processBySheet(ExcelSheetUtil.ACCESSRIGHTS,sess);

				ArrayList<HashMap<String, String>> info = util.getSheetDetails();
				
				util.print("getUseAccessRights :: "+info.size());
				
				if (info != null && info.size() > 0) {
					for (int i = 0; i < info.size(); i++) {
						HashMap<String, String> rowinfo = info.get(i);
						String[] cols = util.getSheetCols();
						UseAccessDomain row = new UseAccessDomain();
						
						row.setRoleId(rowinfo.get(cols[0]));
						row.setModuleId(rowinfo.get(cols[1]));
						row.setMenuNamelevel1(rowinfo.get(cols[2]));
						row.setMenuNamelevel2(rowinfo.get(cols[3]));
						row.setMenuNamelevel3(rowinfo.get(cols[4]));
						result.add(row);
					}
				}
			} else {
				for (int i = 0; i < spocsRowCount; i++) {
					UseAccessDomain row = new UseAccessDomain();
					result.add(row);
				}
			}
			util.setTotRow(spocsRowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static List<UseNotificationDomain> getUseNotification(HttpSession sess) {
		List<UseNotificationDomain> result = new ArrayList<UseNotificationDomain>();

		try {
			SessionUtil util=(SessionUtil) sess.getAttribute("SessionUtil");
			String operation = util.getConfigLayerbyManualorUpload();
			//String operation = "Upload";
			int spocsRowCount = ExcelSheetUtil.UNTROWCOUNT - 1;
			if (operation.trim().equalsIgnoreCase(ExcelSheetUtil.UPLOAD)) {
				ReadExcelBySheetName excelRead = new ReadExcelBySheetName();
				excelRead.processBySheet(ExcelSheetUtil.UNT,sess);

				ArrayList<HashMap<String, String>> info = util.getSheetDetails();
				if (info != null && info.size() > 0) {
					for (int i = 0; i < info.size(); i++) {
						HashMap<String, String> rowinfo = info.get(i);
						String[] cols = util.getSheetCols();
						UseNotificationDomain row = new UseNotificationDomain();
						
						row.setNotificationType(rowinfo.get(cols[0]));
						row.setSubject(rowinfo.get(cols[1]));
						row.setHeader(rowinfo.get(cols[2]));
						row.setBody(rowinfo.get(cols[3]));
						row.setFooter(rowinfo.get(cols[4]));
						row.setUidSender(rowinfo.get(cols[5]));
						row.setKeywords(rowinfo.get(cols[6]));
						result.add(row);
					}
				}
			} else {
				
				String[] notiType={"Welcome Message","Role Change","Password Expiry","Status Change","Forgot Password","Change Password"};
				for (int i = 0; i < spocsRowCount; i++) {
					UseNotificationDomain row = new UseNotificationDomain();
					row.setNotificationType(notiType[i]);
					result.add(row);
				}
			}
			util.setTotRow(spocsRowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
