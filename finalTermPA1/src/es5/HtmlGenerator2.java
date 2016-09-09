package es5;
import es3.*;
public class HtmlGenerator2 extends HtmlGenerator{
	public String getHtml() {
		String html = super.getHtml();		
		int idx = html.indexOf("\t</script>\n</body>\n</html>");
		html = html.substring(0, idx);		
		html += "\n"+
		    "\tfunction mousemovePointer(e){\n"+
		        "\t\tvar mouseX = e.layerX - canvas.offsetLeft;\n"+
		        "\t\tvar mouseY = e.layerY - canvas.offsetTop;\n"+
		        "\t\tfor(i=0; i<nodes.length; i++){\n"+
		            "\t\t\tdx = mouseX - nodes[i].x;\n"+
		            "\t\t\tdy = mouseY - nodes[i].y;\n"+
		            "\t\t\tif (Math.sqrt((dx*dx) + (dy*dy)) < nodes[i].r) {\n"+         
		                "\t\t\t\tcanvas.style.cursor=\"pointer\";\n"+
		                "\t\t\t\treturn;\n"+
		            "\t\t\t}\n"+
		            "\t\t\tcanvas.style.cursor=\"auto\";\n"+
		        "\t\t}\n"+
		    "\t};\n\n"+		    
		    "\tfunction mousedown(e){\n"+
		        "\t\tvar mouseX = e.layerX - canvas.offsetLeft;\n"+
		        "\t\tvar mouseY = e.layerY - canvas.offsetTop;\n"+
		        "\t\tfor(i=0; i<nodes.length; i++){\n"+
		            "\t\t\tdx = mouseX - nodes[i].x;\n"+
		            "\t\t\tdy = mouseY - nodes[i].y;\n"+
		            "\t\t\tif (Math.sqrt((dx*dx) + (dy*dy)) < nodes[i].r) {\n"+         
		                "\t\t\t\tdragIdx = i;\n"+
		                "\t\t\t\tdragOffsetX = dx;\n"+
		                "\t\t\t\tdragOffsetY = dy;\n"+        
		                "\t\t\t\tcanvas.onmousemove = mousemove;\n"+
		                "\t\t\t\tcanvas.onmouseup = mouseup;\n"+
		                "\t\t\t\treturn;\n"+
		            "\t\t\t}\n"+
		        "\t\t}\n"+
		    "\t};\n\n"+	
		    "\tfunction mousemove(e) {\n"+
		         "\t\tvar mouseX = e.layerX - canvas.offsetLeft;\n"+
		         "\t\tvar mouseY = e.layerY - canvas.offsetTop;\n"+
		         "\t\tnodes[dragIdx].x = mouseX - dragOffsetX;\n"+
		         "\t\tnodes[dragIdx].y = mouseY - dragOffsetY;\n"+
		         "\t\tnodes[dragIdx].color = \"#ddddff\";\n"+
		         "\t\tprintDiagram(ctx,nodes, lines);\n"+
		    "\t};\n\n"+	
		    "\tfunction mouseup(e) {\n"+
		        "\t\tnodes[dragIdx].color = \"#ffffff\";\n"+
		        "\t\tdragIdx = -1;\n"+
		        "\t\tcanvas.onmousemove=null;\n"+
		        "\t\tcanvas.onmouseup=null;\n"+
		        "\t\tprintDiagram(ctx,nodes, lines);\n"+
		    "\t};\n\n"+		    
		    "\tcanvas.addEventListener(\"mousedown\",mousedown);\n"+
		    "\tcanvas.addEventListener(\"mousemove\",mousemovePointer);\n"+
		    "\tvar dragIdx = -1;\n"+
		    "\tvar dragOffsetX, dragOffsetY;\n"+
		    "\t</script>\n"+
		"</body>\n"+
		"</html>";		
		return html;
	}
}
