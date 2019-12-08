import java.util.*;
import java.io.*;

class Inverted{
	Map<String,Set<Integer>> map=new HashMap<>(); //key is the extracted word and value is the vector containing paragraph no

public void buildIndex() throws IOException{
	File file=new File("C:/Users/Subham Sharma/workspace/TapChief1/src/input.txt");		//creation of file descriptor
	FileReader fr=new FileReader(file);		//creation of file reader object
	int c=0; 								//to store character's integer value
	int docId=1;							//to track paragraph number
	String word="";
	int newlinecounter=0;
	BufferedReader br=new BufferedReader(fr);
	while((c=br.read())!=-1)
	{
		char ch=(char)(c);
		if(ch!=' ' && ch!='\n')
			word+=ch;
		else if(ch=='\n' && newlinecounter!=2)
		{
			++newlinecounter;
			continue;
		}
		else if(ch==' ')
		{										//mapping of the word w.r.t docId 
			word=word.toLowerCase();
			
			if(map.get(word)==null)
			{									//word not present,new word to be indexed
				Set<Integer> paragraph=new HashSet<Integer>();
				if(paragraph.add(docId))
				{
				map.put(word,paragraph);
				word="";
				}
			}
			else
			{
				map.get(word).add(docId);		//word already present to be updated with the docId
				word="";
			}
			
		}
		
		else if(newlinecounter==2)				//change of paragraph
			{
			docId++;
		    newlinecounter=0;
		    }
	}
}
public void search(String word){
	if(map.get(word)==null)
		System.out.print("The word entered by you doesn't exist in the document\n");
	else
	{	int counter=1;
		System.out.print("Your words top 10 paragraph is:\n");
		for(Integer i:map.get(word))
		{
			System.out.println("Paragraph "+i);
			if(counter++==10)								//only to display top 10 paragraph
				break;
		}
	}

}
public void clear() 
{
	map.clear();
}
}
public class TapChief {

	public static void main(String[] args) throws IOException {
		Inverted invertedIndex=new Inverted();
		Scanner sc=new Scanner(System.in);
		
		boolean flag=true;
		while(flag)
		{
			System.out.print("Enter your option\n");
			System.out.print("1-Index\n2-Search\n3-Clear the map\n4-Exit\n");
			int choice=sc.nextInt();
			switch(choice){
		
		case 1:
			invertedIndex.buildIndex();
			System.out.print("Document indexed properly\n");
			break;
		case 2:
			System.out.println("Enter the word you want to search \n");
			String word=sc.next().toLowerCase();	
			invertedIndex.search(word);
			break;
		case 3:
			invertedIndex.clear();
			break;
		case 4:
			flag=false;
			break;
		default:
			System.out.print("Enter a valid choice");
		}		
	}
}
}