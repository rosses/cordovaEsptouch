package com.ogemray.smartconfig4demo.utils;

import android.text.TextUtils;
import android.util.Log;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *  this class is used to parse byte array easy
 */
public class BytesIO {

   

    private boolean _bigEndian;

    public byte[] _raw;

    private int _position;

   
    public int get_position() {
        return _position;
    }

    public void set_position(int _position) {
        this._position = _position;
    }

    public BytesIO(byte[] raw){
        _raw = raw;
        _position = 0;
        _bigEndian = false;
    }

    public BytesIO(byte[] raw, boolean bol)
    {
        _raw = raw;
        _position = 0;
        _bigEndian = bol;
    }

    public BytesIO(int pos, byte[] raw, boolean bol)
    {
        _raw = raw;
        _position = pos;
        _bigEndian = bol;
    }

   
    public void put(boolean value)
    {
        _raw[_position++] = value ? (byte)1 : (byte)0;
        //_raw.Add()
    }

   
    public void put(int index, boolean value)
    {
        _raw[index] = value ? (byte)1 : (byte)0;
    }

   
    public void put(byte value)
    {
        _raw[_position++] = value;
    }

   
    public void put(int index, byte value)
    {
        _raw[index] = value;
    }

   
    public void put(byte[] value)
    {
        if (value == null) return;

        for(byte item : value){
             _raw[_position++] = item;
        }
    }

   
    public void put(byte[] value, int len)
    {
        if (value == null)
            return;
        if (len > value.length)
        {
            System.arraycopy(value, 0, _raw, _position, value.length);
            //throw new Exception(" put(byte[] value, int len) value 数组长度：" + value.length + " 大于了要偏移的指针长度len：" + len.ToString() + "”，请检查内容！");
        }
        else
        {
           System.arraycopy(value, 0, _raw, _position, len);
        }
        _position += len;
    }


   
    public void put(int index, byte[] value, int len)
    {
        if (value == null)
            return;
       System.arraycopy(value, 0, _raw, index, len);
    }

   
    public void put(char ch)
    {
        _raw[_position++] = ByteUtils.charToByte(ch)[0];
    }

   
    public void put(int index, char ch)
    {
        _raw[index] = ByteUtils.charToByte(ch)[0];
    }



    public void put(char[] chars)
    {
        if (chars == null) return;
        for (char item : chars)
        _raw[_position++] =  ByteUtils.charToByte(item)[0];
    }

   
    public void put(int index, char[] chars)
    {
        if (chars == null) return;
        for(char item : chars)
        _raw[index++] = ByteUtils.charToByte(item)[0];
    }

   
    public void put(String value, int len )
    {
        if (TextUtils.isEmpty(value)){
            Log.e("TAG"," put(String value, int len = 0) value 字符串值“" + value + "”的长度大于了指定的len长度，请检查内容！");
            put(new byte[len]);
            return;
        }
        if (len != 0 && len < value.length())
        {
           Log.e("TAG"," put(String value, int len = 0) value 字符串值“" + value + "”的长度大于了指定的len长度，请检查内容！");
           put(new byte[len]);
           return;
        }
        byte[] data = ByteUtils.getBytesFromString(value,len);
        put(data);
        if (len > data.length)
        {
            for (int i = 0; i < len - data.length; i++)
            {
                put((byte)0);
            }
        }
    }


