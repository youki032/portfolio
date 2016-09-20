package net.spring.board.service;

import java.util.List;

import net.spring.board.vo.MainTable;

public interface MainTableService {
  
  public List<MainTable> getNoticeList() throws Exception;
  
  public List<MainTable> getNewestList() throws Exception;
  
  public List<MainTable> getWeekList() throws Exception;
  
  public List<MainTable> getMonthList() throws Exception;
  
  public List<MainTable> getImageList(int imgPage) throws Exception;

}
