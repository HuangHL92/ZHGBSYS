package com.insigma.siis.local.business.entity;

import java.util.HashMap;
import java.util.List;

/**
 * 分装多媒体的播放信息
 * @author luol
 * @time 2020.02.06 16:14
 *
 */
public class MediaEntity {
	//个人信息
	private String name;
	private String Nation;
	private String age;
	private String post;
	private String photo;
	//视频信息
	private  String videoName;
	private  String videoTitle;
	private  String videoAddres;
	//播放视频列表
	private List<HashMap<String,String>> videoList;
	//展示视频列表
	private List<HashMap<String,String>> showVideoList;
	//PDF文件列表
	private List<HashMap<String,String>> showPdfList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNation() {
		return Nation;
	}
	public void setNation(String nation) {
		Nation = nation;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getVideoAddres() {
		return videoAddres;
	}
	public void setVideoAddres(String videoAddres) {
		this.videoAddres = videoAddres;
	}
	public List<HashMap<String, String>> getVideoList() {
		return videoList;
	}
	public void setVideoList(List<HashMap<String, String>> videoList) {
		this.videoList = videoList;
	}
	
	public List<HashMap<String, String>> getShowVideoList() {
		return showVideoList;
	}
	public void setShowVideoList(List<HashMap<String, String>> showVideoList) {
		this.showVideoList = showVideoList;
	}
	
	public List<HashMap<String, String>> getShowPdfList() {
		return showPdfList;
	}
	public void setShowPdfList(List<HashMap<String, String>> showPdfList) {
		this.showPdfList = showPdfList;
	}
	@Override
	public String toString() {
		return "MediaEntity [name=" + name + ", Nation=" + Nation + ", age=" + age + ", post=" + post + ", photo="
				+ photo + ", videoName=" + videoName + ", videoTitle=" + videoTitle + ", videoAddres=" + videoAddres
				+ ", videoList=" + videoList + "]";
	}
	
	
	
}
