package analisador.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

public class TableSymbols extends HashEvent {
	
	private static HashMap<Integer, String> hashTable;
	private boolean inicialized;

	public TableSymbols() {
		this.setInicialized(true);
		hashTable = new HashMap<Integer, String>();
	}
	
	// -------- GET
	public String getValueByKey(Integer key) {
		if(ValueUtil.isEmpty(key)) {
			return null;
		}
		beforeSearch(key);
		String valueLocalized = hashTable.get(key);
		afterSearch(key, valueLocalized);
		return valueLocalized;
	}

	public Integer getKeyByValue(String value) {
		if(ValueUtil.isEmpty(value)) {
			return null;
		}
		beforeSearch(value);
		Entry<Integer, String> valueLocalized = getValue(hashTable, value);
		if(valueLocalized == null) return null;
		Integer keyLocalized = valueLocalized.getKey();
		afterSearch(keyLocalized, value);
		return keyLocalized;
	}
	
	public String getValue(String value) {
		if(ValueUtil.isEmpty(value)) {
			return null;
		}
		Entry<Integer, String> entryGet = getValue(hashTable, value);
		if (entryGet == null) {
			return null;
		}
		return entryGet.getValue();
	}
	
	public static <T, E> Entry<T, E> getValue(HashMap<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (!Objects.equals(value, entry.getValue())) continue;
			return entry;
		}
		return null;
	}
	
	// -------- INSERT
	public boolean insertValue(String value) {
		if(ValueUtil.isEmpty(value)) {
			return false;
		}
		return doInsert(hashTable, value, MathUtil.getHashValue(value, hashTable));
	}
	
	private boolean doInsert(HashMap<Integer, String> hashTableToInsert, String value, int indexOfHashValue) {
		if(ValueUtil.isEmpty(hashTableToInsert) || ValueUtil.isEmpty(value)) {
			return false;
		}
		beforeInsert(hashTableToInsert, value, indexOfHashValue);
		hashTableToInsert.put(indexOfHashValue, value);
		afterInsert(hashTableToInsert, value, indexOfHashValue);
		return true;
	}
	
	// -------- DELETE
	public boolean deleteByValue(String value) {
		if(ValueUtil.isEmpty(value)) {
			return false;
		}
		Integer key = getKeyByValue(value);
		if(key == null) {
			return false;
		}
		return deleteByKey(key);
	}
	
	public boolean delete(Entry<Integer, String> hashEntry) {
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
		String previousValue = hashTable.remove(key);
		afterDelete(key, previousValue);
		return ValueUtil.isNotEmpty(previousValue) ? true : false;
		
	}
	
	//ALTERAÇÃO
	public boolean alterByKey(Integer key, String newValue) {
		if(ValueUtil.isEmpty(key) || ValueUtil.isEmpty(newValue) || ValueUtil.isEmpty(getValueByKey(key))) {
			return false;
		}
		beforeAlter(key, newValue);
		String oldValue = hashTable.put(key, newValue);
		afterAlter(key, newValue, oldValue);
		return oldValue != null;
	}

	// ---------- GET SET
	public boolean isInicialized() {
		return inicialized;
	}

	public void setInicialized(boolean inicialized) {
		this.inicialized = inicialized;
	}
	
	public HashMap<Integer, String> getHashTable() {
		return TableSymbols.hashTable;
	}
	
}
