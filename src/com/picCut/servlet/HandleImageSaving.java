package com.picCut.servlet;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.aspose.words.IImageSavingCallback;
import com.aspose.words.ImageSavingArgs;
import com.aspose.words.Shape;

public class HandleImageSaving implements IImageSavingCallback{
	@Override
	public void imageSaving(ImageSavingArgs e) throws Exception {
		e.setImageStream(new ByteArrayOutputStream());
		e.setKeepImageStreamOpen(false);
	}

}