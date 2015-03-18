/*
 * author: Aditya Iyengar
 */
package extra_credit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TableItem;

public class GUI {

	protected Shell shlPageframe;
	Table table;
	private Text txtCurrentProcess;
	ArrayList<String> inputs = new ArrayList<String>();
	pMemory_Space phy_mem = new pMemory_Space(16,1);
	pMemory_SC phy_mem2 = new pMemory_SC(16,1);
	private Text txtTotalPageFaults;
	private Text txtPleaseClickThe;
	private Table table_1;
	Button btnStep;
	Button btnSimulate;
	boolean sC = false;
	long time = 1000;
	
	TableItem tableItem_1;
	
	TableItem tableItem;
	
	TableItem tableItem_2;
	
	TableItem tableItem_3;
	
	TableItem tableItem_4;
	
	TableItem tableItem_5;
	
	TableItem tableItem_6;
	
	TableItem tableItem_7;
	
	TableItem tableItem_8;
	
	TableItem tableItem_9;
	
	TableItem tableItem_10;
	
	TableItem tableItem_11;
	
	TableItem tableItem_12;
	
	TableItem tableItem_13;
	
	TableItem tableItem_14;
	
	TableItem tableItem_15;
	private Button btnSetUp;
	private Button btnRefresh;
	private Button btnSecondChance;
	private Button btnLru;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GUI window = new GUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlPageframe.open();
		shlPageframe.layout();
		while (!shlPageframe.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlPageframe = new Shell();
		shlPageframe.setMinimumSize(new Point(1350, 700));
		shlPageframe.setSize(905, 499);
		shlPageframe.setText("LRU/Second Chance Page Replacement Simulation\r\n");
		shlPageframe.setLayout(null);
		
		btnSimulate = new Button(shlPageframe, SWT.NONE);
		btnSimulate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!sC){
					for (int i = 0; i < inputs.size(); i++){
						String current_input = inputs.get(i);
						String [] parts = current_input.split("\\s");
						phy_mem.access_frame(Character.getNumericValue((parts[0].charAt(1))),getDecimalFromBinary(Integer.valueOf(parts[1])));
						txtCurrentProcess.setText("CURRENT PROCESS: PROCESS "+parts[0].charAt(1));
						txtTotalPageFaults.setText("TOTAL PAGE FAULTS: " + phy_mem.page_faults);
						if (phy_mem.page_fault && phy_mem.enoughSpace){
							txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
									getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
											+ " not found in the memory which resulted in page fault. Since,"
											+ " there is enough room in the memory, the page was added to the memory.");
						}
						else if (phy_mem.page_fault){
							txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
									getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
											+ " not found in the memory which resulted in page fault. This page"
											+ " was added in the memory by removing the least recently used page "
											+ phy_mem.removed.page_num + " of process " + phy_mem.removed.process_num);
						}
						else{
							txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
									getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was found"
											+ " in the memory and therefore no page fault occured in this reference.");
						}
						
					}
				}
				else {
					for (int i = 0; i < inputs.size(); i++){
						String current_input = inputs.get(i);
						String [] parts = current_input.split("\\s");
						phy_mem2.access_frame(Character.getNumericValue((parts[0].charAt(1))),getDecimalFromBinary(Integer.valueOf(parts[1])));
						txtCurrentProcess.setText("CURRENT PROCESS: PROCESS "+parts[0].charAt(1));
						txtTotalPageFaults.setText("TOTAL PAGE FAULTS: " + phy_mem2.page_faults);
						if (phy_mem2.page_fault && phy_mem2.enoughSpace){
							txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
									getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
											+ " not found in the memory which resulted in page fault. Since,"
											+ " there is enough room in the memory, the page was added to the memory.");
						}
						else if (phy_mem2.page_fault){
							txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
									getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
											+ " not found in the memory which resulted in page fault. This page"
											+ " was added in the memory by removing the least recently used page "
											+ phy_mem2.removed.page_num + " of process " + phy_mem2.removed.process_num);
						}
						else{
							txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
									getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was found"
											+ " in the memory and therefore no page fault occured in this reference.");
						}
					}
				}
				inputs = new ArrayList<String>();
				updateTable();
				btnStep.setEnabled(false);
				btnSimulate.setEnabled(false);
				btnRefresh.setEnabled(true);
				btnSetUp.setEnabled(true);
			}
		});
		btnSimulate.setBounds(10, 10, 75, 25);
		btnSimulate.setText("Simulate");
		btnSimulate.setEnabled(false);
		
		btnStep = new Button(shlPageframe, SWT.NONE);
		btnStep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!sC){
					String current_input = inputs.remove(0);
					String [] parts = current_input.split("\\s");
					phy_mem.access_frame(Character.getNumericValue((parts[0].charAt(1))),getDecimalFromBinary(Integer.valueOf(parts[1])));
					txtCurrentProcess.setText("CURRENT PROCESS: PROCESS "+parts[0].charAt(1));
					txtTotalPageFaults.setText("TOTAL PAGE FAULTS: " + phy_mem.page_faults);
					if (phy_mem.page_fault && phy_mem.enoughSpace){
						txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
								getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
										+ " not found in the memory which resulted in page fault. Since,"
										+ " there is enough room in the memory, the page was added to the memory.");
					}
					else if (phy_mem.page_fault){
						txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
								getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
										+ " not found in the memory which resulted in page fault. This page"
										+ " was added in the memory by removing the least recently used page "
										+ phy_mem.removed.page_num + " of process " + phy_mem.removed.process_num);
					}
					else{
						txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
								getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was found"
										+ " in the memory and therefore no page fault occured in this reference.");
					}
					updateTable();
				}
				else {
					String current_input = inputs.remove(0);
					String [] parts = current_input.split("\\s");
					phy_mem2.access_frame(Character.getNumericValue((parts[0].charAt(1))),getDecimalFromBinary(Integer.valueOf(parts[1])));
					txtCurrentProcess.setText("CURRENT PROCESS: PROCESS "+parts[0].charAt(1));
					txtTotalPageFaults.setText("TOTAL PAGE FAULTS: " + phy_mem2.page_faults);
					if (phy_mem2.page_fault && phy_mem2.enoughSpace){
						txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
								getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
										+ " not found in the memory which resulted in page fault. Since,"
										+ " there is enough room in the memory, the page was added to the memory.");
					}
					else if (phy_mem.page_fault){
						txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
								getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was"
										+ " not found in the memory which resulted in page fault. This page"
										+ " was added in the memory by removing the least recently used page "
										+ phy_mem2.removed.page_num + " of process " + phy_mem2.removed.process_num);
					}
					else{
						txtPleaseClickThe.setText("Process " + parts[0].charAt(1) + " is accessing it's page " + 
								getDecimalFromBinary(Integer.valueOf(parts[1])) + ". The page was found"
										+ " in the memory and therefore no page fault occured in this reference.");
					}
					updateTable();
				}
			}
		});
		btnStep.setBounds(91, 10, 75, 25);
		btnStep.setText("Step");
		btnStep.setEnabled(false);
		
		txtCurrentProcess = new Text(shlPageframe, SWT.BORDER);
		txtCurrentProcess.setText("CURRENT PROCESS: ");
		txtCurrentProcess.setEditable(false);
		txtCurrentProcess.setBounds(32, 156, 222, 40);
		
		btnSetUp = new Button(shlPageframe, SWT.NONE);
		btnSetUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File file = new File(System.getProperty("user.dir")+"/"+"input.txt.txt");
				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							inputs.add(line);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtPleaseClickThe.setText("The program has been set up and is ready to run!!!");
				btnSetUp.setEnabled(false);
				btnSimulate.setEnabled(true);
				btnStep.setEnabled(true);
			}
		});
		btnSetUp.setBounds(172, 10, 75, 25);
		btnSetUp.setText("Set Up");
		
		txtTotalPageFaults = new Text(shlPageframe, SWT.BORDER);
		txtTotalPageFaults.setEditable(false);
		txtTotalPageFaults.setText("TOTAL PAGE FAULTS: ");
		txtTotalPageFaults.setBounds(32, 216, 222, 40);
		
		txtPleaseClickThe = new Text(shlPageframe, SWT.BORDER);
		txtPleaseClickThe.setText("PLEASE CLICK THE SET UP BUTTON FIRST TO TAKE THE REFERENCE STRINGS FROM THE TEXT FILE.");
		txtPleaseClickThe.setEditable(false);
		txtPleaseClickThe.setBounds(32, 86, 1292, 49);
		
		Label lblSimulationMessage = new Label(shlPageframe, SWT.NONE);
		lblSimulationMessage.setBounds(32, 64, 159, 15);
		lblSimulationMessage.setText("SIMULATION MESSAGE:");
		
		btnRefresh = new Button(shlPageframe, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				phy_mem = new pMemory_Space(16,1);
				phy_mem2 = new pMemory_SC(16,1);
				inputs = new ArrayList<String>();
				File file = new File(System.getProperty("user.dir")+"/"+"input.txt.txt");
				try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					String line = null;
					try {
						while ((line = reader.readLine()) != null) {
							inputs.add(line);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				txtPleaseClickThe.setText("The program has been set up and is ready to run!!!");
				txtTotalPageFaults.setText("TOTAL PAGE FAULTS: ");
				txtCurrentProcess.setText("CURRENT PROCESS: ");
				updateTable();
				btnStep.setEnabled(true);
				btnSimulate.setEnabled(true);
			}
			
		});
		btnRefresh.setBounds(253, 10, 75, 25);
		btnRefresh.setText("Refresh");
		
		table_1 = new Table(shlPageframe, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(547, 216, 236, 330);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		tableItem_1 = new TableItem(table_1, SWT.NONE);
		tableItem_1.setText("0");
		
		tableItem = new TableItem(table_1, SWT.NONE);
		tableItem.setText("1");
		
		tableItem_2 = new TableItem(table_1, SWT.NONE);
		tableItem_2.setText("2");
		
		tableItem_3 = new TableItem(table_1, SWT.NONE);
		tableItem_3.setText("3");
		
		tableItem_4 = new TableItem(table_1, SWT.NONE);
		tableItem_4.setText("4");
		
		tableItem_5 = new TableItem(table_1, SWT.NONE);
		tableItem_5.setText("5");
		
		tableItem_6 = new TableItem(table_1, SWT.NONE);
		tableItem_6.setText("6");
		
		tableItem_7 = new TableItem(table_1, SWT.NONE);
		tableItem_7.setText("7");
		
		tableItem_8 = new TableItem(table_1, SWT.NONE);
		tableItem_8.setText("8");
		
		tableItem_9 = new TableItem(table_1, SWT.NONE);
		tableItem_9.setText("9");
		
		tableItem_10 = new TableItem(table_1, SWT.NONE);
		tableItem_10.setText("10");
		
		tableItem_11 = new TableItem(table_1, SWT.NONE);
		tableItem_11.setText("11");
		
		tableItem_12 = new TableItem(table_1, SWT.NONE);
		tableItem_12.setText("12");
		
		tableItem_13 = new TableItem(table_1, SWT.NONE);
		tableItem_13.setText("13");
		
		tableItem_14 = new TableItem(table_1, SWT.NONE);
		tableItem_14.setText("14");
		
		tableItem_15 = new TableItem(table_1, SWT.NONE);
		tableItem_15.setText("15");
		
		Label lblPhysicalMemory = new Label(shlPageframe, SWT.NONE);
		lblPhysicalMemory.setBounds(583, 159, 121, 15);
		lblPhysicalMemory.setText("PHYSICAL MEMORY");
		
		Label lblFrame = new Label(shlPageframe, SWT.NONE);
		lblFrame.setBounds(522, 194, 55, 15);
		lblFrame.setText("Frame #");
		
		Label lblProcess = new Label(shlPageframe, SWT.NONE);
		lblProcess.setBounds(617, 194, 55, 15);
		lblProcess.setText("Process#");
		
		Label lblPage = new Label(shlPageframe, SWT.NONE);
		lblPage.setBounds(728, 194, 55, 15);
		lblPage.setText("Page#");
		
		btnSecondChance = new Button(shlPageframe, SWT.CHECK);
		btnSecondChance.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sC = true;
				txtPleaseClickThe.setText("The program will now use SECOND CHANCE ALGORITHM for page replacements.");
			}
		});
		btnSecondChance.setBounds(1189, 32, 121, 16);
		btnSecondChance.setText("Second Chance");
		
		btnLru = new Button(shlPageframe, SWT.CHECK);
		btnLru.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sC = false;
				txtPleaseClickThe.setText("The program will now use LRU ALGORITHM for page replacements.");
			}
		});
		btnLru.setBounds(1074, 32, 93, 16);
		btnLru.setText("LRU");
		

	}
	//A method that converts binary to decimal.
		private int getDecimalFromBinary(int bin){
		        
		        int dec = 0;
		        int pow = 0;
		        while(true){
		            if(bin == 0){
		                break;
		            } else {
		                int tmp = bin%10;
		                dec += tmp*Math.pow(2, pow);
		                bin = bin/10;
		                pow++;
		            }
		        }
		        return dec;
		}
		
		
		private void updateTable (){
			if (!sC ){
				if (phy_mem.frames[0]!= null){
					tableItem_1.setText( "0                     " + phy_mem.frames[0].process_num 
							+"                                      "+phy_mem.frames[0].page_num);
					tableItem_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[0].turns_not_used == 0){
						tableItem_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_1.setText("0");
					tableItem_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[1]!= null){
					tableItem.setText("1                     " + phy_mem.frames[1].process_num 
							+"                                      "+phy_mem.frames[1].page_num);
					tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[1].turns_not_used == 0){
						tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem.setText("1");
					tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[2]!= null){
					tableItem_2.setText("2                     " + phy_mem.frames[2].process_num 
							+"                                      "+phy_mem.frames[2].page_num);
					tableItem_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[2].turns_not_used == 0){
						tableItem_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else{ 
					tableItem_2.setText("2");
					tableItem_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem.frames[3]!= null){
					tableItem_3.setText("3                     " + phy_mem.frames[3].process_num 
							+"                                      "+phy_mem.frames[3].page_num);
					tableItem_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[3].turns_not_used == 0){
						tableItem_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_3.setText("3");
					tableItem_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem.frames[4]!= null){
					tableItem_4.setText("4                     " + phy_mem.frames[4].process_num 
							+"                                      "+phy_mem.frames[4].page_num);
					tableItem_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[4].turns_not_used == 0){
						tableItem_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_4.setText("4");
					tableItem_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem.frames[5]!= null){
					tableItem_5.setText("5                     " + phy_mem.frames[5].process_num 
							+"                                      "+phy_mem.frames[5].page_num);
					tableItem_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[5].turns_not_used == 0){
						tableItem_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_5.setText("5");
					tableItem_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem.frames[6]!= null){
					tableItem_6.setText("6                     " + phy_mem.frames[6].process_num 
							+"                                      "+phy_mem.frames[6].page_num);
					tableItem_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[6].turns_not_used == 0){
						tableItem_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_6.setText("6");
					tableItem_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[7]!= null){
					tableItem_7.setText("7                     " + phy_mem.frames[7].process_num 
							+"                                      "+phy_mem.frames[7].page_num);
					tableItem_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[7].turns_not_used == 0){
						tableItem_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_7.setText("7");
					tableItem_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem.frames[8]!= null){
					tableItem_8.setText("8                     " + phy_mem.frames[8].process_num 
							+"                                      "+phy_mem.frames[8].page_num);
					tableItem_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[8].turns_not_used == 0){
						tableItem_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else{ 
					tableItem_8.setText("8");
					tableItem_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem.frames[9]!= null){
					tableItem_9.setText("9                     " + phy_mem.frames[9].process_num 
							+"                                      "+phy_mem.frames[9].page_num);
					tableItem_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[9].turns_not_used == 0){
						tableItem_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_9.setText("9");
					tableItem_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[10]!= null){
					tableItem_10.setText("10                    " + phy_mem.frames[10].process_num 
							+"                                      "+phy_mem.frames[10].page_num);
					tableItem_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[10].turns_not_used == 0){
						tableItem_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_10.setText("10");
					tableItem_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[11]!= null){
					tableItem_11.setText("11                    " + phy_mem.frames[11].process_num 
							+"                                      "+phy_mem.frames[11].page_num);
					tableItem_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[11].turns_not_used == 0){
						tableItem_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_11.setText("11");
					tableItem_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[12]!= null){
					tableItem_12.setText("12                    " + phy_mem.frames[12].process_num 
							+"                                      "+phy_mem.frames[12].page_num);
					tableItem_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[12].turns_not_used == 0){
						tableItem_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_12.setText("12");
					tableItem_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[13]!= null){
					tableItem_13.setText("13                    " + phy_mem.frames[13].process_num 
							+"                                      "+phy_mem.frames[13].page_num);
					tableItem_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[13].turns_not_used == 0){
						tableItem_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_13.setText("13");
					tableItem_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[14]!= null){
					tableItem_14.setText("14                    " + phy_mem.frames[14].process_num 
							+"                                      "+phy_mem.frames[14].page_num);
					tableItem_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[14].turns_not_used == 0){
						tableItem_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else{ 
					tableItem_14.setText("14");
					tableItem_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem.frames[15]!= null){
					tableItem_15.setText("15                    " + phy_mem.frames[15].process_num 
							+"                                      "+phy_mem.frames[15].page_num);
					tableItem_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem.frames[15].turns_not_used == 0){
						tableItem_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_15.setText("15");
					tableItem_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
			}
			else{
				if (phy_mem2.frames[0]!= null){
					tableItem_1.setText( "0                     " + phy_mem2.frames[0].process_num 
							+"                                      "+phy_mem2.frames[0].page_num);
					tableItem_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[0].turns_not_used == 0){
						tableItem_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_1.setText("0");
					tableItem_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[1]!= null){
					tableItem.setText("1                     " + phy_mem2.frames[1].process_num 
							+"                                      "+phy_mem2.frames[1].page_num);
					tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[1].turns_not_used == 0){
						tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem.setText("1");
					tableItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[2]!= null){
					tableItem_2.setText("2                     " + phy_mem2.frames[2].process_num 
							+"                                      "+phy_mem2.frames[2].page_num);
					tableItem_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[2].turns_not_used == 0){
						tableItem_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else{ 
					tableItem_2.setText("2");
					tableItem_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem2.frames[3]!= null){
					tableItem_3.setText("3                     " + phy_mem2.frames[3].process_num 
							+"                                      "+phy_mem2.frames[3].page_num);
					tableItem_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[3].turns_not_used == 0){
						tableItem_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_3.setText("3");
					tableItem_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem2.frames[4]!= null){
					tableItem_4.setText("4                     " + phy_mem2.frames[4].process_num 
							+"                                      "+phy_mem2.frames[4].page_num);
					tableItem_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[4].turns_not_used == 0){
						tableItem_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_4.setText("4");
					tableItem_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem2.frames[5]!= null){
					tableItem_5.setText("5                     " + phy_mem2.frames[5].process_num 
							+"                                      "+phy_mem2.frames[5].page_num);
					tableItem_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[5].turns_not_used == 0){
						tableItem_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_5.setText("5");
					tableItem_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem2.frames[6]!= null){
					tableItem_6.setText("6                     " + phy_mem2.frames[6].process_num 
							+"                                      "+phy_mem2.frames[6].page_num);
					tableItem_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[6].turns_not_used == 0){
						tableItem_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_6.setText("6");
					tableItem_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[7]!= null){
					tableItem_7.setText("7                     " + phy_mem2.frames[7].process_num 
							+"                                      "+phy_mem2.frames[7].page_num);
					tableItem_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[7].turns_not_used == 0){
						tableItem_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_7.setText("7");
					tableItem_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem2.frames[8]!= null){
					tableItem_8.setText("8                     " + phy_mem2.frames[8].process_num 
							+"                                      "+phy_mem2.frames[8].page_num);
					tableItem_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[8].turns_not_used == 0){
						tableItem_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else{ 
					tableItem_8.setText("8");
					tableItem_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				
				if (phy_mem2.frames[9]!= null){
					tableItem_9.setText("9                     " + phy_mem2.frames[9].process_num 
							+"                                      "+phy_mem2.frames[9].page_num);
					tableItem_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[9].turns_not_used == 0){
						tableItem_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_9.setText("9");
					tableItem_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[10]!= null){
					tableItem_10.setText("10                    " + phy_mem2.frames[10].process_num 
							+"                                      "+phy_mem2.frames[10].page_num);
					tableItem_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[10].turns_not_used == 0){
						tableItem_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_10.setText("10");
					tableItem_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[11]!= null){
					tableItem_11.setText("11                    " + phy_mem2.frames[11].process_num 
							+"                                      "+phy_mem2.frames[11].page_num);
					tableItem_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[11].turns_not_used == 0){
						tableItem_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_11.setText("11");
					tableItem_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[12]!= null){
					tableItem_12.setText("12                    " + phy_mem2.frames[12].process_num 
							+"                                      "+phy_mem2.frames[12].page_num);
					tableItem_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[12].turns_not_used == 0){
						tableItem_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_12.setText("12");
					tableItem_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[13]!= null){
					tableItem_13.setText("13                    " + phy_mem2.frames[13].process_num 
							+"                                      "+phy_mem2.frames[13].page_num);
					tableItem_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[13].turns_not_used == 0){
						tableItem_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_13.setText("13");
					tableItem_13.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[14]!= null){
					tableItem_14.setText("14                    " + phy_mem2.frames[14].process_num 
							+"                                      "+phy_mem2.frames[14].page_num);
					tableItem_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[14].turns_not_used == 0){
						tableItem_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else{ 
					tableItem_14.setText("14");
					tableItem_14.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
				if (phy_mem2.frames[15]!= null){
					tableItem_15.setText("15                    " + phy_mem2.frames[15].process_num 
							+"                                      "+phy_mem2.frames[15].page_num);
					tableItem_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					if (phy_mem2.frames[15].turns_not_used == 0){
						tableItem_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
				}else {
					tableItem_15.setText("15");
					tableItem_15.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				}
			}
		}	
}
