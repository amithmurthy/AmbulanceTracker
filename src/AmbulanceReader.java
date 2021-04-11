import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class AmbulanceReader {
	static String[] colheaders;
	static Object[][] ambulanceData;
	static String[] ambulance;
	static ArrayList<String[]> temp;
	public static Object[][] table(){
		Scanner scanner = null;
		try{
			scanner = new Scanner(new File("ambulances.csv"));
			if(scanner.hasNext()){
				String line = scanner.nextLine().trim();
				String[] split = line.replace("\"", "").split(",");
				colheaders = new String[split.length];
				colheaders = split;
			}
			temp = new ArrayList<>();
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
			ambulanceData = new Object[temp.size()][];
			for(int row = 0; row < temp.size(); row++){
				ambulanceData[row] = new String[temp.get(row).length];
				ambulanceData[row] = temp.get(row);
			}
			ArrayList<String> arei = new ArrayList<String>();
			for(int i = 0; i < ambulanceData.length;i++){
				arei.add((String) ambulanceData[i][0]);
			}
			ambulance = arei.toArray(new String[arei.size()]);
			System.out.println(Arrays.deepToString(ambulanceData));
			System.out.print(ambulanceData[0][0]);
		} catch(FileNotFoundException e){
			System.out.println("File not found");
			System.exit(0);
		} finally {
			if(scanner != null){
				scanner.close();
			}
			return ambulanceData;
		} 
	}
	public static String[] row(){
		ambulanceData = table();
		ArrayList<String> arei = new ArrayList<String>();
		arei.add("None");
		for(int i = 0; i < ambulanceData.length;i++){
			arei.add((String) ambulanceData[i][0]);
		}
		ambulance = arei.toArray(new String[arei.size()]);
		return ambulance;
	}
	public static void main(String[] args) throws IOException {
		table();	
	}
}
