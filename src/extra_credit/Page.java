/*
 * author: Aditya Iyengar
 */
package extra_credit;

public class Page {
	int process_num;
	int page_num;
	int turns_not_used;
	int second_chance_bit;
	
	public Page(int process_num2, int page_num2) {
		process_num = process_num2;
		page_num = page_num2;
		turns_not_used = 0;
		second_chance_bit = 1;
	}
}
