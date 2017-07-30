# Simple-Seckill-System
The project for a school course.

Although the result is not satisfying, but by rebuilding this project with the team, I learned a lot about Spring boot and using Git to do the team work.

The author can be seen in the head comment.

## APIs
* http://hostname/seckill/getUserById?user_id=xxx
* http://hostname/seckill/getUserAll
* http://hostname/seckill/getCommodityById?commodity_id=xxx
* http://hostname/seckill/getCommodityAll
* http://hostname/seckill/seckill?user_id=xxx1&commodity_id=xxx2
* http://hostname/seckill/getOrderById?order_id=xxx
* http://hostname/seckill/getOrderAll

## Architecture
* Controller
  - Spring boot @RequestMapping
* Service
  - Spring boot
  - the 3rd iteration added: EHCache @CachePut @CacheEvict
* Repository
  - the 2nd iteration: Spring JPA
  - the 1st iteration: JDBCtemplate (be replaced later)
* Object
  * Hibernate annotation, MySQL
 
 # Class Design
 * `SuccessSeckillResult` implements `BaseSeckillResult` 
