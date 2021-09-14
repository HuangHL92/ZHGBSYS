package com.insigma.siis.local.pagemodel.huiyi;

/**
 * @author genggaopeng
 * @date 2019-12-9 11:36
 */
public class HuiyiUploadUtils {

    /**
     * �����ļ���·����ȡ�ļ���ʵ����
     *
     * @param path
     *            �ļ���·��
     * @return �ļ�����
     */
    public static String getRealName(String path) {
        int index = path.lastIndexOf("\\");

        if (index == -1) {
            index = path.lastIndexOf("/");
        }

        return path.substring(index + 1);
    }

    /**
     * �����ļ�������һ��Ŀ¼
     *
     * @param name
     *            �ļ�����
     * @return Ŀ¼
     */
    public static String getDir(String name) {
        int i = name.hashCode();
        String hex = Integer.toHexString(i);
        int j = hex.length();

        for (int k = 0; k < 8 - j; k++) {
            hex = "0" + hex;
        }

        return "/" + hex.charAt(0) + "/" + hex.charAt(1);
    }
}

