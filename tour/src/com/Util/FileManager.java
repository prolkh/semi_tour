package com.Util;

import java.io.File;
import java.util.Calendar;

public class FileManager {
	/**
	 * 파일 다운로드 메소드
	 * 
	 * @param saveFilename     서버에저장된파일명
	 * @param originalFilename 클라이언트가업로드한파일명
	 * @param pathname         서버에저장된경로
	 * @param resp             HttpServletResponse 객체
	 * @return 다운로드성공여부
	 */
	public static boolean doFiledownload() {
		boolean flag = false;

		return flag;
	};

	/**
	 * 파일 이름 변경(년월일시분초나노초)
	 * 
	 * @param pathname 파일이저장된 경로
	 * @param filename 변경할 파일명
	 * @return 새로운파일명
	 */
	public static String doFilerename(String pathname, String filename) {
		String newname = "";

		String fileExt = filename.substring(filename.lastIndexOf("."));
		String s = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		s += System.nanoTime();
		s += fileExt;

		try {
			File f1 = new File(pathname + File.separator + filename);
			File f2 = new File(pathname + File.separator + s);
			f1.renameTo(f2);

			newname = s;
		} catch (Exception e) {
		}

		return newname;
	}

	/**
	 * 파일 삭제
	 * 
	 * @param pathname 파일이 저장된 경로
	 * @param filename 삭제할 파일명
	 * @return 파일 삭제 성공 여부
	 */
	public static boolean doFiledelete(String pathname, String filename) {

		String path = pathname + File.separator + filename; // (File.separator => \)

		try {
			File f = new File(path);

			if (!f.exists()) // 파일이 없으면
				return false;

			f.delete();
		} catch (Exception e) {
		}

		return true;
	}

}
