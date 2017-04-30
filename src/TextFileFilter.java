import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {
	
	@Override
	public boolean accept(File pathname) {
		
		if(pathname.getName().toLowerCase().endsWith(".odt")){
			return pathname.getName().toLowerCase().endsWith(".odt");
		}else if(pathname.getName().toLowerCase().endsWith(".doc")){
			return pathname.getName().toLowerCase().endsWith(".doc");
		}else if(pathname.getName().toLowerCase().endsWith(".docx")){
			return pathname.getName().toLowerCase().endsWith(".docx");
		}else if(pathname.getName().toLowerCase().endsWith(".pdf")){
			return pathname.getName().toLowerCase().endsWith(".pdf");
		}else if(pathname.getName().toLowerCase().endsWith(".ppt")){
			return pathname.getName().toLowerCase().endsWith(".ppt");
		}else if(pathname.getName().toLowerCase().endsWith(".pptx")){
			return pathname.getName().toLowerCase().endsWith(".pptx");
		}else if(pathname.getName().toLowerCase().endsWith(".pptx")){
			return pathname.getName().toLowerCase().endsWith(".pptx");
		}else if(pathname.getName().toLowerCase().endsWith(".pptx")){
			return pathname.getName().toLowerCase().endsWith(".pptx");
		}else if(pathname.getName().toLowerCase().endsWith(".xls")){
			return pathname.getName().toLowerCase().endsWith(".xls");
		}else if(pathname.getName().toLowerCase().endsWith(".xlsx")){
			return pathname.getName().toLowerCase().endsWith(".xlsx");
		}else{
			return pathname.getName().toLowerCase().endsWith(".txt");
		}
	}
}
