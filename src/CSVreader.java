import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

public class CSVreader extends AmbulanceTrackerApp{
	static String[] colnames;
	static Object[][] data;
	public static Object[][] table(){
		Scanner scanner = null;
		try{
			scanner = new Scanner(new File("patients.csv"));
			if(scanner.hasNext()){
				String line = scanner.nextLine().trim();
				String[] split = line.replace("\"", "").split(",");
				colnames = new String[split.length];
				colnames = split;
			}
			ArrayList<String[]> temp = new ArrayList<>();
			for(int row = 0; scanner.hasNext();row++){
				String[] line = scanner.nextLine().replace("\"", "").split(",");
				String[] new_array = new String[4];
				new_array[0] = line[0];
				new_array[1] = "(" + line[1] + "," + line[2] + ")";
				new_array[2] = line[3];
				if (line.length == 4){
					new_array[3] = "-";
					
				}
				
				else{
					new_array[3] = line[4];
				}
				temp.add(new_array);
			}
			data = new Object[temp.size()][];
			for(int row = 0; row < temp.size(); row++){
				data[row] = new String[temp.get(row).length];
				data[row] = temp.get(row);
			}
			System.out.println(Arrays.deepToString(data));
			System.out.print(Arrays.deepToString(data[2]));
		} catch(FileNotFoundException e){
			System.out.println("File not found");
			System.exit(0);
		} finally {
			if(scanner != null){
				scanner.close();
			}
			return data;
		} 
	}
	public static String[] data(){
		data = table();
		ArrayList<String> array = new ArrayList<String>();
		array.add("None");
		for(int i = 0; i < data.length;i++){
			array.add((String) data[i][0]);
		}
		String[] patients = array.toArray(new String[array.size()]);
		return patients;
	}
	public static void main(String[] args) throws IOException {
		table();	
	}
}