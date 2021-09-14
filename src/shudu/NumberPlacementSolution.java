package shudu;
/**
 * 2018年7月23日
 * @author zoulei
 *
 */
public class NumberPlacementSolution {
	//几乘几的长度。
	static Integer length;
	//存储题目
	static Cell[][] cells;
	//次数
	static Integer num = 0;
	//解的个数
	static Integer s = 0;
	static long startTime = 0;
	
	//维度
	static Integer d = 0;
	
	public static void main(String[] args) {
		

		
		/*//题目
		Integer[][] shudu = 
			{{0,0,1,0,7,2,0,9,0},
			 {6,4,0,8,0,0,5,0,0},
			 {8,0,0,0,0,9,0,0,0},
			 {0,0,0,9,2,8,0,0,1},
			 {0,8,0,7,0,0,0,4,9},
			 {0,6,0,0,1,0,0,0,8},
			 {4,0,0,1,0,0,0,0,0},
			 {0,0,8,0,0,6,4,0,5},
			 {0,5,0,2,8,0,0,0,0}
			*/
		/*//题目
		Integer[][] shudu = 
			{{1,0,3,0},
			 {0,0,0,1},
			 {3,0,0,2},
			 {0,4,0,0}
			};*/
			//题目
		Integer[][] shudu = 
			{{0,0,0,1,0,0,2,6,0},
			 {7,0,0,0,3,0,0,0,0},
			 {3,0,2,0,8,0,4,0,0},
			 {0,0,0,4,0,8,0,0,1},
			 {0,3,5,0,0,0,9,4,0},
			 {2,0,0,3,0,5,0,0,0},
			 {0,0,6,0,5,0,7,0,9},
			 {0,0,0,0,4,0,0,0,8},
			 {0,5,7,0,0,9,0,0,0}
			};
		length = shudu.length;
		d = (int)Math.sqrt(shudu.length);
		//存储题目
		cells = new Cell[shudu.length][shudu.length];
		
		// 初始化题目：  把所有的单元格初始化成对象
		for(int i=0; i<shudu.length; i++){
			Integer[] rows = shudu[i];
			for(int j=0; j<rows.length; j++){
				cells[i][j] = new Cell(shudu[i][j]);
			}
		}
		
		// 设置可选数字： 需要填的空格有几种可能性
		for(int i=0; i<shudu.length; i++){
			Integer[] rows = shudu[i];
			for(int j=0; j<rows.length; j++){
				Cell c = cells[i][j];
				if(c.isWriteable()){
					setOptionalNumbers(c,i,j);
				}
			}
		}
		startTime = System.currentTimeMillis();    //获取开始时间
		// 解题
		for(int i=0; i<shudu.length; i++){
			//System.out.println("计算第"+(i+1)+"行");
			Integer[] rows = shudu[i];
			for(int j=0; j<rows.length; j++){
				Cell c = cells[i][j];
				if(c.isWriteable()){//判断是否是需要填写的格子
					int optionalNumber = 0;
					String checkSting = getCheckSting(c, i, j);//获取横向纵向九宫格单位已有的数字
					while((optionalNumber = c.getNextOptionalNumber())!=-1){//取出一个可选数字，判断是否正确，如果不正确，取出下一个可选数字
						if(checkSting.indexOf(optionalNumber+"") == -1){
							c.setCurrentNumber(optionalNumber);
							c.setSelectedNumber(optionalNumber);
							num++;
							if(i==shudu.length-1&&j==shudu.length-1){
								optionalNumber=-1;
								s++;
								printout();
							}
							break;
							
							
							
						}
					}
					if(optionalNumber == -1){//所有的可选数字均不可用，将本格子置为0，然后返回上一个填写格子，继续执行前一步骤
						c.setCurrentNumber(0);
						c.setSelectedNumber(0);
						do{
							num++;
							if(j>0){
								j = j-1;
								//System.out.println("计算不下去倒退一格");
								c = cells[i][j];
							}else{
								//System.out.println("计算不下去倒退一行");
								i = i-1;
								//System.out.println("计算第"+(i+1)+"行");
								j = rows.length;
								if(i<0){
									System.out.println("计算完毕：");
									System.out.println("总计算次数:"+num+",总解数"+s+"个解");
									
									long endTime = System.currentTimeMillis();    //获取结束时间
									System.out.println("总耗时:" + (endTime - startTime) + "毫秒");
									return;
								}
								j = j-1;
								c = cells[i][j];
							}
						}while(!c.isWriteable());
							
						
						j = j-1;
						
					}
					
				}
			}
		}
		
		//printout();
		
		
	}

	/**
	 * 设置可选数字
	 * @param c
	 * @param i
	 * @param j
	 */
	private static void setOptionalNumbers(Cell c, int i, int j) {
		String checkSting = getCheckSting(c, i, j);
		for(int n=1;n<=cells.length;n++){
			if(checkSting.indexOf(n+"") == -1){
				c.getOptionalNumbers().add(n);
			}
		}
	}
	
	//获取校验字符串
	private static String getCheckSting(Cell c, int i, int j) {
		StringBuffer sb = new StringBuffer();
		int iStart = 0,iEnd = length;
		//横向数字
		for(; iStart<iEnd; iStart++ ){
			Cell cell = cells[iStart][j];
			if(cell.getCurrentNumber()!=0 && iStart!=i){
				sb.append(cell.getCurrentNumber()+",");
			}
		}
		//纵向数字
		int jStart = 0,jEnd = length;
		for(; jStart<jEnd; jStart++ ){
			Cell cell = cells[i][jStart];
			if(cell.getCurrentNumber()!=0 && jStart!=j){
				sb.append(cell.getCurrentNumber()+",");
			}
		}
		//九宫格单位数字
		iStart = i/d*d; iEnd = iStart + d;
		jStart = j/d*d; jEnd = jStart + d;
		for(; iStart<iEnd; iStart++){
			for(jStart = j/d*d; jStart<jEnd; jStart++ ){
				Cell cell = cells[iStart][jStart];
				if(cell.getCurrentNumber()!=0 && !(jStart==j && iStart==i)){
					sb.append(cell.getCurrentNumber()+",");
				}
			}
		}
		return sb.toString();
	}
	
	//获取校验字符串
		private static void printout() {
			System.out.println("第"+s+"个解:");
			StringBuffer sb = new StringBuffer("<table cellspacing='0' cellpadding='0' style='margin-top:10px;margin-left:10px;float:left;'>");
			// 输出
			
			for(int i=0; i<cells.length; i++){
				Cell[] rows = cells[i];
				sb.append("<tr>");
				for(int j=0; j<rows.length; j++){
					Cell c = cells[i][j];
					String lineClass = "";
					if(i==cells.length-1){
						lineClass = "bottomclass";
					}
					if(j==rows.length-1){
						lineClass += " rightclass";
					}
					if(c.isWriteable()){
						sb.append("<td class=' "+lineClass+"'>"+c.getCurrentNumber()+"</td>");
					}else{
						sb.append("<td class='cred "+lineClass+"'>"+c.getCurrentNumber()+"</td>");
						
					}
					System.out.print(c.getCurrentNumber()+" ");
				}
				sb.append("</tr>");
				System.out.println();
				
			}
			sb.append("</table>");
			System.out.println("--计算次数:"+num+"------------------------------------------------------------------------------------------------");
			long endTime = System.currentTimeMillis();    //获取结束时间
			System.out.println("第"+s+"个解耗时:" + (endTime - startTime) + "毫秒");
			
			System.out.println(sb.toString());
		}
	

}
