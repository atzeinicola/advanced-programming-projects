package es3;
import java.util.*;
import es1.*; import es2.*;
public class HtmlGenerator {
	protected int canvasWidth = 400;
	protected int canvasHeight = 500;
	protected int nodeRadius = 40;
	public Diagram diagram;

	public void initializeDiagram(ParseTree ptree) {		
		String diagramName = ptree.children.get(1).node.toString(); 
		diagram = new Diagram(diagramName);		
		int type = ptree.children.get(0).node.tag;
		if (type==Token.DIGRAPH) diagram.directed = true;		
		extractNodesAndEdges(ptree.children.get(3));
	}
	
	private void extractNodesAndEdges(ParseTree ptree){					//E
		if (ptree.children.size()==0) return;		
		ParseTree ide = ptree.children.get(0);
		ParseTree attributes = ptree.children.get(1);
		boolean isNode = ptree.children.get(2).children.size()==0;		
		if (isNode) {
			Node n = new Node();
			if (attributes.children.size()!=0) {
				extractAttributes(attributes, n);
				n.r = this.nodeRadius;
				n.x = (int) ((Math.random()*(canvasWidth-2*n.r)) + n.r);
				n.y = (int) ((Math.random()*(canvasHeight-2*n.r)) + n.r);
				n.ide = ide.node.toString();
			}
			diagram.nodes.put(n.ide, n);
		}
		else {
			Edge e = new Edge();
			if (diagram.directed) e.directed=true;
			e.from = diagram.nodes.get(ide.node.toString());
			extractAttributes(attributes, e);
			extractEdges(ptree.children.get(2), e);
		}
		extractNodesAndEdges(ptree.children.get(4));
	}
	
	private void extractEdges(ParseTree ptree, Edge e){				//E'
		if (ptree.children.size()==0) return;		
		e.to = diagram.nodes.get( ptree.children.get(2).node.toString());
		diagram.edges.put("edge"+diagram.edges.size(), e);		
		Edge e1 = new Edge();
		if (diagram.directed) e.directed=true;
		e1.from = e.to;
		ParseTree attributes = ptree.children.get(3);
		extractAttributes( attributes, e1 );	
		extractEdges(ptree.children.get(4), e1);
	}
	
	private void extractAttributes(ParseTree ptree, Edge e){	//A
		if (ptree.children.size()==0) return;		
		ParseTree attribute = ptree.children.get(1);
		ParseTree otherAttributes = ptree.children.get(2);
		Token attributeName = attribute.children.get(0).children.get(0).node;
		Token attributeValue = attribute.children.get(2).children.get(0).node;		
		if (attributeName.tag == Token.STYLE){
			if (attributeValue.tag==Token.DOTTED)
				e.style = Edge.DOTTED;
		}
		else if (attributeName.tag == Token.LABEL)
			e.label = attributeValue.toString().replaceAll("\"","");				
		if (otherAttributes.children.size()!=0)
			extractAttributes(otherAttributes, e);
	}
	
	private void extractAttributes(ParseTree ptree, Node n){	//A
		if (ptree.children.size()==0) return;		
		ParseTree attribute = ptree.children.get(1);
		ParseTree otherAttributes = ptree.children.get(2);
		Token attributeName = attribute.children.get(0).children.get(0).node;
		Token attributeValue = attribute.children.get(2).children.get(0).node;
		if (attributeName.tag == Token.LABEL)
			n.label = attributeValue.toString().replaceAll("\"","");				
		if (otherAttributes.children.size()!=0)
			extractAttributes(otherAttributes, n);
	}
	
