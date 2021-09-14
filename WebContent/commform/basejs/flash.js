function flash(ur,w,h){
        document.write('<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="../sys/activex/swflash.cab#version=6,0,29,0" width="'+w+'" height="'+h+'"> ');
        document.write('<param name="movie" value="' + ur + '">');
        document.write('<param name="quality" value="high"> ');
        document.write('<param name="wmode" value="transparent"> ');
        document.write('<param name="menu" value="false"> ');
        document.write('<embed src="' + ur + '" quality="high" pluginspage="" type="application/x-shockwave-flash" width="'+w+'" height="'+h+'" wmode="transparent"></embed> ');
        document.write('</object> ');
}
