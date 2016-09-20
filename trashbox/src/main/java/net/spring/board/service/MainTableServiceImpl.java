package net.spring.board.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.spring.board.dao.MainTableDao;
import net.spring.board.vo.MainTable;

@Service("MainTableService")
public class MainTableServiceImpl implements MainTableService {
  protected Logger log = LogManager.getLogger(MainTableServiceImpl.class);

  @Autowired
  MainTableDao mainTableDao;
  
  
  public List<MainTable> getNoticeList() throws Exception {
    
    return mainTableDao.noticeList();
    
  }
  
  
  public List<MainTable> getNewestList() throws Exception {
    
    return mainTableDao.newestList();
  
  }


  public List<MainTable> getWeekList() throws Exception {
    
    return mainTableDao.weekList();
  
  }


  public List<MainTable> getMonthList() throws Exception {
    
    return mainTableDao.monthList();
  
  }
  
  
  
  public List<MainTable> getImageList(int imgPage) throws Exception {
    List<MainTable> imageList = mainTableDao.imageList(imgPage);
    MainTable mainTable = null;
    
    log.debug("getImageList = " + imageList);
    
    int count = imageList.size();
    
    for (int i = 0; i < count; i++) {
     
      mainTable = imageList.get(i);
      
      String temp = "";
      temp = mainTable.getContent().substring(mainTable.getContent().indexOf("<img"), mainTable.getContent().lastIndexOf("/>")+2);      
      
      mainTable.setContent(temp);
     
      imageList.set(i, mainTable);

    }
    
    return imageList;
    
  }

}
