<%@page import="java.io.OutputStream"%>
<%@page import="sun.misc.BASE64Decoder"%>
<%	

		String imgBase64Code = request.getParameter("dContent");
		out.clear();
		response.reset();
		response.setContentType("application/octet-stream;image/PNG;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(("Img"+System.currentTimeMillis()+".png").getBytes())); 
        String[] arr = imgBase64Code.split("base64,");
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream os = response.getOutputStream();
        try {
        	os.flush(); 
            byte[] b = decoder.decodeBuffer(arr[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            os.write(b);
            os.close();
            os=null;
        } catch (Exception e) {
           e.printStackTrace();
        }
 %>