package analisador.util;

import java.util.HashMap;

import analisador.domain.Simbolo;

public class HashEvent {
	
		public void afterInsert(HashMap<Integer, Simbolo> hashTableToInsert, Simbolo value, int indexOfHashValue) {
			
		}

		public void beforeInsert(HashMap<Integer, Simbolo> hashTableToInsert, Simbolo value, int indexOfHashValue) {
			// A��o ap�s o metodo de inser��o
		}

		public void beforeSearch(Integer key) {
			// A��o antes o metodo de busca
		}
		
		public void beforeSearch(Simbolo value) {
			// A��o antes o metodo de busca
		}
		
		public void afterSearch(Integer key, Simbolo valueLocalized) {
			// A��o ap�s o metodo de busca
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
