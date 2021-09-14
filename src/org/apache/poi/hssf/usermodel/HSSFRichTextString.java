/*     */ package org.apache.poi.hssf.usermodel;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.apache.poi.hssf.model.InternalWorkbook;
/*     */ import org.apache.poi.hssf.record.LabelSSTRecord;
/*     */ import org.apache.poi.hssf.record.common.UnicodeString;
/*     */ import org.apache.poi.hssf.record.common.UnicodeString.FormatRun;
/*     */ import org.apache.poi.ss.usermodel.Font;
/*     */ import org.apache.poi.ss.usermodel.RichTextString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HSSFRichTextString
/*     */   implements Comparable<HSSFRichTextString>, RichTextString
/*     */ {
/*     */   public static final short NO_FONT = 0;
/*     */   private UnicodeString _string;
public UnicodeString get_string() {
	return _string;
}

public void set_string(UnicodeString _string) {
	this._string = _string;
}

/*     */   private InternalWorkbook _book;
/*     */   private LabelSSTRecord _record;
/*     */   
/*     */   public HSSFRichTextString()
/*     */   {
/*  79 */     this("");
/*     */   }
/*     */   
/*     */   public HSSFRichTextString(String string) {
/*  83 */     if (string == null) {
/*  84 */       this._string = new UnicodeString("");
/*     */     } else {
/*  86 */       this._string = new UnicodeString(string);
/*     */     }
/*     */   }
/*     */   
/*     */   HSSFRichTextString(InternalWorkbook book, LabelSSTRecord record) {
/*  91 */     setWorkbookReferences(book, record);
/*     */     
/*  93 */     this._string = book.getSSTString(record.getSSTIndex());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void setWorkbookReferences(InternalWorkbook book, LabelSSTRecord record)
/*     */   {
/* 100 */     this._book = book;
/* 101 */     this._record = record;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private UnicodeString cloneStringIfRequired()
/*     */   {
/* 109 */     if (this._book == null)
/* 110 */       return this._string;
/* 111 */     UnicodeString s = (UnicodeString)this._string.clone();
/* 112 */     return s;
/*     */   }
/*     */   
/*     */   private void addToSSTIfRequired() {
/* 116 */     if (this._book != null) {
/* 117 */       int index = this._book.addSSTString(this._string);
/* 118 */       this._record.setSSTIndex(index);
/*     */       
/*     */ 
/* 121 */       this._string = this._book.getSSTString(index);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyFont(int startIndex, int endIndex, short fontIndex)
/*     */   {
/* 135 */     if (startIndex > endIndex)
/* 136 */       throw new IllegalArgumentException("Start index must be less than end index.");
/* 137 */     if ((startIndex < 0) || (endIndex > length()))
/* 138 */       throw new IllegalArgumentException("Start and end index not in range.");
/* 139 */     if (startIndex == endIndex) {
/* 140 */       return;
/*     */     }
/*     */     
/*     */ 
/* 144 */     short currentFont = 0;
/* 145 */     if (endIndex != length()) {
/* 146 */       currentFont = getFontAtIndex(endIndex);
/*     */     }
/*     */     
/*     */ 
/* 150 */     this._string = cloneStringIfRequired();
/* 151 */     Iterator<UnicodeString.FormatRun> formatting = this._string.formatIterator();
/* 152 */     if (formatting != null) {
/* 153 */       while (formatting.hasNext()) {
/* 154 */         UnicodeString.FormatRun r = (UnicodeString.FormatRun)formatting.next();
/* 155 */         if ((r.getCharacterPos() >= startIndex) && (r.getCharacterPos() < endIndex)) {
/* 156 */           formatting.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 161 */     this._string.addFormatRun(new UnicodeString.FormatRun((short)startIndex, fontIndex));
/* 162 */     if (endIndex != length()) {
/* 163 */       this._string.addFormatRun(new UnicodeString.FormatRun((short)endIndex, currentFont));
/*     */     }
/* 165 */     addToSSTIfRequired();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyFont(int startIndex, int endIndex, Font font)
/*     */   {
/* 177 */     applyFont(startIndex, endIndex, ((HSSFFont)font).getIndex());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyFont(Font font)
/*     */   {
/* 186 */     applyFont(0, this._string.getCharCount(), font);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clearFormatting()
/*     */   {
/* 193 */     this._string = cloneStringIfRequired();
/* 194 */     this._string.clearFormatting();
/* 195 */     addToSSTIfRequired();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getString()
/*     */   {
/* 203 */     return this._string.getString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   UnicodeString getUnicodeString()
/*     */   {
/* 212 */     return cloneStringIfRequired();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   UnicodeString getRawUnicodeString()
/*     */   {
/* 223 */     return this._string;
/*     */   }
/*     */   
/*     */   void setUnicodeString(UnicodeString str)
/*     */   {
/* 228 */     this._string = str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int length()
/*     */   {
/* 236 */     return this._string.getCharCount();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getFontAtIndex(int index)
/*     */   {
/* 249 */     int size = this._string.getFormatRunCount();
/* 250 */     UnicodeString.FormatRun currentRun = null;
/* 251 */     for (int i = 0; i < size; i++) {
/* 252 */       UnicodeString.FormatRun r = this._string.getFormatRun(i);
/* 253 */       if (r.getCharacterPos() > index) {
/*     */         break;
/*     */       }
/* 256 */       currentRun = r;
/*     */     }
/* 258 */     if (currentRun == null) {
/* 259 */       return 0;
/*     */     }
/* 261 */     return currentRun.getFontIndex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int numFormattingRuns()
/*     */   {
/* 272 */     return this._string.getFormatRunCount();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getIndexOfFormattingRun(int index)
/*     */   {
/* 282 */     UnicodeString.FormatRun r = this._string.getFormatRun(index);
/* 283 */     return r.getCharacterPos();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getFontOfFormattingRun(int index)
/*     */   {
/* 294 */     UnicodeString.FormatRun r = this._string.getFormatRun(index);
/* 295 */     return r.getFontIndex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int compareTo(HSSFRichTextString r)
/*     */   {
/* 302 */     return this._string.compareTo(r._string);
/*     */   }
/*     */   
/*     */   public boolean equals(Object o)
/*     */   {
/* 307 */     if ((o instanceof HSSFRichTextString)) {
/* 308 */       return this._string.equals(((HSSFRichTextString)o)._string);
/*     */     }
/* 310 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 316 */     //if (!$assertionsDisabled) throw new AssertionError("hashCode not designed");
/* 317 */     return 42;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 326 */     return this._string.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyFont(short fontIndex)
/*     */   {
/* 336 */     applyFont(0, this._string.getCharCount(), fontIndex);
/*     */   }
/*     */ }

/* Location:           D:\workspace2\hzb\WebContent\WEB-INF\lib\poi-3.16-beta1.jar
 * Qualified Name:     org.apache.poi.hssf.usermodel.HSSFRichTextString
 * Java Class Version: 6 (50.0)
 * JD-Core Version:    0.7.1
 */