	public String getHtml() {
		String html = ""+		
		"<!DOCTYPE html>\n"+
		"<html>\n"+
		"<body>\n"+
		    "\t<canvas id=\"myCanvas\" width=\""+canvasWidth+"\" height=\""+canvasHeight+"\" style=\"border:1px solid #d3d3d3;\">\n"+
		    "\tYour browser does not support the HTML5 canvas tag.</canvas>\n\n"+
		    "\t<script>\n"+
		    "\tfunction Node (x,y,r,label,color) {\n"+
		        "\t\tthis.x = x, this.y = y, this.r = r, this.label = label, this.color = color;\n"+
		        "\t\tthis.printNode = function (ctx) {\n"+
		            "\t\t\t//circle\n"+
		            "\t\t\tctx.beginPath();\n"+
		            "\t\t\tctx.arc(this.x,this.y,this.r,0,2*Math.PI);\n"+
		            "\t\t\tctx.closePath();\n"+
		            "\t\t\tctx.fillStyle=this.color;\n"+
		            "\t\t\tctx.fill();\n"+
		            "\t\t\tctx.strokeStyle=\"#000000\";\n"+
		            "\t\t\tctx.stroke();\n"+
		            "\t\t\t//label\n"+
		            "\t\t\tctx.textAlign=\"center\";  \n"+
		            "\t\t\tctx.textBaseline=\"middle\";\n"+
		            "\t\t\tctx.fillStyle=\"#000000\";\n"+
		            "\t\t\tctx.fillText(this.label,this.x,this.y);\n"+
		        "\t\t};\n"+
		    "\t}\n\n"+
		    "\tfunction Line (a,b,style,label,directed) {\n"+
		        "\t\tthis.a = a, this.b = b, this.style = style, this.label = label, this.directed = directed;\n"+
		        "\t\tthis.printLine = function (ctx) {\n"+
		            "\t\t\tvar angle = Math.atan2(b.y-a.y,b.x-a.x);\n"+
		            "\t\t\tvar fromX = a.x+a.r*Math.cos(angle)\n"+
		            "\t\t\tvar fromY = a.y+a.r*Math.sin(angle)\n"+
		            "\t\t\tvar toX = b.x-b.r*Math.cos(angle)\n"+
		            "\t\t\tvar toY = b.y-b.r*Math.sin(angle)\n"+
		            "\t\t\tif (this.style=='dotted'){\n"+
		                "\t\t\t\tctx.strokeStyle=\"#000000\";\n"+
		                "\t\t\t\tdrawDashedLine(ctx,fromX,fromY,toX,toY,[2,10]);\n"+
		            "\t\t\t}\n"+
		            "\t\t\telse {\n"+
		                "\t\t\t\tctx.beginPath();\n"+
		                "\t\t\t\tctx.moveTo(fromX,fromY);\n"+
		                "\t\t\t\tctx.lineTo(toX,toY);\n"+
		                "\t\t\t\tctx.closePath();\n"+
		                "\t\t\t\tctx.strokeStyle=\"#000000\";\n"+
		                "\t\t\t\tctx.stroke();\n"+
		            "\t\t\t}\n"+
		            "\t\t\tif (directed) {\n"+
		                "\t\t\t\tctx.beginPath()\n"+
		                "\t\t\t\tctx.moveTo(toX,toY);\n"+
		                "\t\t\t\tctx.lineTo(toX-10*Math.cos(angle-Math.PI/6),toY-10*Math.sin(angle-Math.PI/6));\n"+
		                "\t\t\t\tctx.moveTo(toX, toY);\n"+
		                "\t\t\t\tctx.lineTo(toX-10*Math.cos(angle+Math.PI/6),toY-10*Math.sin(angle+Math.PI/6));\n"+
		                "\t\t\t\tctx.closePath()\n"+
		                "\t\t\t\tctx.strokeStyle=\"#000000\";\n"+
		                "\t\t\t\tctx.stroke();\n"+
		            "\t\t\t}\n"+
		            "\t\t\t//label\n"+
		            "\t\t\tvar x = (fromX + toX)/2;\n"+
		            "\t\t\tvar y = (fromY + toY)/2;\n"+
		            "\t\t\tctx.textAlign=\"center\";  \n"+
		            "\t\t\tctx.textBaseline=\"middle\";\n"+
		            "\t\t\tctx.fillStyle=\"#000000\";\n"+
		            "\t\t\tctx.fillText(this.label,x,y);\n"+
		        "\t\t};\n"+
		    "\t}\n\n"+
		    "\tfunction drawDashedLine(context, fromX, fromY, toX, toY, dashPattern) {\n"+
		        "\t\tvar dx = toX - fromX;\n"+
			    "\t\tvar dy = toY - fromY;\n"+
			    "\t\tvar angle = Math.atan2(dy, dx);\n"+
			    "\t\tvar x = fromX;\n"+
			    "\t\tvar y = fromY;\n"+
			    "\t\tvar idx = 0;\n"+
			    "\t\tvar draw = true;\n"+
			    "\t\tcontext.beginPath();\n"+
			    "\t\tcontext.moveTo(fromX, fromY);\n"+
			    "\t\twhile (!((dx < 0 ? x <= toX : x >= toX) && (dy < 0 ? y <= toY : y >= toY))) {\n"+
				    "\t\t\tvar dashLength = dashPattern[idx++ % dashPattern.length];\n"+
				    "\t\t\tvar nx = x + (Math.cos(angle) * dashLength);\n"+
				    "\t\t\tx = dx < 0 ? Math.max(toX, nx) : Math.min(toX, nx);\n"+
				    "\t\t\tvar ny = y + (Math.sin(angle) * dashLength);\n"+
				    "\t\t\ty = dy < 0 ? Math.max(toY, ny) : Math.min(toY, ny);\n"+
				    "\t\t\tif (draw) context.lineTo(x, y);\n"+
				    "\t\t\telse context.moveTo(x, y);\n"+
				    "\t\t\tdraw = !draw;\n"+
			    "\t\t}\n"+
			    "\t\tcontext.closePath();\n"+
			    "\t\tcontext.stroke();\n"+
		    "\t};\n\n"+			    
		    "\tfunction clear (ctx) {\n"+
		      "\t\tctx.clearRect(0,0,canvas.width,canvas.height);\n"+  
		    "\t};\n\n"+		    
		    "\tfunction printDiagram(ctx,node,line) {\n"+
		      "\t\tclear(ctx)\n"+
		      "\t\tfor(var i=0; i<line.length; i++)  line[i].printLine(ctx);\n"+
		      "\t\tfor(var i=0; i<node.length; i++)  node[i].printNode(ctx);\n"+
		    "\t};\n\n"+		
		    "\tvar canvas=document.getElementById(\"myCanvas\");\n"+
		    "\tvar ctx=canvas.getContext(\"2d\");\n\n"+
		    "\tvar lines = new Array();\n"+
		    "\tvar nodes = new Array();\n\n";		    
			int i=0;
		    for (Map.Entry<String,Node> entry : diagram.nodes.entrySet()){
		    	Node n = entry.getValue();
		    	html += "\tvar "+entry.getKey()+" = new Node ("+n.x+","+n.y+","+n.r+",\""+n.label+"\",\"#ffffff\");\n";
		    	html += "\tnodes["+i+"] = "+entry.getKey()+";\n";
		    	i++;
		    }
		    html += "\n";
		    i=0;
		    for (Map.Entry<String,Edge> entry : diagram.edges.entrySet()){
		    	Edge e = entry.getValue();
		    	html += "\tvar "+entry.getKey()+" = new Line ("+e.from.ide+","+e.to.ide+",\""+(e.style==Edge.DOTTED?"dotted":"")+"\",\""+(e.label!=null?e.label:"")+"\","+(e.directed?"true":"false")+");\n";
		    	html += "\tlines["+i+"] = "+entry.getKey()+";\n";
		    	i++;
		    }
		    html += 
		    "\n\tprintDiagram(ctx,nodes,lines);\n"+     
		    "\t</script>\n"+
		"</body>\n"+
		"</html>";		
		return html;
	}
}
