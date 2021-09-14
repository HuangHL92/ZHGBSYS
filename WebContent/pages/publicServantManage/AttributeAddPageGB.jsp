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
	  		<odin:checkbox property="sheng1"  value="0" label="省管干部"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;行政级别:</span></td>	  
		        <td><odin:checkbox property="sheng2" label="正厅级"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="sheng3" label="副厅级"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="sheng4" label="正处级"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;班子类别:</span></td>	
		        <td><odin:checkbox property="sheng5" label="市委班子"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="sheng6" label="市政府班子"></odin:checkbox>	</td>		  	  				  	 
		 	    <td><odin:checkbox property="sheng7" label="市人大班子"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="sheng8" label="市政协班子"></odin:checkbox></td>	
		        <td><odin:checkbox property="sheng9" label="法检">      </odin:checkbox>	</td>	
		        <td><odin:checkbox property="sheng10" label="纪委副书记"></odin:checkbox></td>	
		        <td><odin:checkbox property="sheng11" label="县市区党政正职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="sheng12" label="其他"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="sheng13" label="挂职"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
	<tr >
	  		<odin:checkbox property="shi1" label="市管干部"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;行政级别:</span></td>	  
		        <td><odin:checkbox property="shi2" label="正处级"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shi3" label="副处级"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shi4" label="正科级"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;职务类别1:</span></td>	
		        <td><odin:checkbox property="shi5" label="正处领导"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shi6" label="副处领导、调研员"></odin:checkbox>	</td>		  	  				  	 
		 	    <td><odin:checkbox property="shi7" label="副处领导"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shi8" label="调研员(正县级待遇)"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi9" label="副调研员(副县级待遇)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi10" label="党组成员(副调研员)"></odin:checkbox></td>			       		       		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      
		        <td><odin:checkbox property="shi11" label="党组成员(正科级)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi12" label="县(市、区)纪委副书记"></odin:checkbox>	</td>			       		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;职务类别2:</span></td>			        	       		       		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职务:</span></td>	      
		        <td><odin:checkbox property="shi13" label="市直单位正职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi14" label="市直单位副职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi15" label="市直单位其他"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi16" label="县(市、区)正职"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi17" label="县(市、区)副职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi18" label="县(市、区)其他"></odin:checkbox>	</td>		       		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职务:</span></td>	      
		        <td><odin:checkbox property="shi19" label="市委委员"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi20" label="市委候补委员"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi21" label="市纪委常委"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi22" label="市纪委委员"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi23" label="市人大常委(专职)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi24" label="市人大常委(兼职)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi25" label="市政协常委(专职)"></odin:checkbox>	</td>			         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      			        
		        <td><odin:checkbox property="shi26" label="市政协常委(兼职)"></odin:checkbox>	</td>		       		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;单位大类:</span></td>			        	       		       		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi27" label="市直部门"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中类:</span></td>	      
		        <td><odin:checkbox property="shi28" label="党委部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi29" label="政府部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi30" label="人大政协"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi31" label="两院"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi32" label="团体协会"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi33" label="民主党派"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi34" label="高校院所"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi35" label="国有企业"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi36" label="临时机构"></odin:checkbox>	</td>			         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;小类:</span></td>	      
		        <td><odin:checkbox property="shi37" label="党政综合"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi38" label="纪检监察"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi39" label="组织人事"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi40" label="意识形态"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi41" label="统战外侨"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi42" label="政法维稳"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi43" label="经济财贸"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi44" label="建设交通"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi45" label="农资环保"></odin:checkbox>	</td>		        		        			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi46" label="社会事务"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi47" label="群众团体"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi48" label="科研院所"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi49" label="高职院校"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi50" label="医疗机构"></odin:checkbox>	</td>			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi51" label="人大"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
    <tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi52" label="人大秘书长"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi53" label="人大专职常委"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi54" label="人大兼职常委"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi55" label="人大委室主任"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi56" label="人大委室副主任"></odin:checkbox>	</td>			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi57" label="政协"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
    <tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi58" label="政协秘书长"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi59" label="政协专职常委"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi60" label="政协兼职常委"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi61" label="政协委室主任"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi62" label="政协委室副主任"></odin:checkbox>	</td>			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi63" label="市纪委派驻纪检组"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
    <tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      		        	        
		        <td><odin:checkbox property="shi64" label="纪检组正处级组长"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi65" label="纪检组副处级组长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi66" label="纪检组副处级副组长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi67" label="纪检组正科级组长"></odin:checkbox>	</td>		        			        		         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi68" label="县市区"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr> 
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区域:</span></td>	      
		        <td><odin:checkbox property="shi69" label="海曙区"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi70" label="江东区"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi71" label="江北区"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi72" label="北仑区"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi73" label="镇海区"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi74" label="鄞州区"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi75" label="象山县"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi76" label="宁海县"></odin:checkbox>	</td>			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;班子:</span></td>	      
		        <td><odin:checkbox property="shi77" label="县市区党委班子"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi78" label="县市区政府班子"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi79" label="县市区人大班子"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi80" label="县市区政协班子"></odin:checkbox></td>
		        <td><odin:checkbox property="shi81" label="县市区法检"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi82" label="县市区纪委"></odin:checkbox></td>	
		        <td><odin:checkbox property="shi83" label="县市区其他"></odin:checkbox></td>	        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职务:</span></td>	      
		        <td><odin:checkbox property="shi84" label="书记"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi85" label="县市区长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi86" label="副书记"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi87" label="常务副县市区长"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi88" label="纪委书记"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi89" label="组织部长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi90" label="宣传部长"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi91" label="公安局长"></odin:checkbox>	</td>		        
		        <td><odin:checkbox property="shi92" label="统战部长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi93" label="其他常委"></odin:checkbox>	</td>			        			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      
		        <td><odin:checkbox property="shi94" label="农业副县市区长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi95" label="工业副县市区长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi96" label="城建副县市区长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi97" label="财贸副县市区长"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi98" label="文教副县市区长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi99" label="其他副县市区长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi100" label="人大主任"></odin:checkbox>	</td>		        			        			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>
	   <td colspan="2">
			<table>
		       <tr>	
		        <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	      
		        <td><odin:checkbox property="shi101" label="人大副主任"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi102" label="政协主席"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi103" label="政协副主席"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi104" label="法院院长"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi105" label="检察长"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi106" label="纪委副书记"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi107" label="其他"></odin:checkbox>	</td>		        			        			        	         		 	  		 	  		 	    		 	    		 	    
		 	   </tr>
			</table>
		</td>						
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi108" label="垂直单位"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>			       
		        <td><odin:checkbox property="shi109" label="挂职"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;领导工作经历:</span></td>			       
		        <td><odin:checkbox property="shi110" label="党政综合部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi111" label="人大政协部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi112" label="纪检监察部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi113" label="组织人事部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi114" label="意识形态部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi115" label="统战外侨部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi116" label="政法维稳部门"></odin:checkbox>	</td>	
		        			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	
		        <td><odin:checkbox property="shi117" label="经济财贸部门"></odin:checkbox>	</td>		       
		        <td><odin:checkbox property="shi118" label="建设交通部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi119" label="农资环保部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi120" label="科教文卫部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi121" label="社会事务部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi122" label="群众团体"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi123" label="科研院所"></odin:checkbox>	</td>			        			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
		        <td><odin:checkbox property="shi124" label="高职院校"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi125" label="医疗机构"></odin:checkbox>	</td>			       
		        <td><odin:checkbox property="shi126" label="国有企业"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi127" label="开发区(园区)"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi128" label="县(市、区)党政领导"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi129" label="乡镇党政正职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi130" label="乡镇其他领导"></odin:checkbox>	</td>			        			        			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr>	     
	    <td colspan="2">
			<table>
		       <tr>
		       <td align="right" colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>		       	
		        <td><odin:checkbox property="shi131" label="对口支援"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi132" label="军转安置"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shi133" label="大学生村官"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shi134" label="村干部"></odin:checkbox>	</td>			 	    		 	    
		 	   </tr>
			</table>
		</td>
	</tr>
	<tr >
	  		<odin:checkbox property="shihou1" label="市管后备干部"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;岗位经历:</span></td>	  
		        <td><odin:checkbox property="shihou2" label="正科领导岗位1个"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou3" label="正科领导岗位2个"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou4" label="正科领导岗位3个及以上"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou5" label="副科领导岗位(不计个数)"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;工作单位:</span></td>	  
		        <td><odin:checkbox property="shihou6" label="党务部门"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou7" label="政府部门"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou8" label="人大政协两院"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou9" label="群众团体"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou10" label="高校院所"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou11" label="国有企业"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou12" label="海曙区"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou13" label="江东区"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="shihou14" label="江北区"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou15" label="北仑区"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="shihou16" label="镇海区"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou17" label="鄞州区"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="shihou18" label="象山县"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="shihou19" label="宁海县"></odin:checkbox></td>		  	  				  	 		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;工作经历:</span></td>	  
		        <td><odin:checkbox property="shihou20" label="党政综合部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou21" label="人大政协部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou22" label="纪检监察部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou23" label="组织人事部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou24" label="意识形态部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou25" label="统战外侨部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou26" label="政法维稳部门"></odin:checkbox>	</td>	
		 	   </tr>
			</table>
		</td> 
	</tr>	
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>	    
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>    
		        <td><odin:checkbox property="shihou27" label="经济财贸部门"></odin:checkbox>	</td>		       
		        <td><odin:checkbox property="shihou28" label="建设交通部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou29" label="农资环保部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou30" label="科教文卫部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou31" label="社会事务部门"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou32" label="群众团体"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou33" label="科研院所"></odin:checkbox>	</td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>		
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>   			        
		        <td><odin:checkbox property="shihou34" label="高职院校"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou35" label="医疗机构"></odin:checkbox>	</td>			       
		        <td><odin:checkbox property="shihou36" label="国有企业"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou37" label="开发区(园区)"></odin:checkbox>	</td>		
		        <td><odin:checkbox property="shihou38" label="乡镇党政正职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou39" label="乡镇其他领导"></odin:checkbox>	</td>		       
		        <td><odin:checkbox property="shihou40" label="大学生村官"></odin:checkbox>	</td>
		        <td><odin:checkbox property="shihou41" label="村干部"></odin:checkbox>	</td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;挂职情况:</span></td>	  
		        <td><odin:checkbox property="shihou42" label="中央和省直机关挂职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou43" label="市直机关挂职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou44" label="重点工作和重点工程挂职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou45" label="乡镇(街道)挂职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou46" label="企业挂职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou47" label="其他挂职"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou48" label="对口支援"></odin:checkbox>	</td>	
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;中青班培训情况:</span></td>	  
		        <td><odin:checkbox property="shihou49" label="参加过宁波市中青班培训"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou50" label="未参加过宁波市中青班培训"></odin:checkbox>	</td>			       
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;选调生:</span></td>	  
		        <td><odin:checkbox property="shihou51" label="否"></odin:checkbox>	</td>	
		        <td><odin:checkbox property="shihou52" label="是"></odin:checkbox>	</td>			       
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr >
	  		<odin:checkbox property="jd1" label="乡镇(街道)党政正职"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;县市区:</span></td>	  
		        <td><odin:checkbox property="jd2" label="海曙区"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="jd3" label="江东区"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="jd4" label="江北区"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="jd5" label="北仑区"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="jd6" label="镇海区"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="jd7" label="鄞州区"></odin:checkbox></td>		  	  				  	 
		 	    <td><odin:checkbox property="jd8" label="象山县"></odin:checkbox> </td>
		 	    <td><odin:checkbox property="jd9" label="宁海县"></odin:checkbox> </td>
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;工作岗位:</span></td>	  
		        <td><odin:checkbox property="jd10" label="乡镇(街道)党(工)委书记"></odin:checkbox> </td> 	 		 	  		 	  
		 	    <td><odin:checkbox property="jd11" label="乡镇长、街道办事处主任"></odin:checkbox></td>		  	  				  	 
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr >
	  		<odin:checkbox property="ldbywy1" label="两代表一委员"></odin:checkbox>				  																				
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy2" label="党代表"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy3" label="全国"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy4" label="省级"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy5" label="市直部门"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy6" label="县市区"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy7" label="人大代表"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy8" label="全国"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy9" label="省级"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy10" label="市直部门"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy11" label="县市区"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy12" label="政协委员"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	<tr>	      
	    <td colspan="2">
			<table>
		       <tr>
		       <td  colspan="2"><span style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>	  
		        <td><odin:checkbox property="ldbywy13" label="全国"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy14" label="省级"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy15" label="市直部门"></odin:checkbox> </td> 
		        <td><odin:checkbox property="ldbywy16" label="县市区"></odin:checkbox> </td> 	 		 	  		 	  		 	    
		 	   </tr>
			</table>
		</td> 
	</tr>
	
	
	
	<!-- <tr>
		<td><button onclick=" location=location ">刷新</button></td>
	</tr> -->
</table>

<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="id" title="主键id" ></odin:hidden>	
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