    public void put(InetAddress ip) throws Exception
    {
        if (ip == null)
            ip = InetAddress.getByName("0.0.0.0");
        String[] IPArr = ip.getHostAddress().split(".");
        byte[] IPBuff = new byte[4];
        IPBuff[0] = Byte.parseByte(IPArr[0]);
        IPBuff[1] = Byte.parseByte(IPArr[1]);
        IPBuff[2] = Byte.parseByte(IPArr[2]);
        IPBuff[3] = Byte.parseByte(IPArr[3]);
        put(IPBuff);
    }

   
    public void put(int index, InetAddress ip) throws Exception
    {
        if (ip == null) ip = InetAddress.getByName("0.0.0.0");
        String[] IPArr = ip.getHostAddress().split(".");
        byte[] IPBuff = new byte[4];
        IPBuff[0] = Byte.parseByte(IPArr[0]);
        IPBuff[1] = Byte.parseByte(IPArr[1]);
        IPBuff[2] = Byte.parseByte(IPArr[2]);
        IPBuff[3] = Byte.parseByte(IPArr[3]);
        put(index, IPBuff, IPBuff.length);
    }

   
    public void put(int index, String value, int len)
    {
        if (value == null) value = "";
        byte[] data = ByteUtils.getBytesFromString(value,len);
        put(index, data, data.length);
        if (len > data.length)
        {
            for (int i = 0; i < len - data.length; i++)
            {
                put(index + data.length + i, (byte)0);
            }
        }
    }

   
    public void put(short value)
    {
        if (_bigEndian)
        {
            _raw[_position++] = (byte)(value >> 8);
            _raw[_position++] = (byte)value;
        }
        else
        {
            _raw[_position++] = (byte)value;
            _raw[_position++] = (byte)(value >> 8);
        }
    }

   
    public void put(int index, short value)
    {
        if (_bigEndian)
        {
            _raw[index++] = (byte)(value >> 8);
            _raw[index] = (byte)value;
        }
        else
        {
            _raw[index++] = (byte)value;
            _raw[index] = (byte)(value >> 8);

        }
    }


    public void put(int value)
    {
        if (_bigEndian)
        {
            _raw[_position++] = (byte)(value >> 0x18);
            _raw[_position++] = (byte)(value >> 0x10);
            _raw[_position++] = (byte)(value >> 8);
            _raw[_position++] = (byte)(value);
        }
        else
        {
            _raw[_position++] = (byte)(value);
            _raw[_position++] = (byte)(value >> 8);
            _raw[_position++] = (byte)(value >> 0x10);
            _raw[_position++] = (byte)(value >> 0x18);
        }
    }


   
    public void put(int index,int value)
    {
        if (_bigEndian)
        {
            _raw[index++] = (byte)(value >> 0x18);
            _raw[index++] = (byte)(value >> 0x10);
            _raw[index++] = (byte)(value >> 8);
            _raw[index] = (byte)(value);
        }
        else
        {
            _raw[index++] = (byte)(value);
            _raw[index++] = (byte)(value >> 8);
            _raw[index++] = (byte)(value >> 0x10);
            _raw[index] = (byte)(value >> 0x18);
        }
    }

   
    public void put(long value)
    {
        if (_bigEndian)
        {
            _raw[_position++] = (byte)(value >> 0x38);
            _raw[_position++] = (byte)(value >> 0x30);
            _raw[_position++] = (byte)(value >> 0x28);
            _raw[_position++] = (byte)(value >> 0x20);
            _raw[_position++] = (byte)(value >> 0x18);
            _raw[_position++] = (byte)(value >> 0x10);
            _raw[_position++] = (byte)(value >> 8);
            _raw[_position++] = (byte)value;
        }
        else
        {
            _raw[_position++] = (byte)value;
            _raw[_position++] = (byte)(value >> 8);
            _raw[_position++] = (byte)(value >> 0x10);
            _raw[_position++] = (byte)(value >> 0x18);
            _raw[_position++] = (byte)(value >> 0x20);
            _raw[_position++] = (byte)(value >> 0x28);
            _raw[_position++] = (byte)(value >> 0x30);
            _raw[_position++] = (byte)(value >> 0x38);
        }
    }
   

    public void put(int index, long value)
    {
        if (_bigEndian)
        {
            _raw[index++] = (byte)(value >> 0x38);
            _raw[index++] = (byte)(value >> 0x30);
            _raw[index++] = (byte)(value >> 0x28);
            _raw[index++] = (byte)(value >> 0x20);
            _raw[index++] = (byte)(value >> 0x18);
            _raw[index++] = (byte)(value >> 0x10);
            _raw[index++] = (byte)(value >> 8);
            _raw[index] = (byte)value;
        }
        else
        {
            _raw[index++] = (byte)value;
            _raw[index++] = (byte)(value >> 8);
            _raw[index++] = (byte)(value >> 0x10);
            _raw[index++] = (byte)(value >> 0x18);
            _raw[index++] = (byte)(value >> 0x20);
            _raw[index++] = (byte)(value >> 0x28);
            _raw[index++] = (byte)(value >> 0x30);
            _raw[index] = (byte)(value >> 0x38);
        }
    }


