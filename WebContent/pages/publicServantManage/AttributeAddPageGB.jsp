<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}
/*  .dasda td{width: 1020px;padding-bottom: 5px;} */
</style>

<table class="dasda" style="width:100%;margin-top: 50px;">
                            
	<tr >
	  		<odin:checkbox property="sheng1"  value="0" label="ʡ�ܸɲ�"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;��������:</span></td>	  
		        <td><odin:checkbox property="sheng2" label="������"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="sheng3" label="������"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="sheng4" label="������"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;�������:</span></td>	
		        <td><odin:checkbox property="sheng5" label="��ί����"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="sheng6" label="����������"></odin:checkbox>	</td>		  	  				  	 
		 	    <td><odin:checkbox property="sheng7" label="���˴����"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="sheng8" label="����Э����"></odin:checkbox></td>	
		        <td><odin:checkbox property="sheng9" label="����">      </odin:checkbox>	</td>	
		        <td><odin:checkbox property="sheng10" label="��ί�����"></odin:checkbox></td>	
		        <td><odin:checkbox property="sheng11" label="������������ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="sheng12" label="����"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="sheng13" label="��ְ"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
	<tr >
	  		<odin:checkbox property="shi1" label="�йܸɲ�"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;��������:</span></td>	  
		        <td><odin:checkbox property="shi2" label="������"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shi3" label="������"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shi4" label="���Ƽ�"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;ְ�����1:</span></td>	
		        <td><odin:checkbox property="shi5" label="�����쵼"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shi6" label="�����쵼������Ա"></odin:checkbox>	</td>		  	  				  	 
		 	    <td><odin:checkbox property="shi7" label="�����쵼"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shi8" label="����Ա(���ؼ�����)"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi9" label="������Ա(���ؼ�����)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi10" label="�����Ա(������Ա)"></odin:checkbox></td>			       		       		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      
		        <td><odin:checkbox property="shi11" label="�����Ա(���Ƽ�)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi12" label="��(�С���)��ί�����"></odin:checkbox>	</td>			       		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;ְ�����2:</span></td>			        	       		       		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ְ��:</span></td>	      
		        <td><odin:checkbox property="shi13" label="��ֱ��λ��ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi14" label="��ֱ��λ��ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi15" label="��ֱ��λ����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi16" label="��(�С���)��ְ"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi17" label="��(�С���)��ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi18" label="��(�С���)����"></odin:checkbox>	</td>		       		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ְ��:</span></td>	      
		        <td><odin:checkbox property="shi19" label="��ίίԱ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi20" label="��ί��ίԱ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi21" label="�м�ί��ί"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi22" label="�м�ίίԱ"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi23" label="���˴�ί(רְ)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi24" label="���˴�ί(��ְ)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi25" label="����Э��ί(רְ)"></odin:checkbox>	</td>			         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      			        
		        <td><odin:checkbox property="shi26" label="����Э��ί(��ְ)"></odin:checkbox>	</td>		       		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;��λ����:</span></td>			        	       		       		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi27" label="��ֱ����"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����:</span></td>	      
		        <td><odin:checkbox property="shi28" label="��ί����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi29" label="��������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi30" label="�˴���Э"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi31" label="��Ժ"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi32" label="����Э��"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi33" label="��������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi34" label="��УԺ��"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi35" label="������ҵ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi36" label="��ʱ����"></odin:checkbox>	</td>			         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;С��:</span></td>	      
		        <td><odin:checkbox property="shi37" label="�����ۺ�"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi38" label="�ͼ���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi39" label="��֯����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi40" label="��ʶ��̬"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi41" label="ͳս����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi42" label="����ά��"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi43" label="���ò�ó"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi44" label="���轻ͨ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi45" label="ũ�ʻ���"></odin:checkbox>	</td>		        		        			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi46" label="�������"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi47" label="Ⱥ������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi48" label="����Ժ��"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi49" label="��ְԺУ"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi50" label="ҽ�ƻ���"></odin:checkbox>	</td>			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi51" label="�˴�"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
    <tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi52" label="�˴����鳤"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi53" label="�˴�רְ��ί"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi54" label="�˴��ְ��ί"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi55" label="�˴�ί������"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi56" label="�˴�ί�Ҹ�����"></odin:checkbox>	</td>			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi57" label="��Э"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
    <tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi58" label="��Э���鳤"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi59" label="��Эרְ��ί"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi60" label="��Э��ְ��ί"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi61" label="��Эί������"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi62" label="��Эί�Ҹ�����"></odin:checkbox>	</td>			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi63" label="�м�ί��פ�ͼ���"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
    <tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi64" label="�ͼ����������鳤"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi65" label="�ͼ��鸱�����鳤"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi66" label="�ͼ��鸱�������鳤"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi67" label="�ͼ������Ƽ��鳤"></odin:checkbox>	</td>		        			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi68" label="������"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����:</span></td>	      
		        <td><odin:checkbox property="shi69" label="������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi70" label="������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi71" label="������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi72" label="������"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi73" label="����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi74" label="۴����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi75" label="��ɽ��"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi76" label="������"></odin:checkbox>	</td>			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����:</span></td>	      
		        <td><odin:checkbox property="shi77" label="��������ί����"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi78" label="��������������"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi79" label="�������˴����"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi80" label="��������Э����"></odin:checkbox></td>
		        <td><odin:checkbox property="shi81" label="����������"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi82" label="��������ί"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi83" label="����������"></odin:checkbox></td>	        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ְ��:</span></td>	      
		        <td><odin:checkbox property="shi84" label="���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi85" label="��������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi86" label="�����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi87" label="������������"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi88" label="��ί���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi89" label="��֯����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi90" label="��������"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi91" label="�����ֳ�"></odin:checkbox>	</td>		        
		        <td><odin:checkbox property="shi92" label="ͳս����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi93" label="������ί"></odin:checkbox>	</td>			        			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      
		        <td><odin:checkbox property="shi94" label="ũҵ����������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi95" label="��ҵ����������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi96" label="�ǽ�����������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi97" label="��ó����������"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi98" label="�Ľ̸���������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi99" label="��������������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi100" label="�˴�����"></odin:checkbox>	</td>		        			        			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      
		        <td><odin:checkbox property="shi101" label="�˴�����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi102" label="��Э��ϯ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi103" label="��Э����ϯ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi104" label="��ԺԺ��"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi105" label="��쳤"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi106" label="��ί�����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi107" label="����"></odin:checkbox>	</td>		        			        			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi108" label="��ֱ��λ"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi109" label="��ְ"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�쵼��������:</span></td>			       
		        <td><odin:checkbox property="shi110" label="�����ۺϲ���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi111" label="�˴���Э����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi112" label="�ͼ��첿��"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi113" label="��֯���²���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi114" label="��ʶ��̬����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi115" label="ͳս���Ȳ���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi116" label="����ά�Ȳ���"></odin:checkbox>	</td>	
		        			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	
		        <td><odin:checkbox property="shi117" label="���ò�ó����"></odin:checkbox>	</td>		       
		        <td><odin:checkbox property="shi118" label="���轻ͨ����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi119" label="ũ�ʻ�������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi120" label="�ƽ���������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi121" label="���������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi122" label="Ⱥ������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi123" label="����Ժ��"></odin:checkbox>	</td>			        			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
		        <td><odin:checkbox property="shi124" label="��ְԺУ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi125" label="ҽ�ƻ���"></odin:checkbox>	</td>			       
		        <td><odin:checkbox property="shi126" label="������ҵ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi127" label="������(԰��)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi128" label="��(�С���)�����쵼"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi129" label="��������ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi130" label="���������쵼"></odin:checkbox>	</td>			        			        			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>		       	
		        <td><odin:checkbox property="shi131" label="�Կ�֧Ԯ"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi132" label="��ת����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi133" label="��ѧ�����"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi134" label="��ɲ�"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr >
	  		<odin:checkbox property="shihou1" label="�йܺ󱸸ɲ�"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;��λ����:</span></td>	  
		        <td><odin:checkbox property="shihou2" label="�����쵼��λ1��"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou3" label="�����쵼��λ2��"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou4" label="�����쵼��λ3��������"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou5" label="�����쵼��λ(���Ƹ���)"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;������λ:</span></td>	  
		        <td><odin:checkbox property="shihou6" label="������"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou7" label="��������"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou8" label="�˴���Э��Ժ"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou9" label="Ⱥ������"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou10" label="��УԺ��"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou11" label="������ҵ"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou12" label="������"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou13" label="������"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="shihou14" label="������"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou15" label="������"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou16" label="����"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou17" label="۴����"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou18" label="��ɽ��"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou19" label="������"></odin:checkbox></td>		  	  				  	 		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;��������:</span></td>	  
		        <td><odin:checkbox property="shihou20" label="�����ۺϲ���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou21" label="�˴���Э����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou22" label="�ͼ��첿��"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou23" label="��֯���²���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou24" label="��ʶ��̬����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou25" label="ͳս���Ȳ���"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou26" label="����ά�Ȳ���"></odin:checkbox>	</td>	
		 	   </tr>
			</table>
		</td> 
	</tr>	
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>	    
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>    
		        <td><odin:checkbox property="shihou27" label="���ò�ó����"></odin:checkbox>	</td>		       
		        <td><odin:checkbox property="shihou28" label="���轻ͨ����"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou29" label="ũ�ʻ�������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou30" label="�ƽ���������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou31" label="���������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou32" label="Ⱥ������"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou33" label="����Ժ��"></odin:checkbox>	</td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>		
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>   			        
		        <td><odin:checkbox property="shihou34" label="��ְԺУ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou35" label="ҽ�ƻ���"></odin:checkbox>	</td>			       
		        <td><odin:checkbox property="shihou36" label="������ҵ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou37" label="������(԰��)"></odin:checkbox>	</td>		
		        <td><odin:checkbox property="shihou38" label="��������ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou39" label="���������쵼"></odin:checkbox>	</td>		       
		        <td><odin:checkbox property="shihou40" label="��ѧ�����"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shihou41" label="��ɲ�"></odin:checkbox>	</td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;��ְ���:</span></td>	  
		        <td><odin:checkbox property="shihou42" label="�����ʡֱ���ع�ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou43" label="��ֱ���ع�ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou44" label="�ص㹤�����ص㹤�̹�ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou45" label="����(�ֵ�)��ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou46" label="��ҵ��ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou47" label="������ְ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou48" label="�Կ�֧Ԯ"></odin:checkbox>	</td>	
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;�������ѵ���:</span></td>	  
		        <td><odin:checkbox property="shihou49" label="�μӹ��������������ѵ"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou50" label="δ�μӹ��������������ѵ"></odin:checkbox>	</td>			       
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;ѡ����:</span></td>	  
		        <td><odin:checkbox property="shihou51" label="��"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou52" label="��"></odin:checkbox>	</td>			       
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr >
	  		<odin:checkbox property="jd1" label="����(�ֵ�)������ְ"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;������:</span></td>	  
		        <td><odin:checkbox property="jd2" label="������"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="jd3" label="������"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="jd4" label="������"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="jd5" label="������"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="jd6" label="����"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="jd7" label="۴����"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="jd8" label="��ɽ��"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="jd9" label="������"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;������λ:</span></td>	  
		        <td><odin:checkbox property="jd10" label="����(�ֵ�)��(��)ί���"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="jd11" label="���򳤡��ֵ����´�����"></odin:checkbox></td>		  	  				  	 
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr >
	  		<odin:checkbox property="ldbywy1" label="������һίԱ"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy2" label="������"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy3" label="ȫ��"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy4" label="ʡ��"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy5" label="��ֱ����"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy6" label="������"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy7" label="�˴����"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy8" label="ȫ��"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy9" label="ʡ��"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy10" label="��ֱ����"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy11" label="������"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy12" label="��ЭίԱ"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy13" label="ȫ��"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy14" label="ʡ��"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy15" label="��ֱ����"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy16" label="������"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	
	
	
	<!-- <tr>
		<td><button onclick=" location=location ">ˢ��</button></td>
	</tr> -->
</table>

<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="id" title="����id" ></odin:hidden>	
</body>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("ATTRIBUTE")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A29")%>;
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});


function save(){
	radow.doEvent('save');
}
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}

Ext.onReady(function(){
	if(fieldsDisabled!=""){
		var inputs = document.getElementsByTagName("input");
		//var inputs = document.getElementsByTagName("input");
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].type == "checkbox") {
            	$(inputs[i]).addClass('bgclor');
            	inputs[i].onclick = function(){return false;};
               // boxs.push(inputs[i])
            }
        } 
	}
});

</script>
