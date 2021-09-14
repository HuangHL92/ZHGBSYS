/*    */ package com.picCut.jspsmart.upload;
/*    */ 
/*    */ import java.util.Enumeration;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public class Request
/*    */ {
/* 14 */   private Hashtable m_parameters = new Hashtable();
/* 15 */   private int m_counter = 0;
/*    */ 
/*    */   protected void putParameter(String s, String s1)
/*    */   {
/* 20 */     if (s == null)
/* 21 */       throw new IllegalArgumentException("The name of an element cannot be null.");
/* 22 */     if (this.m_parameters.containsKey(s))
/*    */     {
/* 24 */       Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
/* 25 */       hashtable.put(new Integer(hashtable.size()), s1);
/*    */     }
/*    */     else {
/* 28 */       Hashtable hashtable1 = new Hashtable();
/* 29 */       hashtable1.put(new Integer(0), s1);
/* 30 */       this.m_parameters.put(s, hashtable1);
/* 31 */       this.m_counter += 1;
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getParameter(String s)
/*    */   {
/* 37 */     if (s == null)
/* 38 */       throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
/* 39 */     Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
/* 40 */     if (hashtable == null) {
/* 41 */       return null;
/*    */     }
/* 43 */     return (String)hashtable.get(new Integer(0));
/*    */   }
/*    */ 
/*    */   public Enumeration getParameterNames()
/*    */   {
/* 48 */     return this.m_parameters.keys();
/*    */   }
/*    */ 
/*    */   public String[] getParameterValues(String s)
/*    */   {
/* 53 */     if (s == null)
/* 54 */       throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
/* 55 */     Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
/* 56 */     if (hashtable == null)
/* 57 */       return null;
/* 58 */     String[] as = new String[hashtable.size()];
/* 59 */     for (int i = 0; i < hashtable.size(); i++) {
/* 60 */       as[i] = ((String)hashtable.get(new Integer(i)));
/*    */     }
/* 62 */     return as;
/*    */   }
/*    */ }

/* Location:           C:\Users\Administrator\Desktop\WebRoot\WEB-INF\classes\
 * Qualified Name:     com.jspsmart.upload.Request
 * JD-Core Version:    0.6.2
 */