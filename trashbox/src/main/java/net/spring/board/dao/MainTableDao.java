package net.spring.board.dao;

import java.util.List;

import net.spring.board.vo.MainTable;

public interface MainTableDao {
  
  List<MainTable> noticeList() throws Exception;
  
  List<MainTable> newestList() throws Exception;
  
  List<MainTable> weekList() throws Exception;
  
  List<MainTable> monthList() throws Exception;
  
  List<MainTable> imageList(int imgPage) throws Exception;
  
}
