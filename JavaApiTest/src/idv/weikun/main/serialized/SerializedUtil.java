package idv.weikun.main.serialized;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SerializedUtil {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<GPN_CHECK_ITEM> list = new ArrayList<GPN_CHECK_ITEM>();
		
		
		try {
			String hex = new SerializedUtil().getSerializeObjectString(list);
			System.out.println("object to string :" + hex);
			
			List<GPN_CHECK_ITEM> list2 = new SerializedUtil().getListFromSerializedString(hex);
			System.out.println("string to object :"+list2.size());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	//encapsulate
	public void encapsulateItem(
			ArrayList itemList, Map checkMap, String items,
			String publishDate, String checkCategory, String checkType){
		if(items!=null && items.length()>0){
			String[] id = items.split(",");
			for (int i = 0; i < id.length; i++) {
				GPN_CHECK_ITEM item = new GPN_CHECK_ITEM();
				item.setCheckYear(publishDate);
				item.setCheckCategory(checkCategory);
				item.setCheckType(checkType);
				
				if(checkMap.containsKey(id[i])){
					item.setCheckId(id[i]);
					item.setCheckName((String)checkMap.get(id[i]));
				}else{
					item.setCheckId(id[i]);
				}
				itemList.add(item);
			}
		}
	}
	
	//transfer to serilize string
	public String getSerializeObjectString(ArrayList<?> itemList) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(itemList);
		oos.flush();
		oos.close();
		
		byte[] obj = baos.toByteArray();
		StringBuffer buf = new StringBuffer(1024);
		for (int i = 0; i < obj.length; i++) {
			byte b = obj[i];
			String tmp = Integer.toHexString(b & 0xff);
			if (tmp.length() == 1) {
				tmp = "0" + tmp;
			}
			buf.append(tmp);
		}
		
		return buf.toString();
	}
	
	//decode and back to List
	public static List getListFromSerializedString(String hex) throws Exception{

		char[] c = hex.toCharArray();
		byte[] reverseData = new byte[c.length / 2];
		for (int i = 0; i < c.length / 2; i++) {
			String tmp = hex.substring(2 * i, 2 * i + 2);
			reverseData[i] = (byte) Integer.parseInt(tmp, 16);
		}
		
		ByteArrayInputStream bais = new ByteArrayInputStream(reverseData, 0, reverseData.length);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object rawobj = ois.readObject();
		
		if (rawobj instanceof ArrayList) {
			return (ArrayList) rawobj;
		}
		
		return Collections.EMPTY_LIST;
	}
	
	public static byte[] writeZipObject(Object obj){
		byte[] buf = null; 
		try{
			ByteArrayOutputStream ayout = new ByteArrayOutputStream();
			GZIPOutputStream gout = new GZIPOutputStream(ayout);
			ObjectOutputStream out = new ObjectOutputStream(gout);
			out.writeObject(obj);
			out.flush();
			out.close();
			gout.close();
			buf = ayout.toByteArray();
			ayout.close();
		}catch(Exception e){}
		
		return buf; 
	}
	
	public static <T>T readZipObject(byte[] v_fuf){ 
		Object obj=null; 
		try { 
			ByteArrayInputStream aryin = new ByteArrayInputStream(v_fuf); 
			GZIPInputStream gin = new GZIPInputStream(aryin); 
			ObjectInputStream in = new ObjectInputStream(gin); 
			obj=in.readObject(); 
			aryin.close(); 
			gin.close(); 
			in.close(); 
		}catch(Exception exc){}
		
		return (T)obj; 
	} 
	
	
}
