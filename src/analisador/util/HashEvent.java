package analisador.util;

import java.util.HashMap;

public class HashEvent {
	
		public void afterInsert(HashMap<Integer, String> hashTableToInsert, String value, int indexOfHashValue) {
			
		}

		public void beforeInsert(HashMap<Integer, String> hashTableToInsert, String value, int indexOfHashValue) {
			// A��o ap�s o metodo de inser��o
		}

		public void beforeSearch(Integer key) {
			// A��o antes o metodo de busca
		}
		
		public void beforeSearch(String value) {
			// A��o antes o metodo de busca
		}
		
		public void afterSearch(Integer key, String valueLocalized) {
			// A��o ap�s o metodo de busca
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