    public void put(Date value)
    {
        byte[] byteArr = ByteUtils.getBytesFromDate(value);

        this.put(byteArr);
    }


    public void put(int index, Date value)
    {
        byte[] byteArr = ByteUtils.getBytesFromDate(value);
        this.put(index, byteArr, byteArr.length);
    }


    public void put(String macString){
        byte[] mac = ByteUtils.getMacBytes(macString);
        this.put(mac);
    }


    public void put(int index,String macString){
        byte[] mac = ByteUtils.getMacBytes(macString);
        this.put(index, mac, mac.length);
    }


    public void putVersionString(String versionName){
        if (versionName==null||!versionName.contains("\\.")){
            Log.e("TAG","byteio put versionName 为null 或不含‘.’ ");
            this.put(new byte[2]);
            return;
        }
        String[] versions = versionName.split("\\.") ;
        put(Byte.parseByte(versions[0]));
        put(Byte.parseByte(versions[1]));

    }


    public void putVersionString(int index,String versionName){
        if (versionName==null||!versionName.contains("\\.")){
            Log.e("TAG","byteio put versionName 为null 或不含‘.’ ");
            this.put(new byte[2]);
            return;
        }
        String[] versions = versionName.split("\\.") ;
        put(index,new byte[]{Byte.parseByte(versions[0]),Byte.parseByte(versions[1])},2);
    }



    public boolean getBoolean()
    {
        return get() == 0 ? false : true;
    }

   

   
    public boolean getBoolean(int index)
    {
        return _raw[index] == 0 ? false : true;
    }

   
    public byte get()
    {
        byte value = _raw[_position++];
        return value;
    }

   
    public byte get(int index)
    {
        return _raw[index];
    }

   

    public byte[] getBytes(int count)
    {
        if (count < 0)
        {
            Log.e("TAG"," byte[] getBytes(int count) 参数count值“" + count + "”不正确！");
            //return null;
        }

        if (_position + count > _raw.length)
        {
            Log.e("TAG"," byte[] getBytes(int count) 参数count值“" + count + "”加上 当前位置 超出了数组总长度！");
            //return null;
        }
        byte[] buffer = new byte[count];
        System.arraycopy(_raw, _position, buffer, 0, count);
        _position += count;
        //if (!_bigEndian) Array.Reverse(buffer);//小端
        return buffer;
    }

    public byte[] getBytes() throws Exception
    {
        return getBytes(0, _position);
    }

   

    public byte[] getBytes(int index, int count)
    {
        if (count < 0 || index < 0)
            Log.e("TAG","byte[] getBytes(int index, int count)参数index值“" + index + "”或count值“" + count + "”不正确！");

        if (index + count > _raw.length)
            Log.e("TAG","byte[] getBytes(int index, int count)参数count值“" + count + "”加上 当前位置 超出了数组总长度！");

        byte[] buffer = new byte[count];

        System.arraycopy(_raw, index, buffer, 0, count);
        //if (!_bigEndian) Array.Reverse(buffer);//小端
        return buffer;
    }

   
    public char getChar()
    {
        return (char) (get());
    }

    public char getChar(int index)
    {
        return (char)(_raw[index]);
    }

   
    public short getShort()
    {
        if (_bigEndian)
        {
            return (short)((short)(get()&0xFF) << 8 | (short)(get()&0xFF));
        }
        else
        {
            return ByteUtils.byteToShort(getBytes(2));
        }
    }

   
    public short getShort(int index)
    {
        if (_bigEndian)
        {
            return (short)(_raw[index++] << 8 | _raw[index]);
        }
        else
            return ByteUtils.byteToShort(getBytes(index,2));
    }

   
    public int getInt()
    {
        if (_bigEndian)
        {
            return (get() << 0x18 | get() << 0x10 | get() << 0x08 | get());
        }
        else
        {
            return ByteUtils.bytesToInt(getBytes(4));
        }
    }

   
    public int getInt(int index)
    {
        if (_bigEndian)
            return (int)(_raw[index] << 0x18 | _raw[index + 1] << 0x10 | _raw[index + 2] << 0x08 | _raw[index + 3]);
        else
            return ByteUtils.bytesToInt(getBytes(index,4));
    }


