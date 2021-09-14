package com.insigma.siis.local.module.common.codeparameter;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.VSSupport;
import com.insigma.siis.local.business.common.bs.CodeParameterBS;
import com.insigma.siis.local.business.common.bs.CodeParameterDTO;
import com.insigma.siis.local.business.common.bs.CodeParameterItemDTO;


public class CodeParameterVS extends VSSupport {
	
	/**
	 * ��ȡ�������AA09�е�ȫ��������ҳ����ʾ
	 * @return List<CodeParameterDTO>
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author ����
	 */
	public List<CodeParameterDTO> loadBasicMessage() throws AppException {

		CodeParameterBS codeParameterBS = new CodeParameterBS();
		return  codeParameterBS.loadBasicMessage(); 
	}
	
	/**
	 * ��ȡ�������AA10�е�ȫ��������ҳ����ʾ
	 * @return List<CodeParameterItemDTO>
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author ����
	 */
	public List<CodeParameterItemDTO> loadParameterMessage(String aaa100) throws AppException {
		
		CodeParameterBS codeParameterBS = new CodeParameterBS();
		return codeParameterBS.loadParameterMessage(aaa100);
	}
	
	
	/**
	 * �������������
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author ����
	 */
	public void addParameter(String aaa100, String aaa101) throws AppException {
		
		CodeParameterDTO dto = new CodeParameterDTO();
		dto.setAaa100(aaa100);
		dto.setAaa101(aaa101);
		
		CodeParameterBS codeParameterBS = new CodeParameterBS();
		codeParameterBS.addParameter(dto);
		
	}
	
	/**
	 * ����Jason�ִ�
	 * @return ParameterEditDTO
	 * @throws AppException
	 * @updated 2008-05-20
	 * @author ����
	 */
	public void deleteParameter(String aaa100) throws AppException {
		
		CodeParameterBS codeParameterBS = new CodeParameterBS();
		codeParameterBS.deleteParameter(aaa100);
	
	}
	
	/**
	 * ����AA10��
	 * @param codeParameterFormDTO
	 * @throws AppException
	 * @updated 2008-04-21
	 * @author ����
	 */
	public void save(CodeParameterFormDTO codeParameterFormDTO) throws AppException{
		
		CodeParameterBS codeParameterBS = new CodeParameterBS();
		
		List<CodeParameterItemDTO> list = new  ArrayList<CodeParameterItemDTO>();
		JSONArray jList=JSONArray.fromObject(codeParameterFormDTO.getList2Data());
		for(int i=0;i<jList.size();i++){
			CodeParameterItemDTO dto = new CodeParameterItemDTO();
			JSONObject jObj=jList.getJSONObject(i);
			dto.setAaz093(jObj.getLong("aaz093"));
			dto.setAaa100(jObj.getString("aaa100"));
			dto.setAaa102(jObj.getString("aaa102"));
			dto.setAaa103(jObj.getString("aaa103"));
			dto.setAaa105(jObj.getString("aaa105"));
			dto.setAae100(jObj.getString("aae100"));
			dto.setAaa104(jObj.getString("aaa104"));
			dto.setFlag(jObj.getLong("flag"));
			list.add(dto);
		}
		
		codeParameterBS.setList(list);
		
		List<CodeParameterItemDTO> delList = new  ArrayList<CodeParameterItemDTO>();
		jList=JSONArray.fromObject(codeParameterFormDTO.getDeleteList());
		for(int i=0;i<jList.size();i++){
			CodeParameterItemDTO dto = new CodeParameterItemDTO();
			JSONObject jObj=jList.getJSONObject(i);
			dto.setAaz093(jObj.getLong("aaz093"));
			dto.setAaa100(jObj.getString("aaa100"));
			dto.setAaa102(jObj.getString("aaa102"));
			dto.setAaa103(jObj.getString("aaa103"));
			dto.setAaa105(jObj.getString("aaa105"));
			dto.setAae100(jObj.getString("aae100"));
			dto.setAaa104(jObj.getString("aaa104"));
			dto.setFlag(jObj.getLong("flag"));
			delList.add(dto);
		}
		
		codeParameterBS.setDelList(delList);
	
		codeParameterBS.save();
	
	}
	
}	
