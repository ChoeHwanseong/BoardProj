package service;

import java.util.Map;

public interface DaoService<T> {
	void close();
	int insert(T dto);
	int update(T dto);
	int delete(T dto);
	int getTotalRecordCount(Map<String, String> map);
	
}
