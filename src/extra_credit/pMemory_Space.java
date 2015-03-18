/*
 * author: Aditya Iyengar
 */
package extra_credit;

public class pMemory_Space {
	int num_frames;
	Page[] frames;
	int num_pages;
	int page_faults;
	boolean page_fault = false;
	Page removed;
	boolean enoughSpace = false;
	
	public pMemory_Space(int mem_size, int frame_size){
		num_frames = mem_size/frame_size;
		frames = new Page[num_frames];
		num_pages = 0;
		page_faults = 0;
	}
	
	public void access_frame (int process_num, int page_num){
		page_fault = true;
		for (int i = 0; i < num_pages; i++){
			if (frames[i].page_num == page_num && frames[i].process_num == process_num){
				page_fault = false;
				frames[i].turns_not_used = 0;
			}
			else{
				frames[i].turns_not_used ++;
			}
		}
		if (page_fault){
			page_faults++;
			add_page(process_num, page_num);
		}
	}

	private void add_page(int process_num, int page_num) {
		Page new_page = new Page(process_num, page_num);
		if (num_pages < num_frames){
			frames[num_pages] = new_page;
			num_pages++;
			enoughSpace = true;
		}
		else{
			int max_unused = 0;
			int index = 0;
			for (int i = 0; i < num_frames; i++){
				if ( frames[i].turns_not_used > max_unused){
					max_unused = frames[i].turns_not_used;
					index = i;
				}
			}
			removed = frames[index];
			frames[index] = new_page;
			enoughSpace = false;
		}
		
	}
	
	public String to_String (){
		String out = "Physical Memory: ";
		for (int i = 0; i < num_frames; i++){
			if (i < num_pages){
				out += "| "+ frames[i].process_num + "-->" + frames[i].page_num;  
			}
			else{
				out += " | empty frame";
			}
		}
		out += "|\n";
		return out;
	}
}
