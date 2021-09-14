/*    */ package com.picCut.jspsmart.upload;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public class Files
/*    */ {
/*    */   private SmartUpload m_parent;
/* 18 */   private Hashtable m_files = new Hashtable();
/* 19 */   private int m_counter = 0;
/*    */ 
/*    */   protected void addFile(File file)
/*    */   {
/* 24 */     if (file == null)
/*    */     {
/* 26 */       throw new IllegalArgumentException("newFile cannot be null.");
/*    */     }
/*    */ 
/* 29 */     this.m_files.put(new Integer(this.m_counter), file);
/* 30 */     this.m_counter += 1;
/*    */   }
/*    */ 
/*    */   public File getFile(int i)
/*    */   {
/* 37 */     if (i < 0)
/* 38 */       throw new IllegalArgumentException("File's index cannot be a negative value (1210).");
/* 39 */     File file = (File)this.m_files.get(new Integer(i));
/* 40 */     if (file == null) {
/* 41 */       throw new IllegalArgumentException("Files' name is invalid or does not exist (1205).");
/*    */     }
/* 43 */     return file;
/*    */   }
/*    */ 
/*    */   public int getCount()
/*    */   {
/* 48 */     return this.m_counter;
/*    */   }
/*    */ 
/*    */   public long getSize()
/*    */     throws IOException
/*    */   {
/* 54 */     long l = 0L;
/* 55 */     for (int i = 0; i < this.m_counter; i++) {
/* 56 */       l += getFile(i).getSize();
/*    */     }
/* 58 */     return l;
/*    */   }
/*    */ 
/*    */   public Collection getCollection()
/*    */   {
/* 63 */     return this.m_files.values();
/*    */   }
/*    */ 
/*    */   public Enumeration getEnumeration()
/*    */   {
/* 68 */     return this.m_files.elements();
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\WebRoot\WEB-INF\classes\
 * Qualified Name:     com.jspsmart.upload.Files
 * JD-Core Version:    0.6.2
 */