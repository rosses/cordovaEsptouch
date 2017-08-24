package com.rosses.smartconfig4.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ByteUtils {

    public  static  final String CODE_TYPE = "GB2312";

	/**********************************************************************
	 * 功能描述：获取00:00:00:00:00:00格式的mac地址，
	 * 输入参数：  数组形式的mac地址
	 * 返回值：mac地址的字符串， 倒序的
	 * 其它说明：无，
	 ***********************************************************************/
	public static String getMacString(byte[] mac){
		if(mac==null||mac.length==0){
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		for(int i=0;i<mac.length-1;i++){
			int a = mac[i]&0xff;
			if(a<16){
				sb.append("0"+Integer.toHexString(a));
			}else{
				sb.append(Integer.toHexString(a));
			}
				sb.append(":");
		}
		int b = mac[mac.length-1]&0xff;
		if(b<16){
			sb.append("0"+Integer.toHexString(b));
		}else{
			sb.append(Integer.toHexString(b));
		}
		return sb.toString();
	}
	
	/**********************************************************************
	 * 功能描述：获取数组形式的mac地址，参数格式00:00:00:00:00:00，
	 * 输入参数：  mac字符窜形式
	 * 返回值：数组形式的mac地址
	 * 其它说明：无，
	 ***********************************************************************/
	public static byte[] getMacBytes(String macString){
		if(macString==null||macString.length()==0){
			return null;
		}
		String[] strArr = macString.split(":");
		if(strArr.length<6){
			return null;
		}
		byte []macBytes = new byte[strArr.length];
		for(int i = 0;i < strArr.length; i++){
		   int value = Integer.parseInt(strArr[i],16);
		   macBytes[i] = (byte) value;
		  }
		return macBytes;
	}


	
	/**********************************************************************
	 * 功能描述：获取数组形式的RGBWT，参数格式以某一符号作为间隔的，比如00,00,00,00,00,00,00,00,00,00,就是以","间隔
	 * 输入参数：  RGBWT的某个符号间隔形式,间隔服，比如：","
	 * 返回值：数组形式的RGBWT
	 * 其它说明：无，
	 ***********************************************************************/
	public static byte[] getRGBWTBytes(String RGBWTString,String sign){
		if(RGBWTString==null){
			return null;
		}
		String[] strArr = RGBWTString.split(sign);
		if(strArr.length<5){
			return null;
		}
		byte[] RGBWT = new byte[strArr.length];
		for(int i=0;i<strArr.length;i++){
			RGBWT[i] = Byte.parseByte(strArr[i]);
		}
		return RGBWT;
	}
	
	/**********************************************************************
	 * 功能描述：获取RGBWT，格式以某一符号间隔的RGBWT值，比如：00,00,00,00,00,00,00,00,00,00
	 * 输入参数： RGBWT的数组形式，间隔符
	 * 返回值：格式以某一符号间隔的RGBWT值，比如：00,00,00,00,00,00,00,00,00,00
	 * 其它说明：无，
	 * 创建记录：版本号：1.00	作者：马杭斌		修改日期：2015/08/11
	 ***********************************************************************/
	public static String getRGBWTString(byte[] RGBWT,String sign){
		StringBuilder sb = new StringBuilder("");
		for(int i=0;i<RGBWT.length-1;i++){
			sb.append(RGBWT[i]+"");
			sb.append(sign);
		}
		sb.append(RGBWT[RGBWT.length-1]+"");
		return sb.toString();
	}



	
	/**********************************************************************
	 * 功能描述：获取192.168.1.1格式的ip地址，
	 * 输入参数：  无
	 * 返回值：ip地址的字符串，
	 * 其它说明：无，
	 * 创建记录：版本号：1.00	作者：马杭斌		修改日期：2015/07/23
	 ***********************************************************************/
	public static String getIpString(byte[] ip){
		if(ip==null||ip.length==0){
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		for(int i=0;i<ip.length-1;i++){
			int a = ip[i]&0xff;
			sb.append(a);
			sb.append(".");
		}
		int b = ip[ip.length-1]&0xff;
		sb.append(b);
		return sb.toString();
	}
	
	/**********************************************************************
	 * 功能描述：获取byte[] 形式的ip地址，
	 * 输入参数：  参数格式192.168.1.1，
	 * 返回值：byte[]形式的ip
	 * 其它说明：无，
	 ***********************************************************************/
	public static byte[] getIp(String ipString){
		if(ipString==null||ipString.length()==0){
			return null;
		}
		String [] strArr = ipString.split("\\.");
		byte [] ipBytes = new byte[strArr.length];
		for(int i = 0;i < strArr.length; i++){
		   int value = Integer.parseInt(strArr[i]);
		   ipBytes[i] = (byte) value;
		  }
		return ipBytes;
	}
	
	/**********************************************************************
	 * 功能描述：把i转化为长度len的byte[]，
	 * 输入参数：  i:long，
	 * 输入参数：返回byte的长度，
	 * 返回值： 长度为len的byte数组，
	 * 其它说明：无，
	 * 创建记录：版本号：1.00	作者：马杭斌		修改日期：2015/09/06	
	 ***********************************************************************/
	public static byte[] longToBytes(long l,int len){
		byte[] a = new byte[len];
		for(int j=0;j<len;j++){
			a[j] = (byte)(((0xffl<<(8l*j))&l)>>(8l*j));
		}
		return a;
	}
	
	/**********************************************************************
	 * 功能描述：把i转化为长度len的byte[]，
	 * 输入参数：  i:整形数据，
	 * 输入参数：返回byte的长度，
	 * 返回值： 长度为len的byte数组，
	 * 其它说明：无，
	 * 创建记录：版本号：1.00	作者：马杭斌		修改日期：2015/07/17	
	 ***********************************************************************/
	public static byte[] intToBytes(int i,int len){
		byte[] a = new byte[len];
		for(int j=0;j<len;j++){
			a[j] = (byte)(((0xff<<(8*j))&i)>>(8*j));
		}
		return a;
	}


	
	/**********************************************************************
	 * 功能描述：把byt转化为int，
	 * 输入参数：返回byte的长度，
	 * 返回值： int数组
	 * 其它说明：无，
	 * 创建记录：版本号：1.00	作者：陈小龙	 修改日期：2015/07/23	
	 ***********************************************************************/
	public static int bytesToInt(byte[] bytes) {        
		int addr ;
		if (bytes.length == 1) {              
			addr = bytes[0] & 0xFF;          
		} else if (bytes.length == 2) {            
			addr = bytes[0] & 0xFF;            
			addr |= (((int) bytes[1] << 8) & 0xFF00);       
		} else {               
			addr = bytes[0] & 0xFF;              
			addr |= (((int) bytes[1] << 8) & 0xFF00);           
			addr |= (((int) bytes[2] << 16) & 0xFF0000);        
			addr |= (((int) bytes[3] << 24) & 0xFF000000); 
		}          
		return addr;       
	}
	
	/**********************************************************************
	 * 功能描述：把byt转化为long，
	 * 输入参数：返回byte的长度，
	 * 返回值： short数组
	 * 其它说明：无，
	 * 创建记录：版本号：1.00	作者：陈小龙	 修改日期：2015/09/15	
	 ***********************************************************************/
	public static long bytesToLong(byte[] b) {  
		long s = 0;        
		long s0 = b[0] & 0xff;// 最低位         
		long s1 = b[1] & 0xff;       
		long s2 = b[2] & 0xff;      
		long s3 = b[3] & 0xff;        
//		long s4 = b[4] & 0xff;// 最低位      
//		long s5 = b[5] & 0xff;       
//		long s6 = b[6] & 0xff;       
//		long s7 = b[7] & 0xff; // s0不变      
		s1 <<= 8;        
		s2 <<= 16;       
		s3 <<= 24;        
//		s4 <<= 8 * 4;        
//		s5 <<= 8 * 5;       
//		s6 <<= 8 * 6;      
//		s7 <<= 8 * 7;        
//        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7; 
        s = s0 | s1 | s2 | s3 ; 
        return s;
	} 
	
	public static long bytesToLong8(byte[] b) {  
		long s = 0;        
		long s0 = b[0] & 0xff;// 最低位         
		long s1 = b[1] & 0xff;       
		long s2 = b[2] & 0xff;      
		long s3 = b[3] & 0xff;        
		long s4 = b[4] & 0xff;// 最低位      
		long s5 = b[5] & 0xff;       
		long s6 = b[6] & 0xff;       
		long s7 = b[7] & 0xff; // s0不变      
		s1 <<= 8;        
		s2 <<= 16;       
		s3 <<= 24;        
		s4 <<= 8 * 4;        
		s5 <<= 8 * 5;       
		s6 <<= 8 * 6;      
		s7 <<= 8 * 7;        
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;  
        return s;  
	} 
	
	/**
	 * byte[] 按16禁止输出
	 * @param buffer
	 * @return
	 */
	public static String get16FromBytes(byte[] buffer){
		StringBuilder sb = new StringBuilder("");
		for(byte b:buffer){
			if((b&0xff)<16){
				sb.append("0");
			}
			sb.append(Integer.toHexString(b&0xff)+" ");
		}
		return sb.toString();
	}




    public static double bytesToDouble(byte[] Array,int Pos) {
        long accum = 0;
        accum = Array[Pos+0] & 0xFF;
        accum |= (long)(Array[Pos+1] & 0xFF)<<8;
        accum |= (long)(Array[Pos+2] & 0xFF)<<16;
        accum |= (long)(Array[Pos+3] & 0xFF)<<24;
        accum |= (long)(Array[Pos+4] & 0xFF)<<32;
        accum |= (long)(Array[Pos+5] & 0xFF)<<40;
        accum |= (long)(Array[Pos+6] & 0xFF)<<48;
        accum |= (long)(Array[Pos+7] & 0xFF)<<56;
        return Double.longBitsToDouble(accum);
    }

    public static byte[] doubleToBytes(double Value) {
        long accum = Double.doubleToRawLongBits(Value);
        byte[] byteRet = new byte[8];
        byteRet[0] = (byte)(accum & 0xFF);
        byteRet[1] = (byte)((accum>>8) & 0xFF);
        byteRet[2] = (byte)((accum>>16) & 0xFF);
        byteRet[3] = (byte)((accum>>24) & 0xFF);
        byteRet[4] = (byte)((accum>>32) & 0xFF);
        byteRet[5] = (byte)((accum>>40) & 0xFF);
        byteRet[6] = (byte)((accum>>48) & 0xFF);
        byteRet[7] = (byte)((accum>>56) & 0xFF);
        return byteRet;
    }

    /**
     * 获取字符串的字节数组 默认的编码为GB2312
     * @param content 内容
     * @param contentLength 要填充的长度
     * @return
     */
    public static byte [] getBytesFromString(String content, int contentLength){
        byte [] contentBytes = new byte[contentLength];
        if (TextUtils.isEmpty(content)){
            return contentBytes;
        }
        try {
            byte[] orginData = content.getBytes(CODE_TYPE);
            for (int i = 0; i < orginData.length&&i<contentBytes.length; i++) {
                contentBytes[i] = orginData[i];
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  contentBytes;
    }


    public static String concat(String Str) {
        String s = null;
        if (Str.length() == 1) {
            s = "0" + Str;
        } else {
            s = Str;
        }
        return s;
    }

    /**********************************************************************
     * 功能描述：把i转化为长度len的正序byte[]，
     * 输入参数：  i:整形数据，
     * 输入参数：返回byte的长度，
     * 返回值： 长度为len的byte数组，
     * 其它说明：无，
     * 创建记录：版本号：1.00	作者：马杭斌		修改日期：2015/09/16
     ***********************************************************************/
    public static byte[] bigIntToByte(int i,int len){
        byte[] a = new byte[len];
        for(int j=0;j<len;j++){
            a[j] = (byte)(((0xff<<(8*j))&i)>>(8*j));
        }
        byte[] b = new byte[len];
        for(int m=0;m<len;m++){
            b[m] = a[len-1-m];
        }
        return b;
    }


    /**
     * 注释：short到字节数组的转换！
     *
     * @param
     * @return
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();//
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * 注释：字节数组到short的转换！
     *
     * @param b
     * @return
     */
    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    /**
     * bytes 转String
     * @param content
     * @return
     */
    public static String bytesToString(byte[] content){
        try {
            return new String(content, "GB2312").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }


    public static char byteToChar(byte[] b) {
        char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
        return c;
    }


    /**
     * @功能: 10进制串转为BCD码
     * @参数: 10进制串
     * @结果: BCD码
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }



    public static String parseDateStringFromBytes(byte[] dateBytes){
        if (dateBytes==null||dateBytes.length!=8){
            return "";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("20");
        for (int i = 0; i < dateBytes.length; i++) {
            if (i==6){
                sb.append(Integer.toHexString(dateBytes[i]&0xff));
            }else if (i==7){
                if ((dateBytes[i]&0xFF)<16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(dateBytes[i]&0xff));
            }
            else{
                if ((dateBytes[i]&0xFF)<16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(dateBytes[i]&0xff));
                sb.append(":");
            }
        }
        return sb.toString();
    }


    /**时间bsd编码*/
    public static final String DATE_FORMAT = "yyyy:MM:dd:HH:mm:ss:sss";

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);

    public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss:sss";

    public static final SimpleDateFormat FORMAT1 = new SimpleDateFormat(DATE_FORMAT_1);


    public static Date parseDateFromBytes(byte[] dateBytes){
        String text = parseDateStringFromBytes(dateBytes);
        try {
            return  FORMAT.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }


    /**
     * 解析bcd码获取时间
     * @param buffer
     * @return
     */
    public static final Date  parstDateByte(byte [] buffer){
        String dateStr = ByteUtils.get16FromBytes(buffer);

        String year = "20"+ dateStr.substring(0,2);
        String month = dateStr.substring(2,4);
        String day =    dateStr.substring(4,6);
        String hour =    dateStr.substring(6,8);
        String minute =   dateStr.substring(10,12);
        String second =   dateStr.substring(13,14);
        String ms     =   dateStr.substring(14,16);
        String str = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+":"+ms;
        try {
            Date date = FORMAT1.parse(str);
            return date;
        }catch (Exception e){
            return  new Date();
        }
    }

    public  static byte[] getDateBytes(Date date){
        String dateStr = FORMAT.format(date);
        return getDateBytes(dateStr);
    }

    /**
     * 日期的格式必须为 yyyy:MM:dd:HH:mm:ss:sss
     * @param
     * @return
     */
    public static byte[] getDateBytes(String dateStr){
        byte[] dateBytes = new byte[8];
        String [] strs = dateStr.split(":");
        int yy = Integer.parseInt(strs[0].substring(2,4));
        int ff1 = Integer.parseInt(strs[6].substring(0,1));
        int ff2 = Integer.parseInt(strs[6].substring(1,3));
        //L.i(TAG,"dateStr"+dateStr+";yy="+yy+";ff1="+ff1+";ff2="+ff2);
        for (int i = 0; i < dateBytes.length; i++) {
            if (i==0){
                dateBytes[i] = str2Bcd(""+yy)[0];
            }
            else if (i==6){
                dateBytes[i]=  str2Bcd(""+ff1)[0];
            }
            else if (i==7){
                dateBytes[i]=  str2Bcd(""+ff2)[0];
            }else {
                dateBytes[i] =  str2Bcd(strs[i])[0];
            }
        }
        return  dateBytes;
    }


    /**
     * 获取当前时间的秒数
     * @return
     */
    public static final int getCurrentSeconds(){
        return (int)(System.currentTimeMillis()/1000);
    }

    /**
     * 获取当前的秒数的bytes
     * @return
     */
    public static final byte[] getCurrentSecondsBytes(){
        return ByteUtils.intToBytes((int)(System.currentTimeMillis()/1000),4);
    }

    /**
     * 获取日期秒数的的bytes
     * @param date
     * @return
     */
    public static final byte[] getBytesFromDate(Date date){
        return ByteUtils.intToBytes((int)(date.getTime()/1000),4);
    }


}
