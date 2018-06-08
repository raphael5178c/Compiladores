package analisador.util;

import java.util.HashMap;
import java.util.Map.Entry;

import analisador.domain.Simbolo;

public class TableSymbols extends HashEvent {
	
	private static HashMap<Integer, Simbolo> hashTable;
	private boolean inicialized;
	public static int qtValuesInserted;

	public TableSymbols() {
		this.setInicialized(true);
		hashTable = new HashMap<Integer, Simbolo>();
	}
	
	// -------- GET
	public Simbolo getValueByKey(Integer key) {
		if(ValueUtil.isEmpty(key)) {
			return null;
		}
		beforeSearch(key);
		Simbolo valueLocalized = hashTable.get(key);
		afterSearch(key, valueLocalized);
		return valueLocalized;
	}

	public Integer getKeyByValue(Simbolo value) {
		if(ValueUtil.isEmpty(value)) {
			return null;
		}
		beforeSearch(value);
		Entry<Integer, Simbolo> valueLocalized = getValue(hashTable, value);
		if(valueLocalized == null) return null;
		Integer keyLocalized = valueLocalized.getKey();
		afterSearch(keyLocalized, value);
		return keyLocalized;
	}
	
	public Simbolo getValue(Simbolo value) {
		if(ValueUtil.isEmpty(value)) {
			return null;
		}
		Entry<Integer, Simbolo> entryGet = getValue(hashTable, value);
		if (entryGet == null) {
			return null;
		}
		return entryGet.getValue();
	}
	
	public static <T, E> Entry<T, E> getValue(HashMap<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (!value.equals(entry.getValue())) continue;
			return entry;
		}
		return null;
	}
	
	public Simbolo getByQtValueInsertedNumber(int number) {
		for (Entry<Integer, Simbolo> entry : getHashTable().entrySet()) {
			if(entry.getValue().getQtValuesPilha() == number) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	// -------- INSERT
	public boolean insertValue(Simbolo value) {
		if(ValueUtil.isEmpty(value)) {
			return false;
		}
		return doInsert(hashTable, value, MathUtil.getHashValue(value.getNomeSimbolo()));
	}
	
	private boolean doInsert(HashMap<Integer, Simbolo> hashTableToInsert, Simbolo value, int indexOfHashValue) {
		if(ValueUtil.isEmpty(hashTableToInsert) || ValueUtil.isEmpty(value)) {
			return false;
		}
		beforeInsert(hashTableToInsert, value, indexOfHashValue);
		value.setQtValuesPilha(qtValuesInserted);
		Simbolo inserted = hashTableToInsert.put(indexOfHashValue, value);
		qtValuesInserted++;
		afterInsert(hashTableToInsert, value, indexOfHashValue);
		return true;
	}
	
	// -------- DELETE
	public boolean deleteByValue(Simbolo value) {
		if(ValueUtil.isEmpty(value)) {
			return false;
		}
		Integer key = getKeyByValue(value);
		if(key == null) {
			return false;
		}
		return deleteByKey(key);
	}
	
	public boolean delete(Entry<Integer, Simbolo> hashEntry) {
		if(ValueUtil.isEmpty(hashEntry)) {
			return false;
		}
		return deleteByKey(hashEntry.getKey());		
	}
	
	public boolean deleteByKey(Integer key) {
		if(ValueUtil.isEmpty(key)) {
			return false;
		}
		beforeDelete(key);
		Simbolo previousValue = hashTable.remove(key);
		if(previousValue != null) {
			qtValuesInserted--;
		}
		afterDelete(key, previousValue);
		return ValueUtil.isNotEmpty(previousValue) ? true : false;
		
	}
	
	//ALTERAÇÃO
	public boolean alterByKey(Integer key, Simbolo newValue) {
		if(ValueUtil.isEmpty(key) || ValueUtil.isEmpty(newValue) || ValueUtil.isEmpty(getValueByKey(key))) {
			return false;
		}
		beforeAlter(key, newValue);
		Simbolo oldValue = hashTable.put(key, newValue);
		afterAlter(key, newValue, oldValue);
		return oldValue != null;
	}
	
	//GET SIZE
	public int size() {
		return (hashTable != null) ? hashTable.size() : 0;
	}

	// ---------- GET SET
	public boolean isInicialized() {
		return inicialized;
	}

	public void setInicialized(boolean inicialized) {
		this.inicialized = inicialized;
	}
	
	public static HashMap<Integer, Simbolo> getHashTable() {
		return TableSymbols.hashTable;
	}
	
}
