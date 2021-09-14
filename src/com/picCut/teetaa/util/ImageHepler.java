package com.picCut.teetaa.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.hibernate.Hibernate;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.A57history;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ImageHepler {
	private static BufferedImage makeThumbnail(Image img, int width, int height) {
		BufferedImage tag = new BufferedImage(width, height, 1);
		Graphics g = tag.getGraphics();
		//ԭͼ������img.getScaledInstance(width, height, 4)
		g.drawImage(img.getScaledInstance(width, height, 4), 0, 0, null);
		g.dispose();
		return tag;
	}

	private static void saveSubImage(BufferedImage image,
			Rectangle subImageBounds, File subImageFile,String a0000) throws IOException {
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		if(fileName==null||"".equals(fileName)){
			fileName = a0000;
		}
		if(formatName==null||"".equals(formatName)){
			formatName = "jpg";
		}
		BufferedImage subImage = new BufferedImage(subImageBounds.width,
				subImageBounds.height, 1);
		Graphics g = subImage.getGraphics();
		
		//������ֱ߽�����  ��4�����
		if (subImageBounds.x < 0||
				subImageBounds.y<0||
				(image.getHeight()-subImageBounds.y)<subImageBounds.height||
				(image.getWidth()-subImageBounds.x)<subImageBounds.width
				) {
			int left=0,//��ͼ��ƫ��λ��
				top=0,//��ͼ��ƫ��λ��	
				x=subImageBounds.x,	//	ԭͼ��ƫ��λ
				y=subImageBounds.y, //	ԭͼ��ƫ��λ
				width=subImageBounds.width,		//	ԭͼ����ȡ�Ŀ��
				height=subImageBounds.height;   //	ԭͼ����ȡ�ĸ߶�
			
			if(subImageBounds.x < 0){//ͼƬ����߽� δ�ﵽ ��ͼ�����߽�
				left = -subImageBounds.x;////��ͼ����ߵ�ƫ��λ�ã��Ӹ�ƫ��λ�ÿ�ʼ��ͼ
				if(left>subImageBounds.width){//��� ��ͼ����ߵ�ƫ��λ�� �����ұ߽�
					left=subImageBounds.width;//��ͼ����ߵ�ƫ��λ�õ��ڽ�ͼ��Ŀ�
				}
				x=0;//ͼƬ������߿�ʼ��ͼ
				width=subImageBounds.width+subImageBounds.x;//ͼƬ���صĿ�ȵ��� ��ͼ��ĳ��ȼ�ȥƫ�Ƶĳ���
				if(width>image.getWidth()){//�����������صĿ�ȴ���ͼƬ����Ŀ��
					width = image.getWidth();//ͼƬ���صĿ�ȵ���ͼƬ�Ŀ��
				}else if(width<=0){//�����������صĿ��С��0
					width=1;//��ȵ���1. ���Ϊ0Ҫ����
				}
			}else if((image.getWidth()-subImageBounds.x)<subImageBounds.width){//ͼƬ���ұ߽� δ�ﵽ��ͼ����ұ߽�
				left=0;//��ͼ�������ʼλ�ÿ�ʼ��ͼ
				x=subImageBounds.x;//ͼƬ�����ƫ��ȿ�ʼ��ͼ
				if(x-image.getWidth()>0){//���ͼƬ��߿�ʼ��ƫ��ȱ�ͼƬ��Ȼ�Ҫ��
					x = image.getWidth();//ƫ��ȵ���ͼƬ���
				}
				width=image.getWidth()-subImageBounds.x;//ͼƬ��ͼ�Ŀ�ȵ���ͼƬ�Ŀ�ȼ�ȥƫ���
				if(width<=0){//������ص�ͼƬ�Ŀ��С��0
					width=1;//���Ϊ1
					x=x-1;//ƫ������ؼ�һ
				}
			}
			if(subImageBounds.y<0){//ͼƬ���ϱ߽� δ�ﵽ ��ͼ����ϱ߽�
				top = -subImageBounds.y;
				if(top>subImageBounds.height){
					top=subImageBounds.height;
				}
				y=0;
				height=subImageBounds.height+subImageBounds.y;
				if(height>image.getHeight()){
					height = image.getHeight();
				}else if(height<=0){
					height=1;
				}
			}else if((image.getHeight()-subImageBounds.y)<subImageBounds.height){//ͼƬ���±߽� δ�ﵽ ��ͼ����±߽�
				top=0;
				y=subImageBounds.y;
				if(y-image.getHeight()>0){
					y=image.getHeight();
				}
				height=image.getHeight()-subImageBounds.y;
				if(height<=0){
					height=1;
					y=y-1;
				}
			}
			
			g.setColor(Color.white);
			g.fillRect(0, 0, subImageBounds.width, subImageBounds.height);
			g.drawImage(image.getSubimage(x, y, width, height), left, top, null);
			//ImageIO.write(image, formatName, subImageFile);
			CommonQueryBS.systemOut("if is running left:" + left + " top: " + top);
		} else {
			g.drawImage(image.getSubimage(subImageBounds.x, subImageBounds.y,
					subImageBounds.width, subImageBounds.height), 0, 0, null);
		}
		g.dispose();
		//������Աͷ��
		saveImg(subImage,fileName,formatName,a0000);
		//ImageIO.write(subImage, formatName, subImageFile);
	}

	private static void saveImg(BufferedImage image, String fileName,String formatName,
			String a0000) {
		if(formatName==null||"".equals(formatName)){
			formatName = "jpg";
		}
		HBSession sess = HBUtil.getHBSession();
		try {
			//fileName = a0000+"."+formatName;
			//��ȡϵͳ��ǰʱ��
			String now = DateUtil.getcurdate();
			fileName = (a0000+"."+formatName).replaceAll(" ", "");
			//String photourl = PhotosUtil.getSavePath(fileName);
			String photourl = (PhotosUtil.getSavePath(fileName)).replaceAll(" ", "");
			sess.beginTransaction();
			A57history a57his = new A57history();
			//A57 a57 = new A57();
			A57 a57old = (A57) sess.get(A57.class, a0000);
			File fileD = null;
			File fileF = null;
			if(a57old==null){
				A57 a57 = new A57();
				a57.setA0000(a0000);
				a57.setPhotopath(photourl);
				a57.setPhotoname(fileName);
				a57.setA5714(fileName);	
				fileD = new File(PhotosUtil.PHOTO_PATH+photourl);
				if(!fileD.isDirectory()){
					fileD.mkdirs();
				}
				fileF = new File(PhotosUtil.PHOTO_PATH+photourl+fileName);
				ImageIO.write(image, formatName, fileF);
				sess.save(a57);
			}else{
				String newPhotoName = UUID.randomUUID().toString()+"."+formatName;
				a57his.setA0000(a0000);
				a57his.setPhotoname(newPhotoName);
				a57his.setPhotopath(a57old.getPhotopath());
				a57his.setPhotostype(a57old.getPhotstype());
				a57his.setPhotousedate(now);
				a57his.setPhotodate(now);
				new File(PhotosUtil.PHOTO_PATH+a57old.getPhotopath()+a57old.getPhotoname()).renameTo(new File(PhotosUtil.PHOTO_PATH+a57old.getPhotopath()+newPhotoName)); 
				fileF = new File(PhotosUtil.PHOTO_PATH+a57old.getPhotopath()+a57old.getPhotoname());
				ImageIO.write(image, formatName, fileF);
				sess.save(a57his);
			}
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			File fileD = new File(PhotosUtil.PHOTO_PATH+photourl);
//			if(!fileD.isDirectory()){
//				fileD.mkdirs();
//			}
//			File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+fileName);
			/*if(fileF.isFile()){
				fileF.delete();
			}*/
			//ImageIO.write(image, formatName, fileF);
			//byte[] imagedata = baos.toByteArray();
			//baos.close();
			//a57.setPhotodata(Hibernate.createBlob(imagedata));
			
			//sess.saveOrUpdate(a57);
			sess.flush();
			sess.getTransaction().commit();
			
		} catch (AppException e) {
			sess.getTransaction().rollback();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public static void cut(String srcImageFile, String descDir, int width,
			int height, Rectangle rect, String a0000) throws IOException {
		cut(srcImageFile, descDir, width, height, rect, a0000,null,"1");
	}
	public static void cut(String srcImageFile, String descDir, int width,
			int height, Rectangle rect, String a0000, InputStream is,String type) throws IOException {
		Image image = null;
		if("1".equals(type)){
			image = ImageIO.read(new File(srcImageFile));
		}else if("2".equals(type)){
			image = ImageIO.read(new File(srcImageFile));
			width = 272;
			height = 340;
		}else{
			image = ImageIO.read(is);
			width = 272;
			height = 340;
		}
		
		BufferedImage bImage = makeThumbnail(image, width, height);

		saveSubImage(bImage, rect, new File(descDir),a0000);
	}
	
	public static byte[] cut(String srcImageFile, String descDir, int width,
			int height, Rectangle rect, String a0000, InputStream is) throws IOException {
		Image image = null;
		image = ImageIO.read(new File(srcImageFile));
		File subImageFile = new File(descDir);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		if(formatName==null||"".equals(formatName)){
			formatName = "jpg";
		}
		width = 272;
		height = 340;
		BufferedImage bImage = makeThumbnail(image, width, height);
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		boolean flag = ImageIO.write(bImage, formatName, out);  
		byte[] b = out.toByteArray();  
		return b;
	}

	public static void cut(File srcImageFile, File descDir, int width,
			int height, Rectangle rect, String a0000) throws IOException {
		if(!srcImageFile.exists()){
			throw new SecurityException("���ϴ���Ƭ");
		}
		Image image = ImageIO.read(srcImageFile);
		BufferedImage bImage = makeThumbnail(image, width, height);

		saveSubImage(bImage, rect, descDir, a0000);
	}
}