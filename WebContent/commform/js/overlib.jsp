<%@page contentType="text/javascript; charset=GBK"language="java"%>var INARRAY=1;var CAPARRAY=2;var STICKY=3;var BACKGROUND=4;var NOCLOSE=5;var CAPTION=6;var LEFT=7;var RIGHT=8;var CENTER=9;var OFFSETX=10;var OFFSETY=11;var FGCOLOR=12;var BGCOLOR=13;var TEXTCOLOR=14;var CAPCOLOR=15;var CLOSECOLOR=16;var WIDTH=17;var BORDER=18;var STATUS=19;var AUTOSTATUS=20;var AUTOSTATUSCAP=21;var HEIGHT=22;var CLOSETEXT=23;var SNAPX=24;var SNAPY=25;var FIXX=26;var FIXY=27;var FGBACKGROUND=28;var BGBACKGROUND=29;var PADX=30;var PADY=31;var FULLHTML=34;var ABOVE=35;var BELOW=36;var CAPICON=37;var TEXTFONT=38;var CAPTIONFONT=39;var CLOSEFONT=40;var TEXTSIZE=41;var CAPTIONSIZE=42;var CLOSESIZE=43;var FRAME=44;var TIMEOUT=45;var FUNCTION=46;var DELAY=47;var HAUTO=48;var VAUTO=49;var CLOSECLICK=50;var CSSOFF=51;var CSSSTYLE=52;var CSSCLASS=53;var FGCLASS=54;var BGCLASS=55;var TEXTFONTCLASS=56;var CAPTIONFONTCLASS=57;var CLOSEFONTCLASS=58;var PADUNIT=59;var HEIGHTUNIT=60;var WIDTHUNIT=61;var TEXTSIZEUNIT=62;var TEXTDECORATION=63;var TEXTSTYLE=64;var TEXTWEIGHT=65;var CAPTIONSIZEUNIT=66;var CAPTIONDECORATION=67;var CAPTIONSTYLE=68;var CAPTIONWEIGHT=69;var CLOSESIZEUNIT=70;var CLOSEDECORATION=71;var CLOSESTYLE=72;var CLOSEWEIGHT=73;if(typeof ol_fgcolor=='undefined'){var ol_fgcolor="#CCCCFF";}
if(typeof ol_bgcolor=='undefined'){var ol_bgcolor="#333399";}
if(typeof ol_textcolor=='undefined'){var ol_textcolor="#000000";}
if(typeof ol_capcolor=='undefined'){var ol_capcolor="#FFFFFF";}
if(typeof ol_closecolor=='undefined'){var ol_closecolor="#9999FF";}
if(typeof ol_textfont=='undefined'){var ol_textfont="Verdana,Arial,Helvetica";}
if(typeof ol_captionfont=='undefined'){var ol_captionfont="Verdana,Arial,Helvetica";}
if(typeof ol_closefont=='undefined'){var ol_closefont="Verdana,Arial,Helvetica";}
if(typeof ol_textsize=='undefined'){var ol_textsize="1";}
if(typeof ol_captionsize=='undefined'){var ol_captionsize="1";}
if(typeof ol_closesize=='undefined'){var ol_closesize="1";}
if(typeof ol_width=='undefined'){var ol_width="200";}
if(typeof ol_border=='undefined'){var ol_border="1";}
if(typeof ol_offsetx=='undefined'){var ol_offsetx=10;}
if(typeof ol_offsety=='undefined'){var ol_offsety=10;}
if(typeof ol_text=='undefined'){var ol_text="Default Text";}
if(typeof ol_cap=='undefined'){var ol_cap="";}
if(typeof ol_sticky=='undefined'){var ol_sticky=0;}
if(typeof ol_background=='undefined'){var ol_background="";}
if(typeof ol_close=='undefined'){var ol_close="Close";}
if(typeof ol_hpos=='undefined'){var ol_hpos=RIGHT;}
if(typeof ol_status=='undefined'){var ol_status="";}
if(typeof ol_autostatus=='undefined'){var ol_autostatus=0;}
if(typeof ol_height=='undefined'){var ol_height=-1;}
if(typeof ol_snapx=='undefined'){var ol_snapx=0;}
if(typeof ol_snapy=='undefined'){var ol_snapy=0;}
if(typeof ol_fixx=='undefined'){var ol_fixx=-1;}
if(typeof ol_fixy=='undefined'){var ol_fixy=-1;}
if(typeof ol_fgbackground=='undefined'){var ol_fgbackground="";}
if(typeof ol_bgbackground=='undefined'){var ol_bgbackground="";}
if(typeof ol_padxl=='undefined'){var ol_padxl=1;}
if(typeof ol_padxr=='undefined'){var ol_padxr=1;}
if(typeof ol_padyt=='undefined'){var ol_padyt=1;}
if(typeof ol_padyb=='undefined'){var ol_padyb=1;}
if(typeof ol_fullhtml=='undefined'){var ol_fullhtml=0;}
if(typeof ol_vpos=='undefined'){var ol_vpos=BELOW;}
if(typeof ol_aboveheight=='undefined'){var ol_aboveheight=0;}
if(typeof ol_caption=='undefined'){var ol_capicon="";}
if(typeof ol_frame=='undefined'){var ol_frame=self;}
if(typeof ol_timeout=='undefined'){var ol_timeout=0;}
if(typeof ol_function=='undefined'){var ol_function=Function();}
if(typeof ol_delay=='undefined'){var ol_delay=0;}
if(typeof ol_hauto=='undefined'){var ol_hauto=0;}
if(typeof ol_vauto=='undefined'){var ol_vauto=0;}
if(typeof ol_closeclick=='undefined'){var ol_closeclick=0;}
if(typeof ol_css=='undefined'){var ol_css=CSSOFF;}
if(typeof ol_fgclass=='undefined'){var ol_fgclass="";}
if(typeof ol_bgclass=='undefined'){var ol_bgclass="";}
if(typeof ol_textfontclass=='undefined'){var ol_textfontclass="";}
if(typeof ol_captionfontclass=='undefined'){var ol_captionfontclass="";}
if(typeof ol_closefontclass=='undefined'){var ol_closefontclass="";}
if(typeof ol_padunit=='undefined'){var ol_padunit="px";}
if(typeof ol_heightunit=='undefined'){var ol_heightunit="px";}
if(typeof ol_widthunit=='undefined'){var ol_widthunit="px";}
if(typeof ol_textsizeunit=='undefined'){var ol_textsizeunit="px";}
if(typeof ol_textdecoration=='undefined'){var ol_textdecoration="none";}
if(typeof ol_textstyle=='undefined'){var ol_textstyle="normal";}
if(typeof ol_textweight=='undefined'){var ol_textweight="normal";}
if(typeof ol_captionsizeunit=='undefined'){var ol_captionsizeunit="px";}
if(typeof ol_captiondecoration=='undefined'){var ol_captiondecoration="none";}
if(typeof ol_captionstyle=='undefined'){var ol_captionstyle="normal";}
if(typeof ol_captionweight=='undefined'){var ol_captionweight="bold";}
if(typeof ol_closesizeunit=='undefined'){var ol_closesizeunit="px";}
if(typeof ol_closedecoration=='undefined'){var ol_closedecoration="none";}
if(typeof ol_closestyle=='undefined'){var ol_closestyle="normal";}
if(typeof ol_closeweight=='undefined'){var ol_closeweight="normal";}
if(typeof ol_texts=='undefined'){var ol_texts=new Array("Text 0","Text 1");}
if(typeof ol_caps=='undefined'){var ol_caps=new Array("Caption 0","Caption 1");}
var o3_text="";var o3_cap="";var o3_sticky=0;var o3_background="";var o3_close="Close";var o3_hpos=RIGHT;var o3_offsetx=2;var o3_offsety=2;var o3_fgcolor="";var o3_bgcolor="";var o3_textcolor="";var o3_capcolor="";var o3_closecolor="";var o3_width=100;var o3_border=1;var o3_status="";var o3_autostatus=0;var o3_height=-1;var o3_snapx=0;var o3_snapy=0;var o3_fixx=-1;var o3_fixy=-1;var o3_fgbackground="";var o3_bgbackground="";var o3_padxl=0;var o3_padxr=0;var o3_padyt=0;var o3_padyb=0;var o3_fullhtml=0;var o3_vpos=BELOW;var o3_aboveheight=0;var o3_capicon="";var o3_textfont="Verdana,Arial,Helvetica";var o3_captionfont="Verdana,Arial,Helvetica";var o3_closefont="Verdana,Arial,Helvetica";var o3_textsize="1";var o3_captionsize="1";var o3_closesize="1";var o3_frame=self;var o3_timeout=0;var o3_timerid=0;var o3_allowmove=0;var o3_function=Function();var o3_delay=0;var o3_delayid=0;var o3_hauto=0;var o3_vauto=0;var o3_closeclick=0;var o3_css=CSSOFF;var o3_fgclass="";var o3_bgclass="";var o3_textfontclass="";var o3_captionfontclass="";var o3_closefontclass="";var o3_padunit="px";var o3_heightunit="px";var o3_widthunit="px";var o3_textsizeunit="px";var o3_textdecoration="";var o3_textstyle="";var o3_textweight="";var o3_captionsizeunit="px";var o3_captiondecoration="";var o3_captionstyle="";var o3_captionweight="";var o3_closesizeunit="px";var o3_closedecoration="";var o3_closestyle="";var o3_closeweight="";var o3_x=0;var o3_y=0;var o3_allow=0;var o3_showingsticky=0;var o3_removecounter=0;var over=null;var ns4=(document.layers)?true:false;var ns6=(document.getElementById)?true:false;var ie4=(document.all)?true:false;var ie5=false;if(ie4){if((navigator.userAgent.indexOf('MSIE 5')>0)||(navigator.userAgent.indexOf('MSIE 6')>0)){ie5=true;}
if(ns6){ns6=false;}}
if((ns4)||(ie4)||(ns6)){document.onmousemove=mouseMove
if(ns4)document.captureEvents(Event.MOUSEMOVE)}else{overlib=no_overlib;nd=no_overlib;ver3fix=true;}
function no_overlib(){return ver3fix;}
function overlib(){o3_text=ol_text;o3_cap=ol_cap;o3_sticky=ol_sticky;o3_background=ol_background;o3_close=ol_close;o3_hpos=ol_hpos;o3_offsetx=ol_offsetx;o3_offsety=ol_offsety;o3_fgcolor=ol_fgcolor;o3_bgcolor=ol_bgcolor;o3_textcolor=ol_textcolor;o3_capcolor=ol_capcolor;o3_closecolor=ol_closecolor;o3_width=ol_width;o3_border=ol_border;o3_status=ol_status;o3_autostatus=ol_autostatus;o3_height=ol_height;o3_snapx=ol_snapx;o3_snapy=ol_snapy;o3_fixx=ol_fixx;o3_fixy=ol_fixy;o3_fgbackground=ol_fgbackground;o3_bgbackground=ol_bgbackground;o3_padxl=ol_padxl;o3_padxr=ol_padxr;o3_padyt=ol_padyt;o3_padyb=ol_padyb;o3_fullhtml=ol_fullhtml;o3_vpos=ol_vpos;o3_aboveheight=ol_aboveheight;o3_capicon=ol_capicon;o3_textfont=ol_textfont;o3_captionfont=ol_captionfont;o3_closefont=ol_closefont;o3_textsize=ol_textsize;o3_captionsize=ol_captionsize;o3_closesize=ol_closesize;o3_timeout=ol_timeout;o3_function=ol_function;o3_delay=ol_delay;o3_hauto=ol_hauto;o3_vauto=ol_vauto;o3_closeclick=ol_closeclick;o3_css=ol_css;o3_fgclass=ol_fgclass;o3_bgclass=ol_bgclass;o3_textfontclass=ol_textfontclass;o3_captionfontclass=ol_captionfontclass;o3_closefontclass=ol_closefontclass;o3_padunit=ol_padunit;o3_heightunit=ol_heightunit;o3_widthunit=ol_widthunit;o3_textsizeunit=ol_textsizeunit;o3_textdecoration=ol_textdecoration;o3_textstyle=ol_textstyle;o3_textweight=ol_textweight;o3_captionsizeunit=ol_captionsizeunit;o3_captiondecoration=ol_captiondecoration;o3_captionstyle=ol_captionstyle;o3_captionweight=ol_captionweight;o3_closesizeunit=ol_closesizeunit;o3_closedecoration=ol_closedecoration;o3_closestyle=ol_closestyle;o3_closeweight=ol_closeweight;if((ns4)||(ie4)||(ns6)){o3_frame=ol_frame;if(ns4)over=o3_frame.document.overDiv
if(ie4)over=o3_frame.overDiv.style
if(ns6)over=o3_frame.document.getElementById("overDiv");}
var parsemode=-1;var ar=arguments;for(i=0;i<ar.length;i++){if(parsemode<0){if(ar[i]==INARRAY){o3_text=ol_texts[ar[++i]];}else{o3_text=ar[i];}
parsemode=0;}else{if(ar[i]==INARRAY){o3_text=ol_texts[ar[++i]];continue;}
if(ar[i]==CAPARRAY){o3_cap=ol_caps[ar[++i]];continue;}
if(ar[i]==STICKY){o3_sticky=1;continue;}
if(ar[i]==BACKGROUND){o3_background=ar[++i];continue;}
if(ar[i]==NOCLOSE){o3_close="";continue;}
if(ar[i]==CAPTION){o3_cap=ar[++i];continue;}
if(ar[i]==CENTER||ar[i]==LEFT||ar[i]==RIGHT){o3_hpos=ar[i];continue;}
if(ar[i]==OFFSETX){o3_offsetx=ar[++i];continue;}
if(ar[i]==OFFSETY){o3_offsety=ar[++i];continue;}
if(ar[i]==FGCOLOR){o3_fgcolor=ar[++i];continue;}
if(ar[i]==BGCOLOR){o3_bgcolor=ar[++i];continue;}
if(ar[i]==TEXTCOLOR){o3_textcolor=ar[++i];continue;}
if(ar[i]==CAPCOLOR){o3_capcolor=ar[++i];continue;}
if(ar[i]==CLOSECOLOR){o3_closecolor=ar[++i];continue;}
if(ar[i]==WIDTH){o3_width=ar[++i];continue;}
if(ar[i]==BORDER){o3_border=ar[++i];continue;}
if(ar[i]==STATUS){o3_status=ar[++i];continue;}
if(ar[i]==AUTOSTATUS){o3_autostatus=1;continue;}
if(ar[i]==AUTOSTATUSCAP){o3_autostatus=2;continue;}
if(ar[i]==HEIGHT){o3_height=ar[++i];o3_aboveheight=ar[i];continue;}
if(ar[i]==CLOSETEXT){o3_close=ar[++i];continue;}
if(ar[i]==SNAPX){o3_snapx=ar[++i];continue;}
if(ar[i]==SNAPY){o3_snapy=ar[++i];continue;}
if(ar[i]==FIXX){o3_fixx=ar[++i];continue;}
if(ar[i]==FIXY){o3_fixy=ar[++i];continue;}
if(ar[i]==FGBACKGROUND){o3_fgbackground=ar[++i];continue;}
if(ar[i]==BGBACKGROUND){o3_bgbackground=ar[++i];continue;}
if(ar[i]==PADX){o3_padxl=ar[++i];o3_padxr=ar[++i];continue;}
if(ar[i]==PADY){o3_padyt=ar[++i];o3_padyb=ar[++i];continue;}
if(ar[i]==FULLHTML){o3_fullhtml=1;continue;}
if(ar[i]==BELOW||ar[i]==ABOVE){o3_vpos=ar[i];continue;}
if(ar[i]==CAPICON){o3_capicon=ar[++i];continue;}
if(ar[i]==TEXTFONT){o3_textfont=ar[++i];continue;}
if(ar[i]==CAPTIONFONT){o3_captionfont=ar[++i];continue;}
if(ar[i]==CLOSEFONT){o3_closefont=ar[++i];continue;}
if(ar[i]==TEXTSIZE){o3_textsize=ar[++i];continue;}
if(ar[i]==CAPTIONSIZE){o3_captionsize=ar[++i];continue;}
if(ar[i]==CLOSESIZE){o3_closesize=ar[++i];continue;}
if(ar[i]==FRAME){opt_FRAME(ar[++i]);continue;}
if(ar[i]==TIMEOUT){o3_timeout=ar[++i];continue;}
if(ar[i]==FUNCTION){opt_FUNCTION(ar[++i]);continue;}
if(ar[i]==DELAY){o3_delay=ar[++i];continue;}
if(ar[i]==HAUTO){o3_hauto=(o3_hauto==0)?1:0;continue;}
if(ar[i]==VAUTO){o3_vauto=(o3_vauto==0)?1:0;continue;}
if(ar[i]==CLOSECLICK){o3_closeclick=(o3_closeclick==0)?1:0;continue;}
if(ar[i]==CSSOFF){o3_css=ar[i];continue;}
if(ar[i]==CSSSTYLE){o3_css=ar[i];continue;}
if(ar[i]==CSSCLASS){o3_css=ar[i];continue;}
if(ar[i]==FGCLASS){o3_fgclass=ar[++i];continue;}
if(ar[i]==BGCLASS){o3_bgclass=ar[++i];continue;}
if(ar[i]==TEXTFONTCLASS){o3_textfontclass=ar[++i];continue;}
if(ar[i]==CAPTIONFONTCLASS){o3_captionfontclass=ar[++i];continue;}
if(ar[i]==CLOSEFONTCLASS){o3_closefontclass=ar[++i];continue;}
if(ar[i]==PADUNIT){o3_padunit=ar[++i];continue;}
if(ar[i]==HEIGHTUNIT){o3_heightunit=ar[++i];continue;}
if(ar[i]==WIDTHUNIT){o3_widthunit=ar[++i];continue;}
if(ar[i]==TEXTSIZEUNIT){o3_textsizeunit=ar[++i];continue;}
if(ar[i]==TEXTDECORATION){o3_textdecoration=ar[++i];continue;}
if(ar[i]==TEXTSTYLE){o3_textstyle=ar[++i];continue;}
if(ar[i]==TEXTWEIGHT){o3_textweight=ar[++i];continue;}
if(ar[i]==CAPTIONSIZEUNIT){o3_captionsizeunit=ar[++i];continue;}
if(ar[i]==CAPTIONDECORATION){o3_captiondecoration=ar[++i];continue;}
if(ar[i]==CAPTIONSTYLE){o3_captionstyle=ar[++i];continue;}
if(ar[i]==CAPTIONWEIGHT){o3_captionweight=ar[++i];continue;}
if(ar[i]==CLOSESIZEUNIT){o3_closesizeunit=ar[++i];continue;}
if(ar[i]==CLOSEDECORATION){o3_closedecoration=ar[++i];continue;}
if(ar[i]==CLOSESTYLE){o3_closestyle=ar[++i];continue;}
if(ar[i]==CLOSEWEIGHT){o3_closeweight=ar[++i];continue;}}}
if(o3_delay==0){return overlib350();}else{o3_delayid=setTimeout("overlib350()",o3_delay);if(o3_sticky){return false;}else{return true;}}}
function nd(){if(o3_removecounter>=1){o3_showingsticky=0};if((ns4)||(ie4)||(ns6)){if(o3_showingsticky==0){o3_allowmove=0;if(over!=null)hideObject(over);}else{o3_removecounter++;}}
return true;}
function overlib350(){var layerhtml;if(o3_background!=""||o3_fullhtml){layerhtml=ol_content_background(o3_text,o3_background,o3_fullhtml);}else{if(o3_fgbackground!=""&&o3_css==CSSOFF){o3_fgbackground="BACKGROUND=\""+o3_fgbackground+"\"";}
if(o3_bgbackground!=""&&o3_css==CSSOFF){o3_bgbackground="BACKGROUND=\""+o3_bgbackground+"\"";}
if(o3_fgcolor!=""&&o3_css==CSSOFF){o3_fgcolor="BGCOLOR=\""+o3_fgcolor+"\"";}
if(o3_bgcolor!=""&&o3_css==CSSOFF){o3_bgcolor="BGCOLOR=\""+o3_bgcolor+"\"";}
if(o3_height>0&&o3_css==CSSOFF){o3_height="HEIGHT="+o3_height;}else{o3_height="";}
if(o3_cap==""){layerhtml=ol_content_simple(o3_text);}else{if(o3_sticky){layerhtml=ol_content_caption(o3_text,o3_cap,o3_close);}else{layerhtml=ol_content_caption(o3_text,o3_cap,"");}}}
if(o3_sticky){o3_showingsticky=1;o3_removecounter=0;}
layerWrite(layerhtml);if(o3_autostatus>0){o3_status=o3_text;if(o3_autostatus>1){o3_status=o3_cap;}}
o3_allowmove=0;if(o3_timeout>0){if(o3_timerid>0)clearTimeout(o3_timerid);o3_timerid=setTimeout("cClick()",o3_timeout);}
disp(o3_status);if(o3_sticky){o3_allowmove=0;return false;}else{return true;}}
function ol_content_simple(text){if(o3_css==CSSCLASS)txt="<TABLE WIDTH="+o3_width+" BORDER=0 CELLPADDING="+o3_border+" CELLSPACING=0 class=\""+o3_bgclass+"\"><TR><TD><TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 class=\""+o3_fgclass+"\"><TR><TD VALIGN=TOP><FONT class=\""+o3_textfontclass+"\">"+text+"</FONT></TD></TR></TABLE></TD></TR></TABLE>";if(o3_css==CSSSTYLE)txt="<TABLE WIDTH="+o3_width+" BORDER=0 CELLPADDING="+o3_border+" CELLSPACING=0 style=\"background-color: "+o3_bgcolor+"; height: "+o3_height+o3_heightunit+";\"><TR><TD><TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 style=\"color: "+o3_fgcolor+"; background-color: "+o3_fgcolor+"; height: "+o3_height+o3_heightunit+";\"><TR><TD VALIGN=TOP><FONT style=\"font-family: "+o3_textfont+"; color: "+o3_textcolor+"; font-size: "+o3_textsize+o3_textsizeunit+"; text-decoration: "+o3_textdecoration+"; font-weight: "+o3_textweight+"; font-style:"+o3_textstyle+"\">"+text+"</FONT></TD></TR></TABLE></TD></TR></TABLE>";if(o3_css==CSSOFF)txt="<TABLE WIDTH="+o3_width+" BORDER=0 CELLPADDING="+o3_border+" CELLSPACING=0 "+o3_bgcolor+" "+o3_height+"><TR><TD><TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 "+o3_fgcolor+" "+o3_fgbackground+" "+o3_height+"><TR><TD VALIGN=TOP><FONT FACE=\""+o3_textfont+"\" COLOR=\""+o3_textcolor+"\" SIZE=\""+o3_textsize+"\">"+text+"</FONT></TD></TR></TABLE></TD></TR></TABLE>";set_background("");return txt;}
function ol_content_caption(text,title,close){closing="";closeevent="onMouseOver";if(o3_closeclick==1)closeevent="onClick";if(o3_capicon!="")o3_capicon="<IMG SRC=\""+o3_capicon+"\"> ";if(close!=""){if(o3_css==CSSCLASS)closing="<TD ALIGN=RIGHT><A HREF=\"/\" "+closeevent+"=\"return cClick();\" class=\""+o3_closefontclass+"\">"+close+"</A></TD>";if(o3_css==CSSSTYLE)closing="<TD ALIGN=RIGHT><A HREF=\"/\" "+closeevent+"=\"return cClick();\" style=\"color: "+o3_closecolor+"; font-family: "+o3_closefont+"; font-size: "+o3_closesize+o3_closesizeunit+"; text-decoration: "+o3_closedecoration+"; font-weight: "+o3_closeweight+"; font-style:"+o3_closestyle+";\">"+close+"</A></TD>";if(o3_css==CSSOFF)closing="<TD ALIGN=RIGHT><A HREF=\"/\" "+closeevent+"=\"return cClick();\"><FONT COLOR=\""+o3_closecolor+"\" FACE=\""+o3_closefont+"\" SIZE=\""+o3_closesize+"\">"+close+"</FONT></A></TD>";}
if(o3_css==CSSCLASS)txt="<TABLE WIDTH="+o3_width+" BORDER=0 CELLPADDING="+o3_border+" CELLSPACING=0 class=\""+o3_bgclass+"\"><TR><TD><TABLE WIDTH=100% BORDER=0 CELLPADDING=0 CELLSPACING=0><TR><TD><FONT class=\""+o3_captionfontclass+"\">"+o3_capicon+title+"</FONT></TD>"+closing+"</TR></TABLE><TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 class=\""+o3_fgclass+"\"><TR><TD VALIGN=TOP><FONT class=\""+o3_textfontclass+"\">"+text+"</FONT></TD></TR></TABLE></TD></TR></TABLE>";if(o3_css==CSSSTYLE)txt="<TABLE WIDTH="+o3_width+" BORDER=0 CELLPADDING="+o3_border+" CELLSPACING=0 style=\"background-color: "+o3_bgcolor+"; background-image: url("+o3_bgbackground+"); height: "+o3_height+o3_heightunit+";\"><TR><TD><TABLE WIDTH=100% BORDER=0 CELLPADDING=0 CELLSPACING=0><TR><TD><FONT style=\"font-family: "+o3_captionfont+"; color: "+o3_capcolor+"; font-size: "+o3_captionsize+o3_captionsizeunit+"; font-weight: "+o3_captionweight+"; font-style: "+o3_captionstyle+";\">"+o3_capicon+title+"</FONT></TD>"+closing+"</TR></TABLE><TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 style=\"color: "+o3_fgcolor+"; background-color: "+o3_fgcolor+"; height: "+o3_height+o3_heightunit+";\"><TR><TD VALIGN=TOP><FONT style=\"font-family: "+o3_textfont+"; color: "+o3_textcolor+"; font-size: "+o3_textsize+o3_textsizeunit+"; text-decoration: "+o3_textdecoration+"; font-weight: "+o3_textweight+"; font-style:"+o3_textstyle+"\">"+text+"</FONT></TD></TR></TABLE></TD></TR></TABLE>";if(o3_css==CSSOFF)txt="<TABLE WIDTH="+o3_width+" BORDER=0 CELLPADDING="+o3_border+" CELLSPACING=0 "+o3_bgcolor+" "+o3_bgbackground+" "+o3_height+"><TR><TD><TABLE WIDTH=100% BORDER=0 CELLPADDING=0 CELLSPACING=0><TR><TD><B><FONT COLOR=\""+o3_capcolor+"\" FACE=\""+o3_captionfont+"\" SIZE=\""+o3_captionsize+"\">"+o3_capicon+title+"</FONT></B></TD>"+closing+"</TR></TABLE><TABLE WIDTH=100% BORDER=0 CELLPADDING=2 CELLSPACING=0 "+o3_fgcolor+" "+o3_fgbackground+" "+o3_height+"><TR><TD VALIGN=TOP><FONT COLOR=\""+o3_textcolor+"\" FACE=\""+o3_textfont+"\" SIZE=\""+o3_textsize+"\">"+text+"</FONT></TD></TR></TABLE></TD></TR></TABLE>";set_background("");return txt;}
function ol_content_background(text,picture,hasfullhtml){if(hasfullhtml){txt=text;}else{if(o3_css==CSSCLASS)txt="<TABLE WIDTH="+o3_width+o3_widthunit+" BORDER=0 CELLPADDING=0 CELLSPACING=0 HEIGHT="+o3_height+o3_heightunit+"><TR><TD COLSPAN=3 HEIGHT="+o3_padyt+o3_padunit+"></TD></TR><TR><TD WIDTH="+o3_padxl+o3_padunit+"></TD><TD VALIGN=TOP WIDTH="+(o3_width-o3_padxl-o3_padxr)+o3_padunit+"><FONT class=\""+o3_textfontclass+"\">"+text+"</FONT></TD><TD WIDTH="+o3_padxr+o3_padunit+"></TD></TR><TR><TD COLSPAN=3 HEIGHT="+o3_padyb+o3_padunit+"></TD></TR></TABLE>";if(o3_css==CSSSTYLE)txt="<TABLE WIDTH="+o3_width+o3_widthunit+" BORDER=0 CELLPADDING=0 CELLSPACING=0 HEIGHT="+o3_height+o3_heightunit+"><TR><TD COLSPAN=3 HEIGHT="+o3_padyt+o3_padunit+"></TD></TR><TR><TD WIDTH="+o3_padxl+o3_padunit+"></TD><TD VALIGN=TOP WIDTH="+(o3_width-o3_padxl-o3_padxr)+o3_padunit+"><FONT style=\"font-family: "+o3_textfont+"; color: "+o3_textcolor+"; font-size: "+o3_textsize+o3_textsizeunit+";\">"+text+"</FONT></TD><TD WIDTH="+o3_padxr+o3_padunit+"></TD></TR><TR><TD COLSPAN=3 HEIGHT="+o3_padyb+o3_padunit+"></TD></TR></TABLE>";if(o3_css==CSSOFF)txt="<TABLE WIDTH="+o3_width+" BORDER=0 CELLPADDING=0 CELLSPACING=0 HEIGHT="+o3_height+"><TR><TD COLSPAN=3 HEIGHT="+o3_padyt+"></TD></TR><TR><TD WIDTH="+o3_padxl+"></TD><TD VALIGN=TOP WIDTH="+(o3_width-o3_padxl-o3_padxr)+"><FONT FACE=\""+o3_textfont+"\" COLOR=\""+o3_textcolor+"\" SIZE=\""+o3_textsize+"\">"+text+"</FONT></TD><TD WIDTH="+o3_padxr+"></TD></TR><TR><TD COLSPAN=3 HEIGHT="+o3_padyb+"></TD></TR></TABLE>";}
set_background(picture);return txt;}
function set_background(pic){if(pic==""){if(ie4)over.backgroundImage="none";if(ns6)over.style.backgroundImage="none";}else{if(ns4){over.background.src=pic;}else if(ie4){over.backgroundImage="url("+pic+")";}else if(ns6){over.style.backgroundImage="url("+pic+")";}}}
function disp(statustext){if((ns4)||(ie4)||(ns6)){if(o3_allowmove==0){placeLayer();showObject(over);o3_allowmove=1;}}
if(statustext!=""){self.status=statustext;}}
function placeLayer(){var placeX,placeY;if(o3_fixx>-1){placeX=o3_fixx;}else{winoffset=(ie4)?o3_frame.document.body.scrollLeft:o3_frame.pageXOffset;if(ie4)iwidth=o3_frame.document.body.clientWidth;if(ns4)iwidth=o3_frame.innerWidth;if(ns6)iwidth=o3_frame.outerWidth;if(o3_hauto==1){if((o3_x-winoffset)>((eval(iwidth))/2)){o3_hpos=LEFT;}else{o3_hpos=RIGHT;}}
if(o3_hpos==CENTER){placeX=o3_x+o3_offsetx-(o3_width/2);}
if(o3_hpos==RIGHT){placeX=o3_x+o3_offsetx;if((eval(placeX)+eval(o3_width))>(winoffset+iwidth)){placeX=iwidth+winoffset-o3_width;if(placeX<0)placeX=0;}}
if(o3_hpos==LEFT){placeX=o3_x-o3_offsetx-o3_width;if(placeX<winoffset)placeX=winoffset;}
if(o3_snapx>1){var snapping=placeX%o3_snapx;if(o3_hpos==LEFT){placeX=placeX-(o3_snapx+snapping);}else{placeX=placeX+(o3_snapx-snapping);}
if(placeX<winoffset)placeX=winoffset;}}
if(o3_fixy>-1){placeY=o3_fixy;}else{scrolloffset=(ie4)?o3_frame.document.body.scrollTop:o3_frame.pageYOffset;if(o3_vauto==1){if(ie4)iheight=o3_frame.document.body.clientHeight;if(ns4)iheight=o3_frame.innerHeight;if(ns6)iheight=o3_frame.outerHeight;iheight=(eval(iheight))/2;if((o3_y-scrolloffset)>iheight){o3_vpos=ABOVE;}else{o3_vpos=BELOW;}}
if(o3_vpos==ABOVE){if(o3_aboveheight==0){var divref=(ie4)?o3_frame.document.all['overDiv']:over;o3_aboveheight=(ns4)?divref.clip.height:divref.offsetHeight;}
placeY=o3_y-(o3_aboveheight+o3_offsety);if(placeY<scrolloffset)placeY=scrolloffset;}else{placeY=o3_y+o3_offsety;}
if(o3_snapy>1){var snapping=placeY%o3_snapy;if(o3_aboveheight>0&&o3_vpos==ABOVE){placeY=placeY-(o3_snapy+snapping);}else{placeY=placeY+(o3_snapy-snapping);}
if(placeY<scrolloffset)placeY=scrolloffset;}}
repositionTo(over,placeX,placeY);}
function mouseMove(e){if((ns4)||(ns6)){o3_x=e.pageX;o3_y=e.pageY;}
if(ie4){o3_x=event.x;o3_y=event.y;}
if(ie5){o3_x=event.x+o3_frame.document.body.scrollLeft;o3_y=event.y+o3_frame.document.body.scrollTop;}
if(o3_allowmove==1){placeLayer();}}
function cClick(){hideObject(over);o3_showingsticky=0;return false;}
function compatibleframe(frameid){if(ns4){if(typeof frameid.document.overDiv=='undefined')return false;}else if(ie4){if(typeof frameid.document.all["overDiv"]=='undefined')return false;}else if(ns6){if(frameid.document.getElementById('overDiv')==null)return false;}
return true;}
function layerWrite(txt){txt+="\n";if(ns4){var lyr=o3_frame.document.overDiv.document
lyr.write(txt)
lyr.close()}else if(ie4){o3_frame.document.all["overDiv"].innerHTML=txt}else if(ns6){range=o3_frame.document.createRange();range.setStartBefore(over);domfrag=range.createContextualFragment(txt);while(over.hasChildNodes()){over.removeChild(over.lastChild);}
over.appendChild(domfrag);}}
function showObject(obj){if(ns4)obj.visibility="show";else if(ie4)obj.visibility="visible";else if(ns6)obj.style.visibility="visible";}
function hideObject(obj){if(ns4)obj.visibility="hide";else if(ie4)obj.visibility="hidden";else if(ns6)obj.style.visibility="hidden";if(o3_timerid>0)clearTimeout(o3_timerid);if(o3_delayid>0)clearTimeout(o3_delayid);o3_timerid=0;o3_delayid=0;self.status="";}
function repositionTo(obj,xL,yL){if((ns4)||(ie4)){obj.left=xL;obj.top=yL;}else if(ns6){obj.style.left=xL+"px";obj.style.top=yL+"px";}}
function opt_FRAME(frm){o3_frame=compatibleframe(frm)?frm:ol_frame;if((ns4)||(ie4||(ns6))){if(ns4)over=o3_frame.document.overDiv;if(ie4)over=o3_frame.overDiv.style;if(ns6)over=o3_frame.document.getElementById("overDiv");}
return 0;}
function opt_FUNCTION(callme){o3_text=callme()
return 0;}
function vpos_convert(d){if(d==0){d=LEFT;}else{if(d==1){d=RIGHT;}else{d=CENTER;}}
return d;}
function dts(d,text){o3_hpos=vpos_convert(d);overlib(text,o3_hpos,CAPTION,"");}
function dtc(d,text,title){o3_hpos=vpos_convert(d);overlib(text,CAPTION,title,o3_hpos);}
function stc(d,text,title){o3_hpos=vpos_convert(d);overlib(text,CAPTION,title,o3_hpos,STICKY);}
function drs(text){dts(1,text);}
function drc(text,title){dtc(1,text,title);}
function src(text,title){stc(1,text,title);}
function dls(text){dts(0,text);}
function dlc(text,title){dtc(0,text,title);}
function slc(text,title){stc(0,text,title);}
function dcs(text){dts(2,text);}
function dcc(text,title){dtc(2,text,title);}
function scc(text,title){stc(2,text,title);}