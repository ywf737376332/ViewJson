//package com.ywf.framework.utils;
//
//
//import org.bouncycastle.asn1.gm.GMNamedCurves;
//import org.bouncycastle.asn1.x9.X9ECParameters;
//import org.bouncycastle.crypto.CipherParameters;
//import org.bouncycastle.crypto.engines.SM2Engine;
//import org.bouncycastle.crypto.params.ECDomainParameters;
//import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
//import org.bouncycastle.crypto.params.ECPublicKeyParameters;
//import org.bouncycastle.crypto.params.ParametersWithRandom;
//import org.bouncycastle.crypto.signers.SM2Signer;
//import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
//import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.jce.spec.ECParameterSpec;
//import org.bouncycastle.jce.spec.ECPrivateKeySpec;
//import org.bouncycastle.math.ec.ECPoint;
//import org.bouncycastle.util.encoders.Hex;
//
//import java.math.BigInteger;
//import java.nio.charset.StandardCharsets;
//import java.security.*;
//import java.security.spec.ECGenParameterSpec;
//import java.util.Base64;
//
///**
// * SM2工具类
// *
// * @author van
// */
///*
//public class SM2Util {
//
//    */
///**
//     * 生成 SM2 公私钥对
//     *
//     * @return
//     * @throws NoSuchAlgorithmException
//     * @throws InvalidAlgorithmParameterException
//     *//*
//
//    public static KeyPair geneSM2KeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
//        final ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
//        // 获取一个椭圆曲线类型的密钥对生成器
//        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
//        // 产生随机数
//        SecureRandom secureRandom = new SecureRandom();
//        // 使用SM2参数初始化生成器
//        kpg.initialize(sm2Spec, secureRandom);
//        // 获取密钥对
//        KeyPair keyPair = kpg.generateKeyPair();
//        return keyPair;
//    }
//
//    */
///**
//     * 生产hex秘钥对
//     *//*
//
//    public static void geneSM2HexKeyPair() {
//        try {
//            KeyPair keyPair = geneSM2KeyPair();
//            PrivateKey privateKey = keyPair.getPrivate();
//            PublicKey publicKey = keyPair.getPublic();
//            System.out.println("========  EC X Y keyPair    ========");
//            System.out.println(privateKey);
//            System.out.println(publicKey);
//            System.out.println("========  hex keyPair       ========");
//            System.out.println("hex priKey: " + getPriKeyHexString(privateKey));
//            System.out.println("hex pubKey: " + getPubKeyHexString(publicKey));
//            System.out.println("========  base64 keyPair    ========");
//            System.out.println("base64 priKey: " + new String(Base64.getEncoder().encode(privateKey.getEncoded())));
//            System.out.println("base64 pubKey: " + new String(Base64.getEncoder().encode(publicKey.getEncoded())));
//            System.out.println("========  generate finished ========");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    */
///**
//     * 获取私钥（16进制字符串，头部不带00长度共64）
//     *
//     * @param privateKey 私钥PrivateKey型
//     * @return
//     *//*
//
//    public static String getPriKeyHexString(PrivateKey privateKey) {
//        // OK
////	        BCECPrivateKey s=(BCECPrivateKey)privateKey;
////	        String priKeyHexString = Hex.toHexString(s.getD().toByteArray());
////	        if(null!= priKeyHexString && priKeyHexString.length()==66 && "00".equals(priKeyHexString.substring(0,2))){
////	            return priKeyHexString.substring(2);
////	        }
//        // OK
//        BCECPrivateKey key = (BCECPrivateKey) privateKey;
//        BigInteger intPrivateKey = key.getD();
//        String priKeyHexString = intPrivateKey.toString(16);
//        return priKeyHexString;
//    }
//
//    */
///**
//     * 获取私钥 base64字符串
//     *
//     * @param privateKey 私钥PrivateKey型
//     * @return
//     *//*
//
//    public static String getPriKeyBase64String(PrivateKey privateKey) {
//        return new String(Base64.getEncoder().encode(privateKey.getEncoded()));
//    }
//
//    */
///**
//     * 获取公钥（16进制字符串，头部带04长度共130）
//     *
//     * @param publicKey 公钥PublicKey型
//     * @return
//     *//*
//
//    public static String getPubKeyHexString(PublicKey publicKey) {
//        BCECPublicKey key = (BCECPublicKey) publicKey;
//        return Hex.toHexString(key.getQ().getEncoded(false));
//    }
//
//    */
///**
//     * 获取公钥 base64字符串
//     *
//     * @param publicKey 公钥PublicKey型
//     * @return
//     *//*
//
//    public static String getPubKeyBase64String(PublicKey publicKey) {
//        return new String(Base64.getEncoder().encode(publicKey.getEncoded()));
//    }
//
//    */
///**
//     * SM2加密算法
//     *
//     * @param publicKey 公钥
//     * @param data      明文数据
//     * @return
//     *//*
//
//    public static String encrypt(String data, PublicKey publicKey) {
//        return encrypt(data.getBytes(StandardCharsets.UTF_8), publicKey);
//    }
//
//    */
///**
//     * @param data
//     * @param publicKey
//     * @return
//     * @author
//     * @version 1.0
//     * 2023年4月12日下午4:41:24
//     *//*
//
//    public static String encrypt(byte[] data, PublicKey publicKey) {
//        BCECPublicKey key = (BCECPublicKey) publicKey;
//        return encrypt(data, Hex.toHexString(key.getQ().getEncoded(false)));
//    }
//
//    */
///**
//     * @param data
//     * @param pubKeyHexString
//     * @return
//     * @author
//     * @version 1.0
//     * 2023年4月12日下午4:46:37
//     *//*
//
//    public static String encrypt(String data, String pubKeyHexString) {
//        return encrypt(data.getBytes(StandardCharsets.UTF_8), pubKeyHexString);
//    }
//
//    */
///**
//     * SM2加密算法
//     *
//     * @param pubKeyHexString 公钥（16进制字符串）
//     * @param data            明文数据
//     * @return hex字符串
//     *//*
//
//    public static String encrypt(byte[] data, String pubKeyHexString) {
//        // 获取一条SM2曲线参数
//        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
//        // 构造ECC算法参数，曲线方程、椭圆曲线G点、大整数N
//        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
//        //提取公钥点
//        ECPoint pukPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(pubKeyHexString));
//        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
//        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);
//
//        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
//        // 设置sm2为加密模式
//        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));
//
//        byte[] arrayOfBytes = null;
//        try {
//            arrayOfBytes = sm2Engine.processBlock(data, 0, data.length);
//        } catch (Exception e) {
//            System.out.println("SM2加密时出现异常:" + e.getMessage());
//        }
//        return Hex.toHexString(arrayOfBytes);
//
//    }
//
//    */
///**
//     * SM2解密算法
//     *
//     * @param cipherData hex格式密文
//     * @param privateKey 密钥PrivateKey型
//     * @return 明文
//     *//*
//
//    public static String decrypt(String cipherData, PrivateKey privateKey) {
//        return decrypt(Hex.decode(cipherData), privateKey);
//    }
//
//    */
///**
//     * @param cipherData
//     * @param privateKey
//     * @return
//     * @author
//     * @version 1.0
//     * 2023年4月12日下午4:46:50
//     *//*
//
//    public static String decrypt(byte[] cipherData, PrivateKey privateKey) {
//        BCECPrivateKey key = (BCECPrivateKey) privateKey;
//        return decrypt(cipherData, Hex.toHexString(key.getD().toByteArray()));
//    }
//
//    */
///**
//     * @param cipherData
//     * @param priKeyHexString
//     * @return
//     * @author
//     * @version 1.0
//     * 2023年4月12日下午4:46:53
//     *//*
//
//    public static String decrypt(String cipherData, String priKeyHexString) {
//        // 使用BC库加解密时密文以04开头，传入的密文前面没有04则补上
//        if (!cipherData.startsWith("04")) {
//            cipherData = "04" + cipherData;
//        }
//        return decrypt(Hex.decode(cipherData), priKeyHexString);
//    }
//
//    */
///**
//     * SM2解密算法
//     *
//     * @param cipherData      密文数据
//     * @param priKeyHexString 私钥（16进制字符串）
//     * @return
//     *//*
//
//    public static String decrypt(byte[] cipherData, String priKeyHexString) {
//        //获取一条SM2曲线参数
//        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
//        //构造domain参数
//        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
//
//        BigInteger privateKeyD = new BigInteger(priKeyHexString, 16);
//        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);
//
//        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
//        // 设置sm2为解密模式
//        sm2Engine.init(false, privateKeyParameters);
//
//        String result = "";
//        try {
//            byte[] arrayOfBytes = sm2Engine.processBlock(cipherData, 0, cipherData.length);
//            return new String(arrayOfBytes);
//        } catch (Exception e) {
//            System.out.println("SM2解密时出现异常:" + e.getMessage());
//        }
//        return result;
//    }
//
//    */
///**
//     * @param data
//     * @param priKeyHexString hex私钥，长度64
//     * @return hex格式签名值
//     * @throws Exception
//     *//*
//
//    public static String sign(String data, String priKeyHexString) throws Exception {
//        return sign(data.getBytes(StandardCharsets.UTF_8), priKeyHexString);
//    }
//
//    */
///**
//     * 签名
//     *
//     * @param data            原始数据，字节数组
//     * @param priKeyHexString hex私钥，64长度
//     * @return Hex字符串
//     * @throws Exception
//     *//*
//
//    public static String sign(byte[] data, String priKeyHexString) throws Exception {
//        String signValue = null;
//        SM2Signer signer = new SM2Signer();
//        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
//        //构造domain参数
//        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
//        CipherParameters param = new ParametersWithRandom(new ECPrivateKeyParameters(new BigInteger(priKeyHexString, 16), domainParameters));
//        signer.init(true, param);
//        signer.update(data, 0, data.length);
//        signValue = Hex.toHexString(signer.generateSignature());
//        return signValue;
//    }
//
//    */
///**
//     * 验签
//     *
//     * @param data        数据
//     * @param signValue   签名值(hex型)
//     * @param pKHexString hex130长度公钥
//     * @return
//     *//*
//
//    public static boolean verify(String data, String signValue, String publicKeyHexString) throws Exception {
//        return verify(data.getBytes(StandardCharsets.UTF_8), Hex.decode(signValue), publicKeyHexString);
//    }
//
//    */
///**
//     * 验签
//     *
//     * @param data        原始数据字节数组
//     * @param sign        字节数组()
//     * @param pKHexString hex130长度公钥
//     * @return true or false
//     * @throws Exception
//     *//*
//
//    public static boolean verify(byte[] data, byte[] sign, String pKHexString) throws Exception {
//        SM2Signer signer = new SM2Signer();
//        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
//        //构造domain参数
//        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
//        if (pKHexString.length() == 128) {
//            pKHexString = "04" + pKHexString;
//        }
//        ECPoint ecPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(pKHexString));
//        CipherParameters param = new ECPublicKeyParameters(ecPoint, domainParameters);
//        signer.init(false, param);
//        signer.update(data, 0, data.length);
//        return signer.verifySignature(sign);
//    }
//
//    */
///**
//     * 私钥生成公钥
//     *
//     * @param priKeyHexString 私钥Hex格式，必须64位
//     * @return 公钥Hex格式，04开头，130位
//     * @throws Exception 例如：
//     *//*
//
//    public static String getPubKeyByPriKey(String priKeyHexString) throws Exception {
//        if (priKeyHexString == null || priKeyHexString.length() != 64) {
//            System.err.println("priKey 必须是Hex 64位格式，例如：11d0a44d47449d48d614f753ded6b06af76033b9c3a2af2b8b2239374ccbce3a");
//            return "";
//        }
//        String pubKeyHexString = null;
//        X9ECParameters sm2ECParameters = GMNamedCurves.getByName("sm2p256v1");
//        //构造domain参数
//        BigInteger privateKeyD = new BigInteger(priKeyHexString, 16);
//
//        ECParameterSpec ecParameterSpec = new ECParameterSpec(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
//        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(privateKeyD, ecParameterSpec);
//        PrivateKey privateKey = null;
//        privateKey = KeyFactory.getInstance("EC", new BouncyCastleProvider()).generatePrivate(ecPrivateKeySpec);
//
//        // 临时解决办法
//        String pointString = privateKey.toString();
////	        System.out.println(pointString);
//        String pointString_X = pointString.substring(pointString.indexOf("X: ") + "X: ".length(), pointString.indexOf("Y: ")).trim();
//        String pointString_Y = pointString.substring(pointString.indexOf("Y: ") + "Y: ".length()).trim();
////	        System.out.println(pointString_X);
////	        System.out.println(pointString_Y);
//
//        pubKeyHexString = "04" + pointString_X + pointString_Y;
//        return pubKeyHexString;
//
//    }
//
//}*/
