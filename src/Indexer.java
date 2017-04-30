import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class Indexer {
	
	private IndexWriter writer;
	
	@SuppressWarnings("deprecation")
	public Indexer(String indexDiretoryPath) throws IOException{
		
		Directory indexDirectory = FSDirectory.open(new File(indexDiretoryPath));
		writer = new IndexWriter(indexDirectory, new StandardAnalyzer(Version.LUCENE_36), true, IndexWriter.MaxFieldLength.UNLIMITED);
	}
	
	public void close() throws CorruptIndexException, IOException{
		
		writer.close();
	}
	
	private Document getDocument(File file) throws IOException{
		
		Document document = new Document();
		
		Field contentField = new Field(Constants.CONTENTS, new FileReader(file));
		Field fileNameField = new Field(Constants.FILE_NAME, file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field filePathField = new Field(Constants.FILE_PATH, file.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED);
		
		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);
		
		return document;
	}
	
	private void indexFile(File file) throws IOException{
		
		System.out.println("Indexação " + file.getCanonicalPath());
		Document document = getDocument(file);
		writer.addDocument(document);
	}
	
	public int createIdex(String dataDirPath, FileFilter filter) throws IOException{
		
		File[] files = new File(dataDirPath).listFiles();
		
		for (File file : files){
			
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead()){
				indexFile(file);
			}
		}
		
		return writer.numDocs();
	}
}
