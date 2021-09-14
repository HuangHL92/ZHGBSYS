package com.insigma.odin.framework;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * �ǶԳƼ����㷨RSA�㷨���
 * �ǶԳ��㷨һ�����������ͶԳƼ����㷨����Կ��ʹ�õģ������DH�㷨��RSA�㷨ֻ��Ҫһ��������Կ������Ҫ
 * ������µĹ�����Ա��ص���Կ���ˡ�DH�㷨ֻ���㷨�ǶԳ��㷨�ĵײ�ʵ�֡���RSA�㷨�㷨ʵ��������Ϊ��
 *
 * @author zoul
 */
public class RSACoder {
    //�ǶԳ���Կ�㷨
    public static final String KEY_ALGORITHM = "RSA";


    /**
     * ��Կ���ȣ�DH�㷨��Ĭ����Կ������1024
     * ��Կ���ȱ�����64�ı�������512��65536λ֮��
     */
    private static final int KEY_SIZE = 512;
    //��Կ
    private static final String PUBLIC_KEY = "RSAPublicKey";

    //˽Կ
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * ��ʼ����Կ��
     *
     * @return Map �׷���Կ��Map
     */
    public static Map<String, Object> initKey() throws Exception {
        //ʵ������Կ������
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //��ʼ����Կ������
        keyPairGenerator.initialize(KEY_SIZE);
        //������Կ��
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //�׷���Կ
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //�׷�˽Կ
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //����Կ�洢��map��
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;

    }


    /**
     * ˽Կ����
     *
     * @param data ����������
     * @param key       ��Կ
     * @return byte[] ��������
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {

        //ȡ��˽Կ
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //����˽Կ
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //���ݼ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * ��Կ����
     *
     * @param data ����������
     * @param key       ��Կ
     * @return byte[] ��������
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //ʵ������Կ����
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //��ʼ����Կ
        //��Կ����ת��
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //������Կ
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //���ݼ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * ˽Կ����
     *
     * @param data ����������
     * @param key  ��Կ
     * @return byte[] ��������
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        //ȡ��˽Կ
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //����˽Կ
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //���ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * ��Կ����
     *
     * @param data ����������
     * @param key  ��Կ
     * @return byte[] ��������
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //ʵ������Կ����
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //��ʼ����Կ
        //��Կ����ת��
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //������Կ
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //���ݽ���
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * ȡ��˽Կ
     *
     * @param keyMap ��Կmap
     * @return byte[] ˽Կ
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * ȡ�ù�Կ
     *
     * @param keyMap ��Կmap
     * @return byte[] ��Կ
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //��ʼ����Կ
        //������Կ��
        Map<String, Object> keyMap = RSACoder.initKey();
        //��Կ
        byte[] publicKey = RSACoder.getPublicKey(keyMap);

        //˽Կ
        byte[] privateKey = RSACoder.getPrivateKey(keyMap);
        System.out.println("��Կ��" + new String(Base64.encodeBase64(publicKey)));
        System.out.println("˽Կ��" + new String(Base64.encodeBase64(privateKey)));

        System.out.println("================��Կ�Թ������,�׷�����Կ�������ҷ�����ʼ���м������ݵĴ���=============");
        String str = "RSA���뽻���㷨";
        System.out.println("===========�׷����ҷ����ͼ�������==============");
        System.out.println("ԭ��:" + str);
        //�׷��������ݵļ���
        byte[] code1 = RSACoder.encryptByPrivateKey(str.getBytes(), privateKey);
        System.out.println("���ܺ�����ݣ�" + new String(Base64.encodeBase64(code1)));
        System.out.println("===========�ҷ�ʹ�ü׷��ṩ�Ĺ�Կ�����ݽ��н���==============");
        //�ҷ��������ݵĽ���
        byte[] decode1 = RSACoder.decryptByPublicKey(code1, publicKey);
        System.out.println("�ҷ����ܺ�����ݣ�" + new String(decode1));
        System.out.println("===========������в������ҷ���׷���������==============");

        str = "�ҷ���׷���������RSA�㷨";

        System.out.println("ԭ��:" + str);

        //�ҷ�ʹ�ù�Կ�����ݽ��м���
        byte[] code2 = RSACoder.encryptByPublicKey(str.getBytes(), publicKey);
        System.out.println("===========�ҷ�ʹ�ù�Կ�����ݽ��м���==============");
        System.out.println("���ܺ�����ݣ�" + new String(Base64.encodeBase64(code2)));

        System.out.println("=============�ҷ������ݴ��͸��׷�======================");
        System.out.println("===========�׷�ʹ��˽Կ�����ݽ��н���==============");

        //�׷�ʹ��˽Կ�����ݽ��н���
        byte[] decode2 = RSACoder.decryptByPrivateKey(code2, privateKey);

        System.out.println("�׷����ܺ�����ݣ�" + new String(decode2));
        
        byte[] decode4 = RSACoder.decryptByPrivateKey(
        		Base64.decodeBase64("EB5inszIbRfCwMSIFAL2JwWqFN1zXDczadMPDNZw1a7wqxWQigmStsMfvTiPzjPUW4NqgeSuxKnXXyUVWbN+jw==".getBytes()),
        		Base64.decodeBase64("MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAlOtDa4oXXBvpJCa8yoKOGx3RZG9ZEr+euFiPmZncPRkcnaaoTKleHRdpzwauBPBsQXU0ooUnIpLtaXmMciShNQIDAQABAkAM5lg/v4WIgA5xCD4AWNDQfoO97HtNyXWJSHqf9pkCXcFXKoS59C4c0XRrGv0WAmHGAtAqufCnv47ODE/emC09AiEA4PKjfosEtFJcNNoIIAfXO/YeZBr/PUzzi5RKH7pLiDsCIQCped2x1FyN+AaA6mD8mtyuleYGnj7q+dLa1p980i5VTwIgaxVWm0DWhnjGiCpav9S7s0GgigsIAkiFj6aR+rSWjE0CIHoqeN7ZoCZOphGD4on08COBtqEKrXwgvhg2Ih2OPQwNAiBZwEw1/6MkvZn5Vtn68S0X8BuLq+5rcC4LrBxvcsk+0Q==".getBytes()));

        System.out.println("�׷����ܺ�����ݣ�" + new String(decode4));
    }
}