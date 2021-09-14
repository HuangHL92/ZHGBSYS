package shudu;
/**
 * 2018��7��23��
 * @author zoulei
 *
 */
public class NumberPlacementSolution {
	//���˼��ĳ��ȡ�
	static Integer length;
	//�洢��Ŀ
	static Cell[][] cells;
	//����
	static Integer num = 0;
	//��ĸ���
	static Integer s = 0;
	static long startTime = 0;
	
	//ά��
	static Integer d = 0;
	
	public static void main(String[] args) {
		

		
		/*//��Ŀ
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
		/*//��Ŀ
		Integer[][] shudu = 
			{{1,0,3,0},
			 {0,0,0,1},
			 {3,0,0,2},
			 {0,4,0,0}
			};*/
			//��Ŀ
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
		//�洢��Ŀ
		cells = new Cell[shudu.length][shudu.length];
		
		// ��ʼ����Ŀ��  �����еĵ�Ԫ���ʼ���ɶ���
		for(int i=0; i<shudu.length; i++){
			Integer[] rows = shudu[i];
			for(int j=0; j<rows.length; j++){
				cells[i][j] = new Cell(shudu[i][j]);
			}
		}
		
		// ���ÿ�ѡ���֣� ��Ҫ��Ŀո��м��ֿ�����
		for(int i=0; i<shudu.length; i++){
			Integer[] rows = shudu[i];
			for(int j=0; j<rows.length; j++){
				Cell c = cells[i][j];
				if(c.isWriteable()){
					setOptionalNumbers(c,i,j);
				}
			}
		}
		startTime = System.currentTimeMillis();    //��ȡ��ʼʱ��
		// ����
		for(int i=0; i<shudu.length; i++){
			//System.out.println("�����"+(i+1)+"��");
			Integer[] rows = shudu[i];
			for(int j=0; j<rows.length; j++){
				Cell c = cells[i][j];
				if(c.isWriteable()){//�ж��Ƿ�����Ҫ��д�ĸ���
					int optionalNumber = 0;
					String checkSting = getCheckSting(c, i, j);//��ȡ��������Ź���λ���е�����
					while((optionalNumber = c.getNextOptionalNumber())!=-1){//ȡ��һ����ѡ���֣��ж��Ƿ���ȷ���������ȷ��ȡ����һ����ѡ����
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
					if(optionalNumber == -1){//���еĿ�ѡ���־������ã�����������Ϊ0��Ȼ�󷵻���һ����д���ӣ�����ִ��ǰһ����
						c.setCurrentNumber(0);
						c.setSelectedNumber(0);
						do{
							num++;
							if(j>0){
								j = j-1;
								//System.out.println("���㲻��ȥ����һ��");
								c = cells[i][j];
							}else{
								//System.out.println("���㲻��ȥ����һ��");
								i = i-1;
								//System.out.println("�����"+(i+1)+"��");
								j = rows.length;
								if(i<0){
									System.out.println("������ϣ�");
									System.out.println("�ܼ������:"+num+",�ܽ���"+s+"����");
									
									long endTime = System.currentTimeMillis();    //��ȡ����ʱ��
									System.out.println("�ܺ�ʱ:" + (endTime - startTime) + "����");
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
	 * ���ÿ�ѡ����
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
	
	//��ȡУ���ַ���
	private static String getCheckSting(Cell c, int i, int j) {
		StringBuffer sb = new StringBuffer();
		int iStart = 0,iEnd = length;
		//��������
		for(; iStart<iEnd; iStart++ ){
			Cell cell = cells[iStart][j];
			if(cell.getCurrentNumber()!=0 && iStart!=i){
				sb.append(cell.getCurrentNumber()+",");
			}
		}
		//��������
		int jStart = 0,jEnd = length;
		for(; jStart<jEnd; jStart++ ){
			Cell cell = cells[i][jStart];
			if(cell.getCurrentNumber()!=0 && jStart!=j){
				sb.append(cell.getCurrentNumber()+",");
			}
		}
		//�Ź���λ����
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
	
	//��ȡУ���ַ���
		private static void printout() {
			System.out.println("��"+s+"����:");
			StringBuffer sb = new StringBuffer("<table cellspacing='0' cellpadding='0' style='margin-top:10px;margin-left:10px;float:left;'>");
			// ���
			
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
			System.out.println("--�������:"+num+"------------------------------------------------------------------------------------------------");
			long endTime = System.currentTimeMillis();    //��ȡ����ʱ��
			System.out.println("��"+s+"�����ʱ:" + (endTime - startTime) + "����");
			
			System.out.println(sb.toString());
		}
	

}
