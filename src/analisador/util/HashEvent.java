package analisador.util;

import java.util.HashMap;

import analisador.domain.Simbolo;

public class HashEvent {
	
		public void afterInsert(HashMap<Integer, Simbolo> hashTableToInsert, Simbolo value, int indexOfHashValue) {
			
		}

		public void beforeInsert(HashMap<Integer, Simbolo> hashTableToInsert, Simbolo value, int indexOfHashValue) {
			// Ação após o metodo de inserção
		}

		public void beforeSearch(Integer key) {
			// Ação antes o metodo de busca
		}
		
		public void beforeSearch(Simbolo value) {
			// Ação antes o metodo de busca
		}
		
		public void afterSearch(Integer key, Simbolo valueLocalized) {
			// Ação após o metodo de busca
		}
		
		public void beforeDelete(Integer key) {
			// TODO Auto-generated method stub
		}
		
		public void afterDelete(Integer key, Simbolo previousValue) {
			// TODO Auto-generated method stub
		}
		
		public void beforeAlter(Integer key, Simbolo newValue) {
			// TODO Auto-generated method stub
		}

		public void afterAlter(Integer key, Simbolo newValue, Simbolo oldValue) {
			// TODO Auto-generated method stub
		}
		

}
