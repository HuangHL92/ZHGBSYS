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
		//原图的缩放img.getScaledInstance(width, height, 4)
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
		
		//处理各种边界问题  共4总情况
		if (subImageBounds.x < 0||
				subImageBounds.y<0||
				(image.getHeight()-subImageBounds.y)<subImageBounds.height||
				(image.getWidth()-subImageBounds.x)<subImageBounds.width
				) {
			int left=0,//截图的偏移位置
				top=0,//截图的偏移位置	
				x=subImageBounds.x,	//	原图的偏移位
				y=subImageBounds.y, //	原图的偏移位
				width=subImageBounds.width,		//	原图所截取的宽度
				height=subImageBounds.height;   //	原图所截取的高度
			
			if(subImageBounds.x < 0){//图片的左边界 未达到 截图框的左边界
				left = -subImageBounds.x;////截图框左边的偏移位置，从该偏移位置开始画图
				if(left>subImageBounds.width){//如果 截图框左边的偏移位置 超出右边界
					left=subImageBounds.width;//截图框左边的偏移位置等于截图框的宽长
				}
				x=0;//图片从最左边开始截图
				width=subImageBounds.width+subImageBounds.x;//图片所截的宽度等于 截图框的长度减去偏移的长度
				if(width>image.getWidth()){//如果计算出所截的宽度大于图片本身的宽度
					width = image.getWidth();//图片所截的宽度等于图片的宽度
				}else if(width<=0){//如果计算出所截的宽度小于0
					width=1;//宽度等于1. 宽度为0要报错
				}
			}else if((image.getWidth()-subImageBounds.x)<subImageBounds.width){//图片的右边界 未达到截图框的右边界
				left=0;//截图框左边起始位置开始画图
				x=subImageBounds.x;//图片从左边偏离度开始截图
				if(x-image.getWidth()>0){//如果图片左边开始的偏离度比图片宽度还要宽
					x = image.getWidth();//偏离度等于图片宽度
				}
				width=image.getWidth()-subImageBounds.x;//图片截图的宽度等于图片的宽度减去偏离度
				if(width<=0){//如果所截的图片的宽度小于0
					width=1;//宽度为1
					x=x-1;//偏离度往回减一
				}
			}
			if(subImageBounds.y<0){//图片的上边界 未达到 截图框的上边界
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
			}else if((image.getHeight()-subImageBounds.y)<subImageBounds.height){//图片的下边界 未达到 截图框的下边界
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
		//保存人员头像
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
			//获取系统当前时间
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
			throw new SecurityException("请上传照片");
		}
		Image image = ImageIO.read(srcImageFile);
		BufferedImage bImage = makeThumbnail(image, width, height);

		saveSubImage(bImage, rect, descDir, a0000);
	}
}