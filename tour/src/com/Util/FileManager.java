package com.Util;

public class FileManager {
	/**
	 * 파일 다운로드 메소드
	 * @param saveFilename 서버에저장된파일명
	 * @param originalFilename 클라이언트가업로드한파일명
	 * @param pathname 서버에저장된경로
	 * @param resp HttpServletResponse 객체
	 * @return 다운로드성공여부
	 */
	public static boolean doFiledownload() {
		boolean flag=false;
		
		return flag;
	};
	
	/**
	 * 파일 이름 변경(년월일시분초나노초)
	 * @param pathname 파일이저장된 경로
	 * @param filename 변경할 파일명
	 * @return 새로운파일명
	 */
	public static String dofilerename(String pathname, String filename) {
		String newname="";
		
		return newname;
	}
	
	/**
	 * 파일 삭제
	 * @param pathname 파일이 저장된 경로
	 * @param filename 삭제할 파일명
	 * @return 파일 삭제 성공 여부
	 */
	public static boolean dofiledelete(String pathname, String filename) {
		
		return false;
	}
	
}
