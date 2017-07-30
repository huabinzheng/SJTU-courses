package com.sjtu.Controllers;

import com.sjtu.Services.ImportTextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by liulo on 2017/6/30.
 */
@RestController
@RequestMapping("/seckill")
public class ImportTextController {
    private static final Logger logger = LoggerFactory.getLogger(ImportTextController.class);

    @Autowired
    private ImportTextService importTextService;

    @RequestMapping("/import")
    public String importText() {
        logger.info("将txt文件导入数据库");
        importTextService.clearDB();
        String dataPath = "data.txt";
        try {
            return importTextService.importText(dataPath);
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    @RequestMapping("/clear")
    public String clearDB() {
         logger.info("清空用户user和商品commodity数据");
         return importTextService.clearDB();
    }
}