    public String getString(int length)
    {
        byte[] data = getBytes(length);
        return ByteUtils.bytesToString(data).trim();
    }


    public String getString(int index, int length) throws Exception
    {
        byte[] data = getBytes(index, length);
        return ByteUtils.bytesToString(data).trim();
    }


   


    public InetAddress getIPAddress() throws Exception
    {
        byte[] data = getBytes(4);
        String ipStr = (data[0]&0xFF) + "." + (data[1]&0xFF)  + "." + (data[2]&0xFF)  + "." + (data[3]&0xFF) ;
        return InetAddress.getByName(ipStr);
    }

    public String getIPString()
    {
        byte[] data = getBytes(4);
        String ipStr = (data[0]&0xFF) + "." + (data[1]&0xFF)  + "." + (data[2]&0xFF)  + "." + (data[3]&0xFF) ;
        return ipStr;
    }


    public InetAddress getIPAddress(int index) throws Exception
    {
        byte[] data = getBytes(index, 4);
        String ipStr = (data[0]&0xFF) + "." + (data[1]&0xFF)  + "." + (data[2]&0xFF)  + "." + (data[3]&0xFF) ;
        return InetAddress.getByName(ipStr);
    }

    public String getIPString(int index)
    {
        byte[] data = getBytes(index, 4);
        String ipStr = (data[0]&0xFF) + "." + (data[1]&0xFF)  + "." + (data[2]&0xFF)  + "." + (data[3]&0xFF) ;
        return ipStr;
    }



   
    public long getLong()
    {
        if (_bigEndian)
        {
            return (long)((long)get() << 0x38 | (long)get() << 0x30 | (long)get() << 0x28 | (long)get() << 0x20 | (long)get() << 0x18 | (long)get() << 0x10 | (long)get() << 0x08 | (long)get());
        }
        else
        {
            return ByteUtils.bytesToLong(getBytes(8));
        }

    }

   
    public long getLong(int index)
    {
        if (_bigEndian)
        {
            return (long)((long)_raw[index] << 0x38 | (long)_raw[index + 1] << 0x30 | (long)_raw[index + 2] << 0x28 | (long)_raw[index + 3] << 0x20 | (long)_raw[index + 4] << 0x18 | (long)_raw[index + 5] << 0x10 | (long)_raw[index + 6] << 0x08 | (long)_raw[index + 7]);
        }
        else
        {
            return ByteUtils.bytesToLong(getBytes(index,8));
        }

    }
    public Date getDate(){
        byte[] date = this.getBytes(4);
        Date date1 = new Date(ByteUtils.bytesToInt(date)*1000);
        return  date1;
    }

    public String getDateString(){
        Date date = getDate();
        return  format.format(date);
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   



    private String concat(byte value){
        String s =Integer.toHexString(value);
        if (s.length()==1){
            return  "0"+s;
        }else{
            return s;
        }
    }

   
    public void resetPosition(int index)
    {
        if (index < 0) {
            _position = 0;
        }
        else if (_raw != null && index >= _raw.length) {
            _position = _raw.length - 1;
        }
        else {
            _position = index;
        }
    }

   
    public boolean peek()
    {
        if (_raw == null) return false;
        return _position < _raw.length;
    }

   
    public int bytesLeft()
    {
        return _raw.length - _position;
    }


    public String getMacString(){
        byte[] macs= this.getBytes(6);
        return ByteUtils.getMacString(macs);
    }


    public String getMacString(int index){
        byte[] macs= this.getBytes(index,4);
        return ByteUtils.getMacString(macs);
    }


    public String getVersionString(){
        byte[] version = this.getBytes(2);
        return version[0]+"."+concat(version[1]);
    }


    public String getVersionString(int index){
        byte[] version = this.getBytes(index,2);
        return version[0]+"."+concat(version[1]);
    }


}


