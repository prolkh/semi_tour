package com.Util;

import java.io.File;
import java.util.Calendar;

public class FileManager {
	/**
	 * ���� �ٿ�ε� �޼ҵ�
	 * 
	 * @param saveFilename     ��������������ϸ�
	 * @param originalFilename Ŭ���̾�Ʈ�����ε������ϸ�
	 * @param pathname         ����������Ȱ��
	 * @param resp             HttpServletResponse ��ü
	 * @return �ٿ�ε强������
	 */
	public static boolean doFiledownload() {
		boolean flag = false;

		return flag;
	};

	/**
	 * ���� �̸� ����(����Ͻú��ʳ�����)
	 * 
	 * @param pathname ����������� ���
	 * @param filename ������ ���ϸ�
	 * @return ���ο����ϸ�
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
	 * ���� ����
	 * 
	 * @param pathname ������ ����� ���
	 * @param filename ������ ���ϸ�
	 * @return ���� ���� ���� ����
	 */
	public static boolean doFiledelete(String pathname, String filename) {

		String path = pathname + File.separator + filename; // (File.separator => \)

		try {
			File f = new File(path);

			if (!f.exists()) // ������ ������
				return false;

			f.delete();
		} catch (Exception e) {
		}

		return true;
	}

}
