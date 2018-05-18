package analisador.util;

import java.util.HashMap;

public class HashEvent {
	
		public void afterInsert(HashMap<Integer, String> hashTableToInsert, String value, int indexOfHashValue) {
			
		}

		public void beforeInsert(HashMap<Integer, String> hashTableToInsert, String value, int indexOfHashValue) {
			// Ação após o metodo de inserção
		}

		public void beforeSearch(Integer key) {
			// Ação antes o metodo de busca
		}
		
		public void beforeSearch(String value) {
			// Ação antes o metodo de busca
		}
		
		public void afterSearch(Integer key, String valueLocalized) {
			// Ação após o metodo de busca
		}
		
		public void beforeDelete(Integer key) {
			// TODO Auto-generated method stub
		}
		
		public void afterDelete(Integer key, String previousValue) {
			// TODO Auto-generated method stub
		}
		
		public void beforeAlter(Integer key, String newValue) {
			// TODO Auto-generated method stub
		}

		public void afterAlter(Integer key, String newValue, String oldValue) {
			// TODO Auto-generated method stub
		}
		

}
